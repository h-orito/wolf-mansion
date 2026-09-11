#!/usr/bin/env node
/**
 * april20250401.html / april20250402.html (2ch スレ風アーカイブ) から投稿データを抽出し、
 * TypeScript データファイルを生成する。開発時の一度きりの実行を想定。
 * 生成物は `app/routes/archives/april-2025040{1,2}/posts.ts`。
 *
 * 各投稿は `~/features/archives/posts` の AprilPost (セグメント列)。
 * - レスアンカー (`#resN`) は { anchor, target } セグメント
 * - 内部リンク (`th:href="@{...}"`) は { to, text } セグメント (SPA リンクとして描画)
 * - ログイン状態依存リンク・村一覧・村作成は { kind } マーカー (画面側で動的描画)
 */
import { execFileSync } from "node:child_process";
import { readFileSync, writeFileSync, mkdirSync } from "node:fs";
import { resolve, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const templatesDir = resolve(__dirname, "../../backend/src/main/resources/templates");

const PAGES = [
  { template: "april20250401.html", out: "april-20250401" },
  { template: "april20250402.html", out: "april-20250402" },
];

// <br> 置換用と動的ブロックマーカー用の番兵 (原文に現れない制御文字)
const BR = "\u0000";
const MARK = "\u0001";

// 開きタグ tagName の startIndex から対応する閉じタグまでを depth 計数で切り出す
function sliceElement(html, startIndex, tagName) {
  const re = new RegExp(`<${tagName}[\\s>]|</${tagName}>`, "g");
  re.lastIndex = startIndex;
  let depth = 0;
  for (let m; (m = re.exec(html)); ) {
    if (m[0].startsWith(`<${tagName}`)) {
      depth++;
    } else {
      depth--;
      if (depth === 0) {
        const openEnd = html.indexOf(">", startIndex) + 1;
        return {
          openTag: html.slice(startIndex, openEnd),
          inner: html.slice(openEnd, m.index),
          end: m.index + m[0].length,
        };
      }
    }
  }
  throw new Error(`unbalanced <${tagName}>`);
}

// fragment 直下の <div> 要素を列挙する
function topLevelDivs(fragment) {
  const items = [];
  const re = /<div[\s>]/g;
  for (let m; (m = re.exec(fragment)); ) {
    const el = sliceElement(fragment, m.index, "div");
    items.push(el);
    re.lastIndex = el.end;
  }
  return items;
}

function decodeEntities(text) {
  return text
    .replaceAll("&lt;", "<")
    .replaceAll("&gt;", ">")
    .replaceAll("&quot;", '"')
    .replaceAll("&#39;", "'")
    .replaceAll("&amp;", "&");
}

/**
 * HTML 片をテキストへ。<br> は \n、それ以外の空白連続 (ASCII) はブラウザ描画と同じく
 * 1 個の半角スペースに潰す (全角スペースは AA の一部なので保持)。
 */
function toText(fragment) {
  let text = fragment.replace(/<br\s*\/?>/g, BR);
  if (/[<>]/.test(text)) {
    throw new Error(`unexpected markup remains: ${text.slice(0, 120)}`);
  }
  text = decodeEntities(text);
  text = text.replace(/[ \t\r\n]+/g, " ");
  text = text.replace(new RegExp(` ?${BR} ?`, "g"), "\n");
  return text;
}

// 本文 HTML をセグメント列に変換する
function toSegments(bodyHtml) {
  let html = bodyHtml;

  // 動的ブロックをマーカーに置換 (テンプレートの条件分岐・繰り返しは画面側で動的描画する)
  html = html.replace(
    /<div th:if="\$\{user != null\}">[\s\S]*?<\/div>\s*<div th:if="\$\{user == null\}">[\s\S]*?<\/div>/,
    `${MARK}authLinks${MARK}`,
  );
  html = html.replace(
    /<th:block th:each="village[\s\S]*?<\/th:block>/,
    `${MARK}villageList${MARK}`,
  );
  html = html.replace(
    /<span th:if="\$\{user != null && !content\.participate\}">[\s\S]*?<\/span>/,
    `${MARK}createVillageLink${MARK}`,
  );

  const segments = [];
  const pushText = (fragment) => {
    const text = toText(fragment);
    if (text !== "" && text !== " ") segments.push({ text });
  };

  // レスアンカー / 内部リンク / 動的マーカーで分割
  const tokenRe = new RegExp(
    [
      '<a href="#(res\\d+)"[^>]*><u>&gt;&gt;(\\d+)</u></a>',
      '<a [^>]*th:href="@\\{([^}(]+)(?:\\([^)]*\\))?\\}"[^>]*><u>([\\s\\S]*?)</u></a>',
      `${MARK}(\\w+)${MARK}`,
    ].join("|"),
    "g",
  );
  let last = 0;
  for (let m; (m = tokenRe.exec(html)); ) {
    pushText(html.slice(last, m.index));
    if (m[1] != null) {
      segments.push({ anchor: Number(m[2]), target: m[1] });
    } else if (m[3] != null) {
      segments.push({ to: m[3], text: toText(m[4]).trim() });
    } else {
      segments.push({ kind: m[5] });
    }
    last = m.index + m[0].length;
  }
  pushText(html.slice(last));

  // ブロック端の整形用スペースはブラウザ描画では消えるので落とす
  const first = segments[0];
  if (first != null && "text" in first && first.to == null) {
    first.text = first.text.replace(/^ /, "");
  }
  const lastSeg = segments[segments.length - 1];
  if (lastSeg != null && "text" in lastSeg && lastSeg.to == null) {
    lastSeg.text = lastSeg.text.replace(/ $/, "");
  }
  return segments;
}

function parsePost(el) {
  const anchorId = /id="(res\d+)"/.exec(el.openTag)?.[1];
  const children = topLevelDivs(el.inner);
  const header = children[0];
  if (!header) throw new Error(`no header div: ${el.inner.slice(0, 80)}`);

  // ID が赤 span で囲まれている投稿はスレ主
  const isOwner = /ID:<span style="color: red;">/.test(header.inner);
  const headerText = toText(
    header.inner.replace(/<span[^>]*>/g, "").replaceAll("</span>", ""),
  ).trim();
  const m = /^(\d+) ?: (.+?)(?: : (.+) ID:(\S+))?$/.exec(headerText);
  if (!m) throw new Error(`unparsable header: ${headerText}`);
  const [, no, name, datetime, posterId] = m;

  const bodyMatch = /<(p|div) style="padding-left: 20px;">/.exec(el.inner);
  if (!bodyMatch) throw new Error(`no body element: ${el.inner.slice(0, 80)}`);
  const bodyEl = sliceElement(el.inner, bodyMatch.index, bodyMatch[1]);

  const post = { no: Number(no), name };
  if (datetime) {
    post.datetime = datetime;
    post.posterId = posterId;
    if (isOwner) post.isOwner = true;
  }
  if (anchorId) post.anchorId = anchorId;
  post.bodyTag = bodyMatch[1];
  post.body = toSegments(bodyEl.inner);
  return post;
}

for (const page of PAGES) {
  const html = readFileSync(resolve(templatesDir, page.template), "utf-8");

  const boxStart = html.indexOf('<div class="aahub_light"');
  if (boxStart < 0) throw new Error(`aahub_light not found: ${page.template}`);
  const box = sliceElement(html, boxStart, "div");

  const titleMatch = /<p style="color: red; font-size: 16px;">(.*?)<\/p>/.exec(box.inner);
  if (!titleMatch) throw new Error(`thread title not found: ${page.template}`);
  const threadTitle = decodeEntities(titleMatch[1]);

  const posts = topLevelDivs(box.inner.slice(titleMatch.index + titleMatch[0].length)).map(
    parsePost,
  );
  if (posts.length === 0) throw new Error(`no posts extracted: ${page.template}`);

  const banner = `// ${page.template} から scripts/extract-april-threads.mjs で生成したスレッドデータ。
import type { AprilPost } from "~/features/archives/posts";

export const threadTitle = ${JSON.stringify(threadTitle)};

export const posts: AprilPost[] = `;

  const outDir = resolve(__dirname, `../app/routes/archives/${page.out}`);
  mkdirSync(outDir, { recursive: true });
  const outPath = resolve(outDir, "posts.ts");
  writeFileSync(outPath, banner + JSON.stringify(posts, null, 2) + ";\n");
  // commit 済み生成物と diff が出ないよう、生成のたびにリポジトリの整形を通す
  execFileSync("pnpm", ["exec", "oxfmt", "--write", outPath], {
    cwd: resolve(__dirname, ".."),
    stdio: "inherit",
  });
  console.log(`generated ${outPath} (${posts.length} posts)`);
}

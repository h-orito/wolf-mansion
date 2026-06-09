#!/usr/bin/env node
/**
 * announce.html からリリースノートを抽出し、TypeScript データファイルを生成する。
 * 開発時の一度きりの実行を想定。生成物は `app/routes/announce/releases.ts`。
 *
 * 各エントリは lead (日付 + 概要) と items (変更点リスト) のセグメント列。
 * 内部リンク (`th:href="@{...}"`) は { to, text } セグメントとして保持し、
 * 画面側で SPA リンクとして描画する。
 */
import { readFileSync, writeFileSync } from "node:fs";
import { resolve, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const htmlPath = resolve(__dirname, "../../backend/src/main/resources/templates/announce.html");
const outPath = resolve(__dirname, "../app/routes/announce/releases.ts");

const html = readFileSync(htmlPath, "utf-8");

// <ul class="announce"> の中身 (対応する閉じタグまで) を depth 計数で切り出す
const listStart = html.indexOf('<ul class="announce">');
if (listStart < 0) throw new Error("ul.announce not found");
let depth = 0;
let listEnd = -1;
const ulRe = /<ul[\s>]|<\/ul>/g;
ulRe.lastIndex = listStart;
for (let m; (m = ulRe.exec(html)); ) {
  depth += m[0].startsWith("<ul") ? 1 : -1;
  if (depth === 0) {
    listEnd = m.index;
    break;
  }
}
if (listEnd < 0) throw new Error("unbalanced ul.announce");
const listHtml = html.slice(listStart + '<ul class="announce">'.length, listEnd);

// トップレベル <li> を depth 計数で列挙 (ネストは 1 段のみの前提)
function topLevelItems(fragment) {
  const items = [];
  const re = /<li[\s>]|<\/li>/g;
  let liDepth = 0;
  let start = -1;
  for (let m; (m = re.exec(fragment)); ) {
    if (m[0].startsWith("<li")) {
      liDepth++;
      if (liDepth === 1) start = m.index + m[0].length;
    } else {
      liDepth--;
      if (liDepth === 0) items.push(fragment.slice(start, m.index));
    }
  }
  if (liDepth !== 0) throw new Error("unbalanced li");
  return items;
}

function decodeEntities(text) {
  return text
    .replaceAll("&#091;", "[")
    .replaceAll("&#093;", "]")
    .replaceAll("&lt;", "<")
    .replaceAll("&gt;", ">")
    .replaceAll("&quot;", '"')
    .replaceAll("&#39;", "'")
    .replaceAll("&amp;", "&");
}

// 行をまたぐ整形用の空白は除去して 1 行に潰す (ブラウザの CJK 間 segment break 除去と同じ見た目)
function normalize(text) {
  return decodeEntities(
    text
      .split("\n")
      .map((line) => line.trim())
      .join("")
      .trim(),
  );
}

// HTML 片をセグメント列 (テキスト | 内部リンク) に変換する
function toSegments(fragment) {
  const segments = [];
  const anchorRe = /<a\b[^>]*th:href="@\{([^}]+)\}"[^>]*>([\s\S]*?)<\/a>/g;
  let last = 0;
  for (let m; (m = anchorRe.exec(fragment)); ) {
    const before = normalize(fragment.slice(last, m.index));
    if (before) segments.push(before);
    segments.push({ to: m[1], text: normalize(m[2]) });
    last = m.index + m[0].length;
  }
  const rest = normalize(fragment.slice(last));
  if (rest) segments.push(rest);
  for (const seg of segments) {
    const text = typeof seg === "string" ? seg : seg.text;
    if (/[<>]/.test(text)) throw new Error(`unexpected markup remains: ${text}`);
  }
  return segments;
}

const releases = topLevelItems(listHtml).map((item) => {
  const ulIdx = item.indexOf("<ul>");
  const leadHtml = ulIdx < 0 ? item : item.slice(0, ulIdx);
  const itemsHtml = ulIdx < 0 ? "" : item.slice(ulIdx);
  return {
    lead: toSegments(leadHtml),
    items: itemsHtml ? topLevelItems(itemsHtml).map(toSegments) : [],
  };
});

if (releases.length === 0) throw new Error("no releases extracted");

const banner = `// announce.html から scripts/extract-announce-releases.mjs で生成したリリースノートデータ。
// 新しいお知らせは先頭に手で追記する。

export type ReleaseSegment = string | { to: string; text: string };

export interface Release {
  /** 日付 + 概要 (例: "2026/03/13 以下を変更しました。") */
  lead: ReleaseSegment[];
  /** 変更点 */
  items: ReleaseSegment[][];
}

export const releases: Release[] = `;

writeFileSync(outPath, banner + JSON.stringify(releases, null, 2) + ";\n");
console.log(`generated ${outPath} (${releases.length} releases)`);

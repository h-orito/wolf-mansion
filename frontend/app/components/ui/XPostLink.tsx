import { useEffect, useRef } from "react";
import type { MouseEvent } from "react";

const HASHTAG = "WOLF_MANSION";

/**
 * スマホ（iOS / Android）判定。
 * スマホでは Web の投稿 intent URL を踏むと X アプリがアプリ内ブラウザで
 * 横取りして開き、別セッション扱いでログイン画面に飛ばされてしまう。
 * そのためスマホでは先にアプリ独自スキームでネイティブの投稿画面を開く。
 */
function isMobile(): boolean {
  if (typeof navigator === "undefined") return false;
  return /android|iphone|ipad|ipod/i.test(navigator.userAgent);
}

function buildWebHref(text: string, pageUrl: string): string {
  return `https://x.com/intent/post?text=${encodeURIComponent(text)}&hashtags=${HASHTAG}&url=${encodeURIComponent(pageUrl)}`;
}

/**
 * アプリスキームは本文/URL/ハッシュタグを分けて渡せないため、
 * すべて message にまとめる。
 */
function buildAppHref(text: string, pageUrl: string): string {
  const message = `${text.replace(/\n+$/, "")}\n${pageUrl}\n#${HASHTAG}`;
  return `twitter://post?message=${encodeURIComponent(message)}`;
}

/**
 * X (旧 Twitter) の投稿画面を開くリンクボタン。
 * スマホではアプリのネイティブ投稿画面（ログイン済み）を直接起動し、
 * アプリ未インストール時や PC では Web の投稿 intent にフォールバックする。
 */
export function XPostLink({ text, pageUrl }: { text: string; pageUrl: string }) {
  const webHref = buildWebHref(text, pageUrl);

  // アンマウント（SPA 内の別ページへの遷移）後にフォールバックタイマーが発火して
  // 遷移先ページごと Web intent に飛ばされないよう、後始末をクリーンアップで実行する
  const cancelFallbackRef = useRef<(() => void) | null>(null);
  useEffect(() => () => cancelFallbackRef.current?.(), []);

  const handleClick = (e: MouseEvent<HTMLAnchorElement>) => {
    if (!isMobile()) return; // PC は通常どおり別タブで intent URL を開く

    // スマホはアプリスキームでネイティブ投稿画面を起動。
    // 起動しなければ（未インストール）Web intent にフォールバックする。
    // フォールバック猶予は、iOS の「"X"で開きますか？」ダイアログの操作中も
    // タイマーが進むため、操作の余裕を持たせて 2 秒にしている。
    e.preventDefault();
    cancelFallbackRef.current?.(); // 連打時は前回のフォールバックを破棄

    const cancelFallback = () => {
      window.clearTimeout(fallbackId);
      document.removeEventListener("visibilitychange", onHide);
      cancelFallbackRef.current = null;
    };
    const onHide = () => {
      // アプリへ遷移してページが非表示になったらフォールバックを取り消す
      if (document.visibilityState === "hidden") cancelFallback();
    };
    document.addEventListener("visibilitychange", onHide);

    const fallbackId = window.setTimeout(() => {
      document.removeEventListener("visibilitychange", onHide);
      cancelFallbackRef.current = null;
      // アプリ遷移後にスロットルされたタイマーが復帰発火した場合の保険
      if (document.visibilityState === "hidden") return;
      // ジェスチャ外なので window.open はブロックされる。同一タブ遷移はやむなし
      window.location.href = webHref;
    }, 2000);
    cancelFallbackRef.current = cancelFallback;

    window.location.href = buildAppHref(text, pageUrl);
  };

  return (
    <a
      href={webHref}
      onClick={handleClick}
      target="_blank"
      rel="noreferrer"
      className="inline-flex items-center gap-[5px] rounded-full bg-black px-[12px] py-[4px] font-bold text-white hover:bg-[#333]"
    >
      <svg viewBox="0 0 24 24" className="h-[14px] w-[14px] fill-current" aria-hidden="true">
        <path d="M18.244 2.25h3.308l-7.227 8.26 8.502 11.24H16.17l-5.214-6.817L4.99 21.75H1.68l7.73-8.835L1.254 2.25H8.08l4.713 6.231zm-1.161 17.52h1.833L7.084 4.126H5.117z" />
      </svg>
      ポスト
    </a>
  );
}

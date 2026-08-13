/**
 * X (旧 Twitter) の投稿 intent を開くリンクボタン。
 * 本文とリンク先 URL を受け取り、ハッシュタグ #WOLF_MANSION を付けて投稿画面を開く。
 */
export function XPostLink({ text, pageUrl }: { text: string; pageUrl: string }) {
  const href = `https://x.com/intent/post?text=${encodeURIComponent(text)}&hashtags=WOLF_MANSION&url=${encodeURIComponent(pageUrl)}`;
  return (
    <a
      href={href}
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

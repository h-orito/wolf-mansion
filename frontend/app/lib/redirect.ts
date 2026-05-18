/**
 * `?redirect=...` の値を「同一オリジンの相対 in-app パス」に正規化する。
 * Open redirect / javascript: / protocol-relative (encoded 含む) を弾く。
 *
 * 実装方針: ダミーオリジンを base にして `new URL(raw, base)` で解釈する。
 * encoded `%2F%2Fevil` も URL コンストラクタが decode して評価するため、
 * パス文字列の prefix チェックでは見逃すケースを正しく弾ける。
 * 結果の URL の origin がダミーオリジンと一致しない (= absolute URL を含む) なら拒否。
 */
const DUMMY_ORIGIN = "http://_x_";

export function sanitizeRedirect(raw: string | null | undefined): string {
  if (!raw) return "/";
  // 制御文字 (改行・タブ等) を含む場合は拒否 (\x00-\x1F と DEL)
  if (/[\x00-\x1F\x7F]/.test(raw)) return "/";
  // 単一 `/` で始まる絶対 in-app パスのみ許可。
  // - "me" のような相対は弾く (new URL では `/me` に正規化されてしまうため別途チェック)
  // - "//evil" の protocol-relative も弾く
  if (!raw.startsWith("/") || raw.startsWith("//")) return "/";

  let url: URL;
  try {
    url = new URL(raw, DUMMY_ORIGIN);
  } catch {
    return "/";
  }
  // absolute URL / 別ホストの場合 origin が変わる
  if (url.origin !== DUMMY_ORIGIN) return "/";
  // 念のためスキーム再確認 (URL 仕様の隙間を埋める)
  if (url.protocol !== "http:" && url.protocol !== "https:") return "/";
  return (url.pathname || "/") + url.search;
}

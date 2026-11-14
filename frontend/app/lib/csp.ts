import { createContext, useContext } from "react";

/**
 * Content-Security-Policy。inline script はリクエストごとの nonce を付けたものだけを許可する。
 *
 * - 画像: キャラチップ画像は wolfort.net / wolfort.dev の両方から配信され、どちらのホストで開いても
 *   もう一方は別オリジンになるため両方を許可する。data: / blob: は画像出力 (html-to-image) が使う
 * - 接続: 画像出力が画像と Web フォントを fetch して埋め込むため、画像ホストと Google Fonts も許可する
 * - style は React の style 属性 (SSR で HTML に出る) があるため 'unsafe-inline' を許可する
 * - 広告 (AdSense): script は 'strict-dynamic' で nonce 付き script から読み込まれるものとして許可される。
 *   広告枠の iframe・計測の通信・画像は Google の複数ドメインに出るため、ドメイン単位で許可する
 * - script-src の https: と 'unsafe-inline' は 'strict-dynamic' 非対応ブラウザ向けの代替。
 *   対応ブラウザでは nonce / 'strict-dynamic' があると無視される
 */
export function buildContentSecurityPolicy(nonce: string): string {
  // placehold.jp は画像未登録のオリジナルキャラに backend が入れる仮画像
  const imageHosts = ["https://wolfort.net", "https://wolfort.dev", "https://placehold.jp"];
  const fontCss = "https://fonts.googleapis.com";
  const fontFiles = "https://fonts.gstatic.com";
  const adHosts = [
    "https://*.googlesyndication.com",
    "https://*.doubleclick.net",
    "https://*.adtrafficquality.google",
    "https://*.google.com",
  ];
  const directives: Record<string, string[]> = {
    "default-src": ["'self'"],
    "script-src": [`'nonce-${nonce}'`, "'strict-dynamic'", "https:", "'unsafe-inline'"],
    "style-src": ["'self'", "'unsafe-inline'", fontCss],
    "font-src": ["'self'", fontFiles, "data:"],
    "img-src": ["'self'", "data:", "blob:", ...imageHosts, ...adHosts],
    "connect-src": ["'self'", ...imageHosts, fontCss, fontFiles, ...adHosts],
    "frame-src": adHosts,
    "manifest-src": ["'self'"],
    "object-src": ["'none'"],
    "base-uri": ["'self'"],
    "form-action": ["'self'"],
    "frame-ancestors": ["'self'"],
  };
  return Object.entries(directives)
    .map(([name, values]) => `${name} ${values.join(" ")}`)
    .join("; ");
}

/** SSR 時に entry.server が発行した nonce を root の Scripts などへ渡す */
export const CspNonceContext = createContext<string | undefined>(undefined);

/**
 * inline script に付ける nonce。クライアントでは context が無いため、SSR で出力済みの script から読む
 * (ブラウザは nonce 属性値を隠すが `nonce` プロパティでは読める)。hydration で属性値を揃えるため。
 */
export function useCspNonce(): string | undefined {
  const nonce = useContext(CspNonceContext);
  if (nonce != null || typeof document === "undefined") return nonce;
  return document.querySelector<HTMLScriptElement>("script[nonce]")?.nonce || undefined;
}

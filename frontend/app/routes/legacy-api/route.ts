import type { LoaderFunctionArgs } from "react-router";

/**
 * 外部公開 API の透過 proxy。
 *
 * `/wolf-mansion/{recruiting,village-record/*,skill/list,api/village*}` は外部サイトが
 * 消費しており URL を変更できない。backend の context-path が `/wolf-mansion-api` に
 * 分離されたため、frontend が旧パスのまま受けて backend へ素通しする。
 * ステータス・ヘッダ・ボディは変換しない (レスポンス形状は消費側との契約)。
 */

// SSR サーバから backend へ直接届くオリジン (k8s では cluster-internal URL を env で指定)
const BACKEND_ORIGIN = process.env.BACKEND_ORIGIN ?? "http://localhost:8089";
const BACKEND_BASE = "/wolf-mansion-api";
const FRONTEND_BASE = "/wolf-mansion";

export async function loader({ request }: LoaderFunctionArgs): Promise<Response> {
  const url = new URL(request.url);
  const path = url.pathname.startsWith(FRONTEND_BASE)
    ? url.pathname.slice(FRONTEND_BASE.length)
    : url.pathname;

  const headers = new Headers(request.headers);
  // frontend の Host のまま転送すると backend 側の絶対 URL 生成に混入するため除去
  headers.delete("host");

  const response = await fetch(`${BACKEND_ORIGIN}${BACKEND_BASE}${path}${url.search}`, {
    headers,
    redirect: "manual",
  });

  const responseHeaders = new Headers(response.headers);
  // fetch が body を解凍・再ストリーム化するため、圧縮/長さ系ヘッダを残すと
  // クライアント側でデコード不整合になる
  responseHeaders.delete("content-encoding");
  responseHeaders.delete("content-length");
  responseHeaders.delete("transfer-encoding");

  return new Response(response.body, {
    status: response.status,
    headers: responseHeaders,
  });
}

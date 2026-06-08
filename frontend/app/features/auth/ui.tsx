import type { ReactNode } from "react";

import { Footer } from "~/components/layout/Footer";
import { Header } from "~/components/layout/Header";

/**
 * 認証画面の共通 UI。ブランドのダークテーマ (#222 地・白文字) + 共通ヘッダー (small バナー) +
 * 共通フッターで構成する。フォームは Bootstrap `.form-horizontal` (ラベル左 / 入力右、緑の
 * pull-right 送信ボタン) を `:8091` 基準で再現する。
 */

// `.form-control` 相当: 白地・薄枠・高さ30px・12px。ダーク地で見えるよう入力欄のみ明色にする。
export const inputClass =
  "h-[30px] w-full rounded border border-gray-400 bg-white px-[10px] py-[5px] text-[#555555]";
// `.btn-sm.btn-success` 相当 (緑 #00bc8c)。Footer の successBtn と同系。
export const buttonClass =
  "rounded bg-[#00bc8c] px-3 py-[5px] text-white hover:opacity-90 disabled:opacity-50";
// 認証画面内のリンク (ダーク地) = アクセント teal。
export const linkClass = "text-wm-accent hover:underline";
// フィールド単位のエラー (zod)。ダーク地でも読める明るい赤。
export const fieldErrorClass = "mt-1 text-red-400";
// フォーム全体のエラー (`.text-danger` 相当)。
export const formErrorClass = "mb-2 block text-red-400";

/** 認証画面の外枠: ダーク地 (#222) + container 幅 + 共通ヘッダー + 共通フッター。 */
export function AuthLayout({ title, children }: { title: string; children: ReactNode }) {
  return (
    // ページ地色は既存 `:8091` の body 背景 (#222) を全幅・全高で再現する (home と同方針)。
    <div className="min-h-screen bg-wm-base text-xs text-white">
      {/* 既存 Bootstrap3 .container と同じレスポンシブ最大幅 (768→750 / 992→970 / 1200→1170)。 */}
      <div className="mx-auto w-full min-[768px]:max-w-[750px] min-[992px]:max-w-[970px] min-[1200px]:max-w-[1170px]">
        <Header />
        <div className="px-[15px]">
          {/* 既存 `<h1 class="h4">` (約18px) 相当。 */}
          <h1 className="mt-5 mb-2.5 text-[18px] font-medium">{title}</h1>
          {children}
        </div>
        <Footer />
      </div>
    </div>
  );
}

/** `.form-horizontal` の 1 行 (ラベル左 / 入力右)。 */
export function FormRow({
  label,
  htmlFor,
  children,
}: {
  label: string;
  htmlFor: string;
  children: ReactNode;
}) {
  return (
    // `.form-group` margin-bottom:15px。ラベル col-xs-4(1/3) / col-sm-2(1/6, ≥768 で右寄せ・pt5px)。
    <div className="mb-[15px] flex items-start">
      <label
        htmlFor={htmlFor}
        className="w-1/3 shrink-0 pr-[15px] min-[768px]:w-1/6 min-[768px]:pt-[5px] min-[768px]:text-right"
      >
        {label}
      </label>
      <div className="flex-1">{children}</div>
    </div>
  );
}

/** 送信ボタン行 (`.pull-right` 相当で右寄せ)。 */
export function FormActions({ children }: { children: ReactNode }) {
  return <div className="text-right">{children}</div>;
}

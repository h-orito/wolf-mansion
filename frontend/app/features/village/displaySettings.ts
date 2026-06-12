import { create } from "zustand";
import { persist } from "zustand/middleware";

export const PAGE_SIZE_OPTIONS = [10, 20, 30, 50, 100, 200, 300, 500] as const;

export type DisplaySettings = {
  /** 発言ログをページ分割する */
  isPaging: boolean;
  /** ページあたりの表示発言数 */
  pageSize: number;
  /** 更新検知時に自動で読み込む (最新ページ表示中のみ) */
  autoReload: boolean;
  /** 発言フォームに文字装飾ボタンを表示する */
  showDecorationButtons: boolean;
  /** 発言の画像を大きく表示する */
  largeImage: boolean;
  /** 発言の文字を大きく表示する */
  largeText: boolean;
};

/** 文字サイズだけは画面サイズで初期値を変える (小さい画面で大きい文字は崩れるため)。 */
function defaultLargeText(): boolean {
  if (typeof window === "undefined") return false;
  return window.matchMedia("(min-width: 1024px)").matches;
}

function defaults(): DisplaySettings {
  return {
    isPaging: true,
    pageSize: 50,
    autoReload: true,
    showDecorationButtons: true,
    largeImage: false,
    largeText: defaultLargeText(),
  };
}

type DisplaySettingsStore = DisplaySettings & {
  update: (settings: Partial<DisplaySettings>) => void;
  reset: () => void;
};

/** 村画面の表示設定。サーバには持たずブラウザ (localStorage) に保存する。 */
export const useDisplaySettings = create<DisplaySettingsStore>()(
  persist(
    (set) => ({
      ...defaults(),
      update: (settings) => set(settings),
      reset: () => set(defaults()),
    }),
    { name: "wolf-mansion-display-settings" },
  ),
);

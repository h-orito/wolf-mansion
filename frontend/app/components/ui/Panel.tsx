import { type ReactNode, useCallback, useId, useSyncExternalStore } from "react";

const BOTTOM_FIX_KEY = "village_panel_bottom_fix";

/**
 * パネル「固定」時に画面下端へ張り付けるための共通クラス。
 * viewport-fit=cover 環境で内容やスクロール下端が safe area (ホームインジケータ・ノッチ) の
 * 下に潜らないよう、三方向の inset 分 padding を確保する。
 * 下端は同じく bottom 固定の FooterMenu に重なるため、その実高さ (--footer-menu-height、
 * safe area 込み) 分を確保し、FooterMenu が無い画面では safe area のみ確保する。
 */
export const bottomFixedPanelClass =
  "fixed bottom-0 left-0 z-20 mb-0 w-screen max-h-[30vh] overflow-y-auto pb-[var(--footer-menu-height,env(safe-area-inset-bottom))] pl-[env(safe-area-inset-left)] pr-[env(safe-area-inset-right)]";

function readStorage(key: string): string | null {
  try {
    return localStorage.getItem(key);
  } catch {
    return null;
  }
}

function writeStorage(key: string, value: string) {
  localStorage.setItem(key, value);
  window.dispatchEvent(new StorageEvent("storage", { key, newValue: value }));
}

function subscribeStorage(key: string, cb: () => void) {
  const handler = (e: StorageEvent) => {
    if (e.key === key) cb();
  };
  window.addEventListener("storage", handler);
  return () => window.removeEventListener("storage", handler);
}

function useStorageBoolean(key: string | null, defaultValue: boolean): boolean {
  return useSyncExternalStore(
    useCallback((cb: () => void) => (key ? subscribeStorage(key, cb) : () => {}), [key]),
    () => {
      if (!key) return defaultValue;
      const v = readStorage(key);
      return v == null ? defaultValue : v === "true";
    },
    () => defaultValue,
  );
}

function useStorageString(key: string): string {
  return useSyncExternalStore(
    useCallback((cb: () => void) => subscribeStorage(key, cb), [key]),
    () => readStorage(key) ?? "",
    () => "",
  );
}

export function Panel({
  title,
  headerClassName = "bg-surface-raised",
  headerExtra,
  storageKey,
  defaultOpen = true,
  fixable = false,
  children,
}: {
  title: string;
  headerClassName?: string;
  headerExtra?: ReactNode;
  storageKey?: string;
  defaultOpen?: boolean;
  fixable?: boolean;
  children: ReactNode;
}) {
  const fullKey = storageKey ? `village_panel_${storageKey}` : null;
  const bodyId = useId();

  const open = useStorageBoolean(fullKey, defaultOpen);
  const bottomFixTarget = useStorageString(BOTTOM_FIX_KEY);
  const isFixed = fixable && storageKey != null && bottomFixTarget === storageKey;

  const toggle = useCallback(() => {
    if (!fullKey) return;
    const v = readStorage(fullKey);
    const current = v == null ? defaultOpen : v === "true";
    writeStorage(fullKey, String(!current));
  }, [fullKey, defaultOpen]);

  const toggleFix = useCallback(() => {
    if (!storageKey) return;
    const current = readStorage(BOTTOM_FIX_KEY) ?? "";
    writeStorage(BOTTOM_FIX_KEY, current === storageKey ? "" : storageKey);
  }, [storageKey]);

  return (
    <div
      className={`mb-[20px] rounded border border-border bg-surface ${isFixed ? bottomFixedPanelClass : ""}`}
    >
      <div className={`flex items-center rounded-t px-[15px] py-[10px] ${headerClassName}`}>
        <div className="flex-1">
          {storageKey ? (
            <button
              type="button"
              aria-expanded={open}
              aria-controls={bodyId}
              onClick={toggle}
              className="cursor-pointer text-[15px] text-white hover:underline"
            >
              {title}
            </button>
          ) : (
            <span className="text-[15px] text-white">{title}</span>
          )}
          {headerExtra != null && (
            <span className="ml-[5px] text-[12px] text-white">{headerExtra}</span>
          )}
        </div>
        {fixable && (
          <button
            type="button"
            onClick={toggleFix}
            className="cursor-pointer text-[12px] text-white hover:underline"
          >
            {isFixed ? "固定解除" : "固定"}
          </button>
        )}
      </div>
      <div
        id={bodyId}
        className={`grid transition-[grid-template-rows] duration-300 ease-in-out ${open ? "grid-rows-[1fr]" : "grid-rows-[0fr]"}`}
      >
        <div className="overflow-hidden">
          <div className="p-[15px]">{children}</div>
        </div>
      </div>
    </div>
  );
}

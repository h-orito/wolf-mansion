import { useEffect, useState } from "react";

function isDocumentVisible(): boolean {
  return typeof document === "undefined" || !document.hidden;
}

/**
 * 村画面のポーリングを行ってよいか。
 * 終了・廃村の村は日付も発言も増えないため止め、タブが非表示の間も止める (表示に戻ると true に戻る)。
 * 村を読み込むまで (`isFinished` が undefined) は、終了した村に問い合わせないよう待つ。
 */
export function usePollingActive(isFinished: boolean | undefined): boolean {
  const [visible, setVisible] = useState(isDocumentVisible);

  useEffect(() => {
    const onChange = () => setVisible(isDocumentVisible());
    // マウントまでに表示状態が変わっていた場合に追従する
    onChange();
    document.addEventListener("visibilitychange", onChange);
    return () => document.removeEventListener("visibilitychange", onChange);
  }, []);

  return isFinished === false && visible;
}

import { type MouseEvent, useEffect, useRef } from "react";

// dangerouslySetInnerHTMLではなくref経由でDOMを直接設定する。
// [[cw]]/[[netabare]]タグのクリックでclass除去した状態がReact再レンダリングで復元されるのを防ぐため。
export function StableHtml({
  html,
  className,
  onClick,
}: {
  html: string;
  className?: string;
  onClick?: (e: MouseEvent<HTMLDivElement>) => void;
}) {
  const ref = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (ref.current) ref.current.innerHTML = html;
  }, [html]);
  return <div ref={ref} className={className} onClick={onClick} />;
}

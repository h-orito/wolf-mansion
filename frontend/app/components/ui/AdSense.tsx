import { useEffect, useRef, useState } from "react";

const AD_CLIENT = "ca-pub-0917187897820609";

export function AdSense({
  slot,
  className,
  width,
  height,
}: {
  slot: string;
  className?: string;
  width?: number;
  height?: number;
}) {
  const ref = useRef<HTMLDivElement>(null);
  const pushed = useRef(false);

  useEffect(() => {
    if (pushed.current) return;
    const script = document.createElement("script");
    script.async = true;
    script.src = "//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js";
    ref.current?.appendChild(script);
    script.onload = () => {
      try {
        ((window as any).adsbygoogle = (window as any).adsbygoogle || []).push({});
        pushed.current = true;
      } catch {
        // ad blockers may prevent this
      }
    };
  }, []);

  const isFixed = width != null && height != null;

  return (
    <div ref={ref} className={className}>
      <ins
        className="adsbygoogle"
        style={
          isFixed
            ? { display: "inline-block", width: `${width}px`, height: `${height}px` }
            : { display: "block" }
        }
        data-ad-client={AD_CLIENT}
        data-ad-slot={slot}
        {...(!isFixed && {
          "data-ad-format": "auto",
          "data-full-width-responsive": "true",
        })}
      />
    </div>
  );
}

export function ResponsiveAdSense({
  sm,
  lg,
  className,
}: {
  sm: { slot: string; width: number; height: number };
  lg: { slot: string; width: number; height: number };
  className?: string;
}) {
  const [isSmall, setIsSmall] = useState<boolean | null>(null);

  useEffect(() => {
    setIsSmall(window.matchMedia("(max-width: 639px)").matches);
  }, []);

  if (isSmall === null) return null;

  const config = isSmall ? sm : lg;
  return (
    <AdSense slot={config.slot} width={config.width} height={config.height} className={className} />
  );
}

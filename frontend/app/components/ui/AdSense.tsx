import { useEffect, useRef } from "react";

const AD_CLIENT = "ca-pub-0917187897820609";

export function AdSense({ slot, className }: { slot: string; className?: string }) {
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

  return (
    <div ref={ref} className={className}>
      <ins
        className="adsbygoogle"
        style={{ display: "block" }}
        data-ad-client={AD_CLIENT}
        data-ad-slot={slot}
        data-ad-format="auto"
        data-full-width-responsive="true"
      />
    </div>
  );
}

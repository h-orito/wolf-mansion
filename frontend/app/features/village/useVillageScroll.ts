import { useCallback } from "react";

const FOOTER_HEIGHT = 45;

export function useVillageScroll() {
  const scrollToTop = useCallback(() => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  }, []);

  const scrollToBottom = useCallback((smooth = true) => {
    const el = document.getElementById("bottom");
    if (el == null) return;
    const top = el.offsetTop - window.innerHeight + FOOTER_HEIGHT;
    window.scrollTo({ top: Math.max(0, top), behavior: smooth ? "smooth" : "instant" });
  }, []);

  return { scrollToTop, scrollToBottom };
}

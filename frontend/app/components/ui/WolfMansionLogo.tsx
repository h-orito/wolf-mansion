import { cn } from "./cn";

type LogoSize = "sm" | "md" | "lg";

/**
 * 旧 index.html / header.html のロゴ:
 *   <span class="font-anima">
 *     <span style="color:#ff0000">W</span>OLF<br?>
 *     <span style="color:#ff0000">M</span>ANSION
 *   </span>
 *
 * lg は index.html の hero (24px)、sm は header.html の小ロゴ (16px)、
 * md は中間 (20px)。サイズは em で指定し、ユーザー文字サイズ拡大に追従。
 */
const sizeClass: Record<LogoSize, string> = {
  sm: "text-[1.3em] leading-[1.3em]", // 旧 16px @ base 12px
  md: "text-[1.7em] leading-[1.7em]",
  lg: "text-[2em] leading-[2em]", // 旧 24px @ base 12px
};

export function WolfMansionLogo({
  size = "md",
  block = false,
  className,
}: {
  size?: LogoSize;
  /** true なら "WOLF" の後で改行 (旧 hero と同じ縦並び) */
  block?: boolean;
  className?: string;
}) {
  return (
    <span
      aria-label="WOLF MANSION"
      className={cn("font-anima text-white inline-block", sizeClass[size], className)}
    >
      <span className="text-blood-500">W</span>OLF{block ? <br /> : " "}
      <span className="text-blood-500">M</span>ANSION
    </span>
  );
}

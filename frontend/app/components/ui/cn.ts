/**
 * tailwind class 結合の最小ヘルパ。
 * 旧 bootstrap-override の class を Tailwind variant で再現するだけなので
 * tailwind-merge 等の後勝ち merge は不要。
 */
export type ClassValue = string | false | null | undefined;

export function cn(...classes: ClassValue[]): string {
  return classes.filter(Boolean).join(" ");
}

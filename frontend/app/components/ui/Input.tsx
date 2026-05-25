import * as React from "react";
import { cn } from "./cn";

/**
 * 旧 .form-control / .control-label / .input-group-addon の再現。
 *
 * 旧 CSS:
 *   .form-control { font-size: 1em; height: 30px; padding: 5px 10px; }
 *   .control-label (Bootstrap 標準): 右寄せラベル
 *
 * 高さは em で書き直し、ユーザー文字拡大に追従させる。
 */
const fieldBase =
  "w-full rounded-[0.25em] border border-night-700 bg-white text-night-700 " +
  "px-3 py-1 leading-[1.5em] " + // 旧 height 30px ≈ 1em + py-1 で再現
  "placeholder:text-gray-500 " +
  "focus:outline focus:outline-2 focus:outline-mint-500 focus:outline-offset-0 " +
  "disabled:opacity-60 disabled:cursor-not-allowed";

export type InputProps = React.InputHTMLAttributes<HTMLInputElement>;

export const Input = React.forwardRef<HTMLInputElement, InputProps>(
  function Input({ className, type = "text", ...rest }, ref) {
    return (
      <input
        ref={ref}
        type={type}
        className={cn(fieldBase, className)}
        {...rest}
      />
    );
  },
);

export type TextareaProps = React.TextareaHTMLAttributes<HTMLTextAreaElement>;

export const Textarea = React.forwardRef<HTMLTextAreaElement, TextareaProps>(
  function Textarea({ className, ...rest }, ref) {
    return (
      <textarea
        ref={ref}
        className={cn(fieldBase, "resize-y min-h-[6em] py-2", className)}
        {...rest}
      />
    );
  },
);

export type SelectProps = React.SelectHTMLAttributes<HTMLSelectElement>;

export const Select = React.forwardRef<HTMLSelectElement, SelectProps>(
  function Select({ className, ...rest }, ref) {
    return (
      <select ref={ref} className={cn(fieldBase, className)} {...rest} />
    );
  },
);

/**
 * 旧 .control-label。Bootstrap 標準は右寄せ。
 * 用途に応じて className で left/right を切り替え可能。
 */
export function Label({
  className,
  ...rest
}: React.LabelHTMLAttributes<HTMLLabelElement>) {
  return (
    <label
      className={cn("block text-[1em] leading-[2em] font-medium", className)}
      {...rest}
    />
  );
}

export function FieldError({
  className,
  children,
  ...rest
}: React.HTMLAttributes<HTMLParagraphElement>) {
  if (!children) return null;
  return (
    <p
      role="alert"
      className={cn("text-blood-500 text-[1em] mt-1", className)}
      {...rest}
    >
      {children}
    </p>
  );
}

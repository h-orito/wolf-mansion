import type { ReactNode } from "react";

export function ErrorMessage({ error }: { error: string | null }) {
  if (error == null) return null;
  return <p className="text-danger">{error}</p>;
}

type AlertVariant = "info" | "warning" | "default";

const variantStyles: Record<AlertVariant, string> = {
  info: "border-info text-info",
  warning: "border-warning text-warning",
  default: "border-white",
};

export function Alert({
  variant = "default",
  className = "",
  children,
}: {
  variant?: AlertVariant;
  className?: string;
  children: ReactNode;
}) {
  return (
    <div className={`rounded border p-[10px] ${variantStyles[variant]} ${className}`}>
      {children}
    </div>
  );
}

export function AlertList({
  variant = "warning",
  className = "",
  children,
}: {
  variant?: AlertVariant;
  className?: string;
  children: ReactNode;
}) {
  return (
    <ul
      className={`list-disc rounded border p-[10px] pl-[25px] ${variantStyles[variant]} ${className}`}
    >
      {children}
    </ul>
  );
}

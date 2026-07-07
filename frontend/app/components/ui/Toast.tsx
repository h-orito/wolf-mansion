import { useEffect } from "react";
import { create } from "zustand";

type ToastItem = {
  id: number;
  message: string;
  variant: "success" | "info" | "error";
  persistent: boolean;
  duration?: number;
};

type ToastState = {
  items: ToastItem[];
  nextId: number;
  show: (
    message: string,
    opts?: { variant?: ToastItem["variant"]; persistent?: boolean; duration?: number },
  ) => void;
  dismiss: (id: number) => void;
};

export const useToast = create<ToastState>((set) => ({
  items: [],
  nextId: 1,
  show: (message, opts) =>
    set((s) => ({
      items: [
        ...s.items,
        {
          id: s.nextId,
          message,
          variant: opts?.variant ?? "success",
          persistent: opts?.persistent ?? false,
          duration: opts?.duration,
        },
      ],
      nextId: s.nextId + 1,
    })),
  dismiss: (id) => set((s) => ({ items: s.items.filter((t) => t.id !== id) })),
}));

const VARIANT_STYLE: Record<ToastItem["variant"], string> = {
  success: "bg-success",
  info: "bg-info",
  error: "bg-danger",
};

function ToastEntry({ item }: { item: ToastItem }) {
  const dismiss = useToast((s) => s.dismiss);

  useEffect(() => {
    if (item.persistent && item.duration == null) return;
    const timer = setTimeout(() => dismiss(item.id), item.duration ?? 3000);
    return () => clearTimeout(timer);
  }, [item.id, item.persistent, item.duration, dismiss]);

  return (
    <div
      className={`rounded px-[20px] py-[10px] text-white shadow-lg ${VARIANT_STYLE[item.variant]} ${item.persistent ? "cursor-pointer" : ""}`}
      onClick={item.persistent ? () => dismiss(item.id) : undefined}
    >
      {item.message}
    </div>
  );
}

export function Toast() {
  const items = useToast((s) => s.items);
  if (items.length === 0) return null;

  return (
    <div className="fixed top-[40px] left-1/2 z-[110] flex -translate-x-1/2 flex-col gap-[8px]">
      {items.map((item) => (
        <ToastEntry key={item.id} item={item} />
      ))}
    </div>
  );
}

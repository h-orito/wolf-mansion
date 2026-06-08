import type { ReactNode } from "react";

type SystemMessageType = "public-system" | "private-system" | "creator";

const typeStyles: Record<SystemMessageType, string> = {
  "public-system": "border-[#e3e3e3] text-white",
  "private-system": "border-[#cccccc] bg-[#333333] text-[#eeeeee]",
  creator: "border-[#00bc8c] text-[#00bc8c]",
};

export function SystemMessage({
  type,
  children,
}: {
  type: SystemMessageType;
  children: ReactNode;
}) {
  return (
    <div className="mb-[20px] w-full">
      <div
        className={`rounded-[5px] border p-[9px] break-words ${typeStyles[type]}`}
        style={{ fontFamily: "sans-serif" }}
      >
        {children}
      </div>
    </div>
  );
}

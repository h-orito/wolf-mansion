type SystemMessageType = "public-system" | "private-system" | "creator";

const typeStyles: Record<SystemMessageType, string> = {
  "public-system": "border-[#e3e3e3] text-white",
  "private-system": "border-[#cccccc] bg-[#333333] text-[#eeeeee]",
  creator: "border-[#00bc8c] text-[#00bc8c]",
};

export function SystemMessage({ type, message }: { type: SystemMessageType; message: string }) {
  return (
    <div className="w-full">
      <div
        className={`rounded-[5px] border p-[9px] font-sans text-[1em] break-words ${typeStyles[type]}`}
        dangerouslySetInnerHTML={{ __html: message }}
      />
    </div>
  );
}

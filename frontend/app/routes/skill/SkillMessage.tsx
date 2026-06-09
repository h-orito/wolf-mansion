const MESSAGE_STYLES: Record<string, string> = {
  "message-private-seer": "bg-[#334033] border-[#34a865] text-[#eee]",
  "message-private-ability": "bg-[#333] border-[#ccc] text-[#eee]",
  "message-private-investigate": "bg-[#403333] border-[#a86534] text-[#eee]",
  "message-private-psychic": "bg-[#333340] border-[#3465a8] text-[#eee]",
  "message-public-system": "bg-transparent border-[#e3e3e3] text-white",
  "message-private-system": "bg-[#333] border-[#ccc] text-[#eee]",
  "message-private-werewolf": "bg-[#403333] border-[#a83434] text-[#eee]",
  "message-werewolf": "bg-[#f2aeae] border-[#f2aeae] text-[#333]",
  "message-private": "bg-transparent border-[#e3e3e3] text-white",
  "message-private-lover": "bg-[#403333] border-[#f9318f] text-[#eee]",
  "message-public": "bg-transparent border-[#e3e3e3] text-white",
  "message-private-fox": "bg-[#403333] border-[#c9c934] text-[#eee]",
};

export function SkillMessage({ messageType, content }: { messageType: string; content: string }) {
  const style = MESSAGE_STYLES[messageType] ?? "bg-transparent border-[#e3e3e3] text-white";
  return (
    <div className={`rounded border p-[9px] ${style}`}>
      {content.split("\n").map((line, i) => (
        <span key={i}>
          {i > 0 && <br />}
          {line}
        </span>
      ))}
    </div>
  );
}

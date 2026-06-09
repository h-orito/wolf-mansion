import type { ReactNode } from "react";

type MessageType = "message-normal" | "message-werewolf" | "message-grave" | "message-monologue";

const CHARA_IMAGE_BASE = "https://wolfort.dev/wmansion/1";

const typeStyles: Record<MessageType, string> = {
  "message-normal": "bg-white text-[#555555] border-[#e3e3e3]",
  "message-werewolf": "bg-[#f2aeae] text-[#333333] border-[#f2aeae]",
  "message-grave": "bg-[#a9edf7] text-[#333333] border-[#a9edf7]",
  "message-monologue": "bg-[#aaaaaa] text-[#333333] border-[#b5b5b5]",
};

export function MessageBubble({
  type,
  children,
  chara,
  isLeft,
}: {
  type: MessageType;
  children: ReactNode;
  chara: string;
  isLeft: boolean;
}) {
  const charaImg = (
    <div
      className="h-[77px] w-[50px] shrink-0 bg-no-repeat"
      role="img"
      aria-label={chara}
      style={{
        backgroundImage: `url('${CHARA_IMAGE_BASE}/${chara}.png')`,
      }}
    />
  );

  return (
    <div className="mb-[20px] w-full">
      <div className="flex">
        {isLeft && charaImg}
        <div
          className={`min-h-[77px] flex-1 rounded-[5px] border p-[9px] break-words ${typeStyles[type]} ${isLeft ? "ml-[5px]" : "mr-[5px]"}`}
          style={{ fontFamily: "sans-serif" }}
        >
          {children}
        </div>
        {!isLeft && charaImg}
      </div>
    </div>
  );
}

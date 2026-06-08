type MessageType = "message-normal" | "message-werewolf" | "message-grave";

const typeStyles: Record<MessageType, string> = {
  "message-normal": "bg-white text-[#555555] border-[#e3e3e3]",
  "message-werewolf": "bg-[#f2aeae] text-[#333333] border-[#f2aeae]",
  "message-grave": "bg-[#a9edf7] text-[#333333] border-[#a9edf7]",
};

export function MessageBubble({
  type,
  message,
  chara,
  isLeft,
}: {
  type: MessageType;
  message: string;
  chara: string;
  isLeft: boolean;
}) {
  const charaImg = (
    <div
      className="h-[77px] w-[50px] shrink-0 bg-no-repeat"
      style={{
        backgroundImage: `url('https://wolfort.dev/wmansion/1/${chara}.png')`,
      }}
    />
  );

  return (
    <div className="mb-0 w-full">
      <div className="flex">
        {isLeft && charaImg}
        <div
          className={`min-h-[77px] flex-1 rounded-[5px] border p-[9px] font-sans text-[1em] break-words ${typeStyles[type]} ${isLeft ? "ml-[5px]" : "mr-[5px]"}`}
          dangerouslySetInnerHTML={{ __html: message }}
        />
        {!isLeft && charaImg}
      </div>
    </div>
  );
}

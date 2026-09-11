import { DEFAULT_MESSAGE_STYLE, MESSAGE_STYLES } from "./messageStyles";

const bubbleStyle = (styleKey: string) =>
  `rounded-[5px] border p-[9px] break-words font-[sans-serif] ${MESSAGE_STYLES[styleKey] ?? DEFAULT_MESSAGE_STYLE}`;

export function SkillMessage({ messageType, content }: { messageType: string; content: string }) {
  return (
    <div className={`mb-[20px] ${bubbleStyle(messageType)}`}>
      <MessageText text={content} />
    </div>
  );
}

export function SkillSayMessage({
  messageType,
  content,
  imageUrl,
}: {
  messageType: string;
  content: string;
  imageUrl: string;
}) {
  return (
    <div className="mb-[20px] flex">
      <img src={imageUrl} width={50} height={77} alt="" className="shrink-0 object-cover" />
      <div className={`ml-[5px] min-h-[77px] flex-1 ${bubbleStyle(messageType)}`}>
        <MessageText text={content} />
      </div>
    </div>
  );
}

function MessageText({ text }: { text: string }) {
  return (
    <>
      {text.split("\n").map((line, i) => (
        <span key={i}>
          {i > 0 && <br />}
          {line}
        </span>
      ))}
    </>
  );
}

import { DEFAULT_MESSAGE_STYLE, MESSAGE_STYLES } from "./messageStyles";

export function SkillMessage({ messageType, content }: { messageType: string; content: string }) {
  const style = MESSAGE_STYLES[messageType] ?? DEFAULT_MESSAGE_STYLE;
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

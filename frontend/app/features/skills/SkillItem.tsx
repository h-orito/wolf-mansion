import { SkillMessage, SkillSayMessage } from "~/components/ui/SkillMessage";
import type { SimpleSkillView } from "~/features/skills/api";
import { skillDescriptions } from "~/features/skills/descriptions";

export function SkillItem({ skill }: { skill: SimpleSkillView }) {
  const items = skillDescriptions[skill.code.toLowerCase()] ?? [];
  return (
    <li id={skill.code.toLowerCase()} className="mb-[10px]">
      【{skill.shortName}】{skill.name}
      <ul className="list-disc pl-[20px] leading-[17.14px]">
        {items.map((item, i) =>
          item.type === "message" ? (
            <li key={i}>
              <SkillMessage messageType={item.messageType} content={item.content} />
            </li>
          ) : item.type === "say" ? (
            <li key={i}>
              <SkillSayMessage
                messageType={item.messageType}
                content={item.content}
                imageUrl={item.imageUrl}
              />
            </li>
          ) : (
            <li key={i}>
              {item.content.split("\n").map((line, j) => (
                <span key={j}>
                  {j > 0 && <br />}
                  {line}
                </span>
              ))}
            </li>
          ),
        )}
      </ul>
    </li>
  );
}

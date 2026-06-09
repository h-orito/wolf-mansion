import { useMemo, useState } from "react";

import { Heading, SubHeading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import {
  skillDescriptions,
  type CampDescriptions,
  type SkillDescription,
} from "~/features/skills/descriptions";
import { useSkillList, useSkillSearch } from "~/features/skills/useSkillList";
import { useVillages } from "~/features/villages/useVillages";
import { siteMeta } from "~/lib/meta";

import { SearchPanel, type SearchValue } from "./SearchPanel";
import { SkillMessage } from "./SkillMessage";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("役職一覧");
}

export default function SkillList() {
  const { data: skillData } = useSkillList();
  const { data: villageData } = useVillages({ random: false, order: "desc" });

  const [searchValue, setSearchValue] = useState<SearchValue | null>(null);
  const hasSearched = searchValue !== null;
  const hasFilter =
    hasSearched &&
    (searchValue.tags.length > 0 || searchValue.name.length > 0 || searchValue.villageId !== null);

  const { data: searchResult } = useSkillSearch(
    {
      tags: searchValue?.tags,
      name: searchValue?.name || undefined,
      villageId: searchValue?.villageId,
    },
    hasFilter,
  );

  const visibleCodes = useMemo<Set<string> | null>(() => {
    if (!hasFilter) return null;
    if (!searchResult) return null;
    return new Set(searchResult.skillCodes);
  }, [hasFilter, searchResult]);

  const allTags = skillData?.tags ?? [];
  const villages = villageData?.villages ?? [];

  return (
    <PageLayout>
      <div className="px-[15px]">
        <Heading>役職一覧</Heading>

        <SearchPanel allTags={allTags} villages={villages} onSearch={setSearchValue} />

        <CampMenu descriptions={skillDescriptions} visibleCodes={visibleCodes} />

        <hr className="my-[21px] border-[#464545]" />
        <SubHeading>役職詳細</SubHeading>

        <SkillDetailList descriptions={skillDescriptions} visibleCodes={visibleCodes} />
      </div>
    </PageLayout>
  );
}

function CampMenu({
  descriptions,
  visibleCodes,
}: {
  descriptions: CampDescriptions[];
  visibleCodes: Set<string> | null;
}) {
  return (
    <div className="rounded bg-[#303030] px-[5px] pt-[5px] pb-[15px]">
      {descriptions.map((camp) => {
        const visibleSkills = camp.skills.filter(
          (s) => visibleCodes === null || visibleCodes.has(s.code.toUpperCase()),
        );
        if (visibleSkills.length === 0) return null;
        return (
          <div key={camp.id}>
            <p className="mb-[5px]">{camp.name}</p>
            <div className="mb-[10px]">
              {visibleSkills.map((skill, i) => (
                <span key={skill.code}>
                  <a href={`#${skill.code}`} className="text-wm-accent hover:underline">
                    {skill.name}
                  </a>
                  {i < visibleSkills.length - 1 && <span> / </span>}
                </span>
              ))}
            </div>
          </div>
        );
      })}
    </div>
  );
}

function SkillDetailList({
  descriptions,
  visibleCodes,
}: {
  descriptions: CampDescriptions[];
  visibleCodes: Set<string> | null;
}) {
  return (
    <ul className="list-disc pl-[20px]">
      {descriptions.map((camp) => {
        const visibleSkills = camp.skills.filter(
          (s) => visibleCodes === null || visibleCodes.has(s.code.toUpperCase()),
        );
        if (visibleSkills.length === 0) return null;
        return (
          <li key={camp.id} id={camp.id}>
            <h5 className="text-[15px]">{camp.name}</h5>
            <ul className="list-disc pl-[20px]">
              {visibleSkills.map((skill) => (
                <SkillItem key={skill.code} skill={skill} />
              ))}
            </ul>
          </li>
        );
      })}
    </ul>
  );
}

function SkillItem({ skill }: { skill: SkillDescription }) {
  return (
    <li id={skill.code} className="mb-[10px]">
      【{skill.shortName}】{skill.name}
      <ul className="list-disc pl-[20px]">
        {skill.items.map((item, i) =>
          item.type === "message" ? (
            <li key={i} className="list-none">
              <SkillMessage messageType={item.messageType} content={item.content} />
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

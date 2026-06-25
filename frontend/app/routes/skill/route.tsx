import { useMemo, useState } from "react";

import { Divider } from "~/components/ui/Divider";
import { Heading, SubHeading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import type { SimpleSkillView } from "~/features/skills/api";
import { SkillItem } from "~/features/skills/SkillItem";
import { useSkillList, useSkillSearch } from "~/features/skills/useSkillList";
import { useVillages } from "~/features/villages/useVillages";
import { siteMeta } from "~/lib/meta";

import { SearchPanel, type SearchValue } from "./SearchPanel";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("役職一覧");
}

type CampGroup = {
  campCode: string;
  campName: string;
  skills: SimpleSkillView[];
};

function groupByCamp(skills: SimpleSkillView[]): CampGroup[] {
  const map = new Map<string, CampGroup>();
  for (const skill of skills) {
    let group = map.get(skill.campCode);
    if (!group) {
      group = { campCode: skill.campCode, campName: skill.campName, skills: [] };
      map.set(skill.campCode, group);
    }
    group.skills.push(skill);
  }
  return Array.from(map.values());
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
  const campGroups = useMemo(() => groupByCamp(skillData?.skills ?? []), [skillData]);
  const villages = villageData?.villages ?? [];

  return (
    <PageLayout>
      <div className="px-[15px]">
        <Heading>役職一覧</Heading>

        <SearchPanel allTags={allTags} villages={villages} onSearch={setSearchValue} />

        <CampMenu campGroups={campGroups} visibleCodes={visibleCodes} />

        <Divider />
        <SubHeading>役職詳細</SubHeading>

        <SkillDetailList campGroups={campGroups} visibleCodes={visibleCodes} />
      </div>
    </PageLayout>
  );
}

function CampMenu({
  campGroups,
  visibleCodes,
}: {
  campGroups: CampGroup[];
  visibleCodes: Set<string> | null;
}) {
  return (
    <div className="rounded bg-[#303030] px-[5px] pt-[5px] pb-[15px]">
      {campGroups.map((camp) => {
        const visible = camp.skills.filter(
          (s) => visibleCodes === null || visibleCodes.has(s.code),
        );
        if (visible.length === 0) return null;
        return (
          <div key={camp.campCode}>
            <p className="mb-[5px]">{camp.campName}</p>
            <div className="mb-[10px]">
              {visible.map((skill, i) => (
                <span key={skill.code}>
                  <a
                    href={`#${skill.code.toLowerCase()}`}
                    className="text-wm-accent hover:underline"
                  >
                    {skill.name}
                  </a>
                  {i < visible.length - 1 && <span> / </span>}
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
  campGroups,
  visibleCodes,
}: {
  campGroups: CampGroup[];
  visibleCodes: Set<string> | null;
}) {
  return (
    <ul className="mb-[10.5px] list-disc pl-[20px]">
      {campGroups.map((camp) => {
        const visible = camp.skills.filter(
          (s) => visibleCodes === null || visibleCodes.has(s.code),
        );
        if (visible.length === 0) return null;
        return (
          <li key={camp.campCode} className="mb-[10px]">
            <h5 className="my-[10.5px] text-[15px]">{camp.campName}</h5>
            <ul className="list-disc pl-[20px]">
              {visible.map((skill) => (
                <SkillItem key={skill.code} skill={skill} />
              ))}
            </ul>
          </li>
        );
      })}
    </ul>
  );
}

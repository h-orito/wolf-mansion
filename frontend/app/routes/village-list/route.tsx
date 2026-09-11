import { Link, useSearchParams } from "react-router";

import { Heading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import type { SimpleVillageView } from "~/features/villages/api";
import { villageListParticipateNum, villageNumber } from "~/features/villages/format";
import { useCharachips, useSkills, useVillages } from "~/features/villages/useVillages";
import { siteMeta } from "~/lib/meta";
import { SearchPanel, type SearchValue } from "./SearchPanel";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("村一覧");
}

/** 編成 query (`random`) を boolean|null に変換する (`true`=闇鍋 / `false`=固定 / それ以外=両方)。 */
function parseRandom(value: string | null): boolean | null {
  if (value === "true") return true;
  if (value === "false") return false;
  return null;
}

export default function VillageList() {
  const [searchParams, setSearchParams] = useSearchParams();

  // 絞り込み条件は URL の searchParams を正本にする (共有可能 URL)。
  const filter: SearchValue = {
    charachips: searchParams
      .getAll("charachip")
      .map(Number)
      .filter((n) => !Number.isNaN(n)),
    skills: searchParams.getAll("skill"),
    random: parseRandom(searchParams.get("random")),
  };

  // 村一覧は全村 (終了/廃村含む) が対象なので status では絞らない。
  const { data: villageData } = useVillages(filter);
  const { data: charachips } = useCharachips();
  const { data: skills } = useSkills();

  const villages = villageData?.villages ?? [];

  const onSearch = (value: SearchValue) => {
    const params = new URLSearchParams();
    value.charachips.forEach((c) => params.append("charachip", String(c)));
    value.skills.forEach((s) => params.append("skill", s));
    if (value.random != null) params.set("random", String(value.random));
    setSearchParams(params);
  };

  return (
    <PageLayout>
      <div className="px-[15px]">
        <Heading>村一覧</Heading>

        {/* URL (searchParams) が絞り込みの正本。URL が変わったら remount してドラフトを再同期し、
            検索後はパネルを畳む。 */}
        <SearchPanel
          key={searchParams.toString()}
          charachips={charachips ?? []}
          skills={skills ?? []}
          initial={filter}
          onSearch={onSearch}
        />

        {villages.length > 0 && (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse text-[10.5px]">
              <colgroup>
                <col className="w-[8.333%]" />
                <col />
                <col className="w-[16.667%]" />
                <col className="w-[16.667%]" />
              </colgroup>
              <thead>
                <tr>
                  <th className="border border-border p-[5px] text-left font-bold">村番号</th>
                  <th className="border border-border p-[5px] text-left font-bold">村名</th>
                  <th className="border border-border p-[5px] text-left font-bold">人数</th>
                  <th className="border border-border p-[5px] text-left font-bold">状態</th>
                </tr>
              </thead>
              <tbody>
                {villages.map((v) => (
                  <VillageRow key={v.id} village={v} />
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </PageLayout>
  );
}

function VillageRow({ village }: { village: SimpleVillageView }) {
  const cell = "border border-border p-[5px]";
  return (
    <tr>
      <td className={cell}>{villageNumber(village.id)}</td>
      <td className={cell}>
        <Link to={`/village/${village.id}`} className="text-wm-accent hover:underline">
          {village.name}
        </Link>
      </td>
      <td className={cell}>{villageListParticipateNum(village)}</td>
      <td className={cell}>{village.status.name}</td>
    </tr>
  );
}

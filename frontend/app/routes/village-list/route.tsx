import { useSearchParams } from "react-router";

import { PageLayout } from "~/components/layout/PageLayout";
import type { SimpleVillageView, VillageFilter } from "~/features/villages/api";
import { villageListParticipateNum, villageNumber } from "~/features/villages/format";
import { useVillages, useVillageSearchCandidates } from "~/features/villages/useVillages";
import { legacyUrl } from "~/lib/api";
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

  // 絞り込み条件は URL の searchParams を正本にする (legacy の GET フォーム submit 相当・共有可能 URL)。
  const filter: VillageFilter & SearchValue = {
    charachips: searchParams
      .getAll("charachip")
      .map(Number)
      .filter((n) => !Number.isNaN(n)),
    skills: searchParams.getAll("skill"),
    random: parseRandom(searchParams.get("random")),
  };

  // 村一覧は全村 (終了/廃村含む) が対象なので status では絞らない。
  const { data: villageData } = useVillages(filter);
  const { data: candidates } = useVillageSearchCandidates();

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
        {/* 既存 `<h1 class="h4">` (19px・weight 400) 相当。 */}
        <h1 className="my-[10px] text-[19px] font-normal">村一覧</h1>

        <SearchPanel
          candidates={candidates}
          initial={{ charachips: filter.charachips, skills: filter.skills, random: filter.random }}
          onSearch={onSearch}
        />

        {/* legacy `table-bordered table-condensed small`。0 件時はテーブル非表示 (:8091 と同じ)。 */}
        {villages.length > 0 && (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse text-[10.5px]">
              {/* 列幅は Bootstrap3 grid 相当: 村番号 col-1=8.33% / 村名=残り / 人数・状態 col-2=16.67%。 */}
              <colgroup>
                <col className="w-[8.333%]" />
                <col />
                <col className="w-[16.667%]" />
                <col className="w-[16.667%]" />
              </colgroup>
              <thead>
                <tr>
                  <th className="border border-[#464545] p-[5px] text-left font-bold">村番号</th>
                  <th className="border border-[#464545] p-[5px] text-left font-bold">村名</th>
                  <th className="border border-[#464545] p-[5px] text-left font-bold">人数</th>
                  <th className="border border-[#464545] p-[5px] text-left font-bold">状態</th>
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
  const cell = "border border-[#464545] p-[5px]";
  return (
    <tr>
      <td className={cell}>{villageNumber(village.id)}</td>
      <td className={cell}>
        {/* 村画面は未移行のため当面 legacy SSR (`/village/{id}`) へフルナビゲーション。 */}
        <a href={legacyUrl(`/village/${village.id}`)} className="text-wm-accent hover:underline">
          {village.name}
        </a>
      </td>
      <td className={cell}>{villageListParticipateNum(village)}</td>
      <td className={cell}>{village.status.name}</td>
    </tr>
  );
}

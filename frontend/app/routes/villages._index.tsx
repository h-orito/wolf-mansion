import { useState } from "react";
import { Link, useSearchParams } from "react-router";
import { ChevronDownIcon, ChevronUpIcon } from "@heroicons/react/24/outline";
import type { Route } from "./+types/villages._index";
import { fetchVillages, type VillagesView, type VillageStatusCode } from "~/features/village/api";
import { useVillagesQuery } from "~/features/village/hooks";
import { ssrFetch } from "~/lib/api/client";
import { PageHeader } from "~/components/layout/PageHeader";
import { PageFooter } from "~/components/layout/PageFooter";
import { Panel, PanelHeading, PanelBody } from "~/components/ui/Panel";
import { Button } from "~/components/ui/Button";
import { Table, TableResponsive } from "~/components/ui/Table";
import { VillageTag, villageTagLevel } from "~/components/ui/VillageTag";

// NOTE: backend (VillageRestController#parseStatuses) と意味的に対応する。
const ALL_STATUSES: VillageStatusCode[] = ["募集中", "進行中", "エピローグ", "終了", "廃村"];

export function meta(_: Route.MetaArgs) {
  return [{ title: "村一覧 | WOLF MANSION" }];
}

function parseStatuses(raw: string | null): VillageStatusCode[] {
  if (!raw) return [];
  return raw
    .split(",")
    .map((s) => s.trim())
    .filter((s): s is VillageStatusCode => ALL_STATUSES.includes(s as VillageStatusCode));
}

export async function loader({ request }: Route.LoaderArgs) {
  const url = new URL(request.url);
  const statuses = parseStatuses(url.searchParams.get("status"));
  const api = ssrFetch(request);
  try {
    const villages = await fetchVillages({ statuses }, api);
    return { villages, statuses };
  } catch {
    return { villages: { list: [] } satisfies VillagesView, statuses };
  }
}

/**
 * 旧 templates/village-list.html を React で復元。
 *
 * 旧画面: panel-default 内に collapse する form-horizontal 検索 +
 * table-bordered table-condensed small の村一覧。
 * 現 React 側は status 絞り込みのみ (キャラセット / 役職 / 編成は将来)。
 */
export default function VillagesIndex({ loaderData }: Route.ComponentProps) {
  const [params, setParams] = useSearchParams();
  const selected = parseStatuses(params.get("status"));
  const [searchOpen, setSearchOpen] = useState(selected.length > 0);

  const villagesQuery = useVillagesQuery(
    { statuses: selected.length > 0 ? selected : undefined },
    loaderData.villages,
  );

  function toggleStatus(s: VillageStatusCode) {
    const next = selected.includes(s)
      ? selected.filter((x) => x !== s)
      : [...selected, s];
    const newParams = new URLSearchParams(params);
    if (next.length === 0) newParams.delete("status");
    else newParams.set("status", next.join(","));
    setParams(newParams, { replace: true });
  }

  const list = villagesQuery.data?.list ?? [];

  const searchPanelId = "villages-search-panel";
  return (
    <main className="max-w-[1170px] mx-auto">
      <PageHeader />
      <div className="px-3">
        <h1 className="text-[1.5em] font-medium mb-3">村一覧</h1>

        <Panel className="mb-3">
          <PanelHeading>
            <button
              type="button"
              onClick={() => setSearchOpen((v) => !v)}
              aria-expanded={searchOpen}
              aria-controls={searchPanelId}
              className="inline-flex items-center gap-1 text-[1.17em] text-white hover:text-mint-500 transition-colors"
            >
              検索
              {searchOpen ? (
                <ChevronUpIcon className="w-[1em] h-[1em]" aria-hidden />
              ) : (
                <ChevronDownIcon className="w-[1em] h-[1em]" aria-hidden />
              )}
            </button>
          </PanelHeading>
          {searchOpen && (
            <PanelBody id={searchPanelId}>
              <div className="flex flex-wrap items-start gap-x-2 gap-y-2 mb-2">
                <span className="w-[5em] text-right pr-2 shrink-0 leading-[2em]">
                  状態
                </span>
                <div className="flex-1 flex flex-wrap gap-1">
                  {ALL_STATUSES.map((s) => {
                    const active = selected.includes(s);
                    return (
                      <Button
                        key={s}
                        variant={active ? "success" : "dark-success"}
                        onClick={() => toggleStatus(s)}
                        aria-pressed={active}
                      >
                        {s}
                      </Button>
                    );
                  })}
                  {selected.length > 0 && (
                    <Button
                      variant="gray"
                      onClick={() =>
                        setParams(new URLSearchParams(), { replace: true })
                      }
                    >
                      クリア
                    </Button>
                  )}
                </div>
              </div>
            </PanelBody>
          )}
        </Panel>

        <p className="mb-2 text-[0.95em] opacity-80">
          {selected.length === 0
            ? `全村 (${list.length}件)`
            : `${selected.join(" / ")} (${list.length}件)`}
        </p>

        {list.length === 0 ? (
          <p className="py-4 text-center opacity-70">該当する村がありません</p>
        ) : (
          <TableResponsive>
            <Table>
              <thead>
                <tr>
                  <th className="w-[3em]">村番号</th>
                  <th>村名</th>
                  <th className="w-[6em]">人数</th>
                  <th className="w-[6em]">状態</th>
                </tr>
              </thead>
              <tbody>
                {list.map((v) => (
                  <tr
                    key={v.id}
                    className="hover:bg-night-800 hover:text-mint-500 transition-colors"
                  >
                    <td className="text-right">{v.number}</td>
                    <td>
                      <Link
                        to={`/villages/${v.id}`}
                        className="text-white no-underline hover:text-mint-500"
                      >
                        {v.name}
                      </Link>
                    </td>
                    <td className="text-right">
                      {v.spectatorCount > 0
                        ? `${v.participantCount} (${v.spectatorCount})`
                        : v.participantCount}
                    </td>
                    <td>
                      <VillageTag level={villageTagLevel(v.statusName)}>
                        {v.statusName}
                      </VillageTag>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </TableResponsive>
        )}
      </div>
      <PageFooter />
    </main>
  );
}

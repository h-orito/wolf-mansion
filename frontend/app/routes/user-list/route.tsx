import { Link, useSearchParams } from "react-router";

import { Heading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import { usePlayers } from "~/features/player/usePlayer";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("ユーザー一覧");
}

export default function UserList() {
  const [searchParams, setSearchParams] = useSearchParams();
  const pageNum = Number(searchParams.get("pageNum")) || 1;

  const { data } = usePlayers(pageNum);

  const players = data?.players ?? [];
  const allPageCount = data?.allPageCount ?? 0;

  const goToPage = (page: number) => {
    const params = new URLSearchParams();
    if (page > 1) params.set("pageNum", String(page));
    setSearchParams(params);
  };

  return (
    <PageLayout>
      <div className="px-[15px]">
        <Heading>ユーザー一覧</Heading>

        {allPageCount > 1 && (
          <Pagination currentPage={pageNum} allPageCount={allPageCount} onPage={goToPage} />
        )}

        {players.length > 0 && (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse text-[10.5px]">
              <thead>
                <tr>
                  <th className="border border-[#464545] p-[5px] text-left font-bold">
                    ユーザー名
                  </th>
                </tr>
              </thead>
              <tbody>
                {players.map((p) => (
                  <tr key={p.name}>
                    <td className="border border-[#464545] p-[5px]">
                      <Link to={`/user/${p.name}`} className="text-wm-accent hover:underline">
                        {p.name}
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        {allPageCount > 1 && (
          <Pagination currentPage={pageNum} allPageCount={allPageCount} onPage={goToPage} />
        )}
      </div>
    </PageLayout>
  );
}

function Pagination({
  currentPage,
  allPageCount,
  onPage,
}: {
  currentPage: number;
  allPageCount: number;
  onPage: (page: number) => void;
}) {
  const pageNums = calcPageNums(currentPage, allPageCount);
  const hasPrev = currentPage > 1;
  const hasNext = currentPage < allPageCount;

  const btn = "px-[8px] py-[4px] border border-[#464545] text-[10.5px]";
  const disabled = `${btn} text-gray-600 cursor-default`;
  const active = `${btn} bg-wm-accent text-black`;
  const normal = `${btn} text-wm-accent hover:underline cursor-pointer`;

  return (
    <div className="my-[10px] flex justify-end gap-[2px]">
      <button
        type="button"
        className={hasPrev ? normal : disabled}
        onClick={() => hasPrev && onPage(1)}
      >
        &laquo;
      </button>
      <button
        type="button"
        className={hasPrev ? normal : disabled}
        onClick={() => hasPrev && onPage(currentPage - 1)}
      >
        &lsaquo;
      </button>
      {pageNums.map((n) => (
        <button
          key={n}
          type="button"
          className={n === currentPage ? active : normal}
          onClick={() => onPage(n)}
        >
          {n}
        </button>
      ))}
      <button
        type="button"
        className={hasNext ? normal : disabled}
        onClick={() => hasNext && onPage(currentPage + 1)}
      >
        &rsaquo;
      </button>
      <button
        type="button"
        className={hasNext ? normal : disabled}
        onClick={() => hasNext && onPage(allPageCount)}
      >
        &raquo;
      </button>
    </div>
  );
}

function calcPageNums(current: number, total: number): number[] {
  let start = current - 2;
  let end = current + 2;
  if (start < 1) {
    start = 1;
    end = Math.min(5, total);
  }
  if (end > total) {
    end = total;
    start = Math.max(1, total - 4);
  }
  const pages: number[] = [];
  for (let i = start; i <= end; i++) pages.push(i);
  return pages;
}

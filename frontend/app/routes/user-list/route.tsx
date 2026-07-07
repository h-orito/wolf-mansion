import { Link, useSearchParams } from "react-router";

import { Heading } from "~/components/ui/Heading";
import { Pagination } from "~/components/ui/Pagination";
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
          <Pagination
            currentPage={pageNum}
            allPageCount={allPageCount}
            pageNums={calcPageNums(pageNum, allPageCount)}
            hasPrev={pageNum > 1}
            hasNext={pageNum < allPageCount}
            onPage={goToPage}
          />
        )}

        {players.length > 0 && (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse text-[10.5px]">
              <thead>
                <tr>
                  <th className="border border-border p-[5px] text-left font-bold">ユーザー名</th>
                </tr>
              </thead>
              <tbody>
                {players.map((p) => (
                  <tr key={p.name}>
                    <td className="border border-border p-[5px]">
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
          <Pagination
            currentPage={pageNum}
            allPageCount={allPageCount}
            pageNums={calcPageNums(pageNum, allPageCount)}
            hasPrev={pageNum > 1}
            hasNext={pageNum < allPageCount}
            onPage={goToPage}
          />
        )}
      </div>
    </PageLayout>
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

import { Pagination, paginationStyles } from "~/components/ui/Pagination";
import type { VillageMessageListContent } from "~/features/village/api";

export type PageState = {
  pageNum: number;
  isDispLatest: boolean;
};

export function MessagePagination({
  content,
  onChange,
}: {
  content: VillageMessageListContent;
  onChange: (page: PageState) => void;
}) {
  if (content.allPageCount === 1) return null;

  const allPageCount = content.allPageCount;
  // 「最新」表示は最終ページの次にある仮想ページとして扱う。
  // これにより < は最終ページ、> >> は範囲外になり最終ページへ丸められ、<< は 1 ページ目に移動できる。
  const currentPageNum = content.isDispLatest ? allPageCount + 1 : (content.currentPageNum ?? 0);
  const goto = (pageNum: number) => {
    if (pageNum < 1) return;
    onChange({ pageNum: Math.min(pageNum, allPageCount), isDispLatest: false });
  };

  return (
    <Pagination
      currentPage={currentPageNum}
      allPageCount={allPageCount}
      pageNums={content.pageNumList ?? []}
      hasPrev={content.isDispLatest || content.isExistPrePage}
      hasNext={content.isDispLatest || content.isExistNextPage}
      onPage={goto}
    >
      <li>
        <button
          type="button"
          className={content.isDispLatest ? paginationStyles.active : paginationStyles.enabled}
          onClick={() => onChange({ pageNum: 1, isDispLatest: true })}
        >
          最新
        </button>
      </li>
    </Pagination>
  );
}

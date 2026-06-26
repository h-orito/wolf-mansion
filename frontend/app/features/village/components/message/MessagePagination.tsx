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

  const currentPageNum = content.currentPageNum ?? 0;
  const goto = (pageNum: number) => {
    if (pageNum < 1) return;
    onChange({ pageNum, isDispLatest: false });
  };

  return (
    <Pagination
      currentPage={currentPageNum}
      allPageCount={content.allPageCount}
      pageNums={content.pageNumList ?? []}
      hasPrev={content.isExistPrePage}
      hasNext={content.isExistNextPage}
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

import { useEffect, useState } from "react";

import { useDisplaySettings } from "~/features/village/displaySettings";
import type { MessageFilter } from "~/features/village/filter";
import type { PageState } from "~/features/village/components/message/MessagePagination";

const initialPage = (day: number | undefined): PageState =>
  day == null ? { pageNum: 1, isDispLatest: true } : { pageNum: 1, isDispLatest: false };

export function useMessagePaging(day: number | undefined, filter: MessageFilter) {
  const [page, setPage] = useState<PageState>(() => initialPage(day));

  const isPaging = useDisplaySettings((s) => s.isPaging);
  const pageSize = useDisplaySettings((s) => s.pageSize);

  const filterKey = JSON.stringify(filter);
  useEffect(() => {
    setPage(initialPage(day));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [day, filterKey, isPaging, pageSize]);

  const resetToLatest = () => setPage({ pageNum: 1, isDispLatest: true });

  return { page, setPage, isPaging, pageSize, resetToLatest };
}

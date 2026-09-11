import type { ReactNode } from "react";

const itemBaseClass =
  "-ml-px rounded-none border border-transparent px-[14px] py-[2px] text-white border-l-success-dark mb-[2px]";

export const paginationStyles = {
  enabled: `${itemBaseClass} cursor-pointer bg-success hover:bg-success-light`,
  active: `${itemBaseClass} bg-success-light`,
  disabled: `${itemBaseClass} cursor-not-allowed bg-success-muted`,
};

export function Pagination({
  currentPage,
  allPageCount,
  pageNums,
  hasPrev,
  hasNext,
  onPage,
  children,
}: {
  currentPage: number;
  allPageCount: number;
  pageNums: number[];
  hasPrev: boolean;
  hasNext: boolean;
  onPage: (page: number) => void;
  children?: ReactNode;
}) {
  return (
    <div className="flex justify-end">
      <ul className="my-[10px] flex [&>li:first-child>button]:ml-0 [&>li:first-child>button]:rounded-l-[4px] [&>li:last-child>button]:rounded-r-[4px]">
        <li>
          <button
            type="button"
            className={hasPrev ? paginationStyles.enabled : paginationStyles.disabled}
            disabled={!hasPrev}
            onClick={() => onPage(1)}
          >
            &lt;&lt;
          </button>
        </li>
        <li>
          <button
            type="button"
            className={hasPrev ? paginationStyles.enabled : paginationStyles.disabled}
            disabled={!hasPrev}
            onClick={() => onPage(currentPage - 1)}
          >
            &lt;
          </button>
        </li>
        {pageNums.map((n) => (
          <li key={n}>
            <button
              type="button"
              className={n === currentPage ? paginationStyles.active : paginationStyles.enabled}
              onClick={() => onPage(n)}
            >
              {n}
            </button>
          </li>
        ))}
        <li>
          <button
            type="button"
            className={hasNext ? paginationStyles.enabled : paginationStyles.disabled}
            disabled={!hasNext}
            onClick={() => onPage(currentPage + 1)}
          >
            &gt;
          </button>
        </li>
        <li>
          <button
            type="button"
            className={hasNext ? paginationStyles.enabled : paginationStyles.disabled}
            disabled={!hasNext}
            onClick={() => onPage(allPageCount)}
          >
            &gt;&gt;
          </button>
        </li>
        {children}
      </ul>
    </div>
  );
}

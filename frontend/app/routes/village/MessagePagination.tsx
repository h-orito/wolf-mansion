import type { VillageMessageListContent } from "~/features/village/api";

export type PageState = {
  pageNum: number;
  isDispLatest: boolean;
};

const itemBaseClass =
  "-ml-px border border-transparent px-[14px] py-[2px] text-[12px] text-white first:ml-0 first:rounded-l-[4px] last:rounded-r-[4px] border-l-[#009c6c] mb-[2px]";
const enabledClass = `${itemBaseClass} cursor-pointer bg-[#00bc8c] hover:bg-[#00dba3]`;
const activeClass = `${itemBaseClass} bg-[#00dba3]`;
const disabledClass = `${itemBaseClass} cursor-not-allowed bg-[#007053]`;

/**
 * 発言一覧のページング (1 ページのみの日は出さない)。
 * 先頭/前/番号/次/末尾/最新。「最新」は最終ページを最新発言が下になる向きで表示する。
 */
export function MessagePagination({
  content,
  onChange,
}: {
  content: VillageMessageListContent;
  onChange: (page: PageState) => void;
}) {
  if (content.allPageCount === 1) return null;

  const currentPageNum = content.currentPageNum;
  const goto = (pageNum: number) => onChange({ pageNum, isDispLatest: false });

  return (
    <div className="flex justify-end">
      <ul className="my-[10px] flex">
        <li>
          <button
            type="button"
            className={content.isExistPrePage ? enabledClass : disabledClass}
            disabled={!content.isExistPrePage}
            onClick={() => goto(1)}
          >
            &lt;&lt;
          </button>
        </li>
        <li>
          <button
            type="button"
            className={content.isExistPrePage ? enabledClass : disabledClass}
            disabled={!content.isExistPrePage || currentPageNum == null}
            onClick={() => currentPageNum != null && goto(currentPageNum - 1)}
          >
            &lt;
          </button>
        </li>
        {(content.pageNumList ?? []).map((pageNum) => (
          <li key={pageNum}>
            <button
              type="button"
              className={pageNum === currentPageNum ? activeClass : enabledClass}
              onClick={() => goto(pageNum)}
            >
              {pageNum}
            </button>
          </li>
        ))}
        <li>
          <button
            type="button"
            className={content.isExistNextPage ? enabledClass : disabledClass}
            disabled={!content.isExistNextPage || currentPageNum == null}
            onClick={() => currentPageNum != null && goto(currentPageNum + 1)}
          >
            &gt;
          </button>
        </li>
        <li>
          <button
            type="button"
            className={content.isExistNextPage ? enabledClass : disabledClass}
            disabled={!content.isExistNextPage}
            onClick={() => goto(content.allPageCount)}
          >
            &gt;&gt;
          </button>
        </li>
        <li>
          <button
            type="button"
            className={content.isDispLatest ? activeClass : enabledClass}
            onClick={() => onChange({ pageNum: 1, isDispLatest: true })}
          >
            最新
          </button>
        </li>
      </ul>
    </div>
  );
}

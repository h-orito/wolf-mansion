import { useState } from "react";

import { Button, LinkButton } from "~/components/ui/Button";
import { Heading } from "~/components/ui/Heading";
import { inlineInputClass } from "~/components/ui/Input";
import { PageLayout } from "~/components/layout/PageLayout";
import type { RandomKeyword } from "~/features/random-keywords/api";
import { useRandomKeywords } from "~/features/random-keywords/useRandomKeywords";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("ランダムメッセージ一覧");
}

/** 折りたたみ時に表示する変換後文字列の行数。 */
const VISIBLE_LINES = 5;

const thClass = "border border-[#464545] p-[5px] text-left align-bottom";
const cellClass = "border border-[#464545] p-[5px]";

function matches(keyword: RandomKeyword, term: string): boolean {
  return (
    `[[${keyword.keyword}]]`.includes(term) ||
    keyword.contents.some((content) => content.message.includes(term))
  );
}

/** 変換後文字列セル。先頭 5 行のみ表示し、残りは「全て表示」で展開する。 */
function ContentCell({ keyword }: { keyword: RandomKeyword }) {
  const [expanded, setExpanded] = useState(false);
  const messages = keyword.contents.map((content) => content.message);
  const visible = expanded ? messages : messages.slice(0, VISIBLE_LINES);
  return (
    <td className={`${cellClass} align-top`}>
      {visible.map((message, i) => (
        // 同一行の重複は登録できないため行テキストを key にできる
        <span key={message}>
          {i > 0 && <br />}
          {message}
        </span>
      ))}
      {!expanded && messages.length > VISIBLE_LINES && (
        <>
          <br />
          <button
            type="button"
            className="cursor-pointer text-wm-accent underline"
            onClick={() => setExpanded(true)}
          >
            全て表示
          </button>
        </>
      )}
    </td>
  );
}

async function copyKeyword(keyword: string) {
  const text = `[[${keyword}]]`;
  try {
    await navigator.clipboard.writeText(text);
    alert(`コピーしました： ${text}`);
  } catch {
    alert("コピーに失敗しました");
  }
}

export default function RandomMessage() {
  const { data: keywords } = useRandomKeywords();
  const [searchText, setSearchText] = useState("");
  const [searchTerm, setSearchTerm] = useState("");

  const filtered = keywords?.filter((k) => searchTerm.length === 0 || matches(k, searchTerm));

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>ランダムメッセージ一覧</Heading>
        <div className="mb-[5px] flex gap-[2px]">
          <input
            type="text"
            className={`${inlineInputClass} w-[154px]`}
            placeholder="キーワード絞り込み"
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
          />
          <Button onClick={() => setSearchTerm(searchText)}>検索</Button>
        </div>
        <div className="overflow-x-auto">
          <table className="mb-[21px] w-full border-collapse text-[10.32px]">
            <thead>
              <tr>
                <th className={thClass}>キーワード</th>
                <th className={thClass}>変換後</th>
                <th className={thClass}></th>
              </tr>
            </thead>
            <tbody>
              {keywords && keywords.length === 0 && (
                <tr>
                  <td className={cellClass} colSpan={2}>
                    登録されているキーワードがありません。
                  </td>
                </tr>
              )}
              {filtered?.map((keyword) => (
                <tr key={keyword.id}>
                  <td className={`${cellClass} align-middle`}>[[{keyword.keyword}]]</td>
                  <ContentCell keyword={keyword} />
                  <td className={`${cellClass} align-top`}>
                    <span className="inline-flex items-center gap-[2px]">
                      <LinkButton to={`/random-keyword/${keyword.id}`}>編集</LinkButton>
                      <Button onClick={() => copyKeyword(keyword.keyword)}>キーワードコピー</Button>
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <LinkButton to="/new-random-keyword">新規追加</LinkButton>
      </div>
    </PageLayout>
  );
}

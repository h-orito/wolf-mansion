import { Heading } from "~/components/ui/Heading";
import { ExternalLink, TextLink } from "~/components/ui/TextLink";
import { PageLayout } from "~/components/layout/PageLayout";
import { useCharachipList } from "~/features/charachips/useCharachips";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("キャラチップ一覧");
}

export default function CharaGroupList() {
  const { data: charachips } = useCharachipList();

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>キャラチップ一覧</Heading>

        {charachips && charachips.length > 0 && (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse text-xs">
              <thead>
                <tr>
                  <th className="border border-border p-[5px] align-middle">キャラチップ名</th>
                  <th className="border border-border p-[5px] align-middle">作者名</th>
                  <th className="border border-border p-[5px] align-middle">
                    キャラ
                    <br />
                    チップ数
                  </th>
                  <th className="border border-border p-[5px] align-middle">例</th>
                </tr>
              </thead>
              <tbody>
                {charachips.map((chip) => (
                  <tr key={chip.id}>
                    <td className="border border-border p-[5px] align-middle">
                      <TextLink to={`/chara-group/${chip.id}`}>{chip.name}</TextLink>
                    </td>
                    <td className="border border-border p-[5px] align-middle">
                      {chip.designerName}
                    </td>
                    <td className="border border-border p-[5px] align-middle">{chip.charaNum}人</td>
                    <td className="border border-border p-[5px] align-middle">
                      <img
                        src={chip.dummyImgUrl}
                        width={chip.dummyImgWidth}
                        height={chip.dummyImgHeight}
                        alt={chip.name}
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        <Heading as="h2">キャラチップ製作者様へ</Heading>
        <ul className="mb-[10.5px] list-disc pl-[20px]">
          <li>キャラチップを提供いただきありがとうございます。</li>
          <li>
            各国での実装を補助する目的で、設定ファイル（の一部）を生成するための簡易ツールを作成いたしました。
          </li>
          <li>
            <ExternalLink href="https://docs.google.com/spreadsheets/d/1dtq4ZJRBN0U9uEylh9kjh9QsNu0owEZ6PB4lZlIKI2M/edit?usp=sharing">
              キャラチップ設定ファイル作成補助
            </ExternalLink>
          </li>
          <li>
            もしよろしければ、利用いただき、キャラチップ画像と共にzipに含めていただけますと幸いです。
          </li>
        </ul>
      </div>
    </PageLayout>
  );
}

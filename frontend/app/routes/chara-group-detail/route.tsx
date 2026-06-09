import { Heading } from "~/components/ui/Heading";
import { ExternalLink } from "~/components/ui/TextLink";
import { PageLayout } from "~/components/layout/PageLayout";
import { useCharachipDetail } from "~/features/charachips/useCharachips";
import { siteMeta } from "~/lib/meta";
import { useParams } from "react-router";
import type { RoomAssignmentCellView } from "~/features/charachips/api";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("キャラチップ詳細");
}

export default function CharaGroupDetail() {
  const { id } = useParams();
  const charachipId = Number(id);

  if (!id || Number.isNaN(charachipId)) {
    return (
      <PageLayout>
        <div className="px-[15px] pb-[10px]">
          <Heading>キャラチップが見つかりません</Heading>
        </div>
      </PageLayout>
    );
  }

  return <CharaGroupDetailContent charachipId={charachipId} />;
}

function CharaGroupDetailContent({ charachipId }: { charachipId: number }) {
  const { data: detail, error } = useCharachipDetail(charachipId);

  if (error) {
    return (
      <PageLayout>
        <div className="px-[15px] pb-[10px]">
          <Heading>キャラチップが見つかりません</Heading>
        </div>
      </PageLayout>
    );
  }

  if (!detail) {
    return (
      <PageLayout>
        <div className="px-[15px] pb-[10px]" />
      </PageLayout>
    );
  }

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>キャラチップ: {detail.name}</Heading>
        <div className="mb-[10px]">
          <p>作者: {detail.designerName}様</p>
          <p>肩書・名称変更: {detail.isAvailableChangeName ? "可能" : "不可"}</p>
          {detail.descriptionUrl && (
            <ExternalLink href={detail.descriptionUrl}>作者様HP</ExternalLink>
          )}
        </div>

        <div className="flex flex-wrap">
          {detail.charas.map((chara) => (
            <div
              key={chara.id}
              className="box-border w-full border border-[#464545] p-[5px] min-[768px]:w-1/2"
            >
              <span className="block text-center">
                {chara.imageUrls.map((url, i) => (
                  <img
                    key={i}
                    src={url}
                    width={chara.width}
                    height={chara.height}
                    alt={chara.name}
                    className="inline-block"
                  />
                ))}
              </span>
              <span className="block text-center">
                [{chara.shortName}] {chara.name}
              </span>
            </div>
          ))}
        </div>

        <div className="mt-[20px] overflow-x-auto">
          <Heading as="h2">部屋割り例</Heading>
          <RoomAssignmentTable
            rows={detail.roomAssignment.rows}
            maxCharaWidth={detail.roomAssignment.maxCharaWidth}
            maxCharaHeight={detail.roomAssignment.maxCharaHeight}
          />
        </div>
      </div>
    </PageLayout>
  );
}

function RoomAssignmentTable({
  rows,
  maxCharaWidth,
  maxCharaHeight,
}: {
  rows: { cells: RoomAssignmentCellView[] }[];
  maxCharaWidth: number;
  maxCharaHeight: number;
}) {
  return (
    <table className="border-collapse text-xs">
      <tbody>
        {rows.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {row.cells.map((cell, cellIndex) => (
              <td
                key={cellIndex}
                className="relative border border-[#464545] p-0 text-center align-middle"
                style={{
                  width: `${maxCharaWidth}px`,
                  minWidth: `${maxCharaWidth}px`,
                  height: `${maxCharaHeight}px`,
                }}
              >
                <div
                  className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-contain bg-center bg-no-repeat"
                  title={cell.charaName ?? undefined}
                  style={{
                    width: `${cell.charaImgWidth ?? maxCharaWidth}px`,
                    height: `${cell.charaImgHeight ?? maxCharaHeight}px`,
                    backgroundImage: cell.charaImgUrl ? `url('${cell.charaImgUrl}')` : undefined,
                  }}
                />
                <div className="absolute bottom-0 left-1/2 -translate-x-1/2 opacity-80">
                  <span className="whitespace-nowrap bg-[#222222]">
                    {cell.roomNumber} {cell.charaShortName ?? ""}
                  </span>
                </div>
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

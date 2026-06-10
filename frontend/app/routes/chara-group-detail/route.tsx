import { useMemo } from "react";
import { useParams } from "react-router";

import { Heading } from "~/components/ui/Heading";
import { ExternalLink } from "~/components/ui/TextLink";
import { PageLayout } from "~/components/layout/PageLayout";
import type { Chara, RoomAssignmentResponse } from "~/features/charachips/api";
import { useCharachipDetail, useRoomAssignment } from "~/features/charachips/useCharachips";
import { siteMeta } from "~/lib/meta";
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
  const { data: charachip, error } = useCharachipDetail(charachipId);
  const personNum = charachip?.charas.list.length ?? 0;
  const { data: roomData } = useRoomAssignment(personNum, personNum > 0);

  if (error) {
    return (
      <PageLayout>
        <div className="px-[15px] pb-[10px]">
          <Heading>キャラチップが見つかりません</Heading>
        </div>
      </PageLayout>
    );
  }

  if (!charachip) {
    return (
      <PageLayout>
        <div className="px-[15px] pb-[10px]" />
      </PageLayout>
    );
  }

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>キャラチップ: {charachip.name}</Heading>
        <div className="mb-[10px]">
          <p>作者: {charachip.designer?.name ?? ""}様</p>
          <p>肩書・名称変更: {charachip.isAvailableChangeName ? "可能" : "不可"}</p>
          {charachip.descriptionUrl && (
            <ExternalLink href={charachip.descriptionUrl}>作者様HP</ExternalLink>
          )}
        </div>

        <div className="flex flex-wrap">
          {charachip.charas.list.map((chara) => (
            <div
              key={chara.id}
              className="box-border w-full border border-[#464545] p-[5px] min-[768px]:w-1/2"
            >
              <span className="block text-center">
                {chara.images.list.map((img, i) => (
                  <img
                    key={i}
                    src={img.url}
                    width={chara.size.width}
                    height={chara.size.height}
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

        {roomData && (
          <div className="mt-[20px] overflow-x-auto">
            <Heading as="h2">部屋割り例</Heading>
            <RoomAssignmentTable charas={charachip.charas.list} roomData={roomData} />
          </div>
        )}
      </div>
    </PageLayout>
  );
}

function RoomAssignmentTable({
  charas,
  roomData,
}: {
  charas: Chara[];
  roomData: RoomAssignmentResponse;
}) {
  const { grid, maxWidth, maxHeight } = useMemo(() => {
    const roomNumberToChara = new Map<number, Chara>();
    roomData.roomNumbers.forEach((num, i) => {
      if (i < charas.length) roomNumberToChara.set(num, charas[i]);
    });
    const mw = charas.reduce((max, c) => Math.max(max, c.size.width), 0);
    const mh = charas.reduce((max, c) => Math.max(max, c.size.height), 0);
    const rows = Array.from({ length: roomData.height }, (_, rowIdx) =>
      Array.from({ length: roomData.width }, (_, colIdx) => {
        const roomNumber = roomData.width * rowIdx + colIdx + 1;
        const chara = roomNumberToChara.get(roomNumber);
        return { roomNumber, chara };
      }),
    );
    return { grid: rows, maxWidth: mw, maxHeight: mh };
  }, [charas, roomData]);

  return (
    <table className="border-collapse text-xs">
      <tbody>
        {grid.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {row.map((cell) => {
              const defaultImg =
                cell.chara?.images.list.find((img) => img.faceType.code === "NORMAL") ??
                cell.chara?.images.list[0];
              return (
                <td
                  key={cell.roomNumber}
                  className="relative border border-[#464545] p-0 text-center align-middle"
                  style={{
                    width: `${maxWidth}px`,
                    minWidth: `${maxWidth}px`,
                    height: `${maxHeight}px`,
                  }}
                >
                  <div
                    className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-contain bg-center bg-no-repeat"
                    title={cell.chara?.name ?? undefined}
                    style={{
                      width: `${cell.chara?.size.width ?? maxWidth}px`,
                      height: `${cell.chara?.size.height ?? maxHeight}px`,
                      backgroundImage: defaultImg ? `url('${defaultImg.url}')` : undefined,
                    }}
                  />
                  <div className="absolute bottom-0 left-1/2 -translate-x-1/2 opacity-80">
                    <span className="whitespace-nowrap bg-[#222222]">
                      {String(cell.roomNumber).padStart(2, "0")} {cell.chara?.shortName ?? ""}
                    </span>
                  </div>
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

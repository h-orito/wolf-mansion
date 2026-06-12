import { useEffect } from "react";
import { Link } from "react-router";

import { PageLayout } from "~/components/layout/PageLayout";
import { LinkButton } from "~/components/ui/Button";
import { useMe } from "~/features/auth/useMe";
import type { VillageDetailView } from "~/features/village/api";
import {
  useInvalidateVillage,
  useMyVillageSituation,
  useVillage,
  useVillagePolling,
  useVillageSituation,
} from "~/features/village/useVillage";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import { DayList } from "./DayList";
import { FooterMenu } from "./FooterMenu";
import { SituationPanel } from "./SituationPanel";
import { useCountdown } from "./useCountdown";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  // 村名は CSR で取得するため、確定後に document.title を村名へ差し替える (タイトルは村名のみ)
  return siteMeta();
}

function latestDayOf(village: VillageDetailView): number {
  const days = village.days.list ?? [];
  return days.length > 0 ? days[days.length - 1].day : 0;
}

/** 村番号は 4 桁 0 埋めで表示する。 */
function villageNumber(id: number): string {
  return String(id).padStart(4, "0");
}

function formatStartDatetime(iso: string): string {
  const d = new Date(iso);
  const pad = (n: number) => String(n).padStart(2, "0");
  return `${d.getFullYear()}/${pad(d.getMonth() + 1)}/${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}`;
}

/**
 * 村の共有ツイートボタン。村名 (募集中は開始予定日時も) + ハッシュタグを共有する。
 * 公式 widget script は読み込まず、同じ共有 URL を開くボタンで代替する。
 */
function TweetButton({ village }: { village: VillageDetailView }) {
  const lines = [village.name];
  if (latestDayOf(village) === 0) {
    lines.push(`開始予定: ${formatStartDatetime(village.setting.startDatetime)}`);
  }
  const url = `https://twitter.com/share?text=${encodeURIComponent(lines.join("\n") + "\n")}&hashtags=WOLF_MANSION`;
  return (
    <a
      href={url}
      target="_blank"
      rel="noreferrer"
      className="rounded-full bg-[#1d9bf0] px-[12px] py-[4px] text-[11px] font-bold text-white hover:bg-[#0c7abf]"
    >
      ツイート
    </a>
  );
}

export default function Village({ params }: Route.ComponentProps) {
  const villageId = Number(params.villageId);
  const dayParam = params.day != null ? Number(params.day) : undefined;

  const { me } = useMe();
  const { data: village, error: villageError } = useVillage(villageId);
  const { data: situation } = useVillageSituation(villageId, dayParam, me?.name ?? null);
  const { error: mySituationError } = useMyVillageSituation(villageId, dayParam);
  const invalidate = useInvalidateVillage(villageId);

  const latestDay = village != null ? latestDayOf(village) : undefined;
  const currentDay = dayParam ?? latestDay ?? 0;

  const daychangeDetected = useVillagePolling(villageId, latestDay);
  // ログイン中のはずなのに認証が立て直せない (refresh 失敗) 場合は再ログインを促す
  const sessionExpired =
    me != null && mySituationError instanceof ApiError && mySituationError.status === 401;

  const dayChangeDatetime =
    village != null && !village.status.isFinished
      ? (village.days.list?.[village.days.list.length - 1]?.dayChangeDatetime ?? null)
      : null;
  const leftTime = useCountdown(dayChangeDatetime);

  useEffect(() => {
    if (village != null) document.title = `WOLF MANSION | ${village.name}`;
  }, [village]);

  if (villageError instanceof ApiError && villageError.status === 404) {
    return (
      <PageLayout>
        <div className="px-[15px] py-[30px]">村が見つかりませんでした。</div>
      </PageLayout>
    );
  }
  if (village == null) {
    return (
      <PageLayout>
        <div className="px-[15px] py-[30px] text-gray-400">読み込み中...</div>
      </PageLayout>
    );
  }

  const noAd = (village.setting.tags.list ?? []).some((tag) => tag.code === "R18");

  return (
    <PageLayout noAd={noAd}>
      <div className="px-[15px] pb-[45px]">
        {/* 村タイトル */}
        <div className="flex">
          <h1 className="my-[10.5px] flex-1 text-[15px] font-normal">
            {villageNumber(village.id)}. {village.name}
          </h1>
          <div className="my-[10.5px]">
            <TweetButton village={village} />
          </div>
        </div>
        <hr className="mt-[5px] mb-[10px] border-[#464545]" />

        <DayList
          villageId={villageId}
          dayList={(village.days.list ?? []).map((d) => d.day)}
          currentDay={currentDay}
          epilogueDay={village.epilogueDay}
        />

        {/* 発言ログ (メッセージ表示は未実装) */}
        <div className="text-gray-400">読み込み中...</div>
        {!noAd && (
          <div className="mt-[15px] min-h-[90px] border border-dashed border-gray-600 p-2 text-center text-gray-400">
            広告（移行中はプレースホルダー）
          </div>
        )}
        <hr className="mt-[5px] mb-[10px] border-[#464545]" />

        {/* 最下部への移動先 (発言後のスクロール先) */}
        <div id="bottom" />

        {situation != null && <SituationPanel situation={situation} day={currentDay} />}

        <DayList
          villageId={villageId}
          dayList={(village.days.list ?? []).map((d) => d.day)}
          currentDay={currentDay}
          epilogueDay={village.epilogueDay}
        />

        <div className="mb-[10px]">
          <LinkButton to="/" variant="default">
            サイトトップへ
          </LinkButton>
          <LinkButton
            to={`/village/${villageId}/scrap`}
            target="_blank"
            variant="success"
            className="ml-[10px]"
          >
            切り抜き画面へ
          </LinkButton>
        </div>
      </div>

      <FooterMenu onRefresh={() => invalidate()} />

      {/* 通知系のオーバーレイ */}
      {daychangeDetected && (
        <div className="fixed top-0 right-[5px] left-[5px] z-[105] rounded bg-[#00bc8c] p-[15px] text-white">
          日付が更新されました。ページを再読み込みしてください。
        </div>
      )}
      {leftTime != null && (
        <div className="fixed top-[5px] right-[5px] z-[100] rounded bg-[#3498db] p-[5px] text-white">
          更新まで <span>{leftTime}</span>
        </div>
      )}
      {me != null && !sessionExpired && (
        <Link to={`/user/${me.name}`} target="_blank">
          <div className="fixed top-[5px] left-[5px] z-[100] rounded bg-[#3498db] p-[5px] text-white">
            ユーザID: {me.name}
          </div>
        </Link>
      )}
      {sessionExpired && (
        <div className="fixed top-[5px] left-[5px] z-[100] rounded bg-[#e74c3c] p-[5px] text-white">
          要再ログイン
        </div>
      )}
    </PageLayout>
  );
}

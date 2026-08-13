import { toBlob, toPng } from "html-to-image";
import { useEffect, useRef, useState } from "react";

import { PageLayout } from "~/components/layout/PageLayout";
import { Button, LinkButton } from "~/components/ui/Button";
import { selectClass } from "~/components/ui/Input";
import { XPostLink } from "~/components/ui/XPostLink";
import { MESSAGE_STYLES } from "~/components/ui/messageStyles";
import { useMe } from "~/features/auth/useMe";
import { useRandomKeywordList } from "~/features/random-keywords/useRandomKeywords";
import { SAY_TYPE_ORDER } from "~/features/village/components/action/SayPanel";
import { SAY_VARIANTS } from "~/features/village/components/message/message";
import { MessageType } from "~/features/village/components/message/messageType";
import { AgeLimitModal } from "~/features/village/components/modal/AgeLimitModal";
import {
  type ReportOptions,
  ReportPreview,
} from "~/features/village/components/report/ReportPreview";
import { useMyVillageSituation, useVillage } from "~/features/village/useVillage";
import { VillageProvider } from "~/features/village/VillageContext";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta();
}

/**
 * 参加報告メーカー。エピローグ以降の村で、参加した本人のキャラクターについて
 * 発言欄と同じ見た目の吹き出しに好きな発言を入れた画像を作成し、
 * X への参加報告投稿を補助する。
 */
export default function VillageReport({ params }: Route.ComponentProps) {
  const villageId = Number(params.villageId);
  const { data: village, error: villageError } = useVillage(villageId);
  const { me, isLoading: meLoading } = useMe();
  const { data: mySituation, isLoading: mySituationLoading } = useMyVillageSituation(
    villageId,
    undefined,
  );
  const randomKeywords = useRandomKeywordList();

  const [faceTypeCode, setFaceTypeCode] = useState<string | null>(null);
  const [messageType, setMessageType] = useState<string>(MessageType.NORMAL_SAY);
  const [messageText, setMessageText] = useState("");
  const [convertDisable, setConvertDisable] = useState(false);
  const [options, setOptions] = useState<ReportOptions>({
    showSkillHistory: true,
    showSkillDescription: true,
    showAbilityHistory: true,
  });
  const [notice, setNotice] = useState<string | null>(null);
  const previewRef = useRef<HTMLDivElement>(null);

  // 参加した本人のキャラクターのみ対象にする (/situation/me はサーバ側で本人分しか返さない)
  const participant = mySituation?.myself ?? null;
  const images = participant?.chara.images.list.filter((image) => image.isDisplay) ?? [];
  const faceImage = images.find((i) => i.faceType.code === faceTypeCode) ?? images[0] ?? null;
  const abilityHistories = participant != null ? (mySituation?.ability.skillHistoryList ?? []) : [];

  const download = async () => {
    const node = previewRef.current;
    if (node == null || village == null) return;
    setNotice(null);
    try {
      const dataUrl = await toPng(node, { pixelRatio: 2, cacheBust: true });
      const anchor = document.createElement("a");
      anchor.href = dataUrl;
      anchor.download = `wolf-mansion-${String(village.id).padStart(4, "0")}-report.png`;
      anchor.click();
      setNotice("画像を保存しました。X の投稿画面で添付してください。");
    } catch {
      setNotice(
        "画像の生成に失敗しました。キャラクター画像の読み込みがブロックされた可能性があります。",
      );
    }
  };

  const copy = async () => {
    const node = previewRef.current;
    if (node == null) return;
    setNotice(null);
    if (typeof ClipboardItem === "undefined") {
      setNotice("このブラウザは画像コピーに対応していません。「画像を保存」を使ってください。");
      return;
    }
    try {
      const blob = await toBlob(node, { pixelRatio: 2, cacheBust: true });
      if (blob == null) throw new Error("blob is null");
      await navigator.clipboard.write([new ClipboardItem({ "image/png": blob })]);
      setNotice("画像をコピーしました。X の投稿画面に貼り付けてください。");
    } catch {
      setNotice("画像のコピーに失敗しました。「画像を保存」を使ってください。");
    }
  };

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
        <div className="px-[15px] pb-[30px]" />
      </PageLayout>
    );
  }

  const noAd = (village.setting.tags.list ?? []).some((tag) => tag.code === "R18");
  const ageLimit = (village.setting.tags.list ?? []).find(
    (tag) => tag.code === "R15" || tag.code === "R18",
  )?.name;

  if (!village.status.isSettled) {
    return (
      <PageLayout noAd={noAd}>
        <div className="px-[15px] py-[30px]">
          <p>参加報告メーカーはエピローグ以降に利用できます。</p>
          <LinkButton to={`/village/${villageId}`} variant="default" className="mt-[10px]">
            村へ
          </LinkButton>
        </div>
      </PageLayout>
    );
  }

  // ログイン判定・本人情報の取得が終わるまではガード文言を出さない (ちらつき防止)
  if (meLoading || mySituationLoading) {
    return (
      <PageLayout noAd={noAd}>
        <div className="px-[15px] pb-[30px]" />
      </PageLayout>
    );
  }

  if (participant == null) {
    return (
      <PageLayout noAd={noAd}>
        <div className="px-[15px] py-[30px]">
          <p>参加報告メーカーは、この村に参加した本人のみ利用できます。</p>
          {me == null && <p>参加したユーザーでログインしてください。</p>}
          <div className="mt-[10px] flex gap-[10px]">
            {me == null && (
              <LinkButton to="/login" variant="success">
                ログイン
              </LinkButton>
            )}
            <LinkButton to={`/village/${villageId}`} variant="default">
              村へ
            </LinkButton>
          </div>
        </div>
      </PageLayout>
    );
  }

  const pageUrl = `${globalThis.location?.origin ?? ""}/wolf-mansion/village/${village.id}`;
  const postText = `${village.name}\n${participant.name}\n`;

  const textareaStyle =
    MESSAGE_STYLES[SAY_VARIANTS[messageType]?.styleKey ?? "message-normal"] ??
    "bg-white text-[#555]";

  return (
    <PageLayout noAd={noAd}>
      <VillageProvider value={village}>
        <div className="px-[15px] pb-[30px]">
          <h1 className="my-[10.5px] text-[15px] font-normal">
            {String(village.id).padStart(4, "0")}. {village.name} - 参加報告メーカー
          </h1>
          <hr className="mt-[5px] mb-[10px] border-border" />

          <p className="mb-[10px] text-village-sm text-gray-300">
            発言欄と同じ見た目で好きな発言を入れた画像を作成できます。X
            の投稿画面には画像は自動添付されないため、保存またはコピーした画像を添付してください。
          </p>

          <p className="mb-[10px]">{participant.name}</p>

          {/* 発言種別 */}
          <div className="mb-[10px] flex flex-wrap">
            {SAY_TYPE_ORDER.map((t) => {
              const active = messageType === t.code;
              return (
                <button
                  key={t.code}
                  type="button"
                  className={`not-first:-ml-px cursor-pointer border border-success px-[9px] py-[5px] first:rounded-l-[3px] last:rounded-r-[3px] hover:opacity-90 ${
                    active
                      ? "bg-success text-white shadow-[inset_0_3px_5px_rgba(0,0,0,0.125)]"
                      : "bg-wm-base text-success"
                  }`}
                  onClick={() => setMessageType(t.code)}
                >
                  {t.label}
                </button>
              );
            })}
          </div>

          {/* 表情 + 本文 */}
          <div className="flex">
            <div>
              {faceImage != null && (
                <img
                  src={faceImage.url}
                  alt={faceImage.faceType.name}
                  width={participant.chara.size.width}
                  height={participant.chara.size.height}
                />
              )}
              <select
                className={`${selectClass} mt-[5px]`}
                style={{ maxWidth: participant.chara.size.width }}
                value={faceImage?.faceType.code ?? ""}
                onChange={(e) => setFaceTypeCode(e.target.value)}
                aria-label="表情"
              >
                {images.map((image) => (
                  <option key={image.faceType.code} value={image.faceType.code}>
                    {image.faceType.name}
                  </option>
                ))}
              </select>
            </div>
            <textarea
              className={`ml-[5px] min-h-[150px] flex-1 rounded border border-border p-[9px] font-[sans-serif] ${textareaStyle}`}
              value={messageText}
              onChange={(e) => setMessageText(e.target.value)}
              placeholder="ロールプレイなど好きな発言を入力してください"
              aria-label="発言"
            />
          </div>
          <div className="mt-[5px] flex flex-wrap gap-x-[15px] gap-y-[5px]">
            <label className="flex cursor-pointer items-center gap-[5px]">
              <input
                type="checkbox"
                checked={convertDisable}
                onChange={() => setConvertDisable(!convertDisable)}
              />
              装飾・変換無効
            </label>
            <label className="flex cursor-pointer items-center gap-[5px]">
              <input
                type="checkbox"
                checked={options.showSkillHistory}
                onChange={() =>
                  setOptions((prev) => ({ ...prev, showSkillHistory: !prev.showSkillHistory }))
                }
              />
              役職履歴
            </label>
            <label className="flex cursor-pointer items-center gap-[5px]">
              <input
                type="checkbox"
                checked={options.showSkillDescription}
                onChange={() =>
                  setOptions((prev) => ({
                    ...prev,
                    showSkillDescription: !prev.showSkillDescription,
                  }))
                }
              />
              役職説明
            </label>
            {abilityHistories.length > 0 && (
              <label className="flex cursor-pointer items-center gap-[5px]">
                <input
                  type="checkbox"
                  checked={options.showAbilityHistory}
                  onChange={() =>
                    setOptions((prev) => ({
                      ...prev,
                      showAbilityHistory: !prev.showAbilityHistory,
                    }))
                  }
                />
                能力行使履歴
              </label>
            )}
          </div>

          {/* プレビュー (このボックスがそのまま画像になる)。スマホ想定の横幅で固定する */}
          <p className="mt-[15px] mb-[5px] text-village-sm text-gray-300">
            プレビュー（この内容がそのまま画像になります）
          </p>
          <div className="w-[400px] max-w-full border border-border">
            <div ref={previewRef}>
              <ReportPreview
                village={village}
                participant={participant}
                imageUrl={faceImage?.url ?? null}
                messageType={messageType}
                messageText={messageText}
                convertDisable={convertDisable}
                randomKeywords={randomKeywords}
                abilityHistories={abilityHistories}
                options={options}
              />
            </div>
          </div>

          {notice != null && <p className="mt-[10px] text-attention">{notice}</p>}

          <div className="mt-[10px] flex flex-wrap items-center gap-[10px]">
            <Button onClick={download}>画像を保存</Button>
            <Button variant="default" onClick={copy}>
              画像をコピー
            </Button>
            <XPostLink text={postText} pageUrl={pageUrl} />
          </div>

          <div className="mt-[15px]">
            <LinkButton to={`/village/${villageId}`} variant="default">
              村へ
            </LinkButton>
          </div>
        </div>
        {ageLimit != null && <AgeLimitModal ageLimit={ageLimit} />}
      </VillageProvider>
    </PageLayout>
  );
}

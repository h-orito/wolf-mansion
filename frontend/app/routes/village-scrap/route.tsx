import { useQuery } from "@tanstack/react-query";
import { useEffect, useRef, useState } from "react";
import { useSearchParams } from "react-router";

import { PageLayout } from "~/components/layout/PageLayout";
import { Button, LinkButton } from "~/components/ui/Button";
import { inputClass } from "~/components/ui/Input";
import { useRandomKeywords } from "~/features/random-keywords/useRandomKeywords";
import { fetchAnchorMessages } from "~/features/village/api";
import { useVillage } from "~/features/village/useVillage";
import { VillageProvider } from "~/features/village/VillageContext";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import { MessageCard } from "~/features/village/components/message/MessageCard";
import { AgeLimitModal } from "~/features/village/components/modal/AgeLimitModal";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta();
}

/**
 * アンカー入力を API 用のキー文字列に変換する。
 * 変換できない場合は null を返す。
 */
function parseAnchorInput(input: string): string | null {
  if (input.startsWith(">>*")) return "w" + input.substring(3);
  if (input.startsWith(">>#")) return "c" + input.substring(3);
  if (input.startsWith(">>s")) return "S" + input.substring(3);
  if (input.startsWith(">>=")) return "m" + input.substring(3);
  if (input.startsWith(">>?")) return "l" + input.substring(3);
  if (input.startsWith(">>_")) return "f" + input.substring(3);
  if (input.startsWith(">>@")) return "s" + input.substring(3);
  if (input.startsWith(">>-")) return "M" + input.substring(3);
  if (input.startsWith(">>+")) return "g" + input.substring(3);
  if (input.startsWith(">>a")) return "a" + input.substring(3);
  if (input.startsWith(">>")) return "n" + input.substring(2);
  return null;
}

export default function VillageScrap({ params }: Route.ComponentProps) {
  const villageId = Number(params.villageId);
  const [searchParams, setSearchParams] = useSearchParams();
  const anchors = searchParams.get("anchors") ?? "";
  const [anchorInput, setAnchorInput] = useState("");
  const inputRef = useRef<HTMLInputElement>(null);

  const { data: village, error: villageError } = useVillage(villageId);
  const { data: randomKeywords } = useRandomKeywords();
  const { data } = useQuery({
    queryKey: ["village-anchor-messages", villageId, anchors],
    queryFn: () => fetchAnchorMessages(villageId, anchors),
    enabled: anchors !== "",
    retry: false,
  });
  const keywordList = (randomKeywords ?? []).map((k) => k.keyword ?? "").filter(Boolean);

  useEffect(() => {
    if (village != null) document.title = `WOLF MANSION | ${village.name}`;
  }, [village]);

  const addAnchor = () => {
    const parsed = parseAnchorInput(anchorInput.trim());
    if (parsed == null) return;
    const next = anchors !== "" ? `${anchors}_${parsed}` : parsed;
    setSearchParams({ anchors: next });
    setAnchorInput("");
    inputRef.current?.focus();
  };

  const clearAnchors = () => {
    setSearchParams({});
  };

  if (villageError instanceof ApiError && villageError.status === 404) {
    return (
      <PageLayout>
        <div className="px-[15px] py-[30px]">村が見つかりませんでした。</div>
      </PageLayout>
    );
  }

  const noAd = (village?.setting.tags.list ?? []).some((tag) => tag.code === "R18");
  const ageLimit = (village?.setting.tags.list ?? []).find(
    (tag) => tag.code === "R15" || tag.code === "R18",
  )?.name;

  const messages = data?.messageList ?? [];

  return (
    <PageLayout noAd={noAd}>
      {village != null ? (
        <VillageProvider value={village}>
          <div className="px-[15px] pb-[30px]">
            <h1 className="my-[10.5px] text-[15px] font-normal">
              {String(village.id).padStart(4, "0")}. {village.name}
            </h1>
            <hr className="mt-[5px] mb-[10px] border-[#464545]" />

            {messages.map((message, index) => (
              <MessageCard
                key={`${message.messageType}-${message.messageNumber ?? index}`}
                message={message}
                randomKeywords={keywordList}
              />
            ))}

            <div className="mt-[10px]">
              <p className="mb-[5px] text-gray-300">
                アンカーを貼り付けて「追加」すると、発言が読み込まれます（追加も可能です）。
              </p>
              <div className="flex gap-[5px]">
                <input
                  ref={inputRef}
                  type="text"
                  className={inputClass}
                  placeholder=">>1"
                  autoFocus
                  aria-label="アンカー"
                  value={anchorInput}
                  onChange={(e) => setAnchorInput(e.target.value)}
                  onKeyDown={(e) => {
                    if (e.key === "Enter") addAnchor();
                  }}
                />
                <Button variant="success" onClick={addAnchor} className="shrink-0">
                  追加
                </Button>
              </div>
              <div className="mt-[10px] flex gap-[10px]">
                <Button variant="danger" onClick={clearAnchors}>
                  全消去
                </Button>
                <LinkButton to={`/village/${villageId}`} variant="default">
                  村へ
                </LinkButton>
              </div>
            </div>
          </div>
          {ageLimit != null && <AgeLimitModal villageId={villageId} ageLimit={ageLimit} />}
        </VillageProvider>
      ) : (
        <div className="px-[15px] pb-[30px]" />
      )}
    </PageLayout>
  );
}

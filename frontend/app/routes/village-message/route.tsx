import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import { useSearchParams } from "react-router";

import { PageLayout } from "~/components/layout/PageLayout";
import { useRandomKeywords } from "~/features/random-keywords/useRandomKeywords";
import { fetchAnchorMessages } from "~/features/village/api";
import { useVillage } from "~/features/village/useVillage";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import { MessageCard } from "~/features/village/components/message/MessageCard";
import { AgeLimitModal } from "~/features/village/components/modal/AgeLimitModal";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta();
}

/**
 * アンカー発言のパーマリンクページ。通知 (Discord 等) に貼られる URL
 * (`/village/{id}/message?anchors=n123_w45`) 専用で、アプリ内からの導線は無い。
 */
export default function VillageMessagePermalink({ params }: Route.ComponentProps) {
  const villageId = Number(params.villageId);
  const [searchParams] = useSearchParams();
  const anchors = searchParams.get("anchors") ?? "";

  const { data: village, error: villageError } = useVillage(villageId);
  const { data: randomKeywords } = useRandomKeywords();
  const { data } = useQuery({
    queryKey: ["village-anchor-messages", villageId, anchors],
    queryFn: () => fetchAnchorMessages(villageId, anchors),
    enabled: anchors !== "",
    retry: false,
  });

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

  const ageLimit = (village?.setting.tags.list ?? []).find(
    (tag) => tag.code === "R15" || tag.code === "R18",
  )?.name;

  return (
    <PageLayout noAd>
      <div className="px-[15px] pb-[30px]">
        {village != null && (
          <>
            <h1 className="my-[10.5px] text-[15px] font-normal">
              {String(village.id).padStart(4, "0")}. {village.name}
            </h1>
            <hr className="mt-[5px] mb-[10px] border-[#464545]" />
          </>
        )}
        {(data?.messageList ?? []).map((message, index) => (
          <MessageCard
            key={`${message.messageType}-${message.messageNumber ?? index}`}
            villageId={villageId}
            message={message}
            randomKeywords={(randomKeywords ?? []).map((k) => k.keyword ?? "").filter(Boolean)}
          />
        ))}
      </div>
      {ageLimit != null && <AgeLimitModal villageId={villageId} ageLimit={ageLimit} />}
    </PageLayout>
  );
}

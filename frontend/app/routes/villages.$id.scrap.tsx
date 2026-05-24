import { useMemo } from "react";
import { Link } from "react-router";
import type { Route } from "./+types/villages.$id.scrap";
import {
  fetchVillage,
  fetchVillageMessages,
  type MessagesQuery,
  type MessageView,
  type VillageParticipantView,
} from "~/features/village/detail/api";
import { MessageCard } from "~/features/village/detail/MessageCard";
import { SayFormProvider } from "~/features/village/detail/SayFormContext";
import { ssrFetch } from "~/lib/api/client";

/**
 * 旧 .old-thymeleaf/templates/scrap.html 相当の "切り抜き" 画面。
 *
 * 村画面に対して header / 入力 / 操作系を全て削いだ read-only ビュー。URL クエリで
 * `day` `type` `from` `to` `kw` `page` を受け、印刷 / 別タブ共有用途を意図する。
 *
 * クエリパラメータの命名 / 解釈は villages.$id.tsx と揃える (同じ実装をモジュール化
 * せずコピーしているのは、scrap は静的な共有用なので import 関係を増やしたくないため)。
 */
function parseDay(raw: string | null): number | undefined {
  if (raw == null || raw === "") return undefined;
  const n = Number(raw);
  if (!Number.isInteger(n) || n < 0) return undefined;
  return n;
}

function parsePage(raw: string | null): number | undefined {
  if (raw == null || raw === "") return undefined;
  const n = Number(raw);
  if (!Number.isInteger(n) || n < 1) return undefined;
  return n;
}

function parseCsv(raw: string | null): string[] {
  return (raw ?? "")
    .split(",")
    .map((s) => s.trim())
    .filter(Boolean);
}

function parseCsvInt(raw: string | null): number[] {
  return parseCsv(raw)
    .map((s) => Number(s))
    .filter((n) => Number.isInteger(n) && n > 0);
}

export function meta({ data }: Route.MetaArgs) {
  const name = data?.village?.name ?? "切り抜き";
  return [{ title: `${name} - 切り抜き - wolf-mansion` }];
}

export async function loader({ request, params }: Route.LoaderArgs) {
  const villageId = Number(params.id);
  if (!Number.isFinite(villageId)) {
    throw new Response("invalid village id", { status: 400 });
  }
  const url = new URL(request.url);
  const api = ssrFetch(request);
  const village = await fetchVillage(villageId, api).catch(() => null);
  if (!village) throw new Response("village not found", { status: 404 });
  const day = parseDay(url.searchParams.get("day")) ?? village.time.latestDay;
  const page = parsePage(url.searchParams.get("page"));
  const messageType = parseCsv(url.searchParams.get("type"));
  const fromParticipantId = parseCsvInt(url.searchParams.get("from"));
  const toParticipantId = parseCsvInt(url.searchParams.get("to"));
  const keyword = url.searchParams.get("kw") ?? "";
  const query: MessagesQuery = {
    day,
    pageSize: typeof page === "number" ? 100 : undefined,
    pageNum: page,
    messageType: messageType.length > 0 ? messageType : undefined,
    fromParticipantId: fromParticipantId.length > 0 ? fromParticipantId : undefined,
    toParticipantId: toParticipantId.length > 0 ? toParticipantId : undefined,
    keyword: keyword.trim() || undefined,
  };
  const messages = await fetchVillageMessages(villageId, query, api).catch(
    () => null,
  );
  return {
    villageId,
    village,
    day,
    messages,
    filterSummary: { messageType, fromParticipantId, toParticipantId, keyword },
  };
}

export default function VillageScrap({ loaderData }: Route.ComponentProps) {
  const { villageId, village, day, messages, filterSummary } = loaderData;
  const participantsById = useMemo(
    () => new Map(village.participants.list.map((p) => [p.id, p])),
    [village.participants.list],
  );
  const list: MessageView[] = messages?.list ?? [];

  const filterSummaryText = useMemo(
    () => buildFilterSummary(village.participants.list, filterSummary),
    [village.participants.list, filterSummary],
  );

  return (
    <main className="min-h-screen bg-slate-50 text-slate-900 print:bg-white">
      <section className="max-w-3xl mx-auto px-6 py-6 space-y-4">
        <header className="space-y-1">
          <div className="flex items-baseline gap-3">
            <span className="font-mono text-slate-500 text-sm">
              #{village.number}
            </span>
            <h1 className="text-xl font-bold">{village.name}</h1>
          </div>
          <p className="text-xs text-slate-500">
            {day === 0 ? "プロローグ" : `${day}日目`} · {list.length}件
            {filterSummaryText && ` · ${filterSummaryText}`}
          </p>
          <div className="flex gap-2 pt-1 text-xs print:hidden">
            <Link
              to={`/villages/${villageId}?day=${day}`}
              className="text-slate-600 hover:text-slate-900 underline"
            >
              ← 村に戻る
            </Link>
            <button
              type="button"
              className="text-slate-600 hover:text-slate-900 underline"
              onClick={() => window.print()}
            >
              印刷
            </button>
          </div>
        </header>

        {list.length === 0 ? (
          <p className="text-slate-500 text-sm py-2">
            条件に該当する発言はありません
          </p>
        ) : (
          // MessageCard はアンカー / 返信ボタンが SayFormContext を必要とするため
          // 表示専用でも provider で包む。scrap には SayForm 自体は出さない。
          <SayFormProvider>
            <ul className="space-y-2">
              {list.map((m, i) => (
                <li key={`${m.typeCode}-${m.number ?? i}`}>
                  <MessageCard message={m} participantsById={participantsById} />
                </li>
              ))}
            </ul>
          </SayFormProvider>
        )}
      </section>
    </main>
  );
}

function buildFilterSummary(
  participants: VillageParticipantView[],
  s: {
    messageType: string[];
    fromParticipantId: number[];
    toParticipantId: number[];
    keyword: string;
  },
): string {
  const parts: string[] = [];
  if (s.messageType.length > 0) parts.push(`種別 ${s.messageType.length}`);
  if (s.fromParticipantId.length > 0) {
    const names = s.fromParticipantId
      .map((id) => participants.find((p) => p.id === id)?.name)
      .filter(Boolean)
      .join(", ");
    parts.push(`発言者: ${names || s.fromParticipantId.length + "件"}`);
  }
  if (s.toParticipantId.length > 0) parts.push(`宛先 ${s.toParticipantId.length}`);
  if (s.keyword.trim()) parts.push(`「${s.keyword.trim()}」`);
  return parts.join(" / ");
}


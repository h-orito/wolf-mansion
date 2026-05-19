import { useState } from "react";
import {
  useAdminForceAccessMutation,
  useAdminForceLeaveMutation,
  useAdminForceVoteMutation,
  useAdminPlayersQuery,
} from "./hooks";
import type { VillageParticipantView, VillageView } from "./api";

/**
 * 管理者 (authority=管理者) 専用パネル。
 *
 * - 全員アクセス: 突然死回避用に全員の lastAccessDatetime を now() に更新
 * - 全員自分票: 当日未投票の生存者全員に「自分票」を入れる (進行不能回避用)
 * - 強制退村: 任意の villagePlayerId を退村 (assertLeave 経由 = プロローグ中のみ)
 * - プレイヤー確認: キャラ名 ↔ 中の人プレイヤー名 を表示
 *
 * 表示判定 (= 現在ユーザが管理者か) は呼び出し側で担う想定。
 */
export function AdminPanel({ village }: { village: VillageView }) {
  return (
    <section className="rounded-xl bg-rose-900/10 border border-rose-700/40 p-4 space-y-4">
      <h2 className="text-sm text-rose-200">管理者メニュー</h2>

      <ForceAccessRow villageId={village.id} />
      <ForceVoteRow villageId={village.id} />
      <ForceLeaveSection village={village} />
      <PlayersListSection villageId={village.id} />
    </section>
  );
}

function ForceAccessRow({ villageId }: { villageId: number }) {
  const mutation = useAdminForceAccessMutation(villageId);
  function onClick() {
    if (!confirm("全参加者の最終アクセス時刻を now に更新します。よろしいですか？")) return;
    mutation.mutate();
  }
  return (
    <div className="flex items-center justify-between gap-3 flex-wrap border-t border-rose-700/30 pt-3 first:border-t-0 first:pt-0">
      <p className="text-xs text-rose-200/80">全員アクセス (突然死回避)</p>
      <div className="flex items-center gap-3">
        {mutation.isSuccess && <span className="text-xs text-emerald-300">更新済み</span>}
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
        <button
          type="button"
          onClick={onClick}
          disabled={mutation.isPending}
          className={secondaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "全員アクセス更新"}
        </button>
      </div>
    </div>
  );
}

function ForceVoteRow({ villageId }: { villageId: number }) {
  const mutation = useAdminForceVoteMutation(villageId);
  function onClick() {
    if (!confirm("当日未投票の生存者全員に「自分票」を入れます。よろしいですか？")) return;
    mutation.mutate();
  }
  return (
    <div className="flex items-center justify-between gap-3 flex-wrap border-t border-rose-700/30 pt-3">
      <p className="text-xs text-rose-200/80">全員自分票 (進行不能回避)</p>
      <div className="flex items-center gap-3">
        {mutation.isSuccess && <span className="text-xs text-emerald-300">投票登録済み</span>}
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
        <button
          type="button"
          onClick={onClick}
          disabled={mutation.isPending}
          className={secondaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "全員自分票"}
        </button>
      </div>
    </div>
  );
}

function ForceLeaveSection({ village }: { village: VillageView }) {
  const mutation = useAdminForceLeaveMutation(village.id);
  // 退村可能なのはプロローグ中の参加者のみ (assertLeave に倣う)。
  // とはいえ admin パネルとしてはステータスにかかわらず一覧を出し、
  // backend が assertLeave で 400 を返す挙動に従う方が「管理者は何でもできる」感がある。
  const candidates = village.participants.list.filter(
    (p) => !p.isSpectator && !p.isDead,
  );

  function onLeave(p: VillageParticipantView) {
    if (!confirm(`「${p.name}」を強制退村させます。よろしいですか？`)) return;
    mutation.mutate({ villagePlayerId: p.id });
  }

  return (
    <div className="border-t border-rose-700/30 pt-3 space-y-2">
      <p className="text-xs text-rose-200/80">強制退村 (admin) — プロローグ中のみ成功</p>
      {candidates.length === 0 ? (
        <p className="text-xs text-slate-400">対象の参加者がいません</p>
      ) : (
        <ul className="grid grid-cols-1 sm:grid-cols-2 gap-2">
          {candidates.map((p) => (
            <li key={p.id} className="flex items-center justify-between gap-2 text-sm">
              <span className="truncate">{p.name}</span>
              <button
                type="button"
                onClick={() => onLeave(p)}
                disabled={mutation.isPending}
                className={dangerButtonClass}
              >
                強制退村
              </button>
            </li>
          ))}
        </ul>
      )}
      {mutation.isError && (
        <p className="text-xs text-rose-300">{mutation.error.message}</p>
      )}
    </div>
  );
}

function PlayersListSection({ villageId }: { villageId: number }) {
  const [open, setOpen] = useState(false);
  const query = useAdminPlayersQuery(villageId, open);

  return (
    <div className="border-t border-rose-700/30 pt-3 space-y-2">
      <div className="flex items-center justify-between gap-3 flex-wrap">
        <p className="text-xs text-rose-200/80">参加プレイヤー確認 (キャラ ↔ 中の人)</p>
        <button
          type="button"
          onClick={() => setOpen((v) => !v)}
          className={secondaryButtonClass}
        >
          {open ? "閉じる" : "表示する"}
        </button>
      </div>
      {open && (
        <>
          {query.isLoading && <p className="text-xs text-slate-400">読み込み中...</p>}
          {query.isError && (
            <p className="text-xs text-rose-300">{(query.error as Error).message}</p>
          )}
          {query.data && (
            <ul className="text-sm space-y-1 bg-slate-900/40 rounded p-2 max-h-60 overflow-y-auto">
              {query.data.length === 0 ? (
                <li className="text-xs text-slate-400">参加者がいません</li>
              ) : (
                query.data.map((row, i) => (
                  <li key={i} className="flex items-center justify-between gap-3">
                    <span className="truncate">{row.charaName}</span>
                    <span className="text-xs text-slate-400 truncate">{row.playerName}</span>
                  </li>
                ))
              )}
            </ul>
          )}
        </>
      )}
    </div>
  );
}

const secondaryButtonClass =
  "rounded bg-slate-700 hover:bg-slate-600 disabled:opacity-40 disabled:cursor-not-allowed px-3 py-1.5 text-xs font-medium";

const dangerButtonClass =
  "rounded bg-rose-600 hover:bg-rose-500 disabled:opacity-40 disabled:cursor-not-allowed px-3 py-1 text-xs font-medium";

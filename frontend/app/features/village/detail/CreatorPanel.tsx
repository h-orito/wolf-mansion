import { useState } from "react";
import { Link } from "react-router";
import {
  useCancelVillageMutation,
  useCreatorSayMutation,
  useExtendEpilogueMutation,
  useKickMutation,
  useShortenEpilogueMutation,
} from "./hooks";
import type { VillageParticipantView, VillageView } from "./api";

/**
 * 村建て (creator) 専用パネル。
 *
 * 表示判定は呼び出し側で行う想定 (creator 本人 + 管理者は呼び出し側で or 判定)。
 * 各操作の有効条件はパネル側で村ステータスを見て出し分ける。
 *
 * - 募集中: 廃村 / kick (参加者選択)
 * - 進行中以降: なし (村建て発言のみ常時、エピローグ時のみ延長/短縮)
 * - エピローグ: 延長 / 短縮
 *
 * 村建て発言だけは村ステータスに依らず常時可能。
 */
export function CreatorPanel({ village }: { village: VillageView }) {
  const isPrologue = village.statusCode === "IN_PREPARATION";
  const isEpilogue = village.statusCode === "EPILOGUE";

  return (
    <section className="rounded-xl bg-amber-900/10 border border-amber-700/40 p-4 space-y-4">
      <h2 className="text-sm text-amber-200">村建てメニュー</h2>

      <CreatorSayForm villageId={village.id} />

      {isPrologue && (
        <>
          <SettingsLink villageId={village.id} />
          <KickForm village={village} />
          <CancelButton villageId={village.id} />
        </>
      )}

      {isEpilogue && <EpilogueControls villageId={village.id} />}
    </section>
  );
}

function CreatorSayForm({ villageId }: { villageId: number }) {
  const [text, setText] = useState("");
  const [convertDisable, setConvertDisable] = useState(false);
  const mutation = useCreatorSayMutation(villageId);

  function submit(e: React.FormEvent) {
    e.preventDefault();
    const trimmed = text.trim();
    if (!trimmed) return;
    if (!confirm("村建てとして発言します。よろしいですか？")) return;
    mutation.mutate(
      { message: trimmed, convertDisable },
      { onSuccess: () => setText("") },
    );
  }

  return (
    <form onSubmit={submit} className="space-y-2 border-t border-amber-700/30 pt-3 first:border-t-0 first:pt-0">
      <p className="text-xs text-amber-200/80">村建て発言</p>
      <textarea
        value={text}
        onChange={(e) => setText(e.target.value)}
        rows={3}
        maxLength={400}
        placeholder="村建てとしての発言 (400 文字以内)"
        className={inputClass}
        disabled={mutation.isPending}
      />
      <div className="flex items-center justify-between gap-2 flex-wrap">
        <label className="flex items-center gap-2 text-xs text-slate-200">
          <input
            type="checkbox"
            checked={convertDisable}
            onChange={(e) => setConvertDisable(e.target.checked)}
            disabled={mutation.isPending}
          />
          変換を無効化
        </label>
        <div className="flex items-center gap-3">
          <span className="text-xs text-slate-500">{text.length} / 400</span>
          <button
            type="submit"
            disabled={mutation.isPending || text.trim().length === 0}
            className={primaryButtonClass}
          >
            {mutation.isPending ? "送信中..." : "村建て発言"}
          </button>
        </div>
      </div>
      {mutation.isError && (
        <p className="text-xs text-rose-300">{mutation.error.message}</p>
      )}
    </form>
  );
}

function SettingsLink({ villageId }: { villageId: number }) {
  return (
    <div className="border-t border-amber-700/30 pt-3 flex items-center gap-3 flex-wrap">
      <p className="text-xs text-amber-200/80">村設定変更 (プロローグ中のみ)</p>
      <Link to={`/villages/${villageId}/settings`} className={secondaryButtonClass}>
        設定を編集
      </Link>
    </div>
  );
}

function KickForm({ village }: { village: VillageView }) {
  const mutation = useKickMutation(village.id);
  const candidates = village.participants.list.filter(
    (p) => !p.isSpectator && !p.isDead,
  );

  function onKick(p: VillageParticipantView) {
    if (!confirm(`「${p.name}」を強制退村させます。よろしいですか？`)) return;
    mutation.mutate({ charaId: p.chara.id });
  }

  if (candidates.length === 0) {
    return (
      <div className="border-t border-amber-700/30 pt-3 space-y-2">
        <p className="text-xs text-amber-200/80">強制退村 (kick)</p>
        <p className="text-xs text-slate-400">対象の参加者がいません</p>
      </div>
    );
  }

  return (
    <div className="border-t border-amber-700/30 pt-3 space-y-2">
      <p className="text-xs text-amber-200/80">強制退村 (kick) — プロローグ中のみ</p>
      <ul className="grid grid-cols-1 sm:grid-cols-2 gap-2">
        {candidates.map((p) => (
          <li key={p.id} className="flex items-center justify-between gap-2 text-sm">
            <span className="truncate">{p.name}</span>
            <button
              type="button"
              onClick={() => onKick(p)}
              disabled={mutation.isPending}
              className={dangerButtonClass}
            >
              kick
            </button>
          </li>
        ))}
      </ul>
      {mutation.isError && (
        <p className="text-xs text-rose-300">{mutation.error.message}</p>
      )}
    </div>
  );
}

function CancelButton({ villageId }: { villageId: number }) {
  const mutation = useCancelVillageMutation(villageId);
  function onClick() {
    if (!confirm("この村を廃村にします。よろしいですか？")) return;
    mutation.mutate();
  }
  return (
    <div className="border-t border-amber-700/30 pt-3 flex items-center gap-3 flex-wrap">
      <p className="text-xs text-amber-200/80">廃村 (プロローグ中のみ)</p>
      <button
        type="button"
        onClick={onClick}
        disabled={mutation.isPending}
        className={dangerButtonClass}
      >
        {mutation.isPending ? "送信中..." : "廃村にする"}
      </button>
      {mutation.isError && (
        <span className="text-xs text-rose-300">{mutation.error.message}</span>
      )}
    </div>
  );
}

function EpilogueControls({ villageId }: { villageId: number }) {
  const extend = useExtendEpilogueMutation(villageId);
  const shorten = useShortenEpilogueMutation(villageId);
  return (
    <div className="border-t border-amber-700/30 pt-3 space-y-2">
      <p className="text-xs text-amber-200/80">エピローグ日数の調整</p>
      <div className="flex items-center gap-3 flex-wrap">
        <button
          type="button"
          onClick={() => extend.mutate()}
          disabled={extend.isPending || shorten.isPending}
          className={secondaryButtonClass}
        >
          {extend.isPending ? "送信中..." : "1日延長"}
        </button>
        <button
          type="button"
          onClick={() => shorten.mutate()}
          disabled={extend.isPending || shorten.isPending}
          className={secondaryButtonClass}
        >
          {shorten.isPending ? "送信中..." : "1日短縮"}
        </button>
      </div>
      {(extend.isError || shorten.isError) && (
        <p className="text-xs text-rose-300">
          {extend.error?.message ?? shorten.error?.message}
        </p>
      )}
    </div>
  );
}

const inputClass =
  "w-full bg-slate-900 border border-slate-700 rounded px-3 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500 disabled:opacity-50";

const primaryButtonClass =
  "rounded bg-amber-500 hover:bg-amber-400 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium text-slate-900";

const secondaryButtonClass =
  "rounded bg-slate-700 hover:bg-slate-600 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

const dangerButtonClass =
  "rounded bg-rose-600 hover:bg-rose-500 disabled:opacity-40 disabled:cursor-not-allowed px-3 py-1 text-xs font-medium";

import { useEffect, useState } from "react";
import type { MyselfFaceTypeView, MyselfView, VillageView } from "./api";
import {
  useChangeNameMutation,
  useFaceTypesMutation,
  useFaceTypesQuery,
  useMemoMutation,
} from "./hooks";

/**
 * RP 系 (キャラ名 / 簡易メモ / 表情差分) の編集パネル。
 *
 * - キャラ名変更: 募集中 + キャラチップが変更を許可 + 自分がまだ変更可能ステータス、のとき。
 * - 簡易メモ: 募集中 + 自分がまだ変更可能ステータス、のとき。
 * - 表情差分: オリジナルキャラチップ村のときのみ。
 *   別 endpoint `/rp/face-types` で取得した自キャラ分のみを編集する。multipart の画像追加は未実装。
 *
 * 編集可能項目が 1 つも無い場合は何も描画しない。
 */
export function RpActions({
  village,
  myself,
}: {
  village: VillageView;
  myself: MyselfView;
}) {
  const { rp } = myself;
  const showChangeName = rp.isAvailableChangeName;
  const showMemo = rp.isAvailableMemo;
  // canEditFaceType は backend 側で「オリジナルキャラチップ村 + 募集中相当」のときに true。
  // 但しオリジナルキャラチップ村でない通常村でも、過去に追加したオリジナル差分はない (チップ
  // 全体が差分 1 つのみ) ので、ここでは canEditFaceType に従う。
  const showFaceType = rp.canEditFaceType;

  if (!showChangeName && !showMemo && !showFaceType) return null;

  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-4">
      <h2 className="text-sm text-slate-400">RP</h2>
      {showChangeName && <ChangeNameForm villageId={village.id} myself={myself} />}
      {showMemo && <MemoForm villageId={village.id} myself={myself} />}
      {showFaceType && <FaceTypesForm villageId={village.id} />}
    </section>
  );
}

// ---------- キャラ名変更 ----------

function ChangeNameForm({ villageId, myself }: { villageId: number; myself: MyselfView }) {
  const mutation = useChangeNameMutation(villageId);
  const [name, setName] = useState(myself.charaName);
  const [shortName, setShortName] = useState(myself.charaShortName);

  // 他の操作 (例えば管理者操作) で名前が変わったら同期。
  useEffect(() => {
    setName(myself.charaName);
    setShortName(myself.charaShortName);
  }, [myself.charaName, myself.charaShortName]);

  function submit(e: React.FormEvent) {
    e.preventDefault();
    const trimmedName = name.trim();
    const trimmedShort = shortName.trim();
    if (!trimmedName || trimmedShort.length !== 1) return;
    mutation.mutate({ name: trimmedName, shortName: trimmedShort });
  }

  const submittable =
    !mutation.isPending &&
    name.trim().length > 0 &&
    name.trim().length <= 40 &&
    shortName.trim().length === 1;
  const dirty = name !== myself.charaName || shortName !== myself.charaShortName;

  return (
    <form onSubmit={submit} className="space-y-2 first:border-t-0 first:pt-0 border-t border-slate-700/60 pt-3">
      <p className="text-xs text-slate-400">キャラ名変更</p>
      <div className="grid grid-cols-[1fr_5rem] gap-2 items-end">
        <Field label="表示名 (40 文字以内)">
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            maxLength={40}
            className={inputClass}
            disabled={mutation.isPending}
          />
        </Field>
        <Field label="略称 (1 文字)">
          <input
            type="text"
            value={shortName}
            onChange={(e) => setShortName(e.target.value)}
            maxLength={1}
            className={inputClass}
            disabled={mutation.isPending}
          />
        </Field>
      </div>
      <div className="flex items-center gap-3">
        <button
          type="submit"
          disabled={!submittable || !dirty}
          className={primaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "名前を変更"}
        </button>
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

// ---------- 簡易メモ ----------

const MEMO_MAX_LENGTH = 20;

function MemoForm({ villageId, myself }: { villageId: number; myself: MyselfView }) {
  const mutation = useMemoMutation(villageId);
  const [memo, setMemo] = useState(myself.memo ?? "");

  useEffect(() => {
    setMemo(myself.memo ?? "");
  }, [myself.memo]);

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (memo.length > MEMO_MAX_LENGTH) return;
    mutation.mutate({ memo });
  }

  const submittable = !mutation.isPending && memo.length <= MEMO_MAX_LENGTH;
  const dirty = memo !== (myself.memo ?? "");

  return (
    <form onSubmit={submit} className="space-y-2 border-t border-slate-700/60 pt-3">
      <p className="text-xs text-slate-400">簡易メモ ({MEMO_MAX_LENGTH} 文字以内、自分しか見えない)</p>
      <input
        type="text"
        value={memo}
        onChange={(e) => setMemo(e.target.value)}
        maxLength={MEMO_MAX_LENGTH}
        placeholder="(空文字でクリア)"
        className={inputClass}
        disabled={mutation.isPending}
      />
      <div className="flex items-center gap-3">
        <button
          type="submit"
          disabled={!submittable || !dirty}
          className={primaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "メモを保存"}
        </button>
        <span className="text-xs text-slate-500">
          {memo.length} / {MEMO_MAX_LENGTH}
        </span>
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

// ---------- 表情差分 ----------

function FaceTypesForm({ villageId }: { villageId: number }) {
  // RpActions が canEditFaceType=true のときだけマウントするので、ここでは常に enabled.
  const query = useFaceTypesQuery(villageId, true);
  const mutation = useFaceTypesMutation(villageId);

  // ローカル編集状態。backend からの最新が入ってきたら再初期化する。
  const [items, setItems] = useState<MyselfFaceTypeView[]>([]);
  useEffect(() => {
    if (query.data) setItems(query.data.list);
  }, [query.data]);

  function update(idx: number, patch: Partial<MyselfFaceTypeView>) {
    setItems((prev) => prev.map((it, i) => (i === idx ? { ...it, ...patch } : it)));
  }

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (items.length === 0) return;
    // backend は name に @Size(1,5) を持つ。1 件でも違反していたら submit を弾く。
    if (items.some((it) => it.name.trim().length < 1 || it.name.trim().length > 5)) return;
    mutation.mutate({
      faceTypeList: items.map((it) => ({
        code: it.code,
        name: it.name.trim(),
        display: it.isDisplay,
      })),
    });
  }

  const submittable =
    !mutation.isPending &&
    items.length > 0 &&
    items.every((it) => it.name.trim().length >= 1 && it.name.trim().length <= 5);

  return (
    <form onSubmit={submit} className="space-y-3 border-t border-slate-700/60 pt-3">
      <p className="text-xs text-slate-400">
        表情差分編集 (画像追加は未対応、編集のみ)
      </p>
      {query.isLoading && <p className="text-slate-400 text-sm">読み込み中...</p>}
      {query.isError && (
        <p className="text-rose-300 text-sm">表情差分の取得に失敗しました</p>
      )}
      {query.data && items.length === 0 && (
        <p className="text-slate-400 text-sm">編集可能な表情差分がありません</p>
      )}
      {items.length > 0 && (
        <ul className="grid grid-cols-1 sm:grid-cols-2 gap-3">
          {items.map((it, idx) => (
            <li
              key={it.code}
              className="flex gap-3 items-start rounded border border-slate-700 p-2 bg-slate-900/40"
            >
              <img
                src={it.url}
                alt={it.name}
                width={60}
                height={60}
                className="rounded shrink-0"
              />
              <div className="flex-1 space-y-1">
                <input
                  type="text"
                  value={it.name}
                  onChange={(e) => update(idx, { name: e.target.value })}
                  maxLength={5}
                  className={inputClass}
                  placeholder="表情名 (1-5 文字)"
                  disabled={mutation.isPending}
                />
                <label className="flex items-center gap-1 text-xs text-slate-300">
                  <input
                    type="checkbox"
                    checked={it.isDisplay}
                    onChange={(e) => update(idx, { isDisplay: e.target.checked })}
                    disabled={mutation.isPending}
                  />
                  発言フォームに表示する
                </label>
              </div>
            </li>
          ))}
        </ul>
      )}
      <div className="flex items-center gap-3">
        <button
          type="submit"
          disabled={!submittable}
          className={primaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "表情差分を更新"}
        </button>
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

// ---------- 部品 ----------

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <div className="space-y-1">
      <label className="text-xs text-slate-400">{label}</label>
      {children}
    </div>
  );
}

const inputClass =
  "w-full bg-slate-900 border border-slate-700 rounded px-3 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500 disabled:opacity-50";

const primaryButtonClass =
  "rounded bg-indigo-500 hover:bg-indigo-400 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

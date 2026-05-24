import { useEffect, useState } from "react";
import type { VillageParticipantView } from "./api";

/**
 * UI 上で「1 つの絞り込み単位」として扱う発言種別。backend 側の
 * `MESSAGE_FILTER_TYPE_MAP` と 1:1 対応する。`GRAVE_SPECTATE_SAY` (墓下見学)
 * のような合成 code は frontend にだけ存在する別名扱い。
 */
export type FilterMessageType = {
  code: string;
  label: string;
};

export const MESSAGE_FILTER_TYPES: FilterMessageType[] = [
  { code: "NORMAL_SAY", label: "通常" },
  { code: "CREATOR_SAY", label: "村建て" },
  { code: "WEREWOLF_SAY", label: "囁き" },
  { code: "MASON_SAY", label: "共鳴" },
  { code: "LOVERS_SAY", label: "恋人" },
  { code: "TELEPATHY", label: "念話" },
  { code: "GRAVE_SPECTATE_SAY", label: "墓下/見学" },
  { code: "MONOLOGUE_SAY", label: "独り言" },
  { code: "SECRET_SAY", label: "秘話" },
  { code: "ACTION", label: "アクション" },
  { code: "PUBLIC_SYSTEM", label: "公開シスメ" },
  { code: "PRIVATE_SYSTEM", label: "非公開シスメ" },
];

/**
 * 発言フィルタの値。`messageType` / 参加者 ID は「未絞り込み = 全件」を意味する
 * 空配列で表現する (= 旧 Thymeleaf の挙動踏襲)。
 */
export type MessageFilterValue = {
  messageType: string[];
  fromParticipantId: number[];
  toParticipantId: number[];
  keyword: string;
};

export const EMPTY_FILTER: MessageFilterValue = {
  messageType: [],
  fromParticipantId: [],
  toParticipantId: [],
  keyword: "",
};

export function isEmptyFilter(v: MessageFilterValue): boolean {
  return (
    v.messageType.length === 0 &&
    v.fromParticipantId.length === 0 &&
    v.toParticipantId.length === 0 &&
    v.keyword.trim() === ""
  );
}

export function MessageFilterModal({
  open,
  value,
  participants,
  onApply,
  onClose,
}: {
  open: boolean;
  value: MessageFilterValue;
  participants: VillageParticipantView[];
  onApply: (v: MessageFilterValue) => void;
  onClose: () => void;
}) {
  const [draft, setDraft] = useState<MessageFilterValue>(value);
  // モーダルを開くたびに親の最新値で初期化する。閉じている間は不要な再 render を避ける。
  useEffect(() => {
    if (open) setDraft(value);
  }, [open, value]);

  if (!open) return null;

  const toggleType = (code: string) => {
    setDraft((d) => ({
      ...d,
      messageType: d.messageType.includes(code)
        ? d.messageType.filter((x) => x !== code)
        : [...d.messageType, code],
    }));
  };
  const toggleFrom = (id: number) => {
    setDraft((d) => ({
      ...d,
      fromParticipantId: d.fromParticipantId.includes(id)
        ? d.fromParticipantId.filter((x) => x !== id)
        : [...d.fromParticipantId, id],
    }));
  };
  const toggleTo = (id: number) => {
    setDraft((d) => ({
      ...d,
      toParticipantId: d.toParticipantId.includes(id)
        ? d.toParticipantId.filter((x) => x !== id)
        : [...d.toParticipantId, id],
    }));
  };

  // 退村済以外を選択候補にする (= 旧画面踏襲)。
  const selectable = participants.filter((p) => !p.isGone);

  return (
    <div
      role="dialog"
      aria-modal="true"
      aria-label="発言フィルタ"
      className="fixed inset-0 z-40 flex items-center justify-center bg-black/60 p-4"
      onClick={onClose}
    >
      <div
        className="w-full max-w-3xl max-h-[90vh] overflow-y-auto rounded-xl bg-slate-900 border border-slate-700 p-5 space-y-4"
        onClick={(e) => e.stopPropagation()}
      >
        <h3 className="text-base font-semibold">発言抽出</h3>

        <section>
          <div className="flex items-center justify-between">
            <h4 className="text-sm text-slate-300">発言種別</h4>
            <div className="text-xs space-x-2">
              <button
                type="button"
                className="text-slate-300 hover:text-slate-100"
                onClick={() => setDraft((d) => ({ ...d, messageType: [] }))}
              >
                クリア
              </button>
              <button
                type="button"
                className="text-slate-300 hover:text-slate-100"
                onClick={() =>
                  setDraft((d) => ({ ...d, messageType: MESSAGE_FILTER_TYPES.map((t) => t.code) }))
                }
              >
                全選択
              </button>
            </div>
          </div>
          <div className="mt-2 grid grid-cols-2 sm:grid-cols-4 gap-1">
            {MESSAGE_FILTER_TYPES.map((t) => {
              const active = draft.messageType.includes(t.code);
              return (
                <label
                  key={t.code}
                  className={
                    "flex items-center gap-2 text-xs cursor-pointer rounded px-2 py-1 border " +
                    (active
                      ? "bg-indigo-600/30 border-indigo-400 text-indigo-50"
                      : "bg-slate-800/40 border-slate-700 text-slate-300 hover:bg-slate-700/40")
                  }
                >
                  <input
                    type="checkbox"
                    checked={active}
                    onChange={() => toggleType(t.code)}
                    className="accent-indigo-500"
                  />
                  {t.label}
                </label>
              );
            })}
          </div>
        </section>

        <ParticipantSelector
          title="発言者"
          selectable={selectable}
          selected={draft.fromParticipantId}
          onToggle={toggleFrom}
          onClear={() => setDraft((d) => ({ ...d, fromParticipantId: [] }))}
        />

        <ParticipantSelector
          title="宛先 (秘話)"
          selectable={selectable}
          selected={draft.toParticipantId}
          onToggle={toggleTo}
          onClear={() => setDraft((d) => ({ ...d, toParticipantId: [] }))}
        />

        <section>
          <h4 className="text-sm text-slate-300">キーワード</h4>
          <input
            type="text"
            value={draft.keyword}
            onChange={(e) => setDraft((d) => ({ ...d, keyword: e.target.value }))}
            placeholder="スペース区切りで AND"
            className="mt-2 w-full rounded bg-slate-800 border border-slate-700 px-3 py-1.5 text-sm"
          />
        </section>

        <div className="flex justify-end gap-2 pt-2 border-t border-slate-700">
          <button
            type="button"
            className="rounded border border-slate-600 px-3 py-1.5 text-sm text-slate-200 hover:bg-slate-800"
            onClick={() => setDraft(EMPTY_FILTER)}
          >
            リセット
          </button>
          <button
            type="button"
            className="rounded border border-slate-600 px-3 py-1.5 text-sm text-slate-200 hover:bg-slate-800"
            onClick={onClose}
          >
            閉じる
          </button>
          <button
            type="button"
            className="rounded bg-indigo-500 hover:bg-indigo-400 px-3 py-1.5 text-sm text-white"
            onClick={() => {
              onApply(draft);
              onClose();
            }}
          >
            抽出
          </button>
        </div>
      </div>
    </div>
  );
}

function ParticipantSelector({
  title,
  selectable,
  selected,
  onToggle,
  onClear,
}: {
  title: string;
  selectable: VillageParticipantView[];
  selected: number[];
  onToggle: (id: number) => void;
  onClear: () => void;
}) {
  return (
    <section>
      <div className="flex items-center justify-between">
        <h4 className="text-sm text-slate-300">{title}</h4>
        <button
          type="button"
          className="text-xs text-slate-300 hover:text-slate-100"
          onClick={onClear}
        >
          クリア
        </button>
      </div>
      <div className="mt-2 grid grid-cols-2 sm:grid-cols-3 gap-1">
        {selectable.map((p) => {
          const active = selected.includes(p.id);
          return (
            <label
              key={p.id}
              className={
                "flex items-center gap-2 text-xs cursor-pointer rounded px-2 py-1 border " +
                (active
                  ? "bg-indigo-600/30 border-indigo-400 text-indigo-50"
                  : "bg-slate-800/40 border-slate-700 text-slate-300 hover:bg-slate-700/40")
              }
            >
              <input
                type="checkbox"
                checked={active}
                onChange={() => onToggle(p.id)}
                className="accent-indigo-500"
              />
              <span className="truncate">{p.name}</span>
            </label>
          );
        })}
      </div>
    </section>
  );
}

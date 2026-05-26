import { useEffect, useState } from "react";
import type { VillageParticipantView } from "./api";
import { Modal } from "~/components/ui/Modal";
import { Button } from "~/components/ui/Button";

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

/**
 * 発言フィルタ Modal。Step 13c で Modal primitive に揃え、checkbox UI を
 * 旧 BS3 .form-horizontal 風の見た目に近づけた。
 *
 * 旧画面の `aria-controls` 先要素は collapse 時に DOM から消える設計だったが、
 * 本実装は Modal の open/close で全体を出し入れする形なので、JAWS 等で問題に
 * なる aria-controls 先要素の常駐化問題は構造上発生しない (13a 残 nit 解消)。
 */
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
  useEffect(() => {
    if (open) setDraft(value);
  }, [open, value]);

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
    <Modal open={open} onClose={onClose} title="発言抽出">
      <div className="space-y-4">
        <FilterSection
          title="発言種別"
          onClear={() => setDraft((d) => ({ ...d, messageType: [] }))}
          onSelectAll={() =>
            setDraft((d) => ({ ...d, messageType: MESSAGE_FILTER_TYPES.map((t) => t.code) }))
          }
        >
          <div className="grid grid-cols-2 sm:grid-cols-4 gap-1">
            {MESSAGE_FILTER_TYPES.map((t) => (
              <CheckboxItem
                key={t.code}
                checked={draft.messageType.includes(t.code)}
                onToggle={() => toggleType(t.code)}
                label={t.label}
              />
            ))}
          </div>
        </FilterSection>

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
          <h4 className="text-[0.95em] mb-1">キーワード</h4>
          <input
            type="text"
            value={draft.keyword}
            onChange={(e) => setDraft((d) => ({ ...d, keyword: e.target.value }))}
            placeholder="スペース区切りで OR"
            className="w-full bg-night-900 border border-night-700 rounded-[3px] px-2 py-1 text-[1em]"
          />
        </section>

        <div className="flex justify-end gap-2 pt-2 border-t border-night-550">
          <Button variant="default" onClick={() => setDraft(EMPTY_FILTER)}>リセット</Button>
          <Button variant="default" onClick={onClose}>閉じる</Button>
          <Button
            variant="success"
            onClick={() => {
              onApply(draft);
              onClose();
            }}
          >
            抽出
          </Button>
        </div>
      </div>
    </Modal>
  );
}

function FilterSection({
  title,
  onClear,
  onSelectAll,
  children,
}: {
  title: string;
  onClear: () => void;
  onSelectAll?: () => void;
  children: React.ReactNode;
}) {
  return (
    <section>
      <div className="flex items-center justify-between mb-1">
        <h4 className="text-[0.95em] m-0">{title}</h4>
        <div className="text-[0.85em] flex gap-2">
          <button type="button" className="message-link hover:underline" onClick={onClear}>
            クリア
          </button>
          {onSelectAll && (
            <button type="button" className="message-link hover:underline" onClick={onSelectAll}>
              全選択
            </button>
          )}
        </div>
      </div>
      {children}
    </section>
  );
}

function CheckboxItem({
  checked,
  onToggle,
  label,
}: {
  checked: boolean;
  onToggle: () => void;
  label: string;
}) {
  return (
    <label
      className={
        "flex items-center gap-2 text-[0.95em] cursor-pointer rounded-[3px] px-2 py-1 border " +
        (checked
          ? "bg-mint-600 border-mint-600 text-white"
          : "bg-night-900 border-night-700 hover:border-mint-600")
      }
    >
      <input
        type="checkbox"
        checked={checked}
        onChange={onToggle}
        className="accent-mint-600"
      />
      <span className="truncate">{label}</span>
    </label>
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
    <FilterSection title={title} onClear={onClear}>
      <div className="grid grid-cols-2 sm:grid-cols-3 gap-1">
        {selectable.map((p) => (
          <CheckboxItem
            key={p.id}
            checked={selected.includes(p.id)}
            onToggle={() => onToggle(p.id)}
            label={p.name}
          />
        ))}
      </div>
    </FilterSection>
  );
}

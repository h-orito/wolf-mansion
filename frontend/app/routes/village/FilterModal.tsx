import { useEffect, useState } from "react";

import { Button } from "~/components/ui/Button";
import { Modal } from "~/components/ui/Modal";
import { inputClass } from "~/components/ui/Input";
import type { VillageFilterParticipantContent } from "~/features/village/api";
import { EMPTY_FILTER, FILTER_TYPES, type MessageFilter } from "~/features/village/filter";

const ALL_TYPE_VALUES = FILTER_TYPES.map((t) => t.value);

/** チェック状態。フィルタの「空 = 全選択」と UI の「全チェック」を相互変換する。 */
type Draft = {
  types: string[];
  participantIds: number[];
  toParticipantIds: number[];
  keywords: string;
  spoiled: boolean;
};

function toDraft(filter: MessageFilter, allParticipantIds: number[]): Draft {
  return {
    types: filter.types.length === 0 ? ALL_TYPE_VALUES : filter.types,
    participantIds: filter.participantIds.length === 0 ? allParticipantIds : filter.participantIds,
    toParticipantIds:
      filter.toParticipantIds.length === 0 ? allParticipantIds : filter.toParticipantIds,
    keywords: filter.keywords,
    spoiled: filter.spoiled,
  };
}

function toFilter(draft: Draft, allParticipantIds: number[]): MessageFilter {
  return {
    types: draft.types.length === ALL_TYPE_VALUES.length ? [] : draft.types,
    participantIds:
      draft.participantIds.length === allParticipantIds.length
        ? []
        : [...draft.participantIds].sort((a, b) => a - b),
    toParticipantIds:
      draft.toParticipantIds.length === allParticipantIds.length
        ? []
        : [...draft.toParticipantIds].sort((a, b) => a - b),
    keywords: draft.keywords.replace(/　/g, " ").trim(),
    spoiled: draft.spoiled,
  };
}

function toggle<T>(list: T[], value: T): T[] {
  return list.includes(value) ? list.filter((v) => v !== value) : [...list, value];
}

const linkClass = "text-wm-accent cursor-pointer hover:underline";

function ParticipantCheckList({
  participants,
  checked,
  onChange,
}: {
  participants: VillageFilterParticipantContent[];
  checked: number[];
  onChange: (ids: number[]) => void;
}) {
  const allIds = participants.map((p) => p.id);
  return (
    <div>
      <div className="mb-[10px]">
        <button type="button" className={linkClass} onClick={() => onChange(allIds)}>
          全てON
        </button>
        &nbsp;/&nbsp;
        <button type="button" className={linkClass} onClick={() => onChange([])}>
          全てOFF
        </button>
        &nbsp;/&nbsp;
        <button
          type="button"
          className={linkClass}
          onClick={() => onChange(allIds.filter((id) => !checked.includes(id)))}
        >
          反転
        </button>
      </div>
      <div className="grid grid-cols-1 border-b border-[#ccc] min-[768px]:grid-cols-2 min-[1200px]:grid-cols-3">
        {participants.map((participant) => (
          <label
            key={participant.id}
            className="flex cursor-pointer items-center border-t border-[#ccc] px-[10px] py-[5px]"
          >
            <input
              type="checkbox"
              checked={checked.includes(participant.id)}
              onChange={() => onChange(toggle(checked, participant.id))}
            />
            <div className="px-[10px]">
              {participant.imgUrl != null && (
                <img
                  src={participant.imgUrl}
                  width={(participant.imgWidth ?? 0) / 2}
                  height={(participant.imgHeight ?? 0) / 2}
                  alt={participant.name ?? ""}
                />
              )}
            </div>
            <div className="flex-1 font-normal">
              <p>{participant.name}</p>
              <p
                className={
                  participant.deadStatus !== "生存" && participant.deadStatus !== "見学"
                    ? "text-[#e74c3c]"
                    : ""
                }
              >
                {participant.deadStatus}
              </p>
            </div>
          </label>
        ))}
      </div>
    </div>
  );
}

/**
 * 発言抽出モーダル。種別 / 発言者 / 宛先 / キーワード / ショートカットで絞り込み、
 * 「抽出」で確定する (確定するまで表示には反映しない)。
 */
export function FilterModal({
  open,
  onClose,
  filter,
  participants,
  myselfId,
  notificationKeyword,
  onApply,
  onApplyNewTab,
}: {
  open: boolean;
  onClose: () => void;
  filter: MessageFilter;
  participants: VillageFilterParticipantContent[];
  /** 参加中の自分の参加者 ID (自分宛ショートカット用、未参加は null) */
  myselfId: number | null;
  /** Discord 通知キーワード (未設定は null) */
  notificationKeyword: string | null;
  onApply: (filter: MessageFilter) => void;
  onApplyNewTab: (filter: MessageFilter) => void;
}) {
  const allParticipantIds = participants.map((p) => p.id);
  const [draft, setDraft] = useState<Draft>(() => toDraft(filter, allParticipantIds));

  // モーダルを開くたびに現在の適用状態から編集を始める
  useEffect(() => {
    if (open)
      setDraft(
        toDraft(
          filter,
          participants.map((p) => p.id),
        ),
      );
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [open]);

  const apply = (next: Draft) => {
    onApply(toFilter(next, allParticipantIds));
    onClose();
  };

  const sayShortcut = (type: string) => {
    apply({ ...toDraft(EMPTY_FILTER, allParticipantIds), types: [type] });
  };

  return (
    <Modal open={open} onClose={onClose} title="発言抽出" size="wide">
      <div className="max-h-[70vh] overflow-x-hidden overflow-y-auto text-[12px]">
        <div>
          <strong>ショートカット機能</strong>
          <div className="mt-[10px]">
            <button type="button" className={linkClass} onClick={() => sayShortcut("WEREWOLF_SAY")}>
              囁き
            </button>
            &nbsp;/&nbsp;
            <button type="button" className={linkClass} onClick={() => sayShortcut("MASON_SAY")}>
              共鳴
            </button>
            &nbsp;/&nbsp;
            <button type="button" className={linkClass} onClick={() => sayShortcut("LOVERS_SAY")}>
              恋人
            </button>
            &nbsp;/&nbsp;
            <button type="button" className={linkClass} onClick={() => sayShortcut("TELEPATHY")}>
              念話
            </button>
            {myselfId != null && (
              <>
                &nbsp;/&nbsp;
                <button
                  type="button"
                  className={linkClass}
                  onClick={() =>
                    apply({
                      ...toDraft(EMPTY_FILTER, allParticipantIds),
                      toParticipantIds: [myselfId],
                    })
                  }
                >
                  自分宛
                </button>
              </>
            )}
            {notificationKeyword != null && notificationKeyword !== "" && (
              <>
                &nbsp;/&nbsp;
                <button
                  type="button"
                  className={linkClass}
                  onClick={() =>
                    apply({
                      ...toDraft(EMPTY_FILTER, allParticipantIds),
                      keywords: notificationKeyword,
                    })
                  }
                >
                  通知キーワード
                </button>
              </>
            )}
          </div>
          <hr className="my-[15px] border-[#464545]" />
        </div>

        <div className="mt-[15px]">
          <strong>発言種別</strong>
          <div className="mt-[10px]">
            <button
              type="button"
              className={linkClass}
              onClick={() => setDraft({ ...draft, types: ALL_TYPE_VALUES })}
            >
              全てON
            </button>
            &nbsp;/&nbsp;
            <button
              type="button"
              className={linkClass}
              onClick={() => setDraft({ ...draft, types: [] })}
            >
              全てOFF
            </button>
            &nbsp;/&nbsp;
            <button
              type="button"
              className={linkClass}
              onClick={() =>
                setDraft({
                  ...draft,
                  types: ALL_TYPE_VALUES.filter((t) => !draft.types.includes(t)),
                })
              }
            >
              反転
            </button>
          </div>
          {[0, 4, 8].map((start) => (
            <div key={start} className="mt-[10px] flex">
              {FILTER_TYPES.slice(start, start + 4).map((type) => {
                const active = draft.types.includes(type.value);
                return (
                  <label
                    key={type.value}
                    className={`flex-1 cursor-pointer border border-[#00bc8c] px-[9px] py-[6px] text-center text-white first:rounded-l-[3px] last:rounded-r-[3px] ${
                      active ? "bg-[#00bc8c]" : "bg-transparent"
                    }`}
                  >
                    <input
                      type="checkbox"
                      className="hidden"
                      checked={active}
                      onChange={() =>
                        setDraft({ ...draft, types: toggle(draft.types, type.value) })
                      }
                    />
                    {type.label}
                  </label>
                );
              })}
            </div>
          ))}
        </div>

        <div className="mt-[40px]">
          <details open>
            <summary className="cursor-pointer">
              <strong>発言者（クリックで開閉）</strong>
            </summary>
            <div className="mt-[10px]">
              <ParticipantCheckList
                participants={participants}
                checked={draft.participantIds}
                onChange={(ids) => setDraft({ ...draft, participantIds: ids })}
              />
            </div>
          </details>
        </div>

        <div className="mt-[40px]">
          <strong>キーワード</strong>
          <input
            type="text"
            className={`${inputClass} mt-[5px] w-full`}
            placeholder="スペース区切り"
            value={draft.keywords}
            onChange={(e) => setDraft({ ...draft, keywords: e.target.value })}
          />
          <button
            type="button"
            className={`${linkClass} mt-[10px]`}
            onClick={() => setDraft({ ...draft, keywords: "" })}
          >
            クリア
          </button>
        </div>

        <div className="mt-[40px]">
          <details open>
            <summary className="cursor-pointer">
              <strong>宛先（クリックで開閉）</strong>
              {myselfId != null && (
                <>
                  &nbsp;
                  <button
                    type="button"
                    className={linkClass}
                    onClick={(e) => {
                      e.preventDefault();
                      setDraft({ ...draft, toParticipantIds: [myselfId] });
                    }}
                  >
                    自分宛
                  </button>
                </>
              )}
            </summary>
            <div className="mt-[10px]">
              <ParticipantCheckList
                participants={participants}
                checked={draft.toParticipantIds}
                onChange={(ids) => setDraft({ ...draft, toParticipantIds: ids })}
              />
            </div>
          </details>
        </div>

        <div className="mt-[40px] mb-[20px]">
          <label className="flex cursor-pointer items-center gap-[5px]">
            <input
              type="checkbox"
              checked={draft.spoiled}
              onChange={() => setDraft({ ...draft, spoiled: !draft.spoiled })}
            />
            エピローグ前同等の表示にする
          </label>
        </div>
      </div>

      <div className="mt-[20px] flex justify-end gap-[10px] text-[12px]">
        <Button variant="default" onClick={onClose}>
          閉じる
        </Button>
        <Button
          variant="default"
          onClick={() => setDraft(toDraft(EMPTY_FILTER, allParticipantIds))}
        >
          リセット
        </Button>
        <Button
          onClick={() => {
            onApplyNewTab(toFilter(draft, allParticipantIds));
            onClose();
          }}
        >
          別タブ
        </Button>
        <Button onClick={() => apply(draft)}>抽出</Button>
      </div>
    </Modal>
  );
}

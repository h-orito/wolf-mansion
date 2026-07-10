import { useEffect, useState } from "react";

import { Button } from "~/components/ui/Button";
import { ButtonCheckboxGroup } from "~/components/ui/ButtonCheckboxGroup";
import { inputClass } from "~/components/ui/Input";
import { Modal } from "~/components/ui/Modal";
import { TextButton } from "~/components/ui/TextButton";
import { findNormalImage } from "~/features/charachips/charaImage";
import type { VillageParticipantView } from "~/features/village/api";
import { useVillageContext } from "~/features/village/VillageContext";
import { allParticipants, sortByRoomNumber } from "~/features/village/participants";
import { useMyVillageSituation } from "~/features/village/useVillage";
import { EMPTY_FILTER, FILTER_TYPES, type MessageFilter } from "~/features/village/filter";
import { MessageType } from "~/features/village/components/message/messageType";

const ALL_TYPE_VALUES = FILTER_TYPES.map((t) => t.value);

type FilterParticipant = {
  id: number;
  name: string;
  imgWidth: number;
  imgHeight: number;
  imgUrl: string;
  deadStatus: string | null;
};

function toFilterParticipant(p: VillageParticipantView): FilterParticipant {
  return {
    id: p.id,
    name: p.name,
    imgWidth: p.chara.size.width,
    imgHeight: p.chara.size.height,
    imgUrl: findNormalImage(p.chara.images.list)?.url ?? "",
    deadStatus: toDeadStatus(p),
  };
}

function toDeadStatus(p: VillageParticipantView): string | null {
  if (p.isSpectator) return "見学";
  if (!p.dead.isDead) return "生存";
  const name = p.dead.reason?.name ?? "";
  const reason = name.endsWith("死") ? name : `${name}死`;
  return `${p.dead.deadDay}d${reason}`;
}

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

function ParticipantCheckList({
  participants,
  checked,
  onChange,
}: {
  participants: FilterParticipant[];
  checked: number[];
  onChange: (ids: number[]) => void;
}) {
  const allIds = participants.map((p) => p.id);
  return (
    <div>
      <div className="mb-[10px]">
        <TextButton onClick={() => onChange(allIds)}>全てON</TextButton>
        &nbsp;/&nbsp;
        <TextButton onClick={() => onChange([])}>全てOFF</TextButton>
        &nbsp;/&nbsp;
        <TextButton onClick={() => onChange(allIds.filter((id) => !checked.includes(id)))}>
          反転
        </TextButton>
      </div>
      <div className="grid grid-cols-1 border-b border-border-soft min-[768px]:grid-cols-2 min-[1200px]:grid-cols-3">
        {participants.map((participant) => (
          <label
            key={participant.id}
            className="flex cursor-pointer items-center border-t border-border-soft px-[10px] py-[5px]"
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
                    ? "text-danger"
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
  dayParam,
  onApply,
  onApplyNewTab,
}: {
  open: boolean;
  onClose: () => void;
  filter: MessageFilter;
  dayParam: number | undefined;
  onApply: (filter: MessageFilter) => void;
  onApplyNewTab: (filter: MessageFilter) => void;
}) {
  const village = useVillageContext();
  const { data: mySituation } = useMyVillageSituation(village.id, dayParam);
  // 部屋割りのある村では部屋番号と一覧の並びを一致させて対象を探しやすくする (表示専用のソート)
  const participants = sortByRoomNumber(allParticipants(village)).map(toFilterParticipant);
  const myselfId = mySituation?.myself?.id ?? null;
  const notificationKeyword =
    mySituation?.myself?.notification?.message?.keywords?.join("\n") ?? null;

  const allParticipantIds = participants.map((p) => p.id);
  const [draft, setDraft] = useState<Draft>(() => toDraft(filter, allParticipantIds));

  // モーダルを開くたびに現在の適用状態から編集を始める
  useEffect(() => {
    if (open) setDraft(toDraft(filter, allParticipantIds));
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
            <TextButton onClick={() => sayShortcut(MessageType.WEREWOLF_SAY)}>囁き</TextButton>
            &nbsp;/&nbsp;
            <TextButton onClick={() => sayShortcut(MessageType.MASON_SAY)}>共鳴</TextButton>
            &nbsp;/&nbsp;
            <TextButton onClick={() => sayShortcut(MessageType.LOVERS_SAY)}>恋人</TextButton>
            &nbsp;/&nbsp;
            <TextButton onClick={() => sayShortcut(MessageType.TELEPATHY)}>念話</TextButton>
            {myselfId != null && (
              <>
                &nbsp;/&nbsp;
                <TextButton
                  onClick={() =>
                    apply({
                      ...toDraft(EMPTY_FILTER, allParticipantIds),
                      toParticipantIds: [myselfId],
                    })
                  }
                >
                  自分宛
                </TextButton>
              </>
            )}
            {notificationKeyword != null && notificationKeyword !== "" && (
              <>
                &nbsp;/&nbsp;
                <TextButton
                  onClick={() =>
                    apply({
                      ...toDraft(EMPTY_FILTER, allParticipantIds),
                      keywords: notificationKeyword,
                    })
                  }
                >
                  通知キーワード
                </TextButton>
              </>
            )}
          </div>
          <hr className="my-[15px] border-border" />
        </div>

        <div className="mt-[15px]">
          <strong>発言種別</strong>
          <div className="mt-[10px]">
            <TextButton onClick={() => setDraft({ ...draft, types: ALL_TYPE_VALUES })}>
              全てON
            </TextButton>
            &nbsp;/&nbsp;
            <TextButton onClick={() => setDraft({ ...draft, types: [] })}>全てOFF</TextButton>
            &nbsp;/&nbsp;
            <TextButton
              onClick={() =>
                setDraft({
                  ...draft,
                  types: ALL_TYPE_VALUES.filter((t) => !draft.types.includes(t)),
                })
              }
            >
              反転
            </TextButton>
          </div>
          {[0, 4, 8].map((start) => (
            <div key={start} className="mt-[10px]">
              <ButtonCheckboxGroup
                options={FILTER_TYPES.slice(start, start + 4)}
                values={draft.types}
                onToggle={(value) => setDraft({ ...draft, types: toggle(draft.types, value) })}
                ariaLabel="発言種別"
              />
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
          <TextButton className="mt-[10px]" onClick={() => setDraft({ ...draft, keywords: "" })}>
            クリア
          </TextButton>
        </div>

        <div className="mt-[40px]">
          <details open>
            <summary className="cursor-pointer">
              <strong>宛先（クリックで開閉）</strong>
              {myselfId != null && (
                <>
                  &nbsp;
                  <TextButton
                    onClick={(e) => {
                      e.preventDefault();
                      setDraft({ ...draft, toParticipantIds: [myselfId] });
                    }}
                  >
                    自分宛
                  </TextButton>
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
          onClick={() => {
            if (window.confirm("抽出条件をリセットしてよろしいですか？")) {
              onApply(EMPTY_FILTER);
              onClose();
            }
          }}
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

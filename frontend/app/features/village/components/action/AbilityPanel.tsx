import { Link } from "react-router";

import { Alert, AlertList, ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { Divider } from "~/components/ui/Divider";
import { selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  setVillageAbility,
  type ParticipantSituationView,
  type VillageAbilityRequest,
  type VillageDetailView,
  type VillageRoomAssignedRow,
} from "~/features/village/api";
import { resolveParticipantName } from "~/features/village/participants";
import { useAsyncAction } from "~/lib/useAsyncAction";
import type { useAbilityState } from "./useAbilityState";

const NO_FOOTSTEP = "なし";

/**
 * 役職能力のセット。入力パターンは situation の素材で出し分ける:
 * 襲撃 (襲撃者 + 対象 + 足音) / 調査 (足音 select) / 徘徊 (部屋トグル) /
 * 対象 + 足音 / 対象のみ (対象なし許容含む)。
 */
export function AbilityPanel({
  villageId,
  village,
  mySituation,
  roomAssignedRows,
  abilityState,
  onDone,
}: {
  villageId: number;
  village: VillageDetailView;
  mySituation: ParticipantSituationView;
  roomAssignedRows: VillageRoomAssignedRow[] | null | undefined;
  abilityState: ReturnType<typeof useAbilityState>;
  onDone: () => Promise<unknown>;
}) {
  const ability = mySituation.ability;
  const skill = mySituation.myself?.skill;

  const {
    isAttack,
    isInvestigate,
    isDisturb,
    attackerCharaId,
    targetCharaId,
    footstep,
    setFootstep,
    disturbRooms,
    setDisturbRooms,
    targets,
    footstepOptions,
    onAttackerChange,
    onTargetChange,
  } = abilityState;

  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      const request: VillageAbilityRequest = isDisturb
        ? { footstep: disturbRooms.length === 0 ? NO_FOOTSTEP : disturbRooms.join(",") }
        : isInvestigate
          ? { footstep }
          : {
              attackerCharaId: isAttack && attackerCharaId !== "" ? Number(attackerCharaId) : null,
              targetCharaId: targetCharaId === "" ? null : Number(targetCharaId),
              footstep: footstep === "" ? null : footstep,
            };
      await setVillageAbility(villageId, request);
      showToast("能力をセットしました");
      await onDone();
    }, "能力セットに失敗しました");

  const needsFootstepSelect = isAttack || ability.isTargetingAndFootstep;

  return (
    <Panel title="役職" storageKey="skillform" fixable>
      <div>
        <ErrorMessage error={error} />
        {mySituation.myself?.dead.isDead && (
          <Alert variant="info" className="mb-[10px] text-village-sm">
            あなたは死亡しました。
          </Alert>
        )}
        {skill != null && (
          <>
            <Alert className="mb-[5px] text-village-sm">
              <p dangerouslySetInnerHTML={{ __html: skill.description ?? "" }} />
              {mySituation.myself?.camp != null && (
                <p>
                  あなたは <strong>{mySituation.myself.camp.name}</strong> です。
                </p>
              )}
            </Alert>
            <div className="mb-[10px] text-right text-village-sm">
              <Link
                to={`/skill#${skill.code.toLowerCase()}`}
                target="_blank"
                className="text-wm-accent hover:underline"
              >
                役職「{skill.name}」の詳細
              </Link>
            </div>
          </>
        )}
        {ability.canUseAbility && <Divider />}
        {ability.canUseAbility && ability.targetingMessage != null && (
          <p className="text-village-sm">
            現在の選択: <strong className="text-base">{ability.targetingMessage}</strong>
          </p>
        )}
        {ability.canUseAbility && ability.footstep != null && (
          <p className="text-village-sm">
            通過する部屋:{" "}
            <strong className="text-base">
              {ability.footstep === "" ? "なし" : ability.footstep}
            </strong>{" "}
            （明日朝時点で生存者のいる部屋のみ響きます）
          </p>
        )}
        {ability.canUseAbility && ability.targetingMessage != null && <Divider />}

        {ability.canUseAbility && isDisturb && (
          <div className="mt-[10px]">
            <p>
              任意の部屋からその直線上の部屋に向かって徘徊し、徘徊した部屋に足音を響かせることが可能です。部屋を選択してセットしてください。
              <br />
              徘徊しない場合は何も選択せずセットしてください。
            </p>
            {roomAssignedRows != null && (
              <div className="mt-[10px] overflow-x-auto">
                <table className="border-collapse border border-[#464545] text-village-sm">
                  <tbody>
                    {roomAssignedRows.map((row, rowIndex) => (
                      <tr key={rowIndex}>
                        {(row.roomAssignedList ?? []).map((room) => {
                          const selected = disturbRooms.includes(room.roomNumber ?? "");
                          return (
                            <td
                              key={room.roomNumber}
                              className="cursor-pointer text-center align-bottom"
                              style={{
                                border: selected ? "2px solid #0ce3ac" : "1px solid #464545",
                                width: room.charaImgWidth ?? 50,
                                height: room.charaImgHeight ?? 60,
                                ...(room.charaImgUrl
                                  ? {
                                      backgroundImage: `url(${room.charaImgUrl})`,
                                      backgroundRepeat: "no-repeat",
                                      backgroundSize: "contain",
                                    }
                                  : {}),
                                ...(room.isDead == null || room.isDead ? { opacity: 0.3 } : {}),
                              }}
                              onClick={() =>
                                setDisturbRooms((prev) =>
                                  prev.includes(room.roomNumber ?? "")
                                    ? prev.filter((r) => r !== room.roomNumber)
                                    : [...prev, room.roomNumber ?? ""],
                                )
                              }
                            >
                              <span
                                className="whitespace-nowrap"
                                style={{ backgroundColor: "#222222", opacity: 0.8 }}
                              >
                                {room.roomNumber} {room.charaShortName ?? ""}
                              </span>
                            </td>
                          );
                        })}
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
            <p className="mt-[5px]">
              通過する部屋: {disturbRooms.length === 0 ? NO_FOOTSTEP : disturbRooms.join(",")}
            </p>
          </div>
        )}

        {ability.canUseAbility && !isDisturb && isInvestigate && (
          <div>
            {footstepOptions.length === 0 ? (
              <p>調査対象の足音がないため、本日は能力セットできません。</p>
            ) : (
              <div className="flex flex-wrap items-center gap-[5px]">
                <select
                  className={`${selectClass} max-w-[240px]`}
                  value={footstep}
                  onChange={(e) => setFootstep(e.target.value)}
                  aria-label="調査する足音"
                >
                  {footstepOptions.map((option) => (
                    <option key={option} value={option}>
                      {option}
                    </option>
                  ))}
                </select>
                <span>を調査する</span>
              </div>
            )}
          </div>
        )}

        {ability.canUseAbility && !isDisturb && !isInvestigate && (
          <div className="text-village-sm">
            {isAttack && (
              <AlertList className="mb-[10px]">
                <li>個人ごとに別々の襲撃内容をセットできます。</li>
                <li>あなたの襲撃セット内容はあなたしか参照できません。</li>
                <li>
                  日付更新時に、処刑と同様、最多票となったセット内容（襲撃者x対象x足音）が採用され、その内容で襲撃を行います。
                </li>
              </AlertList>
            )}
            {isAttack && (
              <div className="mb-[5px]">
                <span>襲撃者 </span>
                <select
                  className={selectClass}
                  value={attackerCharaId}
                  onChange={(e) => onAttackerChange(e.target.value)}
                  aria-label="襲撃者"
                >
                  {ability.attackerCharaIds.map((id) => (
                    <option key={id} value={id}>
                      {resolveParticipantName(village, id)}
                    </option>
                  ))}
                </select>
              </div>
            )}
            {(targets.length > 0 || ability.isAvailableNoTarget) && (
              <div className="mb-[5px]">
                {ability.targetPrefix != null && <span>{ability.targetPrefix}</span>}
                <select
                  className={selectClass}
                  value={targetCharaId}
                  onChange={(e) => onTargetChange(e.target.value)}
                  aria-label="能力の対象"
                >
                  {ability.isAvailableNoTarget && <option value="">なし</option>}
                  {targets.map((target) => (
                    <option key={target.charaId} value={target.charaId}>
                      {target.name}
                    </option>
                  ))}
                </select>
                {ability.targetSuffix != null && <span>{ability.targetSuffix}</span>}
              </div>
            )}
            {needsFootstepSelect && (
              <div className="mb-[5px]">
                <span>通過する部屋</span>
                <select
                  className={selectClass}
                  value={footstep}
                  onChange={(e) => setFootstep(e.target.value)}
                  aria-label="通過する部屋"
                >
                  {footstepOptions.map((option) => (
                    <option key={option} value={option}>
                      {option}
                    </option>
                  ))}
                </select>
              </div>
            )}
          </div>
        )}

        {ability.canUseAbility && (
          <div className="flex justify-end">
            <Button
              onClick={submit}
              disabled={submitting || (isInvestigate && !isDisturb && footstepOptions.length === 0)}
            >
              能力セット
            </Button>
          </div>
        )}

        <FactionNotes ability={ability} village={village} />

        {(ability.skillHistoryList ?? []).length > 0 && (
          <div>
            <Divider />
            <p>能力セット履歴</p>
            {(ability.skillHistoryList ?? []).map((history, index) => (
              <p key={index} className="text-village-sm">
                {history}
              </p>
            ))}
          </div>
        )}
      </div>
    </Panel>
  );
}

/** 自陣営の仲間名リスト (見える役職にのみ situation が値を返す)。 */
function FactionNotes({
  ability,
  village,
}: {
  ability: ParticipantSituationView["ability"];
  village: VillageDetailView;
}) {
  const resolve = (id: number) => resolveParticipantName(village, id);
  const notes: { key: string; label: string; names: string[] }[] = [];
  if (ability.wolfCharaIds.length > 0)
    notes.push({
      key: "wolf",
      label: "この村の人狼は、",
      names: ability.wolfCharaIds.map(resolve),
    });
  if (ability.cMadmanCharaIds.length > 0)
    notes.push({
      key: "cmad",
      label: "この村のC国狂人は、",
      names: ability.cMadmanCharaIds.map(resolve),
    });
  if (ability.foxCharaIds.length > 0)
    notes.push({ key: "fox", label: "この村の妖狐は、", names: ability.foxCharaIds.map(resolve) });
  if (ability.masonsCharaIds.length > 0)
    notes.push({
      key: "mason",
      label: "この村の共鳴者は、",
      names: ability.masonsCharaIds.map(resolve),
    });
  if (ability.listenMasonsCharaIds.length > 0)
    notes.push({
      key: "listen",
      label: "この村の共有者は、",
      names: ability.listenMasonsCharaIds.map(resolve),
    });
  const loversLines =
    ability.lovers.length > 0
      ? ability.lovers.map((l) => ({ from: resolve(l.fromCharaId), to: resolve(l.toCharaId) }))
      : null;
  if (notes.length === 0 && loversLines == null) return null;
  return (
    <>
      {notes.map((note) => (
        <div key={note.key}>
          <Divider />
          <p className="text-village-sm">
            {note.label}
            {note.names.map((name, i) => (
              <span key={i}>
                {i > 0 && <br />}
                <strong className="text-base">{name}</strong>
                {i < note.names.length - 1 && "、"}
              </span>
            ))}
            {" です。"}
          </p>
        </div>
      ))}
      {loversLines != null && (
        <div>
          <Divider />
          <p className="text-village-sm">
            {"この村で恋に落ちているのは"}
            {loversLines.map((l, i) => (
              <span key={i}>
                <br />
                <strong className="text-base">{l.from}</strong>
                {" → "}
                <strong className="text-base">{l.to}</strong>
              </span>
            ))}
            <br />
            {"です。"}
          </p>
        </div>
      )}
    </>
  );
}

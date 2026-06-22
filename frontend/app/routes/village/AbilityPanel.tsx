import { useEffect, useState } from "react";

import { Button } from "~/components/ui/Button";
import { selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  fetchAbilityFootsteps,
  fetchAttackTargets,
  setVillageAbility,
  type ParticipantSituationView,
  type VillageAbilityRequest,
  type VillageRoomAssignedRow,
} from "~/features/village/api";
import { ApiError } from "~/lib/api";

const NO_FOOTSTEP = "なし";

/**
 * 役職能力のセット。入力パターンは situation の素材で出し分ける:
 * 襲撃 (襲撃者 + 対象 + 足音) / 調査 (足音 select) / 徘徊 (部屋トグル) /
 * 対象 + 足音 / 対象のみ (対象なし許容含む)。
 */
export function AbilityPanel({
  villageId,
  mySituation,
  roomAssignedRows,
  onDone,
}: {
  villageId: number;
  mySituation: ParticipantSituationView;
  roomAssignedRows: VillageRoomAssignedRow[] | null | undefined;
  onDone: () => Promise<unknown>;
}) {
  const ability = mySituation.ability;
  const skill = mySituation.myself?.skill;

  const isAttack = (ability.attackerList ?? []).length > 0;
  const isInvestigate = skill?.hasInvestigateAbility ?? false;
  const isDisturb = (skill?.hasDisturbAbility ?? false) && (ability.targetList ?? []).length === 0;

  const [attackerCharaId, setAttackerCharaId] = useState<string>(
    ability.attackerCharaId != null ? String(ability.attackerCharaId) : "",
  );
  const [targetCharaId, setTargetCharaId] = useState<string>(
    ability.targetCharaId != null ? String(ability.targetCharaId) : "",
  );
  const [footstep, setFootstep] = useState<string>(
    ability.targetFootstep ?? ability.footstep ?? "",
  );
  // 徘徊の通過部屋 (CSV をトグル選択で組み立てる)
  const [disturbRooms, setDisturbRooms] = useState<string[]>(() => {
    const current = ability.footstep;
    return current == null || current === NO_FOOTSTEP ? [] : current.split(",");
  });
  const [targets, setTargets] = useState(ability.targetList ?? []);
  const [footstepOptions, setFootstepOptions] = useState<string[]>(
    ability.targetFootstepList ?? [],
  );
  const showToast = useToast((s) => s.show);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  // 襲撃の対象候補と現在対象の足音候補は situation に含まれないため、初期表示時に取得する
  useEffect(() => {
    if (!ability.canUseAbility) return;
    if (isAttack && attackerCharaId !== "") {
      fetchAttackTargets(villageId, Number(attackerCharaId))
        .then((response) => setTargets(response.targets ?? []))
        .catch(() => {});
    }
    if ((isAttack || ability.isTargetingAndFootstep) && targetCharaId !== "") {
      fetchAbilityFootsteps(
        villageId,
        isAttack && attackerCharaId !== "" ? Number(attackerCharaId) : null,
        Number(targetCharaId),
      )
        .then((response) => setFootstepOptions(response.footsteps ?? []))
        .catch(() => {});
    }
    // 初期表示のみ。以降の変更は onAttackerChange / onTargetChange が取得する
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // 襲撃者を選ぶと対象候補が変わり、対象を選ぶと足音候補が変わる
  const onAttackerChange = async (value: string) => {
    setAttackerCharaId(value);
    setTargetCharaId("");
    setFootstep("");
    if (value === "") return;
    try {
      const response = await fetchAttackTargets(villageId, Number(value));
      setTargets(response.targets ?? []);
    } catch {
      setTargets([]);
    }
  };

  const onTargetChange = async (value: string) => {
    setTargetCharaId(value);
    setFootstep("");
    if (!(isAttack || ability.isTargetingAndFootstep) || value === "") return;
    try {
      const response = await fetchAbilityFootsteps(
        villageId,
        isAttack && attackerCharaId !== "" ? Number(attackerCharaId) : null,
        Number(value),
      );
      setFootstepOptions(response.footsteps ?? []);
    } catch {
      setFootstepOptions([]);
    }
  };

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    const request: VillageAbilityRequest = isDisturb
      ? { footstep: disturbRooms.length === 0 ? NO_FOOTSTEP : disturbRooms.join(",") }
      : isInvestigate
        ? { footstep }
        : {
            attackerCharaId: isAttack && attackerCharaId !== "" ? Number(attackerCharaId) : null,
            targetCharaId: targetCharaId === "" ? null : Number(targetCharaId),
            footstep: footstep === "" ? null : footstep,
          };
    try {
      await setVillageAbility(villageId, request);
      showToast("能力をセットしました");
      await onDone();
    } catch (e) {
      setError(e instanceof ApiError ? e.detail : "能力セットに失敗しました");
    } finally {
      setSubmitting(false);
    }
  };

  const needsFootstepSelect = isAttack || ability.isTargetingAndFootstep;

  return (
    <Panel title="役職">
      <div className="space-y-[10px]">
        {error != null && <p className="text-[#e74c3c]">{error}</p>}
        {skill != null && (
          <div className="rounded border border-[#00bc8c] p-[10px] text-village-sm">
            <p dangerouslySetInnerHTML={{ __html: skill.description ?? "" }} />
            {mySituation.myself?.camp != null && (
              <p>
                あなたは <strong>{mySituation.myself.camp.name}</strong> です。
              </p>
            )}
          </div>
        )}
        {mySituation.myself?.dead.isDead && (
          <div className="rounded border border-[#e74c3c] p-[10px] text-village-sm text-[#e74c3c]">
            あなたは死亡しました。
          </div>
        )}
        {ability.targetingMessage != null && <p>{ability.targetingMessage}</p>}

        {ability.canUseAbility && isDisturb && (
          <div>
            <p>
              任意の部屋からその直線上の部屋に向かって徘徊し、徘徊した部屋に足音を響かせることが可能です。
              部屋を選択してセットしてください。徘徊しない場合は何も選択せずセットしてください。
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
                              className={`cursor-pointer border border-[#464545] p-[5px] text-center ${
                                selected ? "bg-[#0ce3ac]/30" : ""
                              }`}
                              onClick={() =>
                                setDisturbRooms((prev) =>
                                  prev.includes(room.roomNumber ?? "")
                                    ? prev.filter((r) => r !== room.roomNumber)
                                    : [...prev, room.roomNumber ?? ""],
                                )
                              }
                            >
                              {room.roomNumber} {room.charaShortName ?? ""}
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
                  <option value="">選択してください</option>
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
          <div className="space-y-[10px]">
            {isAttack && (
              <div className="flex flex-wrap items-center gap-[5px]">
                <span>襲撃者</span>
                <select
                  className={`${selectClass} max-w-[240px]`}
                  value={attackerCharaId}
                  onChange={(e) => onAttackerChange(e.target.value)}
                  aria-label="襲撃者"
                >
                  {(ability.attackerList ?? []).map((attacker) => (
                    <option key={attacker.charaId} value={attacker.charaId}>
                      {attacker.name}
                    </option>
                  ))}
                </select>
              </div>
            )}
            {(targets.length > 0 || ability.isAvailableNoTarget) && (
              <div className="flex flex-wrap items-center gap-[5px]">
                {ability.targetPrefix != null && <span>{ability.targetPrefix}</span>}
                <select
                  className={`${selectClass} max-w-[240px]`}
                  value={targetCharaId}
                  onChange={(e) => onTargetChange(e.target.value)}
                  aria-label="能力の対象"
                >
                  {ability.isAvailableNoTarget && <option value="">なし</option>}
                  {!ability.isAvailableNoTarget && <option value="">選択してください</option>}
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
              <div className="flex flex-wrap items-center gap-[5px]">
                <span>通過する部屋</span>
                <select
                  className={`${selectClass} max-w-[240px]`}
                  value={footstep}
                  onChange={(e) => setFootstep(e.target.value)}
                  aria-label="通過する部屋"
                >
                  <option value="">選択してください</option>
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

        <FactionNotes ability={ability} />

        {(ability.skillHistoryList ?? []).length > 0 && (
          <div>
            <hr className="my-[10px] border-[#464545]" />
            <strong>能力セット履歴</strong>
            {(ability.skillHistoryList ?? []).map((history, index) => (
              <p key={index}>{history}</p>
            ))}
          </div>
        )}
      </div>
    </Panel>
  );
}

/** 自陣営の仲間名リスト (見える役職にのみ situation が値を返す)。 */
function FactionNotes({ ability }: { ability: ParticipantSituationView["ability"] }) {
  const notes: { key: string; text: string }[] = [];
  if (ability.werewolfNames)
    notes.push({ key: "wolf", text: `この村の人狼は、 ${ability.werewolfNames} です。` });
  if (ability.cMadmanNames)
    notes.push({ key: "cmad", text: `この村のC国狂人は、 ${ability.cMadmanNames} です。` });
  if (ability.foxNames)
    notes.push({ key: "fox", text: `この村の妖狐は、 ${ability.foxNames} です。` });
  if (ability.masonsNames)
    notes.push({ key: "mason", text: `この村の共鳴者は、 ${ability.masonsNames} です。` });
  if (ability.listenMasonsNames)
    notes.push({ key: "listen", text: `この村の共有者は、 ${ability.listenMasonsNames} です。` });
  if (notes.length === 0 && !ability.loversNames) return null;
  return (
    <div>
      <hr className="my-[10px] border-[#464545]" />
      {notes.map((note) => (
        <p key={note.key}>{note.text}</p>
      ))}
      {ability.loversNames && <p className="whitespace-pre-line">{ability.loversNames}</p>}
    </div>
  );
}

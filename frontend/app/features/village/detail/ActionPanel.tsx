import { useEffect, useMemo, useState } from "react";
import type {
  MyselfAbilityView,
  MyselfCommitView,
  MyselfView,
  MyselfVoteView,
  VillageParticipantView,
  VillageView,
} from "./api";
import {
  useAbilityMutation,
  useAttackTargetsQuery,
  useCommitMutation,
  useFootstepCandidatesQuery,
  useVoteMutation,
} from "./hooks";

/**
 * 進行中の村で「能力 / 投票 / コミット」をまとめて行う操作パネル。
 *
 * 表示条件:
 * - 村が進行中 (statusCode = "PROGRESS")
 * - myself が参加中で見学者でない
 * いずれか満たさなければ何も描画しない。
 *
 * 役職に能力が無い (canUseAbility=false かつ ability.typeCode=null) ケースでも、
 * 投票 / コミットだけは出せるよう独立パネルにしている。
 */
export function ActionPanel({
  village,
  myself,
}: {
  village: VillageView;
  myself: MyselfView;
}) {
  // CDef.VillageStatus.進行中 = "IN_PROGRESS"
  if (village.statusCode !== "IN_PROGRESS") return null;
  if (myself.isSpectator) return null;

  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-4">
      <h2 className="text-sm text-slate-400">行動</h2>
      <AbilitySection
        village={village}
        myself={myself}
        ability={myself.ability}
        isDead={myself.isDead}
      />
      <VoteSection
        village={village}
        myselfCharaId={myself.charaId}
        vote={myself.vote}
      />
      <CommitSection villageId={village.id} commit={myself.commit} />
    </section>
  );
}

// ---------- 能力 ----------

/**
 * 能力フォーム。役職に応じて以下を出し分ける:
 *
 * - 単純対象指定 (占い / 護衛 / 同棲 等): target select 1 つ + 能力セット
 * - 襲撃希望 (人狼): attacker select + target select (attacker 連動で fetch) + 足音 (任意)
 * - 捜査 (探偵): targetFootstep を一覧から選択
 * - 徘徊 (狂人 / 妖狐): target なしで footstep のみ
 * - 護衛など足音同時セット: target select + footstep 候補 (target 連動で fetch)
 *
 * 死亡時 / 行使不可日は履歴のみ表示。
 */
function AbilitySection({
  village,
  myself,
  ability,
  isDead,
}: {
  village: VillageView;
  myself: MyselfView;
  ability: MyselfAbilityView;
  isDead: boolean;
}) {
  // 役職そのものが能力を持たないなら能力セクションごと出さない
  if (!myself.skill) return null;
  if (ability.typeCode == null && !ability.isTargetingAndFootstep && !ability.targetFootsteps.length) {
    // 念のため: type=null は能力なし役職を意味する。履歴があれば履歴だけ出す。
    if (ability.skillHistoryList.length === 0) return null;
  }

  const showForm = ability.canUseAbility && !isDead;

  return (
    <div className="space-y-3 border-t border-slate-700/60 pt-3 first:border-t-0 first:pt-0">
      <div className="text-xs text-slate-400">
        能力 {ability.typeName ? `(${ability.typeName})` : ""}
      </div>

      {showForm && <AbilityForm village={village} ability={ability} />}

      {ability.skillHistoryList.length > 0 && (
        <div className="space-y-1">
          <p className="text-xs text-slate-400">能力セット履歴</p>
          <ul className="text-xs text-slate-300 space-y-0.5">
            {ability.skillHistoryList.map((h, i) => (
              <li key={`${i}-${h}`}>{h}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

function AbilityForm({
  village,
  ability,
}: {
  village: VillageView;
  ability: MyselfAbilityView;
}) {
  const mutation = useAbilityMutation(village.id);

  // 制御 state — backend が返している現在の選択状態を初期値に。
  const [attackerCharaId, setAttackerCharaId] = useState<number | null>(
    ability.attackerCharaId ?? (ability.attackerCharaIds[0] ?? null),
  );
  const [targetCharaId, setTargetCharaId] = useState<number | null>(
    ability.targetCharaId ?? null,
  );
  // 捜査の足音 (targetFootstep)
  const [targetFootstep, setTargetFootstep] = useState<string>(
    ability.targetFootstep ?? "",
  );
  // 徘徊 / 護衛 / 襲撃希望の足音
  const [footstep, setFootstep] = useState<string>(ability.footstep ?? "");

  // backend からの最新状態が変わったら state を同期 (refetch でリセットされる)
  useEffect(() => {
    setAttackerCharaId(ability.attackerCharaId ?? (ability.attackerCharaIds[0] ?? null));
    setTargetCharaId(ability.targetCharaId ?? null);
    setTargetFootstep(ability.targetFootstep ?? "");
    setFootstep(ability.footstep ?? "");
  }, [
    ability.attackerCharaId,
    ability.targetCharaId,
    ability.targetFootstep,
    ability.footstep,
    ability.attackerCharaIds.join(","),
  ]);

  const isAttacker = ability.attackerCharaIds.length > 0;
  const isInvestigate = ability.targetFootsteps.length > 0;
  const targetingAndFootstep = ability.isTargetingAndFootstep;

  // 襲撃希望: attacker 選択時に backend から襲撃可能対象を取得
  const attackTargetsQuery = useAttackTargetsQuery(
    village.id,
    isAttacker ? attackerCharaId : null,
  );
  const attackTargetIds = attackTargetsQuery.data ?? [];

  // 足音同時セット (護衛 / 単独襲撃 / 襲撃希望など): target 連動で候補を fetch
  const footstepCandidatesQuery = useFootstepCandidatesQuery(
    village.id,
    {
      charaId: isAttacker ? attackerCharaId : null,
      targetCharaId,
    },
    targetingAndFootstep,
  );
  const footstepCandidates = footstepCandidatesQuery.data ?? [];

  // backend から取得した候補が変わったら、未選択時は先頭を選択
  useEffect(() => {
    if (targetingAndFootstep && footstepCandidates.length > 0 && !footstep) {
      setFootstep(footstepCandidates[0]);
    }
  }, [footstepCandidates, targetingAndFootstep, footstep]);

  // 襲撃希望時は別 GET から得た候補に絞る
  const targetCharaIds = isAttacker ? attackTargetIds : ability.targetCharaIds;

  // 参加者数は通常数十名だが、能力フォームは render が多い (mutation pending 等で更新)
  // のでキャラ名マップは memo 化しておく。
  const charaNames = useMemo(
    () => buildCharaNameMap(village.participants.list),
    [village.participants.list],
  );
  function nameOf(charaId: number | null): string {
    if (charaId == null) return "";
    return charaNames[charaId] ?? `#${charaId}`;
  }

  function submit(e: React.FormEvent) {
    e.preventDefault();
    const body: {
      attackerCharaId?: number;
      targetCharaId?: number;
      footstep?: string;
    } = {};
    if (isAttacker && attackerCharaId != null) body.attackerCharaId = attackerCharaId;
    if (isInvestigate) {
      body.footstep = targetFootstep || undefined;
    } else if (targetCharaId != null) {
      body.targetCharaId = targetCharaId;
    }
    if (targetingAndFootstep && footstep) body.footstep = footstep;
    // 徘徊系: target なしで footstep のみ
    if (
      !isAttacker &&
      !isInvestigate &&
      !targetingAndFootstep &&
      ability.targetCharaIds.length === 0 &&
      footstep
    ) {
      body.footstep = footstep;
    }
    mutation.mutate(body);
  }

  function cancel() {
    if (!confirm("当日の能力行使を取り消しますか？")) return;
    mutation.mutate({});
  }

  // 対象選択が必須 (徘徊系・捜査系を除く) の能力で未選択なら submit させない。
  // 襲撃希望は attacker 連動で attack-targets を fetch するため、ロード中も無効化する
  // (空配列で targetRequired=false になり submittable が活性化してしまうのを防ぐ)。
  const targetRequired = !isInvestigate && targetCharaIds.length > 0;
  const attackTargetsLoading = isAttacker && attackTargetsQuery.isLoading;
  const submittable =
    !mutation.isPending &&
    !attackTargetsLoading &&
    (!targetRequired || targetCharaId != null);
  const hasCurrent =
    ability.attackerCharaId != null ||
    ability.targetCharaId != null ||
    ability.targetFootstep != null ||
    (ability.footstep != null && ability.footstep.length > 0);

  return (
    <form onSubmit={submit} className="space-y-2">
      {isAttacker && (
        <Row label="襲撃者">
          <select
            value={attackerCharaId ?? ""}
            onChange={(e) =>
              setAttackerCharaId(e.target.value ? Number(e.target.value) : null)
            }
            className={inputClass}
            disabled={mutation.isPending}
          >
            {ability.attackerCharaIds.map((id) => (
              <option key={id} value={id}>
                {nameOf(id)}
              </option>
            ))}
          </select>
        </Row>
      )}

      {isInvestigate ? (
        <Row label="調査対象">
          <select
            value={targetFootstep}
            onChange={(e) => setTargetFootstep(e.target.value)}
            className={inputClass}
            disabled={mutation.isPending}
          >
            <option value="">(選択してください)</option>
            {ability.targetFootsteps.map((fs) => (
              <option key={fs} value={fs}>
                {fs}
              </option>
            ))}
          </select>
        </Row>
      ) : (
        targetCharaIds.length > 0 && (
          <Row
            label={
              ability.targetPrefixMessage
                ? ability.targetPrefixMessage
                : "対象"
            }
            suffix={ability.targetSuffixMessage ?? undefined}
          >
            <select
              value={targetCharaId ?? ""}
              onChange={(e) =>
                setTargetCharaId(e.target.value ? Number(e.target.value) : null)
              }
              className={inputClass}
              disabled={mutation.isPending}
            >
              <option value="">(選択してください)</option>
              {targetCharaIds.map((id) => (
                <option key={id} value={id}>
                  {nameOf(id)}
                </option>
              ))}
            </select>
          </Row>
        )
      )}

      {targetingAndFootstep && (
        <Row label="足音">
          <select
            value={footstep}
            onChange={(e) => setFootstep(e.target.value)}
            className={inputClass}
            disabled={mutation.isPending || footstepCandidatesQuery.isLoading}
          >
            {footstepCandidates.length === 0 ? (
              <option value="">{footstepCandidatesQuery.isLoading ? "読み込み中..." : "(候補なし)"}</option>
            ) : (
              footstepCandidates.map((fs) => (
                <option key={fs} value={fs}>
                  {fs}
                </option>
              ))
            )}
          </select>
        </Row>
      )}

      {/* 徘徊系 (target 不要、footstep のみ): targetCharaIds と attackerCharaIds が両方空のときに出す */}
      {!isAttacker &&
        !isInvestigate &&
        !targetingAndFootstep &&
        ability.targetCharaIds.length === 0 && (
          <Row label="足音 (徘徊)">
            <input
              type="text"
              value={footstep}
              onChange={(e) => setFootstep(e.target.value)}
              placeholder="例: 101,102,103 / なし"
              className={inputClass}
              disabled={mutation.isPending}
            />
          </Row>
        )}

      <div className="flex items-center gap-3 pt-1">
        <button
          type="submit"
          disabled={!submittable}
          className={primaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "能力セット"}
        </button>
        {hasCurrent && (
          <button
            type="button"
            onClick={cancel}
            disabled={!submittable}
            className={secondaryButtonClass}
          >
            取消
          </button>
        )}
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

// ---------- 投票 ----------

function VoteSection({
  village,
  myselfCharaId,
  vote,
}: {
  village: VillageView;
  myselfCharaId: number;
  vote: MyselfVoteView;
}) {
  if (!vote.canVote) return null;
  return (
    <div className="space-y-2 border-t border-slate-700/60 pt-3">
      <div className="text-xs text-slate-400">投票</div>
      <VoteForm
        villageId={village.id}
        participants={village.participants.list}
        myselfCharaId={myselfCharaId}
        vote={vote}
      />
    </div>
  );
}

function VoteForm({
  villageId,
  participants,
  myselfCharaId,
  vote,
}: {
  villageId: number;
  participants: VillageParticipantView[];
  myselfCharaId: number;
  vote: MyselfVoteView;
}) {
  const mutation = useVoteMutation(villageId);
  const [targetCharaId, setTargetCharaId] = useState<number | null>(
    vote.targetCharaId ?? null,
  );

  useEffect(() => {
    setTargetCharaId(vote.targetCharaId ?? null);
  }, [vote.targetCharaId]);

  const names = useMemo(() => buildCharaNameMap(participants), [participants]);

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (targetCharaId == null) return;
    mutation.mutate({ targetCharaId });
  }

  return (
    <form onSubmit={submit} className="space-y-2">
      <Row label="投票先">
        <select
          value={targetCharaId ?? ""}
          onChange={(e) =>
            setTargetCharaId(e.target.value ? Number(e.target.value) : null)
          }
          className={inputClass}
          disabled={mutation.isPending}
        >
          <option value="">(選択してください)</option>
          {vote.targetCharaIds.map((id) => (
            <option key={id} value={id}>
              {id === myselfCharaId ? `${names[id] ?? id} (自分)` : names[id] ?? `#${id}`}
            </option>
          ))}
        </select>
      </Row>
      <div className="flex items-center gap-3">
        <button
          type="submit"
          disabled={mutation.isPending || targetCharaId == null}
          className={primaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : vote.targetCharaId != null ? "投票変更" : "投票"}
        </button>
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

// ---------- コミット ----------

function CommitSection({
  villageId,
  commit,
}: {
  villageId: number;
  commit: MyselfCommitView;
}) {
  const mutation = useCommitMutation(villageId);
  if (!commit.isAvailable) return null;

  function toggle() {
    mutation.mutate({ commit: !commit.isCommitting });
  }

  return (
    <div className="space-y-2 border-t border-slate-700/60 pt-3">
      <div className="text-xs text-slate-400">コミット (行動確定)</div>
      <div className="flex items-center gap-3">
        <span className="text-sm">
          {commit.isCommitting ? (
            <span className="text-emerald-300">確定済み</span>
          ) : (
            <span className="text-slate-300">未確定</span>
          )}
        </span>
        <button
          type="button"
          onClick={toggle}
          disabled={mutation.isPending}
          className={commit.isCommitting ? secondaryButtonClass : primaryButtonClass}
        >
          {mutation.isPending
            ? "送信中..."
            : commit.isCommitting
              ? "コミットを取り消し"
              : "コミットする"}
        </button>
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
      </div>
    </div>
  );
}

// ---------- 部品 / utils ----------

/**
 * 子要素を `<label>` で包んでフォームコントロールと関連付ける。
 * 単一の input/select を渡す前提なので、`<label>` の中に置けば htmlFor は不要
 * (スクリーンリーダーは内包要素を自動で関連付ける)。
 */
function Row({
  label,
  suffix,
  children,
}: {
  label: string;
  suffix?: string;
  children: React.ReactNode;
}) {
  return (
    <label className="block space-y-1">
      <span className="text-xs text-slate-400">{label}</span>
      {children}
      {suffix && <span className="block text-xs text-slate-500">{suffix}</span>}
    </label>
  );
}

function buildCharaNameMap(participants: VillageParticipantView[]): Record<number, string> {
  const map: Record<number, string> = {};
  for (const p of participants) {
    map[p.chara.id] = p.name;
  }
  return map;
}

const inputClass =
  "w-full bg-slate-900 border border-slate-700 rounded px-3 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500 disabled:opacity-50";

const primaryButtonClass =
  "rounded bg-indigo-500 hover:bg-indigo-400 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

const secondaryButtonClass =
  "rounded bg-slate-700 hover:bg-slate-600 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

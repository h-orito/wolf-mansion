import { useMemo, useState } from "react";
import { useNavigate } from "react-router";
import { useUpdateSettingsMutation } from "./hooks";
import type {
  VillageSettingsFormView,
  VillageSettingsUpdateBody,
} from "./api";

/**
 * 村設定編集フォーム (creator 専用)。
 *
 * 旧 Thymeleaf の `/village/{id}/settings` 置き換え。レイアウトはフラット、
 * デザインは Step 12/13 で詰める想定 (HANDOFF: monorepo 移行中はデザインを詰めない)。
 *
 * State は `VillageSettingsUpdateBody` (送信形と同型) をそのまま保持し、表示ラベルは
 * `initial.current` (FormView の現在値、`skillName` / `messageTypeName` を持つ) から引く。
 * cross-field バリデーションは backend 側に任せ、フロントでは Required と type のみ最低限。
 */
export function SettingsForm({
  villageId,
  initial,
}: {
  villageId: number;
  initial: VillageSettingsFormView;
}) {
  const navigate = useNavigate();
  const [body, setBody] = useState<VillageSettingsUpdateBody>(() =>
    toInitialBody(initial.current),
  );
  const mutation = useUpdateSettingsMutation(villageId);

  const optionsByCamp = useMemo(
    () => new Map(initial.current.campAllocationList.map((c) => [c.campCode, c])),
    [initial.current.campAllocationList],
  );
  const sayRestrictLabels = useMemo(
    () => new Map(initial.current.sayRestrictList.map((r) => [r.skillCode, r.skillName])),
    [initial.current.sayRestrictList],
  );
  const messageTypeLabels = useMemo(
    () =>
      new Map([
        ...initial.current.skillSayRestrictList.map((r) => [r.messageTypeCode, r.messageTypeName] as const),
        ...initial.current.rpSayRestrictList.map((r) => [r.messageTypeCode, r.messageTypeName] as const),
      ]),
    [initial.current.skillSayRestrictList, initial.current.rpSayRestrictList],
  );

  function submit(e: React.FormEvent) {
    e.preventDefault();
    mutation.mutate(body, {
      onSuccess: () => {
        navigate(`/villages/${villageId}`);
      },
    });
  }

  return (
    <form onSubmit={submit} className="space-y-6">
      <BasicSection body={body} setBody={setBody} />
      <TagSection body={body} setBody={setBody} options={initial.options} />
      <RulesSection body={body} setBody={setBody} options={initial.options} />
      <JoinPasswordSection
        body={body}
        setBody={setBody}
        passwordRequired={initial.isOriginalCharachip}
        passwordAlreadySet={initial.current.joinPasswordSet}
      />
      <DummyMessageSection body={body} setBody={setBody} />
      <OrganizationSection body={body} setBody={setBody} optionsByCamp={optionsByCamp} />
      <RestrictionsSection
        body={body}
        setBody={setBody}
        sayRestrictLabels={sayRestrictLabels}
        messageTypeLabels={messageTypeLabels}
      />

      <div className="sticky bottom-0 -mx-6 px-6 py-3 bg-slate-900/95 border-t border-slate-700 flex items-center justify-between gap-4">
        <div className="text-xs text-slate-400">
          プロローグ中のみ変更可能。エラー時は backend のメッセージが表示されます。
        </div>
        <div className="flex items-center gap-3">
          {mutation.isError && (
            <span className="text-xs text-rose-300 max-w-md truncate" title={mutation.error.message}>
              {mutation.error.message}
            </span>
          )}
          <button
            type="button"
            onClick={() => navigate(`/villages/${villageId}`)}
            className={secondaryButtonClass}
            disabled={mutation.isPending}
          >
            キャンセル
          </button>
          <button type="submit" className={primaryButtonClass} disabled={mutation.isPending}>
            {mutation.isPending ? "送信中..." : "保存"}
          </button>
        </div>
      </div>
    </form>
  );
}

// ============================================================================
// section components
// ============================================================================

type SectionProps = {
  body: VillageSettingsUpdateBody;
  setBody: React.Dispatch<React.SetStateAction<VillageSettingsUpdateBody>>;
};

function BasicSection({ body, setBody }: SectionProps) {
  return (
    <Section title="基本">
      <Field label="村表示名 (5〜40文字)">
        <input
          type="text"
          value={body.villageName}
          onChange={(e) => setBody((s) => ({ ...s, villageName: e.target.value }))}
          className={inputClass}
          minLength={5}
          maxLength={40}
          required
        />
      </Field>

      <div className="grid grid-cols-2 gap-3">
        <Field label="最少開始人数 (8〜)">
          <RequiredNumber value={body.startPersonMinNum} onChange={(v) => setBody((s) => ({ ...s, startPersonMinNum: v }))} min={8} />
        </Field>
        <Field label="定員">
          <RequiredNumber value={body.personMaxNum} onChange={(v) => setBody((s) => ({ ...s, personMaxNum: v }))} max={999} />
        </Field>
      </div>

      <Field label="更新間隔 (時 / 分 / 秒)">
        <div className="flex items-center gap-2">
          <RequiredNumber value={body.dayChangeIntervalHours} onChange={(v) => setBody((s) => ({ ...s, dayChangeIntervalHours: v }))} min={0} max={72} className="w-20" />
          <span className="text-xs text-slate-400">時</span>
          <RequiredNumber value={body.dayChangeIntervalMinutes} onChange={(v) => setBody((s) => ({ ...s, dayChangeIntervalMinutes: v }))} min={0} max={59} className="w-20" />
          <span className="text-xs text-slate-400">分</span>
          <RequiredNumber value={body.dayChangeIntervalSeconds} onChange={(v) => setBody((s) => ({ ...s, dayChangeIntervalSeconds: v }))} min={0} max={59} className="w-20" />
          <span className="text-xs text-slate-400">秒</span>
        </div>
      </Field>

      <Field label="開始日時">
        <div className="flex items-center gap-2 flex-wrap">
          <RequiredNumber value={body.startYear} onChange={(v) => setBody((s) => ({ ...s, startYear: v }))} min={0} className="w-24" />
          <span className="text-xs text-slate-400">年</span>
          <RequiredNumber value={body.startMonth} onChange={(v) => setBody((s) => ({ ...s, startMonth: v }))} min={1} max={12} className="w-16" />
          <span className="text-xs text-slate-400">月</span>
          <RequiredNumber value={body.startDay} onChange={(v) => setBody((s) => ({ ...s, startDay: v }))} min={1} max={31} className="w-16" />
          <span className="text-xs text-slate-400">日</span>
          <RequiredNumber value={body.startHour} onChange={(v) => setBody((s) => ({ ...s, startHour: v }))} min={0} max={23} className="w-16" />
          <span className="text-xs text-slate-400">:</span>
          <RequiredNumber value={body.startMinute} onChange={(v) => setBody((s) => ({ ...s, startMinute: v }))} min={0} max={59} className="w-16" />
        </div>
      </Field>
    </Section>
  );
}

function TagSection({
  body,
  setBody,
  options,
}: SectionProps & { options: VillageSettingsFormView["options"] }) {
  return (
    <Section title="タグ">
      <Field label="募集範囲">
        <select
          value={body.welcomeRange ?? ""}
          onChange={(e) => setBody((s) => ({ ...s, welcomeRange: e.target.value || null }))}
          className={inputClass}
        >
          <option value="">指定なし</option>
          {options.welcomeRanges.map((o) => (
            <option key={o.code} value={o.code}>{o.name}</option>
          ))}
        </select>
      </Field>
      <Field label="年齢制限">
        <select
          value={body.ageLimit ?? ""}
          onChange={(e) => setBody((s) => ({ ...s, ageLimit: e.target.value || null }))}
          className={inputClass}
        >
          <option value="">全年齢</option>
          {options.ageLimits.map((o) => (
            <option key={o.code} value={o.code}>{o.name}</option>
          ))}
        </select>
      </Field>
    </Section>
  );
}

function RulesSection({
  body,
  setBody,
  options,
}: SectionProps & { options: VillageSettingsFormView["options"] }) {
  return (
    <Section title="ルール">
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
        <CheckRow label="記名投票" checked={body.openVote} onChange={(v) => setBody((s) => ({ ...s, openVote: v }))} />
        <CheckRow label="見学を可能にする" checked={body.availableSpectate} onChange={(v) => setBody((s) => ({ ...s, availableSpectate: v }))} />
        <CheckRow label="突然死あり" checked={body.availableSuddenlyDeath} onChange={(v) => setBody((s) => ({ ...s, availableSuddenlyDeath: v }))} />
        <CheckRow label="コミット可能" checked={body.availableCommit} onChange={(v) => setBody((s) => ({ ...s, availableCommit: v }))} />
        <CheckRow label="アクションあり" checked={body.availableAction} onChange={(v) => setBody((s) => ({ ...s, availableAction: v }))} />
        <CheckRow label="連続襲撃あり" checked={body.availableSameWolfAttack} onChange={(v) => setBody((s) => ({ ...s, availableSameWolfAttack: v }))} />
        <CheckRow label="連続ガードあり" checked={body.availableGuardSameTarget} onChange={(v) => setBody((s) => ({ ...s, availableGuardSameTarget: v }))} />
        <CheckRow label="墓下役職公開" checked={body.openSkillInGrave} onChange={(v) => setBody((s) => ({ ...s, openSkillInGrave: v }))} />
        <CheckRow label="墓下見学発言を地上から見られる" checked={body.visibleGraveSpectateMessage} onChange={(v) => setBody((s) => ({ ...s, visibleGraveSpectateMessage: v }))} />
        <CheckRow label="転生時に全役職を候補とする" checked={body.reincarnationSkillAll} onChange={(v) => setBody((s) => ({ ...s, reincarnationSkillAll: v }))} />
        <CheckRow label="闇鍋編成" checked={body.randomOrganization} onChange={(v) => setBody((s) => ({ ...s, randomOrganization: v }))} />
      </div>
      <Field label="秘話可能範囲">
        <select
          value={body.allowedSecretSayCode}
          onChange={(e) => setBody((s) => ({ ...s, allowedSecretSayCode: e.target.value }))}
          className={inputClass}
        >
          {options.allowedSecretSays.map((o) => (
            <option key={o.code} value={o.code}>{o.name}</option>
          ))}
        </select>
      </Field>
    </Section>
  );
}

function JoinPasswordSection({
  body,
  setBody,
  passwordRequired,
  passwordAlreadySet,
}: SectionProps & { passwordRequired: boolean; passwordAlreadySet: boolean }) {
  // backend は現パスワード値を返さない (ネットワーク経由の漏洩を避ける)。
  // 既設定がある場合、空欄のまま保存するとクリア扱い (= 入村パスワード無し) になる旨を案内する。
  const note = passwordAlreadySet
    ? "現在: 設定済み (空欄で保存するとクリア)。変更しない場合は同じ値を再入力してください。"
    : "現在: 未設定。";
  return (
    <Section title="入村パスワード">
      <Field label={`パスワード (3〜12文字${passwordRequired ? " / オリジナル画像村は必須" : ""})`}>
        <input
          type="password"
          autoComplete="new-password"
          value={body.joinPassword ?? ""}
          onChange={(e) => setBody((s) => ({ ...s, joinPassword: e.target.value || null }))}
          className={inputClass}
          minLength={passwordRequired ? 3 : 0}
          maxLength={12}
          required={passwordRequired}
        />
      </Field>
      <p className="text-xs text-slate-500">{note}</p>
    </Section>
  );
}

function DummyMessageSection({ body, setBody }: SectionProps) {
  return (
    <Section title="ダミーキャラ1日目発言">
      <textarea
        value={body.dummyDay1Message ?? ""}
        onChange={(e) => setBody((s) => ({ ...s, dummyDay1Message: e.target.value || null }))}
        rows={3}
        maxLength={400}
        className={inputClass}
        placeholder="未設定なら空欄 (最大 400 文字)"
      />
    </Section>
  );
}

function OrganizationSection({
  body,
  setBody,
  optionsByCamp,
}: SectionProps & {
  optionsByCamp: Map<string, VillageSettingsFormView["current"]["campAllocationList"][number]>;
}) {
  if (!body.randomOrganization) {
    return (
      <Section title="構成 (改行区切り)">
        <textarea
          value={body.organization ?? ""}
          onChange={(e) => setBody((s) => ({ ...s, organization: e.target.value }))}
          rows={10}
          className={`${inputClass} font-mono`}
          placeholder="1行 = 1構成 (例: 村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊)"
        />
      </Section>
    );
  }
  const camps = body.campAllocationList ?? [];
  const wolf = body.wolfAllocation ?? { minNum: 1, maxNum: null };
  return (
    <Section title="闇鍋編成">
      <p className="text-xs text-slate-400">
        各陣営の minNum/maxNum/allocation/reincarnationAllocation と、陣営内の役職を編集します。
      </p>
      {camps.map((camp, ci) => {
        const meta = optionsByCamp.get(camp.campCode);
        return (
          <div key={camp.campCode ?? ci} className="border border-slate-700 rounded p-3 space-y-2">
            <p className="text-sm text-amber-200">{meta?.campName ?? camp.campCode}</p>
            <AllocationRow
              minNum={camp.minNum}
              maxNum={camp.maxNum ?? null}
              allocation={camp.allocation}
              reincarnationAllocation={camp.reincarnationAllocation}
              onChange={(patch) =>
                setBody((s) => ({
                  ...s,
                  campAllocationList: (s.campAllocationList ?? []).map((c, i) =>
                    i === ci ? { ...c, ...patch } : c,
                  ),
                }))
              }
            />
            <details>
              <summary className="text-xs text-slate-300 cursor-pointer">役職別 ({camp.skillAllocation.length}件)</summary>
              <div className="mt-2 space-y-1">
                {camp.skillAllocation.map((skill, si) => {
                  const skillMeta = meta?.skillAllocation.find((sa) => sa.skillCode === skill.skillCode);
                  return (
                    <div key={skill.skillCode ?? si} className="flex items-center gap-2 text-xs">
                      <span className="w-16 shrink-0 text-slate-300">{skillMeta?.skillName ?? skill.skillCode}</span>
                      <AllocationRow
                        minNum={skill.minNum}
                        maxNum={skill.maxNum ?? null}
                        allocation={skill.allocation}
                        reincarnationAllocation={skill.reincarnationAllocation}
                        compact
                        onChange={(patch) =>
                          setBody((s) => ({
                            ...s,
                            campAllocationList: (s.campAllocationList ?? []).map((c, i) =>
                              i === ci
                                ? {
                                    ...c,
                                    skillAllocation: c.skillAllocation.map((sk, j) =>
                                      j === si ? { ...sk, ...patch } : sk,
                                    ),
                                  }
                                : c,
                            ),
                          }))
                        }
                      />
                    </div>
                  );
                })}
              </div>
            </details>
          </div>
        );
      })}
      <div className="border border-slate-700 rounded p-3 space-y-2">
        <p className="text-sm text-amber-200">人狼カウント</p>
        <div className="flex items-center gap-2 text-xs">
          <span className="w-16 shrink-0 text-slate-300">min/max</span>
          <RequiredNumber
            value={wolf.minNum}
            onChange={(v) => setBody((s) => ({ ...s, wolfAllocation: { ...wolf, minNum: v } }))}
            min={1}
            max={100}
            className="w-20"
          />
          <span>〜</span>
          <OptionalNumber
            value={wolf.maxNum ?? null}
            onChange={(v) => setBody((s) => ({ ...s, wolfAllocation: { ...wolf, maxNum: v } }))}
            min={1}
            max={100}
            className="w-20"
          />
        </div>
      </div>
    </Section>
  );
}

function AllocationRow({
  minNum,
  maxNum,
  allocation,
  reincarnationAllocation,
  compact,
  onChange,
}: {
  minNum: number;
  maxNum: number | null;
  allocation: number;
  reincarnationAllocation: number;
  compact?: boolean;
  onChange: (patch: {
    minNum?: number;
    maxNum?: number | null;
    allocation?: number;
    reincarnationAllocation?: number;
  }) => void;
}) {
  const w = compact ? "w-14" : "w-16";
  return (
    <div className="flex items-center gap-2 text-xs flex-wrap">
      <Labeled label="min">
        <RequiredNumber value={minNum} onChange={(v) => onChange({ minNum: v })} min={0} max={100} className={w} />
      </Labeled>
      <Labeled label="max">
        <OptionalNumber value={maxNum} onChange={(v) => onChange({ maxNum: v })} min={0} max={100} className={w} />
      </Labeled>
      <Labeled label="配分">
        <RequiredNumber value={allocation} onChange={(v) => onChange({ allocation: v })} min={0} max={100} className={w} />
      </Labeled>
      <Labeled label="転生配分">
        <RequiredNumber value={reincarnationAllocation} onChange={(v) => onChange({ reincarnationAllocation: v })} min={0} max={100} className={w} />
      </Labeled>
    </div>
  );
}

function RestrictionsSection({
  body,
  setBody,
  sayRestrictLabels,
  messageTypeLabels,
}: SectionProps & {
  sayRestrictLabels: Map<string, string>;
  messageTypeLabels: Map<string, string>;
}) {
  const sayList = body.sayRestrictList ?? [];
  const skillSayList = body.skillSayRestrictList ?? [];
  const rpSayList = body.rpSayRestrictList ?? [];
  return (
    <Section title="発言制限">
      <details>
        <summary className="cursor-pointer text-sm text-slate-300">通常発言: 役職別 ({sayList.length}件)</summary>
        <div className="mt-2 space-y-1">
          {sayList.map((r, i) => (
            <RestrictRow
              key={r.skillCode ?? i}
              label={sayRestrictLabels.get(r.skillCode) ?? r.skillCode}
              restrict={r.restrict}
              count={r.count ?? null}
              length={r.length ?? null}
              onChange={(patch) =>
                setBody((s) => ({
                  ...s,
                  sayRestrictList: (s.sayRestrictList ?? []).map((row, j) =>
                    i === j ? { ...row, ...patch } : row,
                  ),
                }))
              }
            />
          ))}
        </div>
      </details>

      <details>
        <summary className="cursor-pointer text-sm text-slate-300">役職発言制限 ({skillSayList.length}件)</summary>
        <div className="mt-2 space-y-1">
          {skillSayList.map((r, i) => (
            <RestrictRow
              key={r.messageTypeCode ?? i}
              label={messageTypeLabels.get(r.messageTypeCode) ?? r.messageTypeCode}
              restrict={r.restrict}
              count={r.count ?? null}
              length={r.length ?? null}
              onChange={(patch) =>
                setBody((s) => ({
                  ...s,
                  skillSayRestrictList: (s.skillSayRestrictList ?? []).map((row, j) =>
                    i === j ? { ...row, ...patch } : row,
                  ),
                }))
              }
            />
          ))}
        </div>
      </details>

      <details>
        <summary className="cursor-pointer text-sm text-slate-300">RP 発言制限 ({rpSayList.length}件)</summary>
        <div className="mt-2 space-y-1">
          {rpSayList.map((r, i) => (
            <RestrictRow
              key={r.messageTypeCode ?? i}
              label={messageTypeLabels.get(r.messageTypeCode) ?? r.messageTypeCode}
              restrict={r.restrict}
              count={r.count ?? null}
              length={r.length ?? null}
              onChange={(patch) =>
                setBody((s) => ({
                  ...s,
                  rpSayRestrictList: (s.rpSayRestrictList ?? []).map((row, j) =>
                    i === j ? { ...row, ...patch } : row,
                  ),
                }))
              }
            />
          ))}
        </div>
      </details>
    </Section>
  );
}

function RestrictRow({
  label,
  restrict,
  count,
  length,
  onChange,
}: {
  label: string;
  restrict: boolean;
  count: number | null;
  length: number | null;
  onChange: (patch: { restrict?: boolean; count?: number | null; length?: number | null }) => void;
}) {
  return (
    <div className="flex items-center gap-2 text-xs">
      <label className="flex items-center gap-1 w-40 shrink-0">
        <input type="checkbox" checked={restrict} onChange={(e) => onChange({ restrict: e.target.checked })} />
        <span>{label}</span>
      </label>
      <Labeled label="回数">
        <OptionalNumber value={count} onChange={(v) => onChange({ count: v })} min={0} max={100} className="w-16" disabled={!restrict} />
      </Labeled>
      <Labeled label="文字数">
        <OptionalNumber value={length} onChange={(v) => onChange({ length: v })} min={0} max={400} className="w-20" disabled={!restrict} />
      </Labeled>
    </div>
  );
}

// ============================================================================
// initial mapping (FormView.current -> UpdateBody)
// ============================================================================

function toInitialBody(current: VillageSettingsFormView["current"]): VillageSettingsUpdateBody {
  return {
    villageName: current.villageName,
    startPersonMinNum: current.startPersonMinNum,
    personMaxNum: current.personMaxNum,
    dayChangeIntervalHours: current.dayChangeIntervalHours,
    dayChangeIntervalMinutes: current.dayChangeIntervalMinutes,
    dayChangeIntervalSeconds: current.dayChangeIntervalSeconds,
    startYear: current.startYear,
    startMonth: current.startMonth,
    startDay: current.startDay,
    startHour: current.startHour,
    startMinute: current.startMinute,
    welcomeRange: current.welcomeRange ?? null,
    ageLimit: current.ageLimit ?? null,
    openVote: current.openVote,
    availableSameWolfAttack: current.availableSameWolfAttack,
    openSkillInGrave: current.openSkillInGrave,
    visibleGraveSpectateMessage: current.visibleGraveSpectateMessage,
    allowedSecretSayCode: current.allowedSecretSayCode,
    availableSpectate: current.availableSpectate,
    availableSuddenlyDeath: current.availableSuddenlyDeath,
    availableCommit: current.availableCommit,
    availableGuardSameTarget: current.availableGuardSameTarget,
    availableAction: current.availableAction,
    organization: current.organization,
    randomOrganization: current.randomOrganization,
    reincarnationSkillAll: current.reincarnationSkillAll,
    campAllocationList: current.campAllocationList.map((c) => ({
      campCode: c.campCode,
      minNum: c.minNum,
      maxNum: c.maxNum ?? null,
      allocation: c.allocation,
      reincarnationAllocation: c.reincarnationAllocation,
      skillAllocation: c.skillAllocation.map((s) => ({
        skillCode: s.skillCode,
        minNum: s.minNum,
        maxNum: s.maxNum ?? null,
        allocation: s.allocation,
        reincarnationAllocation: s.reincarnationAllocation,
      })),
    })),
    wolfAllocation: {
      minNum: current.wolfAllocation.minNum,
      maxNum: current.wolfAllocation.maxNum ?? null,
    },
    dummyDay1Message: current.dummyDay1Message ?? null,
    // 初期値は常に空欄 (backend は現パスワードを返さない)。空欄で保存するとクリア扱い。
    joinPassword: null,
    sayRestrictList: current.sayRestrictList.map((r) => ({
      skillCode: r.skillCode,
      restrict: r.restrict,
      count: r.count,
      length: r.length,
    })),
    skillSayRestrictList: current.skillSayRestrictList.map((r) => ({
      messageTypeCode: r.messageTypeCode,
      restrict: r.restrict,
      count: r.count,
      length: r.length,
    })),
    rpSayRestrictList: current.rpSayRestrictList.map((r) => ({
      messageTypeCode: r.messageTypeCode,
      restrict: r.restrict,
      count: r.count,
      length: r.length,
    })),
  };
}

// ============================================================================
// shared atoms
// ============================================================================

function Section({ title, children }: { title: string; children: React.ReactNode }) {
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-3">
      <h2 className="text-sm text-slate-300 font-medium">{title}</h2>
      {children}
    </section>
  );
}

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <label className="block space-y-1 text-sm">
      <span className="text-xs text-slate-400">{label}</span>
      {children}
    </label>
  );
}

function Labeled({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <span className="inline-flex items-center gap-1">
      <span className="text-slate-500">{label}</span>
      {children}
    </span>
  );
}

function CheckRow({
  label,
  checked,
  onChange,
}: {
  label: string;
  checked: boolean;
  onChange: (v: boolean) => void;
}) {
  return (
    <label className="flex items-center gap-2 text-sm">
      <input type="checkbox" checked={checked} onChange={(e) => onChange(e.target.checked)} />
      <span>{label}</span>
    </label>
  );
}

/** 必須 number 入力。空欄時は 0 にフォールバック (= 後で backend が範囲検証して 400)。 */
function RequiredNumber({
  value,
  onChange,
  min,
  max,
  className,
  disabled,
}: {
  value: number;
  onChange: (v: number) => void;
  min?: number;
  max?: number;
  className?: string;
  disabled?: boolean;
}) {
  return (
    <input
      type="number"
      value={value}
      onChange={(e) => {
        const n = Number(e.target.value);
        onChange(Number.isFinite(n) ? n : 0);
      }}
      min={min}
      max={max}
      disabled={disabled}
      className={`${inputClass} ${className ?? "w-24"} disabled:opacity-40`}
    />
  );
}

/** 任意 number 入力。空欄は null (= 制限なし) として保持。 */
function OptionalNumber({
  value,
  onChange,
  min,
  max,
  className,
  disabled,
}: {
  value: number | null;
  onChange: (v: number | null) => void;
  min?: number;
  max?: number;
  className?: string;
  disabled?: boolean;
}) {
  return (
    <input
      type="number"
      value={value ?? ""}
      onChange={(e) => {
        const raw = e.target.value;
        if (raw === "") {
          onChange(null);
          return;
        }
        const n = Number(raw);
        onChange(Number.isFinite(n) ? n : null);
      }}
      min={min}
      max={max}
      disabled={disabled}
      className={`${inputClass} ${className ?? "w-24"} disabled:opacity-40`}
    />
  );
}

// ============================================================================
// styling
// ============================================================================

const inputClass =
  "bg-slate-900 border border-slate-700 rounded px-2 py-1 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500";

const primaryButtonClass =
  "rounded bg-indigo-500 hover:bg-indigo-400 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

const secondaryButtonClass =
  "rounded bg-slate-700 hover:bg-slate-600 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

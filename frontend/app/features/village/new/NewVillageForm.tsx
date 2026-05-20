import { useEffect, useMemo, useRef, useState } from "react";
import { useNavigate } from "react-router";
import { useCreateVillageMutation } from "./hooks";
import type { NewVillageCreateBody, NewVillageFormView } from "./api";
import { fetchCharachipDetail, type CharachipDetailView } from "~/features/meta/api";

/**
 * 新規村作成フォーム (creator 専用)。
 *
 * 旧 Thymeleaf `/new-village` 置き換え。設定変更 (`SettingsForm`) と多くの構造を共有しつつ、
 * 新規村作成固有のフィールド (キャラチップ選択 / ダミーキャラ情報 / 役職希望可否 / プロデューサー機能)
 * を追加。
 *
 * 確認画面は SPA 内 `<dialog>` ベース (旧 `/new-village/confirm` は不要)。
 * オリジナルキャラチップ村 (`shouldOriginalImage=true`) は backend が 501 を返すので
 * UI でも初期 false / 切り替え不可。後続 step で multipart 対応する予定。
 *
 * cross-field バリデーションは backend に任せ、フロントでは Required と type のみ最低限。
 */
export function NewVillageForm({ initial }: { initial: NewVillageFormView }) {
  const navigate = useNavigate();
  const [body, setBody] = useState<NewVillageCreateBody>(() => toInitialBody(initial.defaults));
  const mutation = useCreateVillageMutation();
  const [confirmOpen, setConfirmOpen] = useState(false);

  // 選択中キャラチップに含まれるキャラ一覧 (ダミーキャラ選択肢)
  const characterSetId = body.characterSetId ?? [];
  const [charaListByChip, setCharaListByChip] = useState<Map<number, CharachipDetailView>>(new Map());
  // in-flight な charachip id (再 fetch 抑止用)。fetch 完了 / 失敗のどちらでも
  // 必ず delete して、unmount → 再 mount でも取得し直せるようにする。
  const fetchingRef = useRef<Set<number>>(new Set());
  useEffect(() => {
    let cancelled = false;
    characterSetId.forEach((id) => {
      if (charaListByChip.has(id) || fetchingRef.current.has(id)) return;
      fetchingRef.current.add(id);
      fetchCharachipDetail(id)
        .then((detail) => {
          if (cancelled) return;
          setCharaListByChip((prev) => {
            const next = new Map(prev);
            next.set(id, detail);
            return next;
          });
        })
        .catch(() => {
          // 取得失敗時は黙って何もしない (UI 上は「キャラ選択肢なし」)
        })
        .finally(() => {
          fetchingRef.current.delete(id);
        });
    });
    return () => {
      cancelled = true;
    };
  }, [characterSetId, charaListByChip]);

  const allCharas = useMemo(
    () => characterSetId.flatMap((id) => charaListByChip.get(id)?.charas ?? []),
    [characterSetId, charaListByChip],
  );

  const sayRestrictLabels = useMemo(
    () => new Map(initial.defaults.sayRestrictList.map((r) => [r.skillCode, r.skillName])),
    [initial.defaults.sayRestrictList],
  );
  const messageTypeLabels = useMemo(
    () =>
      new Map([
        ...initial.defaults.skillSayRestrictList.map((r) => [r.messageTypeCode, r.messageTypeName] as const),
        ...initial.defaults.rpSayRestrictList.map((r) => [r.messageTypeCode, r.messageTypeName] as const),
      ]),
    [initial.defaults.skillSayRestrictList, initial.defaults.rpSayRestrictList],
  );
  const optionsByCamp = useMemo(
    () => new Map(initial.defaults.campAllocationList.map((c) => [c.campCode, c])),
    [initial.defaults.campAllocationList],
  );

  function openConfirm(e: React.FormEvent) {
    e.preventDefault();
    setConfirmOpen(true);
  }

  function submit() {
    mutation.mutate(body, {
      onSuccess: (res) => {
        setConfirmOpen(false);
        navigate(`/villages/${res.id}`);
      },
    });
  }

  return (
    <>
      <form onSubmit={openConfirm} className="space-y-6">
        <BasicSection body={body} setBody={setBody} />
        <CharaSection
          body={body}
          setBody={setBody}
          options={initial.options}
          allCharas={allCharas}
          charaListByChip={charaListByChip}
        />
        <DummyCharaSection body={body} setBody={setBody} />
        <TagSection body={body} setBody={setBody} options={initial.options} />
        <RulesSection body={body} setBody={setBody} options={initial.options} />
        <JoinPasswordSection body={body} setBody={setBody} />
        <OrganizationSection body={body} setBody={setBody} optionsByCamp={optionsByCamp} />
        <RestrictionsSection
          body={body}
          setBody={setBody}
          sayRestrictLabels={sayRestrictLabels}
          messageTypeLabels={messageTypeLabels}
        />

        <div className="sticky bottom-0 -mx-6 px-6 py-3 bg-slate-900/95 border-t border-slate-700 flex items-center justify-between gap-4">
          <div className="text-xs text-slate-400">
            検証エラーは backend のメッセージが表示されます。
          </div>
          <div className="flex items-center gap-3">
            {mutation.isError && (
              <span className="text-xs text-rose-300 max-w-md truncate" title={mutation.error.message}>
                {mutation.error.message}
              </span>
            )}
            <button type="submit" className={primaryButtonClass} disabled={mutation.isPending}>
              {mutation.isPending ? "送信中..." : "内容を確認"}
            </button>
          </div>
        </div>
      </form>

      {confirmOpen && (
        <ConfirmDialog
          body={body}
          allCharas={allCharas}
          onCancel={() => setConfirmOpen(false)}
          onSubmit={submit}
          isPending={mutation.isPending}
          error={mutation.isError ? mutation.error.message : null}
        />
      )}
    </>
  );
}

// ============================================================================
// section components
// ============================================================================

type SectionProps = {
  body: NewVillageCreateBody;
  setBody: React.Dispatch<React.SetStateAction<NewVillageCreateBody>>;
};

function BasicSection({ body, setBody }: SectionProps) {
  return (
    <Section title="基本">
      <Field label="村表示名 (5〜40文字)">
        <input
          type="text"
          value={body.villageName ?? ""}
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
        <Field label="定員 (8〜999)">
          <RequiredNumber value={body.personMaxNum} onChange={(v) => setBody((s) => ({ ...s, personMaxNum: v }))} min={8} max={999} />
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

      <Field label="開始日時 (現在より7日後を初期値、最大14日後まで)">
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

function CharaSection({
  body,
  setBody,
  options,
  allCharas,
  charaListByChip,
}: SectionProps & {
  options: NewVillageFormView["options"];
  allCharas: CharachipDetailView["charas"];
  charaListByChip: Map<number, CharachipDetailView>;
}) {
  const characterSetId = body.characterSetId ?? [];

  function toggleChip(chipId: number, checked: boolean) {
    setBody((s) => {
      const ids = new Set(s.characterSetId ?? []);
      if (checked) ids.add(chipId);
      else ids.delete(chipId);
      const nextIds = Array.from(ids).sort((a, b) => a - b);
      // 残ったキャラチップ群に dummyCharaId が含まれていなければ null にリセットする。
      // (一旦選んだダミーキャラのチップを外すケース)
      const remainingCharaIds = new Set(
        nextIds.flatMap((id) => charaListByChip.get(id)?.charas.map((c) => c.id) ?? []),
      );
      const dummyStillValid = s.dummyCharaId != null && remainingCharaIds.has(s.dummyCharaId);
      return { ...s, characterSetId: nextIds, dummyCharaId: dummyStillValid ? s.dummyCharaId : null };
    });
  }

  return (
    <Section title="キャラクター設定">
      <Field label="キャラチップ (複数選択可)">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-1">
          {options.charachips.map((c) => {
            const checked = characterSetId.includes(c.id);
            return (
              <label key={c.id} className="flex items-center gap-2 text-sm border border-slate-700 rounded px-2 py-1.5">
                <input
                  type="checkbox"
                  checked={checked}
                  onChange={(e) => toggleChip(c.id, e.target.checked)}
                />
                <img src={c.dummyImageUrl} width={c.dummyImageWidth} height={c.dummyImageHeight} alt={c.name} className="bg-slate-900/60 rounded" />
                <div className="flex-1 min-w-0">
                  <span className="block truncate">{c.name}</span>
                  <span className="block text-xs text-slate-400 truncate">{c.designerName} / {c.charaCount}キャラ</span>
                </div>
              </label>
            );
          })}
        </div>
      </Field>

      <Field label="ダミーキャラ (選択キャラチップ内から)">
        <select
          value={body.dummyCharaId ?? ""}
          onChange={(e) => setBody((s) => ({ ...s, dummyCharaId: e.target.value ? Number(e.target.value) : null }))}
          className={inputClass}
          required
        >
          <option value="">選択してください</option>
          {allCharas.map((c) => (
            <option key={c.id} value={c.id}>{c.name} [{c.shortName}]</option>
          ))}
        </select>
      </Field>
    </Section>
  );
}

function DummyCharaSection({ body, setBody }: SectionProps) {
  return (
    <Section title="ダミーキャラの自己紹介">
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
        <Field label="ダミーキャラ表示名 (1〜40文字)">
          <input
            type="text"
            value={body.dummyCharaName ?? ""}
            onChange={(e) => setBody((s) => ({ ...s, dummyCharaName: e.target.value }))}
            className={inputClass}
            minLength={1}
            maxLength={40}
            required
          />
        </Field>
        <Field label="略称 (1文字)">
          <input
            type="text"
            value={body.dummyCharaShortName ?? ""}
            onChange={(e) => setBody((s) => ({ ...s, dummyCharaShortName: e.target.value }))}
            className={inputClass}
            minLength={1}
            maxLength={1}
            required
          />
        </Field>
      </div>
      <Field label="入村発言 (1〜400文字、必須)">
        <textarea
          value={body.dummyJoinMessage ?? ""}
          onChange={(e) => setBody((s) => ({ ...s, dummyJoinMessage: e.target.value }))}
          rows={3}
          maxLength={400}
          className={inputClass}
          required
        />
      </Field>
      <Field label="1日目発言 (任意、最大400文字)">
        <textarea
          value={body.dummyDay1Message ?? ""}
          onChange={(e) => setBody((s) => ({ ...s, dummyDay1Message: e.target.value || null }))}
          rows={3}
          maxLength={400}
          className={inputClass}
        />
      </Field>
    </Section>
  );
}

function TagSection({
  body,
  setBody,
  options,
}: SectionProps & { options: NewVillageFormView["options"] }) {
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
}: SectionProps & { options: NewVillageFormView["options"] }) {
  return (
    <Section title="ルール">
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
        <CheckRow label="記名投票" checked={body.openVote ?? false} onChange={(v) => setBody((s) => ({ ...s, openVote: v }))} />
        <CheckRow label="役職希望を有効にする" checked={body.possibleSkillRequest ?? false} onChange={(v) => setBody((s) => ({ ...s, possibleSkillRequest: v }))} />
        <CheckRow label="見学を可能にする" checked={body.availableSpectate ?? false} onChange={(v) => setBody((s) => ({ ...s, availableSpectate: v }))} />
        <CheckRow label="村建てがプロデューサー機能を持つ" checked={body.creatorIsProducer ?? false} onChange={(v) => setBody((s) => ({ ...s, creatorIsProducer: v }))} />
        <CheckRow label="突然死あり" checked={body.availableSuddenlyDeath ?? false} onChange={(v) => setBody((s) => ({ ...s, availableSuddenlyDeath: v }))} />
        <CheckRow label="コミット可能" checked={body.availableCommit ?? false} onChange={(v) => setBody((s) => ({ ...s, availableCommit: v }))} />
        <CheckRow label="アクションあり" checked={body.availableAction ?? false} onChange={(v) => setBody((s) => ({ ...s, availableAction: v }))} />
        <CheckRow label="連続襲撃あり" checked={body.availableSameWolfAttack ?? false} onChange={(v) => setBody((s) => ({ ...s, availableSameWolfAttack: v }))} />
        <CheckRow label="連続ガードあり" checked={body.availableGuardSameTarget ?? false} onChange={(v) => setBody((s) => ({ ...s, availableGuardSameTarget: v }))} />
        <CheckRow label="墓下役職公開" checked={body.openSkillInGrave ?? false} onChange={(v) => setBody((s) => ({ ...s, openSkillInGrave: v }))} />
        <CheckRow label="墓下見学発言を地上から見られる" checked={body.visibleGraveSpectateMessage ?? false} onChange={(v) => setBody((s) => ({ ...s, visibleGraveSpectateMessage: v }))} />
        <CheckRow label="転生時に全役職を候補とする" checked={body.reincarnationSkillAll ?? false} onChange={(v) => setBody((s) => ({ ...s, reincarnationSkillAll: v }))} />
        <CheckRow label="闇鍋編成" checked={body.randomOrganization ?? false} onChange={(v) => setBody((s) => ({ ...s, randomOrganization: v }))} />
      </div>
      <Field label="秘話可能範囲">
        <select
          value={body.allowedSecretSayCode ?? ""}
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

function JoinPasswordSection({ body, setBody }: SectionProps) {
  return (
    <Section title="入村パスワード (任意)">
      <Field label="3〜12文字、未設定ならパスワード不要">
        <input
          type="password"
          autoComplete="new-password"
          value={body.joinPassword ?? ""}
          onChange={(e) => setBody((s) => ({ ...s, joinPassword: e.target.value || null }))}
          className={inputClass}
          maxLength={12}
        />
      </Field>
    </Section>
  );
}

function OrganizationSection({
  body,
  setBody,
  optionsByCamp,
}: SectionProps & {
  optionsByCamp: Map<string, NewVillageFormView["defaults"]["campAllocationList"][number]>;
}) {
  if (!body.randomOrganization) {
    return (
      <Section title="構成 (改行区切り、固定編成)">
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
        各陣営の min/max/allocation/reincarnationAllocation と陣営内の役職を編集します。
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
                  sayRestrictList: (s.sayRestrictList ?? []).map((row, j) => (i === j ? { ...row, ...patch } : row)),
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
                  skillSayRestrictList: (s.skillSayRestrictList ?? []).map((row, j) => (i === j ? { ...row, ...patch } : row)),
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
                  rpSayRestrictList: (s.rpSayRestrictList ?? []).map((row, j) => (i === j ? { ...row, ...patch } : row)),
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
// confirm dialog
// ============================================================================

function ConfirmDialog({
  body,
  allCharas,
  onCancel,
  onSubmit,
  isPending,
  error,
}: {
  body: NewVillageCreateBody;
  allCharas: CharachipDetailView["charas"];
  onCancel: () => void;
  onSubmit: () => void;
  isPending: boolean;
  error: string | null;
}) {
  const dummyChara = allCharas.find((c) => c.id === body.dummyCharaId);
  const startStr = `${body.startYear}/${pad2(body.startMonth ?? 0)}/${pad2(body.startDay ?? 0)} ${pad2(body.startHour ?? 0)}:${pad2(body.startMinute ?? 0)}`;
  return (
    <div className="fixed inset-0 bg-black/70 flex items-center justify-center p-4 z-50">
      <div className="max-w-md w-full bg-slate-900 border border-slate-700 rounded-xl p-5 space-y-4 max-h-[90vh] overflow-y-auto">
        <h2 className="text-lg font-semibold">この内容で村を建てます</h2>
        <dl className="text-sm space-y-1.5">
          <Row k="村名" v={body.villageName ?? ""} />
          <Row k="人数" v={`${body.startPersonMinNum} 〜 ${body.personMaxNum}`} />
          <Row k="開始日時" v={startStr} />
          <Row k="更新間隔" v={`${body.dayChangeIntervalHours}h ${body.dayChangeIntervalMinutes}m ${body.dayChangeIntervalSeconds}s`} />
          <Row k="編成" v={body.randomOrganization ? "闇鍋編成" : "固定編成"} />
          <Row k="ダミーキャラ" v={dummyChara ? `${dummyChara.name} (${dummyChara.shortName})` : `${body.dummyCharaName} (${body.dummyCharaShortName})`} />
          <Row k="表示名" v={body.dummyCharaName ?? ""} />
        </dl>
        <p className="text-xs text-slate-400">
          作成後はプロローグ画面に遷移します。設定は村建てメニュー → 設定変更で後から調整可能です。
        </p>
        {error && <p className="text-xs text-rose-300">{error}</p>}
        <div className="flex items-center justify-end gap-3">
          <button type="button" onClick={onCancel} className={secondaryButtonClass} disabled={isPending}>
            戻る
          </button>
          <button type="button" onClick={onSubmit} className={primaryButtonClass} disabled={isPending}>
            {isPending ? "作成中..." : "村を建てる"}
          </button>
        </div>
      </div>
    </div>
  );
}

function Row({ k, v }: { k: string; v: string }) {
  return (
    <div className="flex items-baseline gap-3 border-b border-slate-700/40 pb-1">
      <dt className="text-xs text-slate-400 w-24 shrink-0">{k}</dt>
      <dd className="text-sm flex-1 break-words">{v || "(未設定)"}</dd>
    </div>
  );
}

function pad2(n: number): string {
  return String(n).padStart(2, "0");
}

// ============================================================================
// initial mapping
// ============================================================================

function toInitialBody(defaults: NewVillageFormView["defaults"]): NewVillageCreateBody {
  return {
    villageName: defaults.villageName,
    startPersonMinNum: defaults.startPersonMinNum,
    personMaxNum: defaults.personMaxNum,
    dayChangeIntervalHours: defaults.dayChangeIntervalHours,
    dayChangeIntervalMinutes: defaults.dayChangeIntervalMinutes,
    dayChangeIntervalSeconds: defaults.dayChangeIntervalSeconds,
    startYear: defaults.startYear,
    startMonth: defaults.startMonth,
    startDay: defaults.startDay,
    startHour: defaults.startHour,
    startMinute: defaults.startMinute,
    welcomeRange: defaults.welcomeRange ?? null,
    ageLimit: defaults.ageLimit ?? null,
    openVote: defaults.openVote,
    possibleSkillRequest: defaults.possibleSkillRequest,
    availableSameWolfAttack: defaults.availableSameWolfAttack,
    openSkillInGrave: defaults.openSkillInGrave,
    visibleGraveSpectateMessage: defaults.visibleGraveSpectateMessage,
    availableSpectate: defaults.availableSpectate,
    creatorIsProducer: defaults.creatorIsProducer,
    availableSuddenlyDeath: defaults.availableSuddenlyDeath,
    availableCommit: defaults.availableCommit,
    availableGuardSameTarget: defaults.availableGuardSameTarget,
    availableAction: defaults.availableAction,
    randomOrganization: defaults.randomOrganization,
    reincarnationSkillAll: defaults.reincarnationSkillAll,
    allowedSecretSayCode: defaults.allowedSecretSayCode,
    shouldOriginalImage: defaults.shouldOriginalImage,
    characterSetId: defaults.characterSetId,
    dummyCharaId: null,
    dummyCharaName: defaults.dummyCharaName,
    dummyCharaShortName: defaults.dummyCharaShortName,
    dummyJoinMessage: defaults.dummyJoinMessage,
    dummyDay1Message: defaults.dummyDay1Message ?? null,
    organization: defaults.organization,
    campAllocationList: defaults.campAllocationList.map((c) => ({
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
      minNum: defaults.wolfAllocation.minNum,
      maxNum: defaults.wolfAllocation.maxNum ?? null,
    },
    joinPassword: defaults.joinPassword ?? null,
    sayRestrictList: defaults.sayRestrictList.map((r) => ({
      skillCode: r.skillCode,
      restrict: r.restrict,
      count: r.count,
      length: r.length,
    })),
    skillSayRestrictList: defaults.skillSayRestrictList.map((r) => ({
      messageTypeCode: r.messageTypeCode,
      restrict: r.restrict,
      count: r.count,
      length: r.length,
    })),
    rpSayRestrictList: defaults.rpSayRestrictList.map((r) => ({
      messageTypeCode: r.messageTypeCode,
      restrict: r.restrict,
      count: r.count,
      length: r.length,
    })),
  };
}

// ============================================================================
// atoms
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

const inputClass =
  "bg-slate-900 border border-slate-700 rounded px-2 py-1 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500";

const primaryButtonClass =
  "rounded bg-indigo-500 hover:bg-indigo-400 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

const secondaryButtonClass =
  "rounded bg-slate-700 hover:bg-slate-600 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

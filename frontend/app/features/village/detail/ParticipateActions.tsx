import { useState } from "react";
import {
  useChangeRequestSkillMutation,
  useLeaveMutation,
  useParticipateMutation,
  useSelectableCharasQuery,
  useSwitchParticipateMutation,
} from "./hooks";
import type {
  CharaView,
  MyselfView,
  VillageView,
} from "./api";

/**
 * プロローグ中の参加系操作 UI。
 *
 * - 未参加 (myself=null): 入村フォーム (キャラ選択 / 希望役職 / メッセージ / 入村パスワード / 見学トグル)
 * - 参加中: 「参加/見学切替」「希望役職変更」「退村」アクション
 *
 * オリジナルキャラチップ村 (`isOriginalCharachip=true`) は multipart 入村 endpoint 未実装のため、
 * 入村 UI は出さず案内テキストのみ表示する。
 */
export function ParticipateActions({
  village,
  myself,
}: {
  village: VillageView;
  myself: MyselfView | null;
}) {
  // プロローグでないなら参加系操作はそもそも出さない (能力 / 投票等は別 UI 担当)
  // CDef.VillageStatus.募集中 のコードは "IN_PREPARATION"。
  if (village.statusCode !== "IN_PREPARATION") return null;

  if (myself == null) {
    return <ParticipateForm village={village} />;
  }
  return <ParticipatingActions village={village} myself={myself} />;
}

// ---------- 未参加 → 入村フォーム ----------

function ParticipateForm({ village }: { village: VillageView }) {
  const { settings, requestableSkills } = village;

  // hooks のルール上、early return より前にすべての hook を呼び出す必要がある。
  // オリジナルキャラチップ村では下で fetch 結果を使わないため、enabled=false 相当として
  // charachipId に null を渡す (useSelectableCharasQuery 側で disabled になる)。
  const charachipId = settings.isOriginalCharachip ? null : settings.charachipIds[0] ?? null;
  const charasQuery = useSelectableCharasQuery(village.id, charachipId);

  const mutation = useParticipateMutation(village.id);

  const [charaId, setCharaId] = useState<number | null>(null);
  const [charaName, setCharaName] = useState("");
  const [charaShortName, setCharaShortName] = useState("");
  const [joinMessage, setJoinMessage] = useState("");
  const [joinPassword, setJoinPassword] = useState("");
  const [spectator, setSpectator] = useState(false);
  const [requestedSkill, setRequestedSkill] = useState<string>("");
  const [secondRequestedSkill, setSecondRequestedSkill] = useState<string>("");

  if (settings.isOriginalCharachip) {
    return (
      <Panel title="入村">
        <p className="text-slate-400 text-sm">
          オリジナルキャラチップ村への入村は現在この画面では未対応です (旧画面 / API 拡張 予定)。
        </p>
      </Panel>
    );
  }

  function onSelectChara(id: number) {
    setCharaId(id);
    const c = charasQuery.data?.find((x) => x.id === id);
    if (c) {
      setCharaName((prev) => prev || c.name);
      setCharaShortName((prev) => prev || c.shortName);
    }
  }

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (charaId == null) return;
    if (!charaName.trim() || !charaShortName.trim() || !joinMessage.trim()) return;
    mutation.mutate({
      charaId,
      charaName: charaName.trim(),
      charaShortName: charaShortName.trim(),
      joinMessage: joinMessage.trim(),
      // 希望役職指定不可な村では送らない (backend 側で空文字 / おまかせは許容するが、送らないのが明確)
      requestedSkill: settings.isSkillRequestAvailable && requestedSkill ? requestedSkill : undefined,
      secondRequestedSkill:
        settings.isSkillRequestAvailable && secondRequestedSkill ? secondRequestedSkill : undefined,
      joinPassword: settings.joinPasswordRequired ? joinPassword : undefined,
      spectator,
    });
  }

  const charas = charasQuery.data ?? [];
  const isLoadingCharas = charasQuery.isLoading;
  const submittable =
    charaId != null &&
    charaName.trim().length > 0 &&
    charaShortName.trim().length === 1 &&
    joinMessage.trim().length > 0 &&
    !mutation.isPending;

  return (
    <Panel title="入村">
      <form onSubmit={submit} className="space-y-3">
        <Field label="キャラ">
          {isLoadingCharas ? (
            <p className="text-slate-400 text-sm">読み込み中...</p>
          ) : charas.length === 0 ? (
            <p className="text-slate-400 text-sm">選択可能なキャラがいません</p>
          ) : (
            <CharaGrid charas={charas} selectedId={charaId} onSelect={onSelectChara} />
          )}
        </Field>

        <Field label="表示名">
          <input
            type="text"
            value={charaName}
            onChange={(e) => setCharaName(e.target.value)}
            maxLength={40}
            className={inputClass}
            placeholder="キャラ名 (40 文字以内)"
            disabled={mutation.isPending}
          />
        </Field>

        <Field label="略称 (1 文字)">
          <input
            type="text"
            value={charaShortName}
            onChange={(e) => setCharaShortName(e.target.value)}
            maxLength={1}
            className={`${inputClass} w-24`}
            disabled={mutation.isPending}
          />
        </Field>

        {settings.isSkillRequestAvailable && (
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <Field label="希望役職 (第一)">
              <SkillSelect
                value={requestedSkill}
                onChange={setRequestedSkill}
                options={requestableSkills}
                disabled={mutation.isPending}
              />
            </Field>
            <Field label="希望役職 (第二)">
              <SkillSelect
                value={secondRequestedSkill}
                onChange={setSecondRequestedSkill}
                options={requestableSkills}
                disabled={mutation.isPending}
              />
            </Field>
          </div>
        )}

        <Field label="入村メッセージ">
          <textarea
            value={joinMessage}
            onChange={(e) => setJoinMessage(e.target.value)}
            rows={3}
            className={inputClass}
            placeholder="挨拶など (発言扱い)"
            disabled={mutation.isPending}
          />
        </Field>

        {settings.joinPasswordRequired && (
          <Field label="入村パスワード">
            <input
              type="password"
              value={joinPassword}
              onChange={(e) => setJoinPassword(e.target.value)}
              className={inputClass}
              disabled={mutation.isPending}
              autoComplete="off"
            />
          </Field>
        )}

        {settings.isSpectateAvailable && (
          <label className="flex items-center gap-2 text-sm text-slate-200">
            <input
              type="checkbox"
              checked={spectator}
              onChange={(e) => setSpectator(e.target.checked)}
              disabled={mutation.isPending}
            />
            見学として参加する
          </label>
        )}

        <div className="flex items-center gap-3">
          <button
            type="submit"
            disabled={!submittable}
            className={primaryButtonClass}
          >
            {mutation.isPending ? "送信中..." : spectator ? "見学する" : "入村する"}
          </button>
          {mutation.isError && (
            <span className="text-xs text-rose-300">{mutation.error.message}</span>
          )}
        </div>
      </form>
    </Panel>
  );
}

function CharaGrid({
  charas,
  selectedId,
  onSelect,
}: {
  charas: CharaView[];
  selectedId: number | null;
  onSelect: (id: number) => void;
}) {
  return (
    <ul className="grid grid-cols-2 sm:grid-cols-3 gap-2">
      {charas.map((c) => {
        const isSelected = c.id === selectedId;
        return (
          <li key={c.id}>
            <button
              type="button"
              onClick={() => onSelect(c.id)}
              className={`w-full text-left p-2 rounded border text-sm transition ${
                isSelected
                  ? "border-indigo-400 bg-indigo-500/20"
                  : "border-slate-700 hover:border-slate-500 bg-slate-900/40"
              }`}
            >
              <span className="font-medium">{c.name}</span>
              <span className="ml-2 text-xs text-slate-400">[{c.shortName}]</span>
            </button>
          </li>
        );
      })}
    </ul>
  );
}

// ---------- 参加中 → 切替 / 希望変更 / 退村 ----------

function ParticipatingActions({
  village,
  myself,
}: {
  village: VillageView;
  myself: MyselfView;
}) {
  return (
    <Panel title="参加状態の操作">
      <div className="space-y-3">
        {village.settings.isSpectateAvailable && <SwitchParticipateButton villageId={village.id} isSpectator={myself.isSpectator} />}
        {village.settings.isSkillRequestAvailable && !myself.isSpectator && (
          <ChangeRequestSkillForm village={village} myself={myself} />
        )}
        {!village.isCreator && <LeaveButton villageId={village.id} />}
        {village.isCreator && (
          <p className="text-xs text-slate-500">
            村建ては退村できません (廃村は別途 admin 画面で操作)。
          </p>
        )}
      </div>
    </Panel>
  );
}

function SwitchParticipateButton({
  villageId,
  isSpectator,
}: {
  villageId: number;
  isSpectator: boolean;
}) {
  const mutation = useSwitchParticipateMutation(villageId);
  return (
    <div className="flex items-center gap-3">
      <button
        type="button"
        onClick={() => mutation.mutate()}
        disabled={mutation.isPending}
        className={secondaryButtonClass}
      >
        {isSpectator ? "参加者になる" : "見学者になる"}
      </button>
      {mutation.isError && (
        <span className="text-xs text-rose-300">{mutation.error.message}</span>
      )}
    </div>
  );
}

function ChangeRequestSkillForm({
  village,
  myself,
}: {
  village: VillageView;
  myself: MyselfView;
}) {
  const mutation = useChangeRequestSkillMutation(village.id);
  // 既存希望は myself に乗っていない (skill は確定後のもの) ので空からスタート
  const [requestedSkill, setRequestedSkill] = useState<string>("");
  const [secondRequestedSkill, setSecondRequestedSkill] = useState<string>("");

  // 参加直後の myself.skill は未確定 (null)。役職確定後は変更させない。
  if (myself.skill != null) return null;

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (!requestedSkill || !secondRequestedSkill) return;
    mutation.mutate({ requestedSkill, secondRequestedSkill });
  }

  const submittable = requestedSkill && secondRequestedSkill && !mutation.isPending;

  return (
    <form onSubmit={submit} className="space-y-2 border-t border-slate-700/60 pt-3">
      <p className="text-xs text-slate-400">希望役職変更</p>
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
        <SkillSelect
          value={requestedSkill}
          onChange={setRequestedSkill}
          options={village.requestableSkills}
          disabled={mutation.isPending}
        />
        <SkillSelect
          value={secondRequestedSkill}
          onChange={setSecondRequestedSkill}
          options={village.requestableSkills}
          disabled={mutation.isPending}
        />
      </div>
      <div className="flex items-center gap-3">
        <button
          type="submit"
          disabled={!submittable}
          className={secondaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "希望を反映"}
        </button>
        {mutation.isError && (
          <span className="text-xs text-rose-300">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

function LeaveButton({ villageId }: { villageId: number }) {
  const mutation = useLeaveMutation(villageId);
  function onClick() {
    if (!confirm("退村しますか？")) return;
    mutation.mutate();
  }
  return (
    <div className="flex items-center gap-3 border-t border-slate-700/60 pt-3">
      <button
        type="button"
        onClick={onClick}
        disabled={mutation.isPending}
        className="rounded bg-rose-600 hover:bg-rose-500 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium"
      >
        {mutation.isPending ? "送信中..." : "退村する"}
      </button>
      {mutation.isError && (
        <span className="text-xs text-rose-300">{mutation.error.message}</span>
      )}
    </div>
  );
}

// ---------- 部品 ----------

function Panel({ title, children }: { title: string; children: React.ReactNode }) {
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-3">{title}</h2>
      {children}
    </section>
  );
}

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <div className="space-y-1">
      <label className="text-xs text-slate-400">{label}</label>
      {children}
    </div>
  );
}

function SkillSelect({
  value,
  onChange,
  options,
  disabled,
}: {
  value: string;
  onChange: (v: string) => void;
  options: Array<{ code: string; name: string }>;
  disabled?: boolean;
}) {
  return (
    <select
      value={value}
      onChange={(e) => onChange(e.target.value)}
      className={inputClass}
      disabled={disabled}
    >
      <option value="">(おまかせ)</option>
      {options.map((s) => (
        <option key={s.code} value={s.code}>
          {s.name}
        </option>
      ))}
    </select>
  );
}

const inputClass =
  "w-full bg-slate-900 border border-slate-700 rounded px-3 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500 disabled:opacity-50";

const primaryButtonClass =
  "rounded bg-indigo-500 hover:bg-indigo-400 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

const secondaryButtonClass =
  "rounded bg-slate-700 hover:bg-slate-600 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium";

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
import { Panel as UIPanel, PanelBody, PanelHeading } from "~/components/ui/Panel";

/**
 * プロローグ中の参加系操作 UI。
 *
 * - 未参加 (myself=null): 入村フォーム (キャラ選択 / 希望役職 / メッセージ / 入村パスワード / 見学トグル)
 * - 参加中: 「参加/見学切替」「希望役職変更」「退村」アクション
 *
 * オリジナルキャラチップ村 (`isOriginalCharachip=true`) では、公式キャラ一覧の代わりに
 * キャラ名 (必須) + キャラ画像アップロードを受け付け、`postParticipateOriginal` (multipart) を叩く。
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
  const isOriginal = settings.isOriginalCharachip;

  // オリジナル村ではキャラ一覧 fetch を発行しない (空配列)。
  // 通常村は複数キャラチップを持つことがあるので、全 ID に対して並列クエリして結合する。
  const charachipIds = isOriginal ? [] : settings.charachipIds;
  const charasQuery = useSelectableCharasQuery(village.id, charachipIds);

  const mutation = useParticipateMutation(village.id);

  const [charaId, setCharaId] = useState<number | null>(null);
  const [charaName, setCharaName] = useState("");
  const [charaShortName, setCharaShortName] = useState("");
  const [charaImage, setCharaImage] = useState<File | null>(null);
  const [joinMessage, setJoinMessage] = useState("");
  const [joinPassword, setJoinPassword] = useState("");
  const [spectator, setSpectator] = useState(false);
  const [requestedSkill, setRequestedSkill] = useState<string>("");
  const [secondRequestedSkill, setSecondRequestedSkill] = useState<string>("");

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
    if (!charaName.trim() || !charaShortName.trim() || !joinMessage.trim()) return;
    if (isOriginal) {
      // オリジナル村は charaId 不要、画像必須
      if (!charaImage) return;
    } else {
      // 公式キャラチップ村は charaId 必須
      if (charaId == null) return;
    }
    const body = {
      // オリジナル村では charaId 未送信 (multipart endpoint で動的に chara を作る)
      charaId: isOriginal ? undefined : (charaId ?? undefined),
      charaName: charaName.trim(),
      charaShortName: charaShortName.trim(),
      joinMessage: joinMessage.trim(),
      // 希望役職指定不可な村では送らない (backend 側で空文字 / おまかせは許容するが、送らないのが明確)
      requestedSkill: settings.isSkillRequestAvailable && requestedSkill ? requestedSkill : undefined,
      secondRequestedSkill:
        settings.isSkillRequestAvailable && secondRequestedSkill ? secondRequestedSkill : undefined,
      joinPassword: settings.joinPasswordRequired ? joinPassword : undefined,
      spectator,
    };
    mutation.mutate({
      body,
      charaImage: isOriginal && charaImage ? charaImage : undefined,
    });
  }

  const charas = charasQuery.data;
  const isLoadingCharas = charasQuery.isLoading;
  // backend の MessageContent.assertMessageRestrict はプロローグでは early return するため、
  // 入村メッセージの長さ上限を frontend で吸収する (旧 Thymeleaf も同様に 400 文字制限)。
  const submittable =
    (isOriginal ? charaImage != null : charaId != null) &&
    charaName.trim().length > 0 &&
    charaShortName.trim().length === 1 &&
    joinMessage.trim().length > 0 &&
    joinMessage.length <= MESSAGE_MAX_LENGTH &&
    !mutation.isPending;

  return (
    <Panel title="入村">
      <form onSubmit={submit} className="space-y-3">
        {!isOriginal && (
          <Field label="キャラ">
            {isLoadingCharas ? (
              <p className="opacity-80 text-sm">読み込み中...</p>
            ) : charas.length === 0 ? (
              <p className="opacity-80 text-sm">選択可能なキャラがいません</p>
            ) : (
              <CharaGrid charas={charas} selectedId={charaId} onSelect={onSelectChara} />
            )}
          </Field>
        )}

        {isOriginal && (
          <Field label="キャラ画像 (最大 100,000 byte、png/jpg/jpeg/gif/webp)">
            <input
              type="file"
              accept=".png,.jpg,.jpeg,.gif,.webp,image/png,image/jpeg,image/gif,image/webp"
              onChange={(e) => setCharaImage(e.target.files?.[0] ?? null)}
              className={inputClass}
              disabled={mutation.isPending}
            />
            {charaImage && (
              <p className="text-xs opacity-80 mt-1">
                {charaImage.name} ({Math.round(charaImage.size / 1024)} KB)
              </p>
            )}
          </Field>
        )}

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
          <div className="space-y-1">
            <textarea
              value={joinMessage}
              onChange={(e) => setJoinMessage(e.target.value)}
              rows={3}
              maxLength={MESSAGE_MAX_LENGTH}
              className={inputClass}
              placeholder={`挨拶など (発言扱い、${MESSAGE_MAX_LENGTH} 文字以内)`}
              disabled={mutation.isPending}
            />
            <p className="text-xs opacity-60 text-right">
              {joinMessage.length} / {MESSAGE_MAX_LENGTH}
            </p>
          </div>
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
            <span className="text-xs text-blood-500">{mutation.error.message}</span>
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
              className={`w-full text-left p-2 rounded-[3px] border text-[0.95em] transition ${
                isSelected
                  ? "border-mint-500 bg-mint-600/20"
                  : "border-night-700 hover:border-mint-500 bg-night-900"
              }`}
            >
              <span className="font-medium">{c.name}</span>
              <span className="ml-2 text-xs opacity-80">[{c.shortName}]</span>
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
  // isSpectateAvailable=false でも、既に見学者になっているプレイヤーには「参加者に戻る」
  // の動線を残す (将来の設定変更パスで「見学不可だが見学者が存在する」状態が生まれた
  // 場合の保険)。
  const showSwitch = village.settings.isSpectateAvailable || myself.isSpectator;
  return (
    <Panel title="参加状態の操作">
      <div className="space-y-3">
        {showSwitch && <SwitchParticipateButton villageId={village.id} isSpectator={myself.isSpectator} />}
        {village.settings.isSkillRequestAvailable && !myself.isSpectator && (
          <ChangeRequestSkillForm village={village} myself={myself} />
        )}
        {!village.isCreator && <LeaveButton villageId={village.id} />}
        {village.isCreator && (
          <p className="text-xs opacity-60">
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
        <span className="text-xs text-blood-500">{mutation.error.message}</span>
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
  // 既存希望は myself に乗っていない (skill は確定後のもの) ので空からスタート。
  // 空文字 ("(おまかせ)") は送信時に OMAKASE_CODE にマップする (backend は NotBlank)。
  const [requestedSkill, setRequestedSkill] = useState<string>("");
  const [secondRequestedSkill, setSecondRequestedSkill] = useState<string>("");

  // 参加直後の myself.skill は未確定 (null)。役職確定後は変更させない。
  if (myself.skill != null) return null;

  function submit(e: React.FormEvent) {
    e.preventDefault();
    mutation.mutate({
      requestedSkill: requestedSkill || OMAKASE_CODE,
      secondRequestedSkill: secondRequestedSkill || OMAKASE_CODE,
    });
  }

  const submittable = !mutation.isPending;

  return (
    <form onSubmit={submit} className="space-y-2 border-t border-night-700 pt-3">
      <p className="text-xs opacity-80">希望役職変更</p>
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
          <span className="text-xs text-blood-500">{mutation.error.message}</span>
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
    <div className="flex items-center gap-3 border-t border-night-700 pt-3">
      <button
        type="button"
        onClick={onClick}
        disabled={mutation.isPending}
        className="px-[9px] py-[6px] rounded-[3px] border-2 border-blood-500 bg-blood-500 text-white text-[13px] hover:bg-blood-600 hover:border-blood-600 disabled:opacity-50 disabled:cursor-not-allowed"
      >
        {mutation.isPending ? "送信中..." : "退村する"}
      </button>
      {mutation.isError && (
        <span className="text-xs text-blood-500">{mutation.error.message}</span>
      )}
    </div>
  );
}

// ---------- 部品 ----------

function Panel({ title, children }: { title: string; children: React.ReactNode }) {
  return (
    <UIPanel>
      <PanelHeading>
        <h2 className="text-sm m-0">{title}</h2>
      </PanelHeading>
      <PanelBody>{children}</PanelBody>
    </UIPanel>
  );
}

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  // 旧 .form-horizontal 風: sm 以上で label を左 8em、コントロールを右に並べる
  return (
    <div className="sm:flex sm:items-start sm:gap-2">
      <label className="block sm:w-[8em] shrink-0 text-[0.95em] opacity-80 py-1">{label}</label>
      <div className="flex-1 space-y-1">{children}</div>
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

/** 発言系の上限 (旧 Thymeleaf 通常発言フォームと同じ 400 文字)。 */
const MESSAGE_MAX_LENGTH = 400;

/**
 * 「おまかせ」役職 (CDef.Skill.おまかせ) のコード。
 * 入村フォームでは未指定 (undefined) を送れば backend 側で `Skill(おまかせ)` に解決される
 * (`VillageParticipateRestController.resolveSkill`) が、希望役職変更 endpoint の
 * `VillageChangeRequestSkillBody` は `@NotBlank` のため、UI で「おまかせ」を選んだ場合は
 * 明示的にこのコードに変換して送る。
 */
const OMAKASE_CODE = "LEFTOVER";

const inputClass =
  "w-full bg-night-900 border border-night-700 rounded-[3px] px-2 py-1 text-[1em] focus:outline-none focus:border-mint-500 disabled:opacity-50";

const primaryButtonClass =
  "px-[9px] py-[6px] rounded-[3px] border-2 border-bs-success-600 bg-bs-success-500 text-white text-[13px] hover:bg-bs-success-700 hover:border-bs-success-700 disabled:opacity-50 disabled:cursor-not-allowed";

const secondaryButtonClass =
  "px-[9px] py-[6px] rounded-[3px] border-2 border-night-700 bg-night-800 text-white text-[13px] hover:bg-night-700 disabled:opacity-50 disabled:cursor-not-allowed";

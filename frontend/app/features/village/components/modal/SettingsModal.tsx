import { useState, type ReactNode } from "react";

import { ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { inputClass, selectClass } from "~/components/ui/Input";
import { Modal } from "~/components/ui/Modal";
import {
  saveVillageNotificationSetting,
  type ParticipantSituationView,
} from "~/features/village/api";
import { PAGE_SIZE_OPTIONS, useDisplaySettings } from "~/features/village/displaySettings";
import { useVillageId } from "~/features/village/VillageContext";
import { ApiError } from "~/lib/api";

function SettingRow({ label, children }: { label: string; children: ReactNode }) {
  return (
    <div className="flex flex-wrap items-start gap-[10px]">
      <span className="w-[140px] shrink-0 pt-[2px] text-right font-bold">{label}</span>
      <div className="min-w-[200px] flex-1">{children}</div>
    </div>
  );
}

function CheckRow({
  label,
  checked,
  onChange,
  text,
  note,
}: {
  label: string;
  checked: boolean;
  onChange: (value: boolean) => void;
  text: string;
  note?: string;
}) {
  return (
    <SettingRow label={label}>
      <label className="flex cursor-pointer items-center gap-[5px]">
        <input type="checkbox" checked={checked} onChange={(e) => onChange(e.target.checked)} />
        {text}
      </label>
      {note != null && <p className="mt-[3px] text-[10.32px] text-gray-400">{note}</p>}
    </SettingRow>
  );
}

function SectionTitle({ children }: { children: ReactNode }) {
  return (
    <>
      <hr className="my-[15px] border-border" />
      <h5 className="mb-[10px] text-[14px] font-bold">{children}</h5>
    </>
  );
}

/** 表示設定 (ブラウザ保存) と Discord 通知設定 (サーバ保存)。footer-menu の「設定」から開く。 */
export function SettingsModal({
  open,
  onClose,
  mySituation,
}: {
  open: boolean;
  onClose: () => void;
  mySituation: ParticipantSituationView | null | undefined;
}) {
  const settings = useDisplaySettings();

  return (
    <Modal open={open} onClose={onClose} title="ユーザー設定" size="wide">
      <div className="space-y-[10px] p-[15px] text-[12px]">
        <h4 className="text-[16px] font-bold">表示設定</h4>
        <SectionTitle>ページ分割</SectionTitle>
        <CheckRow
          label="ページ分割"
          checked={settings.isPaging}
          onChange={(isPaging) => settings.update({ isPaging })}
          text="ページ分割する"
        />
        <SettingRow label="ページあたりの表示発言数">
          <select
            className={`${selectClass} max-w-[120px]`}
            value={settings.pageSize}
            onChange={(e) => settings.update({ pageSize: Number(e.target.value) })}
            aria-label="ページあたりの表示発言数"
          >
            {PAGE_SIZE_OPTIONS.map((size) => (
              <option key={size} value={size}>
                {size}
              </option>
            ))}
          </select>
        </SettingRow>
        <SectionTitle>更新通知</SectionTitle>
        <CheckRow
          label="自動更新"
          checked={settings.autoReload}
          onChange={(autoReload) => settings.update({ autoReload })}
          text="更新検知時自動で読み込む"
          note="最新ページにいる時に更新を検知したら自動で更新します。"
        />
        <SectionTitle>便利機能</SectionTitle>
        <CheckRow
          label="文字装飾ボタン"
          checked={settings.showDecorationButtons}
          onChange={(showDecorationButtons) => settings.update({ showDecorationButtons })}
          text="表示する"
        />
        <SectionTitle>発言表示</SectionTitle>
        <CheckRow
          label="画像の大きさ"
          checked={settings.largeImage}
          onChange={(largeImage) => settings.update({ largeImage })}
          text="画像を大きく表示する"
        />
        <CheckRow
          label="文字の大きさ"
          checked={settings.largeText}
          onChange={(largeText) => settings.update({ largeText })}
          text="文字を大きく表示する"
        />
        <SectionTitle>表示設定のリセット</SectionTitle>
        <div className="flex justify-end">
          <Button
            variant="danger"
            onClick={() => {
              if (window.confirm("本当に表示設定をリセットしてよろしいですか？")) {
                settings.reset();
              }
            }}
          >
            リセット
          </Button>
        </div>

        {mySituation?.myself != null && <NotificationSection mySituation={mySituation} />}

        <hr className="my-[15px] border-border" />
        <div className="flex justify-end">
          <Button variant="default" onClick={onClose}>
            閉じる
          </Button>
        </div>
      </div>
    </Modal>
  );
}

function NotificationSection({ mySituation }: { mySituation: ParticipantSituationView }) {
  const villageId = useVillageId();
  const current = mySituation.myself?.notification;
  const [webhookUrl, setWebhookUrl] = useState(current?.discordWebhookUrl ?? "");
  const [villageStart, setVillageStart] = useState(current?.village?.start ?? false);
  const [villageDaychange, setVillageDaychange] = useState(current?.village?.dayChange ?? false);
  const [villageEpilogue, setVillageEpilogue] = useState(current?.village?.epilogue ?? false);
  const [secretSay, setSecretSay] = useState(current?.message?.secretSay ?? false);
  const [anchorSay, setAnchorSay] = useState(current?.message?.anchor ?? false);
  const [abilitySay, setAbilitySay] = useState(current?.message?.abilitySay ?? false);
  const [keyword, setKeyword] = useState(current?.message?.keywords?.join("\n") ?? "");
  const [error, setError] = useState<string | null>(null);
  const [saved, setSaved] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    setSaved(false);
    try {
      await saveVillageNotificationSetting(villageId, {
        webhookUrl,
        villageStart,
        villageDaychange,
        villageEpilogue,
        secretSay,
        anchorSay,
        abilitySay,
        keyword,
      });
      setSaved(true);
    } catch (e) {
      setError(e instanceof ApiError ? e.detail : "通知設定の保存に失敗しました");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="space-y-[10px]">
      <hr className="my-[15px] border-border" />
      <h4 className="text-[16px] font-bold">Discord通知設定</h4>
      <ErrorMessage error={error} />
      {saved && <p className="text-success">保存しました。テスト通知を確認してください。</p>}
      <SettingRow label="WebhookURL">
        <input
          type="text"
          className={inputClass}
          value={webhookUrl}
          onChange={(e) => setWebhookUrl(e.target.value)}
          aria-label="WebhookURL"
        />
      </SettingRow>
      <CheckRow
        label=""
        checked={villageStart}
        onChange={setVillageStart}
        text="開始通知"
        note="進行中に遷移した際に通知します。"
      />
      <CheckRow
        label=""
        checked={villageDaychange}
        onChange={setVillageDaychange}
        text="日付更新通知"
        note="日付が更新された際に通知します。"
      />
      <CheckRow
        label=""
        checked={villageEpilogue}
        onChange={setVillageEpilogue}
        text="エピローグ通知"
        note="エピローグに遷移した際に通知します。"
      />
      <CheckRow
        label=""
        checked={secretSay}
        onChange={setSecretSay}
        text="秘話通知"
        note="秘話を受け取った際に通知します。"
      />
      <CheckRow
        label=""
        checked={anchorSay}
        onChange={setAnchorSay}
        text="アンカー通知"
        note="あなたの発言がアンカー指定された際に通知します（梟視点の場合は通知されません）。"
      />
      <CheckRow
        label=""
        checked={abilitySay}
        onChange={setAbilitySay}
        text="役職窓通知"
        note="役職窓発言を受け取った際に通知します（梟視点の場合は通知されません）。"
      />
      <SettingRow label="キーワード（スペース区切り、計30文字まで）">
        <input
          type="text"
          className={inputClass}
          value={keyword}
          maxLength={30}
          onChange={(e) => setKeyword(e.target.value)}
          aria-label="通知キーワード"
        />
        <p className="mt-[3px] text-[10.32px] text-gray-400">
          指定したキーワードが含まれる発言を受け取った際に通知します（梟視点の場合は通知されません）。
        </p>
      </SettingRow>
      <div className="flex items-center justify-end gap-[10px]">
        <span className="text-[10.32px] text-gray-400">保存するとテスト通知が届きます。</span>
        <Button onClick={submit} disabled={submitting || webhookUrl.trim() === ""}>
          保存
        </Button>
      </div>
    </div>
  );
}

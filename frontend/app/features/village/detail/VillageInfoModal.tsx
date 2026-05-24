import type { VillageView } from "./api";

/**
 * 旧 .old-thymeleaf/templates/village/modal-village-info.html 相当の村情報モーダル。
 *
 * 設定変更ボタンは creator のみ表示。設定値の表示だけ行うので backend は
 * 既存の `VillageView.settings` (= VillageSettingsView) をそのまま使う。
 */
export function VillageInfoModal({
  open,
  village,
  onClose,
}: {
  open: boolean;
  village: VillageView;
  onClose: () => void;
}) {
  if (!open) return null;
  const s = village.settings;
  const intervalText = formatInterval(s.dayChangeIntervalSeconds);
  return (
    <div
      role="dialog"
      aria-modal="true"
      aria-label="村情報"
      className="fixed inset-0 z-40 flex items-center justify-center bg-black/60 p-4"
      onClick={onClose}
    >
      <div
        className="w-full max-w-2xl max-h-[90vh] overflow-y-auto rounded-xl bg-slate-900 border border-slate-700 p-5 space-y-3"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex items-center justify-between">
          <h3 className="text-base font-semibold">村情報</h3>
          <button
            type="button"
            className="text-slate-400 hover:text-slate-200 text-sm"
            onClick={onClose}
            aria-label="閉じる"
          >
            ×
          </button>
        </div>
        <dl className="text-xs grid grid-cols-[10rem_1fr] gap-y-1 gap-x-3">
          {s.welcomeRangeName && <Row label="募集範囲" value={s.welcomeRangeName} />}
          <Row label="最少開始人数" value={s.personMin} />
          <Row label="定員" value={s.personMax} />
          <Row label="開始日時" value={formatDateTime(s.startDatetime)} />
          <Row label="更新間隔" value={intervalText} />
          <Row label="投票形式" value={s.voteTypeName} />
          <Row
            label="役職希望"
            value={s.isSkillRequestAvailable ? "可能" : "不可"}
          />
          <Row
            label="見学入村"
            value={s.isSpectateAvailable ? "可能" : "不可"}
          />
          <Row
            label="プロデューサー機能"
            value={s.creatorIsProducer ? "あり" : "なし"}
          />
          <Row
            label="同一人狼連続襲撃"
            value={s.availableSameWolfAttack ? "可能" : "不可"}
          />
          <Row
            label="狩人連続護衛"
            value={s.availableGuardSameTarget ? "可能" : "不可"}
          />
          <Row
            label="転生時役職候補"
            value={s.reincarnationSkillAll ? "全役職" : "編成に含まれる役職のみ"}
          />
          <Row
            label="墓下見学役職公開"
            value={s.openSkillInGrave ? "公開" : "非公開"}
          />
          <Row
            label="墓下/地上の会話"
            value={s.visibleGraveSpectateMessage ? "可能" : "不可"}
          />
          <Row label="秘話" value={s.allowedSecretSayName} />
          <Row label="突然死" value={s.availableSuddenlyDeath ? "あり" : "なし"} />
          <Row label="コミット" value={s.availableCommit ? "あり" : "なし"} />
          <Row label="アクション発言" value={s.availableAction ? "可能" : "不可"} />
          {s.ageLimitName && <Row label="年齢制限" value={s.ageLimitName} />}
          <Row label="入村パスワード" value={s.joinPasswordRequired ? "あり" : "なし"} />
          <Row label="館を建てたプレイヤー" value={village.createPlayerName} />
          {!s.isRandomOrganization && s.organization && (
            <Row
              label="役職構成"
              value={
                <pre className="whitespace-pre-wrap font-sans text-xs">{s.organization}</pre>
              }
            />
          )}
          {s.isRandomOrganization && (
            <Row label="役職構成" value="ランダム編成 (闇鍋)" />
          )}
        </dl>
        <div className="flex justify-end gap-2 pt-2 border-t border-slate-700">
          {village.isCreator && (
            <a
              href={`/wolf-mansion/villages/${village.id}/settings`}
              className="rounded bg-emerald-600 hover:bg-emerald-500 px-3 py-1.5 text-sm text-white"
            >
              設定変更
            </a>
          )}
          <button
            type="button"
            className="rounded border border-slate-600 px-3 py-1.5 text-sm text-slate-200 hover:bg-slate-800"
            onClick={onClose}
          >
            閉じる
          </button>
        </div>
      </div>
    </div>
  );
}

function Row({ label, value }: { label: string; value: React.ReactNode }) {
  return (
    <>
      <dt className="text-slate-400">{label}</dt>
      <dd className="text-slate-100 break-all">{value}</dd>
    </>
  );
}

function formatInterval(totalSeconds: number): string {
  const h = Math.floor(totalSeconds / 3600);
  const m = Math.floor((totalSeconds % 3600) / 60);
  const s = totalSeconds % 60;
  const parts: string[] = [];
  if (h > 0) parts.push(`${h}時間`);
  if (m > 0) parts.push(`${m}分`);
  if (s > 0) parts.push(`${s}秒`);
  return parts.length > 0 ? parts.join("") : "0秒";
}

function formatDateTime(iso: string): string {
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) return iso;
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  const hh = String(d.getHours()).padStart(2, "0");
  const mi = String(d.getMinutes()).padStart(2, "0");
  return `${yyyy}/${mm}/${dd} ${hh}:${mi}`;
}

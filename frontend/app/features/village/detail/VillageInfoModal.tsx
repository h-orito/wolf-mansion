import * as React from "react";
import { Link } from "react-router";
import type { VillageView } from "./api";
import { Modal } from "~/components/ui/Modal";
import { Button, LinkButton } from "~/components/ui/Button";
import { Table, TableResponsive } from "~/components/ui/Table";

/**
 * 旧 .old-thymeleaf/templates/village/modal-village-info.html 相当の村情報モーダル。
 *
 * Step 13c で:
 * - 旧 Bootstrap 3 .modal-* に揃えるため Modal primitive を使う (createPortal で
 *   親 opacity の影響を回避済)
 * - 旧画面と同じ「設定 / RP 設定」2 表 + 発言制限 (通常 / 役職 / RP) 表を復元
 * - キャラセット (公式なら `/charachips/:id` への内部リンク) + ダミーキャラ名を追加
 *   (`.issues/20` を同時解消)
 *
 * 設定変更ボタンは creator のみ表示する。
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
  const s = village.settings;
  const intervalText = formatInterval(s.dayChangeIntervalSeconds);
  return (
    <Modal open={open} onClose={onClose} title="村情報">
      <div className="space-y-3">
        <TableResponsive>
          <Table>
            <tbody>
              {s.welcomeRangeName && <InfoRow label="募集範囲" value={s.welcomeRangeName} />}
              <InfoRow label="最少開始人数" value={s.personMin} />
              <InfoRow label="定員" value={s.personMax} />
              <InfoRow label="開始日時" value={formatDateTime(s.startDatetime)} />
              <InfoRow label="更新間隔" value={intervalText} />
              <InfoRow label="投票形式" value={s.voteTypeName} />
              <InfoRow label="役職希望" value={s.isSkillRequestAvailable ? "可能" : "不可"} />
              <InfoRow
                label="見学入村"
                value={s.isSpectateAvailable ? "可能（[キャラチップ人数 - 定員]人まで）" : "不可"}
              />
              <InfoRow label="プロデューサー機能" value={s.creatorIsProducer ? "あり" : "なし"} />
              <InfoRow
                label="同一人狼による連続襲撃"
                value={s.availableSameWolfAttack ? "可能" : "不可 (開始時点で狼2以下編成の場合は可能に変更されます)"}
              />
              <InfoRow
                label="狩人による連続護衛"
                value={s.availableGuardSameTarget ? "可能" : "不可"}
              />
              <InfoRow
                label="転生時の役職候補"
                value={s.reincarnationSkillAll ? "全役職" : "編成に含まれる役職のみ"}
              />
              <InfoRow label="墓下見学役職公開" value={s.openSkillInGrave ? "公開" : "非公開"} />
              <InfoRow
                label="墓下見学と地上との会話"
                value={s.visibleGraveSpectateMessage ? "可能" : "不可"}
              />
              <InfoRow label="秘話" value={s.allowedSecretSayName} />
              <InfoRow label="突然死" value={s.availableSuddenlyDeath ? "あり" : "なし"} />
              <InfoRow label="コミット" value={s.availableCommit ? "あり" : "なし"} />
              <InfoRow
                label="キャラセット"
                value={<CharachipList charachips={s.charachips} isOriginal={s.isOriginalCharachip} />}
              />
              <InfoRow label="ダミーキャラ" value={s.dummyCharaName} />
              <InfoRow label="入村パスワード" value={s.joinPasswordRequired ? "あり" : "なし"} />
              <InfoRow label="館を建てたプレイヤー" value={village.createPlayerName} />
              {!s.isRandomOrganization && (
                <InfoRow
                  label="役職構成"
                  value={
                    s.organization ? (
                      <pre className="whitespace-pre-wrap font-sans m-0">{s.organization}</pre>
                    ) : (
                      ""
                    )
                  }
                />
              )}
              {s.isRandomOrganization && (
                <InfoRow label="役職構成（闇鍋）" value="ランダム編成" />
              )}
              <InfoRow
                label="発言制限（通常発言）"
                value={<NormalSayRestrictTable list={s.sayRestrictList} />}
              />
              <InfoRow
                label="発言制限（役職発言）"
                value={<MessageTypeRestrictTable list={s.skillSayRestrictList} />}
              />
            </tbody>
          </Table>
        </TableResponsive>

        <p className="text-[0.95em] mb-1">RP設定</p>
        <TableResponsive>
          <Table>
            <tbody>
              {s.ageLimitName && <InfoRow label="年齢制限" value={s.ageLimitName} />}
              <InfoRow label="アクション" value={s.availableAction ? "可能" : "不可"} />
              {s.availableAction && (
                <InfoRow
                  label="発言制限（RP発言）"
                  value={<MessageTypeRestrictTable list={s.rpSayRestrictList} />}
                />
              )}
            </tbody>
          </Table>
        </TableResponsive>

        <p className="text-[0.95em] opacity-80">館を建てたプレイヤーのみ設定を変更できます。</p>
        <div className="flex justify-end gap-2 pt-1">
          {village.isCreator && (
            <LinkButton
              to={`/villages/${village.id}/settings`}
              variant="success"
              onClick={onClose}
            >
              設定変更
            </LinkButton>
          )}
          <Button variant="default" onClick={onClose}>閉じる</Button>
        </div>
      </div>
    </Modal>
  );
}

function InfoRow({ label, value }: { label: string; value: React.ReactNode }) {
  return (
    <tr>
      <th className="w-[12em] align-top">{label}</th>
      <td>{value}</td>
    </tr>
  );
}

function CharachipList({
  charachips,
  isOriginal,
}: {
  charachips: { id: number; name: string }[];
  isOriginal: boolean;
}) {
  if (charachips.length === 0) return <>—</>;
  return (
    <>
      {charachips.map((c, i) => (
        <React.Fragment key={c.id}>
          {/* オリジナルキャラチップ村は外部リンクがないので名前だけ表示 (旧画面踏襲) */}
          {isOriginal ? (
            <span>{c.name}</span>
          ) : (
            <Link to={`/charachips/${c.id}`} className="message-link" target="_blank" rel="noopener noreferrer">
              {c.name}
            </Link>
          )}
          {i < charachips.length - 1 && <span>、</span>}
        </React.Fragment>
      ))}
    </>
  );
}

function NormalSayRestrictTable({
  list,
}: {
  list: { skillCode: string; skillName: string; count: number; length: number }[];
}) {
  if (list.length === 0) return <>制限がかかっている役職はありません。</>;
  return (
    <div>
      <p className="m-0 mb-1 text-[0.95em]">制限がかかっている役職のみ表示しています。</p>
      <Table>
        <thead>
          <tr>
            <th>役職</th>
            <th>1回あたりの発言文字数 * 1日あたりの発言回数</th>
          </tr>
        </thead>
        <tbody>
          {list.map((r) => (
            <tr key={r.skillCode}>
              <td>{r.skillName}</td>
              <td>{r.length} * {r.count}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}

function MessageTypeRestrictTable({
  list,
}: {
  list: { messageTypeCode: string; messageTypeName: string; count: number; length: number }[];
}) {
  if (list.length === 0) return <>制限がかかっている発言種別はありません。</>;
  return (
    <div>
      <p className="m-0 mb-1 text-[0.95em]">制限がかかっている発言種別のみ表示しています。</p>
      <Table>
        <thead>
          <tr>
            <th>発言種別</th>
            <th>1回あたりの発言文字数 * 1日あたりの発言回数</th>
          </tr>
        </thead>
        <tbody>
          {list.map((r) => (
            <tr key={r.messageTypeCode}>
              <td>{r.messageTypeName}</td>
              <td>{r.length} * {r.count}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
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

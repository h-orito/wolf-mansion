import { useState } from "react";
import { useParams } from "react-router";

import { Button } from "~/components/ui/Button";
import { Heading, SubHeading } from "~/components/ui/Heading";
import { Modal } from "~/components/ui/Modal";
import { PageLayout } from "~/components/layout/PageLayout";
import { ExternalLink, TextLink } from "~/components/ui/TextLink";
import { inputClass, textareaClass } from "~/components/ui/Input";
import { useMe } from "~/features/auth/useMe";
import {
  type PlayerProfile,
  usePlayerProfile,
  useUpdatePlayerDetail,
} from "~/features/player/usePlayer";
import { useAsyncAction } from "~/lib/useAsyncAction";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta({ params }: Route.MetaArgs) {
  return siteMeta(`ユーザ: ${params.name}`);
}

export default function UserProfilePage() {
  const { name } = useParams<{ name: string }>();
  if (!name) return null;

  return (
    <PageLayout>
      <UserProfile name={name} />
    </PageLayout>
  );
}

function UserProfile({ name }: { name: string }) {
  const { data, isLoading, error } = usePlayerProfile(name);
  const { me } = useMe();
  const isMyself = me != null && me.name === name;

  return (
    <div className="px-[15px]">
      <Heading>ユーザID: {name}</Heading>
      {isLoading && <p>読み込み中...</p>}
      {error != null && <p>ユーザが存在しません。</p>}
      {data != null && <ProfileContent data={data} name={name} isMyself={isMyself} />}
    </div>
  );
}

type VillageEntry = PlayerProfile["participateVillageList"][number];

function ProfileContent({
  data,
  name,
  isMyself,
}: {
  data: PlayerProfile;
  name: string;
  isMyself: boolean;
}) {
  const [editOpen, setEditOpen] = useState(false);

  return (
    <>
      {data.twitterUserName != null && (
        <div className="mb-[10px]">
          Twitter:{" "}
          <ExternalLink href={`https://twitter.com/${data.twitterUserName}`}>
            @{data.twitterUserName}
          </ExternalLink>
        </div>
      )}
      {data.introduction != null && (
        <p className="mb-[10px] rounded border border-[#464545] p-[10px] whitespace-pre-line">
          {data.introduction}
        </p>
      )}
      {isMyself && (
        <div className="mb-[10px]">
          <Button size="sm" onClick={() => setEditOpen(true)}>
            自己紹介編集
          </Button>
        </div>
      )}

      <div className="sm:flex sm:gap-[15px]">
        <div className="sm:w-1/2">
          <SubHeading>総合戦績</SubHeading>
          <StatsTable
            rows={[
              { label: "参加数", value: `${data.wholeStats.participateNum}` },
              { label: "勝利数", value: `${data.wholeStats.winNum}` },
              { label: "勝率", value: formatRate(data.wholeStats.winRate) },
            ]}
          />

          <SubHeading>陣営戦績</SubHeading>
          <RecordTable
            headers={["陣営", "参加", "勝利", "勝率"]}
            rows={data.campStatsList.map((c) => [
              c.campName,
              `${c.stats.participateNum}回`,
              `${c.stats.winNum}回`,
              formatRate(c.stats.winRate),
            ])}
          />
        </div>
        <div className="sm:w-1/2">
          <SubHeading>役職戦績</SubHeading>
          <RecordTable
            headers={["役職", "参加", "勝利", "勝率"]}
            rows={data.skillStatsList.map((s) => [
              s.skillName,
              `${s.stats.participateNum}回`,
              `${s.stats.winNum}回`,
              formatRate(s.stats.winRate),
            ])}
          />
        </div>
      </div>

      {data.participateVillageList.length > 0 && (
        <>
          <SubHeading>参加した村</SubHeading>
          <VillageTable villages={data.participateVillageList} showDetails />
        </>
      )}

      {data.spectateVillageList.length > 0 && (
        <>
          <SubHeading>見学した村</SubHeading>
          <VillageTable villages={data.spectateVillageList} />
        </>
      )}

      {editOpen && (
        <EditDetailModal
          name={name}
          twitterUserName={data.twitterUserName ?? ""}
          introduction={data.introduction ?? ""}
          onClose={() => setEditOpen(false)}
        />
      )}
    </>
  );
}

function EditDetailModal({
  name,
  twitterUserName: initialTwitter,
  introduction: initialIntro,
  onClose,
}: {
  name: string;
  twitterUserName: string;
  introduction: string;
  onClose: () => void;
}) {
  const [twitterUserName, setTwitterUserName] = useState(initialTwitter);
  const [introduction, setIntroduction] = useState(initialIntro);
  const updateDetail = useUpdatePlayerDetail(name);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await updateDetail({
        twitterUserName: twitterUserName || null,
        introduction: introduction || null,
      });
      onClose();
    }, "保存に失敗しました");

  return (
    <Modal open title="自己紹介編集" onClose={onClose}>
      <div className="space-y-[15px]">
        {error != null && <p className="text-[#e74c3c]">{error}</p>}
        <div>
          <label className="mb-[5px] block">Twitterユーザ名</label>
          <input
            type="text"
            className={inputClass}
            value={twitterUserName}
            maxLength={50}
            onChange={(e) => setTwitterUserName(e.target.value)}
          />
        </div>
        <div>
          <label className="mb-[5px] block">自己紹介（2000字以内）</label>
          <textarea
            className={`${textareaClass} min-h-[150px]`}
            value={introduction}
            maxLength={2000}
            onChange={(e) => setIntroduction(e.target.value)}
          />
        </div>
        <div className="flex justify-end gap-[10px]">
          <Button variant="default" onClick={onClose}>
            閉じる
          </Button>
          <Button onClick={submit} disabled={submitting}>
            保存
          </Button>
        </div>
      </div>
    </Modal>
  );
}

function formatRate(rate: number): string {
  return `${(rate * 100).toFixed(1)}%`;
}

const cellClass = "border border-[#464545] px-[8px] py-[4px]";
const headerClass = `${cellClass} text-left`;
const rightCellClass = `${cellClass} text-right`;

function StatsTable({ rows }: { rows: { label: string; value: string }[] }) {
  return (
    <div className="mb-[10px] overflow-x-auto">
      <table className="w-full border-collapse border border-[#464545]">
        <thead>
          <tr>
            <th className={headerClass}>項目</th>
            <th className={`${cellClass} text-right`}>戦績</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((r) => (
            <tr key={r.label} className="odd:bg-[#2a2a2a]">
              <td className={cellClass}>{r.label}</td>
              <td className={rightCellClass}>{r.value}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function RecordTable({ headers, rows }: { headers: string[]; rows: string[][] }) {
  return (
    <div className="mb-[10px] overflow-x-auto">
      <table className="w-full border-collapse border border-[#464545]">
        <thead>
          <tr>
            {headers.map((h, i) => (
              <th key={h} className={i === 0 ? headerClass : `${cellClass} text-right`}>
                {h}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {rows.map((row, i) => (
            <tr key={i} className="odd:bg-[#2a2a2a]">
              {row.map((cell, j) => (
                <td key={j} className={j === 0 ? cellClass : rightCellClass}>
                  {cell}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function VillageTable({
  villages,
  showDetails = false,
}: {
  villages: VillageEntry[];
  showDetails?: boolean;
}) {
  return (
    <div className="mb-[10px] overflow-x-auto">
      <table className="w-full border-collapse border border-[#464545]">
        <thead>
          <tr>
            <th className={`${cellClass} text-right`}>村番号</th>
            <th className={headerClass}>村名</th>
            <th className={headerClass} />
            {showDetails && (
              <>
                <th className={headerClass}>役職</th>
                <th className={headerClass}>生死</th>
                <th className={headerClass}>陣営</th>
                <th className={headerClass}>勝敗</th>
              </>
            )}
          </tr>
        </thead>
        <tbody>
          {villages.map((v) => (
            <tr key={v.villageId} className="odd:bg-[#2a2a2a]">
              <td className={`${rightCellClass} align-middle`}>
                <TextLink to={`/village/${v.villageId}`}>
                  {String(v.villageId).padStart(4, "0")}
                </TextLink>
              </td>
              <td className={`${cellClass} align-middle`}>{v.villageName}</td>
              <td className={`${cellClass} align-middle`}>
                <img
                  src={v.characterImgUrl}
                  width={v.characterImgWidth}
                  height={v.characterImgHeight}
                  alt={v.characterName}
                  style={{ minWidth: v.characterImgWidth, minHeight: v.characterImgHeight }}
                />
              </td>
              {showDetails && (
                <>
                  <td className={`${cellClass} align-middle`}>{v.skillName}</td>
                  <td className={`${cellClass} align-middle`}>{v.liveStatus}</td>
                  <td className={`${cellClass} align-middle`}>{v.campName}</td>
                  <td className={`${cellClass} align-middle`}>{v.winStatus}</td>
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

import { Link, useParams } from "react-router";

import { Heading, SubHeading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import { type PlayerProfile, usePlayerProfile } from "~/features/player/usePlayer";
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

  return (
    <div className="px-[15px]">
      <Heading>ユーザID: {name}</Heading>
      {isLoading && <p>読み込み中...</p>}
      {error != null && <p>ユーザが存在しません。</p>}
      {data != null && <ProfileContent data={data} />}
    </div>
  );
}

type VillageEntry = PlayerProfile["participateVillageList"][number];

function ProfileContent({ data }: { data: PlayerProfile }) {
  return (
    <>
      {data.twitterUserName != null && (
        <div className="mb-[10px]">
          Twitter:{" "}
          <a
            href={`https://twitter.com/${data.twitterUserName}`}
            target="_blank"
            rel="noreferrer"
            className="text-wm-accent hover:underline"
          >
            @{data.twitterUserName}
          </a>
        </div>
      )}
      {data.introduction != null && (
        <p className="mb-[10px] rounded border border-[#464545] p-[10px] whitespace-pre-line">
          {data.introduction}
        </p>
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
    </>
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
                <Link to={`/village/${v.villageId}`} className="text-wm-accent hover:underline">
                  {String(v.villageId).padStart(4, "0")}
                </Link>
              </td>
              <td className={`${cellClass} align-middle`}>{v.villageName}</td>
              <td className={`${cellClass} align-middle`}>
                <img
                  src={v.characterImgUrl}
                  width={v.characterImgWidth}
                  height={v.characterImgHeight}
                  alt={v.characterName}
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

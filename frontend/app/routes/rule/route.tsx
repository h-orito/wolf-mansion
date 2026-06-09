import { Link } from "react-router";

import { Heading, SubHeading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import { type SimpleSkillView } from "~/features/skills/api";
import { useSkillList } from "~/features/skills/useSkillList";
import { skillDescriptions } from "~/features/skills/descriptions";
import { useJudges } from "~/features/rule/useJudges";
import { SkillMessage } from "~/routes/skill/SkillMessage";
import { siteMeta } from "~/lib/meta";

import { MansionSection } from "./sections/MansionSection";
import { DetailSection } from "./sections/DetailSection";
import { StatusSection } from "./sections/StatusSection";
import { RoomSection } from "./sections/RoomSection";
import { OtherSection } from "./sections/OtherSection";
import { CampSection } from "./sections/CampSection";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("ルール");
}

type CampGroup = {
  campCode: string;
  campName: string;
  skills: SimpleSkillView[];
};

function groupByCamp(skills: SimpleSkillView[]): CampGroup[] {
  const map = new Map<string, CampGroup>();
  for (const skill of skills) {
    let group = map.get(skill.campCode);
    if (!group) {
      group = { campCode: skill.campCode, campName: skill.campName, skills: [] };
      map.set(skill.campCode, group);
    }
    group.skills.push(skill);
  }
  return Array.from(map.values());
}

export default function RulePage() {
  const { data: skillData } = useSkillList();
  const { data: judgeData } = useJudges();

  const campGroups = skillData ? groupByCamp(skillData.skills) : [];
  const judges = judgeData?.judges ?? [];

  return (
    <PageLayout>
      <div className="px-[15px]">
        <Heading>ルール</Heading>

        <TableOfContents campGroups={campGroups} />

        <SectionDivider />
        <SubHeading id="basic">人狼の基本ルール</SubHeading>
        <ul className="mb-[10.5px] list-disc pl-[40px]">
          <li>
            通常の人狼のルールを熟知していることが参加の前提となるため、基本ルールは省略します。
          </li>
        </ul>

        <SectionDivider />
        <SubHeading>人狼館の事件簿村ルール</SubHeading>
        <MansionSection />

        <SectionDivider />
        <SubHeading>詳細ルール</SubHeading>
        <DetailSection />

        <SectionDivider />
        <SubHeading>役職詳細</SubHeading>
        <p className="mb-[10.5px]">
          役職検索は
          <Link to="/skill" className="text-wm-accent hover:underline" target="_blank">
            こちら
          </Link>
        </p>
        <SkillDetailSection campGroups={campGroups} />

        <SectionDivider />
        <SubHeading>占霊判定、勝敗時のカウント</SubHeading>
        <JudgeSection judges={judges} />

        <SectionDivider />
        <SubHeading>ステータス</SubHeading>
        <StatusSection />

        <SectionDivider />
        <SubHeading>陣営・勝敗</SubHeading>
        <CampSection campGroups={campGroups} />

        <SectionDivider />
        <SubHeading>人数と部屋サイズ</SubHeading>
        <RoomSection />

        <SectionDivider />
        <SubHeading>その他</SubHeading>
        <OtherSection />
      </div>
    </PageLayout>
  );
}

function SectionDivider() {
  return <hr className="my-[21px] border-[#464545]" />;
}

function TableOfContents({ campGroups }: { campGroups: CampGroup[] }) {
  return (
    <div className="rounded bg-[#303030] px-[5px] pt-[5px] pb-[15px]">
      <ul className="list-none pl-0">
        <li>
          <a href="#basic" className="text-wm-accent hover:underline">
            人狼の基本ルール
          </a>
        </li>
        <li>
          <a href="#mansion" className="text-wm-accent hover:underline">
            人狼館の事件簿村ルール
          </a>
        </li>
        <li>
          <a href="#detail" className="text-wm-accent hover:underline">
            詳細ルール
          </a>
          <ul className="list-none pl-[20px]">
            <TocLink id="attitude" label="基本的な心構え" />
            <TocLink id="ability" label="能力行使" />
            <TocLink id="suddenly-death" label="突然死" />
            <TocLink id="vote" label="投票" />
            <TocLink id="execute" label="処刑" />
            <TocLink id="anchor" label="アンカー" />
            <TocLink id="default-org" label="役職配分" />
            <TocLink id="random-organization" label="闇鍋編成" />
            <TocLink id="skill-request" label="役職希望" />
            <TocLink id="spectate" label="見学" />
            <TocLink id="producer" label="プロデューサー" />
            <TocLink id="say" label="発言" />
            <TocLink id="action" label="アクション" />
            <TocLink id="change-name" label="名前変更・簡易メモ" />
            <TocLink id="commit" label="コミット" />
            <TocLink id="tensei" label="転生" />
            <TocLink id="change-skill" label="役職変化" />
            <TocLink id="process" label="流れ" />
            <TocLink id="process-order" label="日付更新時の処理順" />
          </ul>
        </li>
        <li>
          <a href="#skill" className="text-wm-accent hover:underline">
            役職詳細
          </a>
          <ul className="list-none pl-[20px]">
            {campGroups.map((camp) => (
              <li key={camp.campCode}>
                <a
                  href={`#${camp.campCode.toLowerCase()}`}
                  className="text-wm-accent hover:underline"
                >
                  {camp.campName}
                </a>
                <ul className="list-none pl-[20px]">
                  {camp.skills.map((skill) => (
                    <li key={skill.code}>
                      <a
                        href={`#${skill.code.toLowerCase()}`}
                        className="text-wm-accent hover:underline"
                      >
                        {skill.name}
                      </a>
                    </li>
                  ))}
                </ul>
              </li>
            ))}
            <li>
              <a href="#skill-plan" className="text-wm-accent hover:underline">
                未実装役職
              </a>
            </li>
          </ul>
        </li>
        <li>
          <a href="#judge" className="text-wm-accent hover:underline">
            占霊判定、勝敗時のカウント
          </a>
        </li>
        <li>
          <a href="#status" className="text-wm-accent hover:underline">
            ステータス
          </a>
          <ul className="list-none pl-[20px]">
            <TocLink id="love" label="恋絆" />
            <TocLink id="fox_possession" label="狐憑き" />
            <TocLink id="insanity" label="狂気" />
            <TocLink id="belief" label="信念" />
            <TocLink id="insurance" label="保険" />
            <TocLink id="disrespectful" label="不敬" />
            <TocLink id="cursemark" label="呪縛符" />
            <TocLink id="countercursemark" label="反呪符" />
            <TocLink id="telekinesis" label="念力" />
          </ul>
        </li>
        <li>
          <a href="#camp" className="text-wm-accent hover:underline">
            陣営
          </a>
        </li>
        <li>
          <a href="#epilogue-condition" className="text-wm-accent hover:underline">
            勝敗
          </a>
          <ul className="list-none pl-[20px]">
            <TocLink id="epilogue-condition" label="終了条件" />
            <TocLink id="personal-wincondition" label="個人ごとの勝敗判定" />
            <TocLink id="camp-wincondition" label="陣営の勝敗判定" />
          </ul>
        </li>
        <li>
          <a href="#room" className="text-wm-accent hover:underline">
            人数と部屋サイズ
          </a>
        </li>
        <li>
          <a href="#other" className="text-wm-accent hover:underline">
            その他
          </a>
          <ul className="list-none pl-[20px]">
            <TocLink id="random-message" label="発言ランダム機能" />
            <TocLink id="message-decorate" label="文字装飾機能" />
            <TocLink id="message-type" label="発言種別" />
            <TocLink id="call-owner" label="国主の召喚方法" />
          </ul>
        </li>
      </ul>
    </div>
  );
}

function TocLink({ id, label }: { id: string; label: string }) {
  return (
    <li>
      <a href={`#${id}`} className="text-wm-accent hover:underline">
        {label}
      </a>
    </li>
  );
}

function SkillDetailSection({ campGroups }: { campGroups: CampGroup[] }) {
  return (
    <ul id="skill" className="mb-[10.5px] list-disc pl-[20px]">
      {campGroups.map((camp) => (
        <li key={camp.campCode} id={camp.campCode.toLowerCase()} className="mb-[10px]">
          <h5 className="my-[10.5px] text-[15px]">{camp.campName}</h5>
          <ul className="list-disc pl-[20px]">
            {camp.skills.map((skill) => (
              <SkillItem key={skill.code} skill={skill} />
            ))}
          </ul>
        </li>
      ))}
      <li id="skill-plan" className="mb-[10px]">
        <h5 className="my-[10.5px] text-[15px]">未実装役職</h5>
        <ul className="list-disc pl-[20px]">
          <li>未実装の役職は今後追加される可能性があります。</li>
        </ul>
      </li>
    </ul>
  );
}

function SkillItem({ skill }: { skill: SimpleSkillView }) {
  const items = skillDescriptions[skill.code.toLowerCase()] ?? [];
  return (
    <li id={skill.code.toLowerCase()} className="mb-[10px]">
      【{skill.shortName}】{skill.name}
      <ul className="list-disc pl-[20px] leading-[17.14px]">
        {items.map((item, i) =>
          item.type === "message" ? (
            <li key={i}>
              <SkillMessage messageType={item.messageType} content={item.content} />
            </li>
          ) : (
            <li key={i}>
              {item.content.split("\n").map((line, j) => (
                <span key={j}>
                  {j > 0 && <br />}
                  {line}
                </span>
              ))}
            </li>
          ),
        )}
      </ul>
    </li>
  );
}

type JudgeViewType = {
  skills: { code: string; name: string }[];
  divineResultWolf: boolean;
  psychicResultWolf: boolean;
  noDeadByAttack: boolean;
  count: string;
};

function JudgeSection({ judges }: { judges: JudgeViewType[] }) {
  return (
    <div id="judge" className="overflow-x-auto">
      <table className="w-full border-collapse text-[12px]">
        <thead>
          <tr className="border-b border-[#464545]">
            <th className="p-[5px] text-left align-middle">役職</th>
            <th className="p-[5px] text-left align-middle">占結果</th>
            <th className="p-[5px] text-left align-middle">霊結果</th>
            <th className="p-[5px] text-left align-middle">襲撃耐性</th>
            <th className="p-[5px] text-left align-middle">
              勝敗判定
              <br />
              カウント
            </th>
          </tr>
        </thead>
        <tbody>
          {judges.map((judge, i) => (
            <tr key={i} className="border-b border-[#464545]">
              <td className="p-[5px] align-middle">
                {judge.skills.map((skill, j) => (
                  <span key={skill.code}>
                    {j > 0 && <br />}
                    {skill.name}
                  </span>
                ))}
              </td>
              <td
                className={`p-[5px] align-middle ${judge.divineResultWolf ? "text-[#d9534f]" : ""}`}
              >
                {judge.divineResultWolf ? "人狼" : "人間"}
              </td>
              <td
                className={`p-[5px] align-middle ${judge.psychicResultWolf ? "text-[#d9534f]" : ""}`}
              >
                {judge.psychicResultWolf ? "人狼" : "人間"}
              </td>
              <td
                className={`p-[5px] align-middle ${judge.noDeadByAttack ? "text-[#f0ad4e]" : ""}`}
              >
                {judge.noDeadByAttack ? "死亡しない" : "なし"}
              </td>
              <td
                className={`p-[5px] align-middle ${judge.count === "人狼" ? "text-[#d9534f]" : judge.count === "カウントしない" ? "text-[#f0ad4e]" : ""}`}
              >
                {judge.count}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

#!/usr/bin/env node
/**
 * rule/skill.html から役職説明データを抽出し、TypeScript データファイルを生成する。
 * 開発時の一度きりの実行を想定。生成物は `app/features/skills/descriptions.ts`。
 *
 * 出力は code (小文字) → 説明項目リストの Record。
 * 役職名・略称・陣営名は API から取得するため含めない。
 */
import { readFileSync, writeFileSync } from "node:fs";
import { resolve, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const htmlPath = resolve(__dirname, "../../backend/src/main/resources/templates/rule/skill.html");
const outPath = resolve(__dirname, "../app/features/skills/descriptions.ts");

const html = readFileSync(htmlPath, "utf-8");

// Find camp boundaries by their header markers (exclude skill-plan = unimplemented roles)
const campHeaders = [...html.matchAll(/<li id="(\w+)"><h5>(.*?)<\/h5>/g)].filter(
  (m) => m[1] !== "skill-plan",
);
const descriptions = {};
let totalSkills = 0;

for (let i = 0; i < campHeaders.length; i++) {
  const sectionStart = campHeaders[i].index;
  // End at the next camp header OR the skill-plan section, whichever comes first
  const skillPlanIdx = html.indexOf('<li id="skill-plan">', sectionStart + 1);
  const nextCampIdx = i < campHeaders.length - 1 ? campHeaders[i + 1].index : html.length;
  const sectionEnd =
    skillPlanIdx > sectionStart && skillPlanIdx < nextCampIdx ? skillPlanIdx : nextCampIdx;
  const campHtml = html.substring(sectionStart, sectionEnd);

  // Extract skills within each camp section
  const skillHeaders = [...campHtml.matchAll(/<li id="(\w+)">【(.+?)】(.+?)\s*\n/g)];

  for (let j = 0; j < skillHeaders.length; j++) {
    const skillId = skillHeaders[j][1];
    const skillStart = skillHeaders[j].index + skillHeaders[j][0].length;
    const skillEnd = j < skillHeaders.length - 1 ? skillHeaders[j + 1].index : campHtml.length;
    const skillHtml = campHtml.substring(skillStart, skillEnd);

    const items = [];
    const itemRegex = /<li(?:\s[^>]*)?>([\s\S]*?)<\/li>/g;
    let itemMatch;
    while ((itemMatch = itemRegex.exec(skillHtml)) !== null) {
      const content = itemMatch[1].trim();
      if (content.startsWith("<h5>") || content.startsWith("【")) continue;

      const msgMatch = content.match(/<div class="message (message-[\w-]+)">([\s\S]*?)<\/div>/);
      if (msgMatch) {
        const msgType = msgMatch[1];
        const msgText = msgMatch[2]
          .replace(/<br\s*\/?>/g, "\n")
          .split("\n")
          .map((l) => l.replace(/\s+/g, " ").trim())
          .filter(Boolean)
          .join("\n");
        items.push({ type: "message", messageType: msgType, content: msgText });
      } else {
        const text = content
          .replace(/<br\s*\/?>/g, "\n")
          .replace(/<[^>]+>/g, "")
          .split("\n")
          .map((l) => l.replace(/\s+/g, " ").trim())
          .filter(Boolean)
          .join("\n");
        if (text) {
          items.push({ type: "text", content: text });
        }
      }
    }

    descriptions[skillId] = items;
    totalSkills++;
  }
}

// Extract unimplemented skills (skill-plan section)
const unimplementedCamps = [];
const skillPlanStart = html.indexOf('<li id="skill-plan">');
const skillPlanMatch = skillPlanStart >= 0 ? { 1: html.substring(skillPlanStart) } : null;
if (skillPlanMatch) {
  const planHtml = skillPlanMatch[1];
  // Each camp is a <li>campName<ul>..skills..</ul></li>
  const campRegex = /<li>([^<\n]+)\s*<ul>([\s\S]*?)<\/ul>\s*<\/li>/g;
  let campMatch;
  while ((campMatch = campRegex.exec(planHtml)) !== null) {
    const campName = campMatch[1].trim();
    const skillsHtml = campMatch[2];
    const skills = [];
    const skillRegex = /<li>\s*([\s\S]*?)\s*<\/li>/g;
    let sMatch;
    while ((sMatch = skillRegex.exec(skillsHtml)) !== null) {
      const text = sMatch[1]
        .replace(/<[^>]+>/g, "")
        .replace(/\s+/g, " ")
        .trim();
      if (text) skills.push(text);
    }
    if (skills.length > 0) unimplementedCamps.push({ name: campName, skills });
  }
}

// Generate TypeScript
let ts = `/**
 * 役職説明データ (rule/skill.html から自動生成)。
 * 再生成: cd frontend && node scripts/extract-skill-descriptions.mjs
 *
 * code (小文字) → 説明項目リストの Record。
 * 役職名・略称・陣営名は API (SimpleSkillView) から取得するため含めない。
 */

export type DescriptionItem =
  | { type: "text"; content: string }
  | { type: "message"; messageType: string; content: string };

export const skillDescriptions: Record<string, DescriptionItem[]> = `;

ts += JSON.stringify(descriptions, null, 2)
  .replace(/"type": "text"/g, 'type: "text"')
  .replace(/"type": "message"/g, 'type: "message"');

ts += ";\n\n";

ts += `export type UnimplementedCamp = { name: string; skills: string[] };\n\n`;
ts += `export const unimplementedSkills: UnimplementedCamp[] = `;
ts += JSON.stringify(unimplementedCamps, null, 2);
ts += ";\n";

writeFileSync(outPath, ts, "utf-8");
console.log(
  `Generated ${outPath} — ${totalSkills} skills, ${unimplementedCamps.length} unimplemented camps`,
);

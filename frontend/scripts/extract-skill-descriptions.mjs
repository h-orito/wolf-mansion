#!/usr/bin/env node
/**
 * rule/skill.html から役職説明データを抽出し、TypeScript データファイルを生成する。
 * 開発時の一度きりの実行を想定。生成物は `app/features/skills/descriptions.ts`。
 */
import { readFileSync, writeFileSync } from "node:fs";
import { resolve, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const htmlPath = resolve(__dirname, "../../backend/src/main/resources/templates/rule/skill.html");
const outPath = resolve(__dirname, "../app/features/skills/descriptions.ts");

const html = readFileSync(htmlPath, "utf-8");

// Step 1: Find camp boundaries by their header markers
const campHeaders = [...html.matchAll(/<li id="(\w+)"><h5>(.*?)<\/h5>/g)];
const camps = [];

for (let i = 0; i < campHeaders.length; i++) {
  const campId = campHeaders[i][1];
  const campName = campHeaders[i][2];
  const sectionStart = campHeaders[i].index;
  const sectionEnd = i < campHeaders.length - 1 ? campHeaders[i + 1].index : html.length;
  const campHtml = html.substring(sectionStart, sectionEnd);

  // Step 2: Extract skills within each camp section
  // Skills: <li id="xxx">【短】役職名 followed by <ul>...</ul>
  const skillHeaders = [...campHtml.matchAll(/<li id="(\w+)">【(.+?)】(.+?)\s*\n/g)];
  const skills = [];

  for (let j = 0; j < skillHeaders.length; j++) {
    const skillId = skillHeaders[j][1];
    const shortName = skillHeaders[j][2];
    const skillName = skillHeaders[j][3].trim();
    const skillStart = skillHeaders[j].index + skillHeaders[j][0].length;
    const skillEnd = j < skillHeaders.length - 1 ? skillHeaders[j + 1].index : campHtml.length;
    const skillHtml = campHtml.substring(skillStart, skillEnd);

    // Step 3: Extract description items from the skill's <ul>
    const items = [];
    // Match top-level <li> items (not nested ones from message divs)
    // Each item is either plain text or contains a <div class="message ...">
    const itemRegex = /<li(?:\s[^>]*)?>([\s\S]*?)<\/li>/g;
    let itemMatch;
    while ((itemMatch = itemRegex.exec(skillHtml)) !== null) {
      const content = itemMatch[1].trim();

      // Skip nested <ul> markers (camp/skill structure)
      if (content.startsWith("<h5>") || content.startsWith("【")) continue;

      // Check if it's a message box
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

    skills.push({ code: skillId, shortName, name: skillName, items });
  }

  camps.push({ id: campId, name: campName, skills });
}

// Generate TypeScript
let ts = `/**
 * 役職説明データ (rule/skill.html から自動生成)。
 * 再生成: cd frontend && node scripts/extract-skill-descriptions.mjs
 */

export type DescriptionItem =
  | { type: "text"; content: string }
  | { type: "message"; messageType: string; content: string };

export type SkillDescription = {
  code: string;
  shortName: string;
  name: string;
  items: DescriptionItem[];
};

export type CampDescriptions = {
  id: string;
  name: string;
  skills: SkillDescription[];
};

export const skillDescriptions: CampDescriptions[] = `;

ts += JSON.stringify(camps, null, 2)
  .replace(/"type": "text"/g, 'type: "text"')
  .replace(/"type": "message"/g, 'type: "message"');

ts += ";\n";

writeFileSync(outPath, ts, "utf-8");
console.log(
  `Generated ${outPath} — ${camps.length} camps, ${camps.reduce((n, c) => n + c.skills.length, 0)} skills`,
);

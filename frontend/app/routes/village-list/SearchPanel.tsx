import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { ButtonRadioGroup } from "~/components/ui/ButtonRadioGroup";
import { CollapsiblePanel } from "~/components/ui/CollapsiblePanel";
import { FormActions, FormRow } from "~/components/ui/Form";
import { MultiSelect } from "~/components/ui/MultiSelect";
import type { SimpleCharachipView, SimpleSkillView } from "~/features/villages/api";

/** 検索フォームの入力値 (random: null=両方 / true=闇鍋 / false=固定)。 */
export type SearchValue = {
  charachips: number[];
  skills: string[];
  random: boolean | null;
};

const ORGANIZE_OPTIONS = [
  { value: null, label: "両方" },
  { value: true, label: "闇鍋" },
  { value: false, label: "固定" },
] satisfies { value: boolean | null; label: string }[];

/**
 * 村一覧の検索パネル。キャラセット / 役職 (複数選択) と編成で絞り込む。
 * ドラフト入力を内部 state で持ち、「検索」押下で確定値を [onSearch] に渡す。
 */
export function SearchPanel({
  charachips,
  skills,
  initial,
  onSearch,
}: {
  charachips: SimpleCharachipView[];
  skills: SimpleSkillView[];
  initial: SearchValue;
  onSearch: (value: SearchValue) => void;
}) {
  const [charachipSel, setCharachipSel] = useState<number[]>(initial.charachips);
  const [skillSel, setSkillSel] = useState<string[]>(initial.skills);
  const [random, setRandom] = useState<boolean | null>(initial.random);

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch({ charachips: charachipSel, skills: skillSel, random });
  };

  return (
    <CollapsiblePanel title="検索">
      <form onSubmit={onSubmit}>
        <FormRow label="キャラセット" labelWidth="wide">
          <MultiSelect
            ariaLabel="キャラセット"
            options={charachips.map((c) => ({ value: String(c.id), label: c.name }))}
            value={charachipSel.map(String)}
            onChange={(values) => setCharachipSel(values.map(Number))}
          />
        </FormRow>
        <FormRow label="役職" labelWidth="wide">
          <MultiSelect
            ariaLabel="役職"
            options={skills.map((s) => ({ value: s.code, label: s.name }))}
            value={skillSel}
            onChange={setSkillSel}
          />
        </FormRow>
        <FormRow label="編成" labelWidth="wide">
          <ButtonRadioGroup
            ariaLabel="編成"
            options={ORGANIZE_OPTIONS}
            value={random}
            onChange={setRandom}
          />
        </FormRow>
        <FormActions>
          <Button type="submit">検索</Button>
        </FormActions>
      </form>
    </CollapsiblePanel>
  );
}

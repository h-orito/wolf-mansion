import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { CollapsiblePanel } from "~/components/ui/CollapsiblePanel";
import { FormActions, FormRow } from "~/components/ui/Form";
import type { SimpleVillageView } from "~/features/villages/api";

export type SearchValue = {
  tags: string[];
  name: string;
  villageId: number | null;
};

export function SearchPanel({
  allTags,
  villages,
  defaultOpen = true,
  onSearch,
}: {
  allTags: string[];
  villages: SimpleVillageView[];
  defaultOpen?: boolean;
  onSearch: (value: SearchValue) => void;
}) {
  const [selectedTags, setSelectedTags] = useState<string[]>([]);
  const [name, setName] = useState("");
  const [villageId, setVillageId] = useState<number | null>(null);

  const toggleTag = (tag: string) => {
    setSelectedTags((prev) =>
      prev.includes(tag) ? prev.filter((t) => t !== tag) : [...prev, tag],
    );
  };

  const handleSearch = () => {
    onSearch({ tags: selectedTags, name, villageId });
  };

  return (
    <CollapsiblePanel title="検索" defaultOpen={defaultOpen}>
      <div className="mb-[10px]">
        <FormRow label="役職名" labelWidth="wide">
          <input
            type="text"
            className="w-full rounded border-0 bg-white px-[12px] py-[6px] text-[#464545]"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="役職名（部分一致）"
          />
        </FormRow>

        <FormRow label="特徴" labelWidth="wide">
          <div className="leading-[15px]">
            {allTags.map((tag, i) => (
              <span key={tag} className="mb-[5px] inline-block">
                <button
                  type="button"
                  onClick={() => toggleTag(tag)}
                  className={`cursor-pointer rounded-[3px] px-[10px] py-[10px] text-[9px] leading-[9px] text-white ${
                    selectedTags.includes(tag) ? "bg-[#00bc8c]" : "bg-[#464545]"
                  }`}
                >
                  {tag}
                </button>
                {i < allTags.length - 1 && <span className="mx-[2px]"> / </span>}
              </span>
            ))}
          </div>
        </FormRow>

        <FormRow label="村に登場する役職" labelWidth="wide">
          <select
            className="w-full rounded border-0 bg-white px-[12px] py-[6px] text-[#464545]"
            value={villageId ?? ""}
            onChange={(e) => setVillageId(e.target.value ? Number(e.target.value) : null)}
          >
            <option value="">抽出したい場合は選択してください</option>
            {villages.map((v) => (
              <option key={v.id} value={v.id}>
                {v.name}
              </option>
            ))}
          </select>
          <p className="mt-[5px] text-[11px] opacity-80">
            闇鍋の村は選択できません。
            <br />
            状態がプロローグ中/廃村の場合は全人数の編成に含まれる役職で抽出します。
            <br />
            それ以外の場合は、1日目開始時点での編成に含まれる役職で抽出します。
          </p>
        </FormRow>

        <FormActions>
          <Button onClick={handleSearch}>検索</Button>
        </FormActions>
      </div>
    </CollapsiblePanel>
  );
}

import { type ReactNode, useState } from "react";

import type { VillageSearchCandidates } from "~/features/villages/api";

/** 検索フォームの入力値 (編成 random: null=両方 / true=闇鍋 / false=固定)。 */
export type SearchValue = {
  charachips: number[];
  skills: string[];
  random: boolean | null;
};

/**
 * 村一覧画面の検索パネル (:8091 `village-list.html` の Bootstrap collapse「検索」を React state で再現)。
 * **初期は閉**。キャラセット / 役職 (native 複数選択) + 編成 (両方/闇鍋/固定) で絞り込む。
 * ドラフト入力を内部 state で持ち、「検索」押下で確定値を [onSearch] に渡す (legacy の GET submit 相当)。
 */
export function SearchPanel({
  candidates,
  initial,
  onSearch,
}: {
  candidates: VillageSearchCandidates | undefined;
  initial: SearchValue;
  onSearch: (value: SearchValue) => void;
}) {
  const [open, setOpen] = useState(false);
  const [charachips, setCharachips] = useState<number[]>(initial.charachips);
  const [skills, setSkills] = useState<string[]>(initial.skills);
  const [random, setRandom] = useState<boolean | null>(initial.random);

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch({ charachips, skills, random });
  };

  return (
    // `.panel.panel-default`: bg #303030 / border #464545。
    <div className="mb-[15px] rounded border border-[#464545] bg-[#303030]">
      {/* `.panel-heading` (bg #464545) + collapse トグル (白・15px)。 */}
      <div className="rounded-t bg-[#464545] px-[15px] py-[10px]">
        <button
          type="button"
          aria-expanded={open}
          onClick={() => setOpen((v) => !v)}
          className="text-[15px] text-white hover:underline"
        >
          検索
        </button>
      </div>
      {open && (
        <form onSubmit={onSubmit} className="p-[15px]">
          <FormRow label="キャラセット">
            <select
              multiple
              aria-label="キャラセット"
              className="h-[74px] w-full rounded border border-gray-400 bg-white px-[10px] py-[5px] text-[#555555]"
              value={charachips.map(String)}
              onChange={(e) => setCharachips(selectedValues(e).map(Number))}
            >
              {candidates?.charachipList.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
          </FormRow>
          <FormRow label="役職">
            <select
              multiple
              aria-label="役職"
              className="h-[74px] w-full rounded border border-gray-400 bg-white px-[10px] py-[5px] text-[#555555]"
              value={skills}
              onChange={(e) => setSkills(selectedValues(e))}
            >
              {candidates?.skillList.map((s) => (
                <option key={s.code} value={s.code}>
                  {s.name}
                </option>
              ))}
            </select>
          </FormRow>
          <FormRow label="編成">
            <div className="inline-flex overflow-hidden rounded">
              <OrganizeButton selected={random === null} onClick={() => setRandom(null)}>
                両方
              </OrganizeButton>
              <OrganizeButton selected={random === true} onClick={() => setRandom(true)}>
                闇鍋
              </OrganizeButton>
              <OrganizeButton selected={random === false} onClick={() => setRandom(false)}>
                固定
              </OrganizeButton>
            </div>
          </FormRow>
          <div className="text-right">
            <button
              type="submit"
              className="rounded bg-[#00bc8c] px-[9px] py-[5px] text-[13px] text-white hover:opacity-90"
            >
              検索
            </button>
          </div>
        </form>
      )}
    </div>
  );
}

/** `<select multiple>` の選択中 value 配列を取り出す。 */
function selectedValues(e: React.ChangeEvent<HTMLSelectElement>): string[] {
  return Array.from(e.target.selectedOptions, (o) => o.value);
}

/** `.form-horizontal` の 1 行。legacy village-list は label=col-sm-3 / control=col-sm-9。 */
function FormRow({ label, children }: { label: string; children: ReactNode }) {
  return (
    <div className="mb-[15px] flex flex-col min-[768px]:flex-row min-[768px]:items-start">
      <div className="shrink-0 pb-1 min-[768px]:w-1/4 min-[768px]:pt-[7px] min-[768px]:pr-[15px] min-[768px]:pb-0 min-[768px]:text-right">
        {label}
      </div>
      <div className="min-[768px]:w-3/4">{children}</div>
    </div>
  );
}

/** 編成ラジオ (Bootstrap btn-group + btn-success 相当)。選択中は inset で押下表現。 */
function OrganizeButton({
  selected,
  onClick,
  children,
}: {
  selected: boolean;
  onClick: () => void;
  children: ReactNode;
}) {
  return (
    <button
      type="button"
      aria-pressed={selected}
      onClick={onClick}
      className={`border-r border-white/20 bg-[#00bc8c] px-[9px] py-[5px] text-[13px] text-white last:border-r-0 hover:opacity-90 ${
        selected ? "shadow-[inset_0_3px_5px_rgba(0,0,0,0.225)] brightness-90" : ""
      }`}
    >
      {children}
    </button>
  );
}

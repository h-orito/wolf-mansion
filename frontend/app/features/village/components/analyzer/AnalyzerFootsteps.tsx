import { useCallback } from "react";

import type { DayFootstep } from "~/features/village/analyzer/types";
import { TextButton } from "~/components/ui/TextButton";

function FootstepRow({
  fs,
  onChange,
}: {
  fs: DayFootstep;
  onChange: (updated: DayFootstep) => void;
}) {
  return (
    <tr>
      <td className="w-[30px] p-[4px] text-center align-middle">
        <input
          type="checkbox"
          checked={fs.show}
          onChange={(e) => onChange({ ...fs, show: e.target.checked })}
          className="cursor-pointer"
        />
      </td>
      <td className="p-[4px] align-middle text-village-sm" style={{ color: `#${fs.color}` }}>
        {fs.footstep}
      </td>
      <td className="w-[40px] p-[4px] text-center align-middle">
        <input
          type="color"
          value={`#${fs.color}`}
          onChange={(e) => onChange({ ...fs, color: e.target.value.replace("#", "") })}
          className="h-[24px] w-[24px] cursor-pointer border-none bg-transparent p-0"
        />
      </td>
      <td className="w-[120px] p-[4px] align-middle sm:w-[180px]">
        <input
          type="text"
          value={fs.memo}
          onChange={(e) => onChange({ ...fs, memo: e.target.value })}
          placeholder="メモ"
          className="w-full rounded border border-[#464545] bg-[#303030] px-[6px] py-[2px] text-village-sm text-white"
        />
      </td>
    </tr>
  );
}

export function AnalyzerFootsteps({
  footsteps,
  onChange,
}: {
  footsteps: DayFootstep[];
  onChange: (updated: DayFootstep[]) => void;
}) {
  const updateAt = useCallback(
    (index: number, updated: DayFootstep) => {
      const next = footsteps.map((f, i) => (i === index ? updated : f));
      onChange(next);
    },
    [footsteps, onChange],
  );

  const showAll = useCallback(() => {
    onChange(footsteps.map((f) => ({ ...f, show: true })));
  }, [footsteps, onChange]);

  const hideAll = useCallback(() => {
    onChange(footsteps.map((f) => ({ ...f, show: false })));
  }, [footsteps, onChange]);

  const reverseAll = useCallback(() => {
    onChange(footsteps.map((f) => ({ ...f, show: !f.show })));
  }, [footsteps, onChange]);

  if (footsteps.length === 0) {
    return <p className="py-[10px] text-village-sm text-gray-400">足音情報がありません</p>;
  }

  return (
    <div className="pt-[10px] pb-[10px]">
      <div className="mb-[8px] flex gap-[8px] text-village-sm">
        <TextButton onClick={showAll}>全てON</TextButton>
        <TextButton onClick={hideAll}>全てOFF</TextButton>
        <TextButton onClick={reverseAll}>反転</TextButton>
      </div>
      <div className="overflow-x-auto">
        <table className="w-full border-collapse">
          <tbody>
            {footsteps.map((fs, idx) => (
              <FootstepRow key={idx} fs={fs} onChange={(updated) => updateAt(idx, updated)} />
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

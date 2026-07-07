import { useCallback } from "react";

import type { DayFootstep } from "~/features/village/analyzer/types";
import { inlineInputClass } from "~/components/ui/Input";
import { TextButton } from "~/components/ui/TextButton";
import { ColorPicker } from "./ColorPicker";

function FootstepRow({
  fs,
  onChange,
}: {
  fs: DayFootstep;
  onChange: (updated: DayFootstep) => void;
}) {
  return (
    <tr>
      <td className="w-[28px] border border-border p-[3px] text-center align-middle">
        <input
          type="checkbox"
          checked={fs.show}
          onChange={(e) => onChange({ ...fs, show: e.target.checked })}
          className="cursor-pointer"
        />
      </td>
      <td
        className="min-w-[100px] border border-border p-[3px] align-middle text-village-sm whitespace-nowrap"
        style={{ color: `#${fs.color}` }}
      >
        {fs.footstep}
      </td>
      <td className="w-[34px] border border-border p-[3px] text-center align-middle">
        <ColorPicker value={fs.color} onChange={(color) => onChange({ ...fs, color })} />
      </td>
      <td className="border border-border p-[3px] align-middle">
        <input
          type="text"
          value={fs.memo}
          onChange={(e) => onChange({ ...fs, memo: e.target.value })}
          placeholder="メモ"
          className={`w-full ${inlineInputClass}`}
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
    return <p className="py-[5px] text-village-sm text-gray-400">足音情報がありません</p>;
  }

  return (
    <div>
      <div className="mb-[6px] flex items-center gap-[8px]">
        <label className="text-village-sm font-bold text-gray-300">足音</label>
        <TextButton onClick={showAll} className="text-village-sm">
          全ON
        </TextButton>
        <TextButton onClick={hideAll} className="text-village-sm">
          全OFF
        </TextButton>
        <TextButton onClick={reverseAll} className="text-village-sm">
          反転
        </TextButton>
      </div>
      <div>
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

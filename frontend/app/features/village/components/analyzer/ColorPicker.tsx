import { useCallback, useRef, useState } from "react";

const TEMPLATE_COLORS = ["ff0000", "ffaa00", "ffff00", "00ff00", "00aaff", "ff00ff", "ffffff"];

export function ColorPicker({
  value,
  onChange,
}: {
  value: string;
  onChange: (color: string) => void;
}) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [showPalette, setShowPalette] = useState(false);

  const handleNativeChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      onChange(e.target.value.replace("#", ""));
    },
    [onChange],
  );

  return (
    <div className="relative inline-flex items-center gap-[4px]">
      <button
        type="button"
        onClick={() => setShowPalette((v) => !v)}
        className="h-[24px] w-[24px] cursor-pointer rounded border border-[#464545]"
        style={{ backgroundColor: `#${value}` }}
      />
      {showPalette && (
        <div className="absolute top-[30px] left-0 z-10 flex gap-[3px] rounded border border-[#464545] bg-[#303030] p-[6px]">
          {TEMPLATE_COLORS.map((c) => (
            <button
              key={c}
              type="button"
              onClick={() => {
                onChange(c);
                setShowPalette(false);
              }}
              className="h-[20px] w-[20px] cursor-pointer rounded-[2px] border border-[#464545]"
              style={{ backgroundColor: `#${c}` }}
            />
          ))}
          <button
            type="button"
            onClick={() => inputRef.current?.click()}
            className="flex h-[20px] w-[20px] cursor-pointer items-center justify-center rounded-[2px] border border-[#464545] bg-[#404040] text-[10px] text-white"
            title="カスタム色"
          >
            ...
          </button>
        </div>
      )}
      <input
        ref={inputRef}
        type="color"
        value={`#${value}`}
        onChange={handleNativeChange}
        className="absolute h-0 w-0 opacity-0"
        tabIndex={-1}
      />
    </div>
  );
}

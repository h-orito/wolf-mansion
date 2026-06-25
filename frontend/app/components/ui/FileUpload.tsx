import { useCallback, useRef, useState, type ReactNode } from "react";

type FileUploadProps = {
  accept?: string;
  maxSizeBytes?: number;
  onSelect: (file: File | null) => void;
  preview?: ReactNode;
  error?: string | null;
  children?: ReactNode;
};

/**
 * ファイルアップロード。クリックまたはドラッグ＆ドロップで選択できる。
 * 選択後は差し替え・削除が可能。
 */
export function FileUpload({
  accept,
  maxSizeBytes,
  onSelect,
  preview,
  error,
  children,
}: FileUploadProps) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [dragging, setDragging] = useState(false);
  const [fileName, setFileName] = useState<string | null>(null);

  const handleFile = useCallback(
    (file: File | null) => {
      if (file == null) {
        setFileName(null);
        onSelect(null);
        return;
      }
      if (maxSizeBytes != null && (file.size === 0 || file.size > maxSizeBytes)) {
        onSelect(null);
        setFileName(null);
        return;
      }
      setFileName(file.name);
      onSelect(file);
    },
    [maxSizeBytes, onSelect],
  );

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    handleFile(e.target.files?.[0] ?? null);
  };

  const handleDragOver = (e: React.DragEvent) => {
    e.preventDefault();
    setDragging(true);
  };

  const handleDragLeave = (e: React.DragEvent) => {
    e.preventDefault();
    setDragging(false);
  };

  const handleDrop = (e: React.DragEvent) => {
    e.preventDefault();
    setDragging(false);
    const file = e.dataTransfer.files?.[0] ?? null;
    if (file && accept) {
      const accepted = accept.split(",").map((s) => s.trim());
      const match = accepted.some(
        (a) =>
          (a.startsWith(".") && file.name.toLowerCase().endsWith(a.toLowerCase())) ||
          (a.endsWith("/*") && file.type.startsWith(a.replace("/*", "/"))) ||
          file.type === a,
      );
      if (!match) return;
    }
    handleFile(file);
    if (inputRef.current) inputRef.current.value = "";
  };

  const clear = () => {
    setFileName(null);
    onSelect(null);
    if (inputRef.current) inputRef.current.value = "";
  };

  return (
    <div className="space-y-[5px]">
      {children}
      <div
        className={`flex cursor-pointer flex-col items-center rounded border-2 border-dashed px-[10px] py-[15px] text-center transition-colors ${
          dragging
            ? "border-[#00bc8c] bg-[#00bc8c]/10"
            : "border-gray-500 hover:border-gray-400"
        }`}
        onClick={() => inputRef.current?.click()}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      >
        <input
          ref={inputRef}
          type="file"
          accept={accept}
          onChange={handleChange}
          className="hidden"
        />
        {fileName ? (
          <div className="flex items-center gap-[10px]">
            {preview}
            <div>
              <p className="text-[13px]">{fileName}</p>
              <button
                type="button"
                className="mt-[3px] text-[12px] text-[#e74c3c] hover:underline"
                onClick={(e) => {
                  e.stopPropagation();
                  clear();
                }}
              >
                削除
              </button>
            </div>
          </div>
        ) : (
          <p className="text-[13px] text-gray-400">
            クリックまたはドラッグ＆ドロップでファイルを選択
          </p>
        )}
      </div>
      {error && <div className="text-[13px] text-[#e74c3c]">{error}</div>}
    </div>
  );
}

import { useCallback, useEffect, useRef, useState, type ReactNode } from "react";

type FileUploadProps = {
  accept?: string;
  maxSizeBytes?: number;
  maxSizeMessage?: string;
  onSelect: (file: File | null) => void;
  /** 画像プレビューのサイズ (px)。指定すると選択後に画像プレビューを表示する。 */
  imagePreviewSize?: number;
  children?: ReactNode;
};

/**
 * ファイルアップロード。クリックまたはドラッグ＆ドロップで選択できる。
 * 選択後は差し替え・削除が可能。サイズ超過時はエラー表示する。
 */
export function FileUpload({
  accept,
  maxSizeBytes,
  maxSizeMessage,
  onSelect,
  imagePreviewSize,
  children,
}: FileUploadProps) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [dragging, setDragging] = useState(false);
  const [fileName, setFileName] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);

  useEffect(() => {
    return () => {
      if (previewUrl) URL.revokeObjectURL(previewUrl);
    };
  }, [previewUrl]);

  const handleFile = useCallback(
    (file: File | null) => {
      setError(null);
      if (previewUrl) URL.revokeObjectURL(previewUrl);
      if (file == null) {
        setFileName(null);
        setPreviewUrl(null);
        onSelect(null);
        return;
      }
      if (maxSizeBytes != null && (file.size === 0 || file.size > maxSizeBytes)) {
        setError(
          maxSizeMessage ??
            `${Math.floor(maxSizeBytes / 1000)}kByteを超えるファイルはアップロードできません。`,
        );
        setFileName(null);
        setPreviewUrl(null);
        onSelect(null);
        return;
      }
      setFileName(file.name);
      setPreviewUrl(imagePreviewSize ? URL.createObjectURL(file) : null);
      onSelect(file);
    },
    [maxSizeBytes, maxSizeMessage, imagePreviewSize, onSelect, previewUrl],
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
    setError(null);
    setFileName(null);
    if (previewUrl) URL.revokeObjectURL(previewUrl);
    setPreviewUrl(null);
    onSelect(null);
    if (inputRef.current) inputRef.current.value = "";
  };

  return (
    <div className="space-y-[5px]">
      {children}
      <div
        className={`flex cursor-pointer flex-col items-center rounded border-2 border-dashed px-[10px] py-[15px] text-center transition-colors ${
          dragging ? "border-[#00bc8c] bg-[#00bc8c]/10" : "border-gray-500 hover:border-gray-400"
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
            {previewUrl && imagePreviewSize && (
              <img
                src={previewUrl}
                alt="プレビュー"
                width={imagePreviewSize}
                height={imagePreviewSize}
              />
            )}
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

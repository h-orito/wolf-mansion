import { useEffect, useLayoutEffect, useMemo, useRef, useState } from "react";
import type { MyselfSayMessageTypeView, MyselfView } from "./api";
import { useSayMutation } from "./hooks";
import { useSayAnchorSubscription } from "./SayFormContext";
import { Panel, PanelBody, PanelHeading } from "~/components/ui/Panel";

const SECRET_SAY_CODE = "SECRET_SAY";

/**
 * 発言フォーム (Step 12d で旧 Thymeleaf `say-form.html` 相当を React に復元 →
 * Step 13c で旧 BS3 .btn-saytypes / .btn-dark-success のスタイルに揃えた)。
 *
 * - 発言種別タブ (myself.say.availableMessageTypes) → 旧 .btn-saytypes
 * - 表情差分セレクタ (myself.say.selectableFaceTypes、選択中の画像をプレビュー)
 * - 装飾タグボタン (太字 / 取消 / 大 / 小 / ルビ / 隠 / 透 / 色 7 種 / 単発 [[]])
 * - アンカー自動入力 (MessageCard クリック → SayFormContext 経由で `>>N\n` 挿入)
 * - 返信ボタン (秘話なら messageType=SECRET_SAY + 宛先 auto-set)
 * - 秘話宛先セレクタ
 * - 変換無効チェック
 * - 残り回数 / 文字数 / 行数表示 + submit ガード
 */
export function SayForm({
  villageId,
  myself,
}: {
  villageId: number;
  myself: MyselfView;
}) {
  const sayMutation = useSayMutation(villageId);
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  const availableTypes = myself.say.availableMessageTypes;
  const defaultType = useMemo(() => {
    const code = myself.say.defaultMessageTypeCode;
    return availableTypes.find((t) => t.code === code) ?? availableTypes[0];
  }, [myself.say.defaultMessageTypeCode, availableTypes]);

  const [messageTypeCode, setMessageTypeCode] = useState<string>(defaultType?.code ?? "NORMAL_SAY");
  const [text, setText] = useState("");
  const [faceTypeCode, setFaceTypeCode] = useState<string>(
    () => myself.say.selectableFaceTypes[0]?.code ?? "",
  );
  const [convertDisable, setConvertDisable] = useState(false);
  const [secretTargetCharaId, setSecretTargetCharaId] = useState<number | "">("");
  const [pendingCursor, setPendingCursor] = useState<number | null>(null);
  useLayoutEffect(() => {
    if (pendingCursor == null) return;
    const el = textareaRef.current;
    if (!el) return;
    el.focus();
    el.setSelectionRange(pendingCursor, pendingCursor);
    setPendingCursor(null);
  }, [pendingCursor, text]);

  useEffect(() => {
    if (availableTypes.find((t) => t.code === messageTypeCode)) return;
    setMessageTypeCode(defaultType?.code ?? "NORMAL_SAY");
  }, [availableTypes, defaultType, messageTypeCode]);

  useEffect(() => {
    if (myself.say.selectableFaceTypes.find((f) => f.code === faceTypeCode)) return;
    setFaceTypeCode(myself.say.selectableFaceTypes[0]?.code ?? "");
  }, [myself.say.selectableFaceTypes, faceTypeCode]);

  useSayAnchorSubscription((req) => {
    if (req.isSecret) {
      if (availableTypes.find((t) => t.code === SECRET_SAY_CODE)) {
        setMessageTypeCode(SECRET_SAY_CODE);
        if (req.secretTargetCharaId != null) {
          setSecretTargetCharaId(req.secretTargetCharaId);
        }
      }
    }
    const anchorText = `${req.anchorPrefix}${req.messageNumber}\n`;
    const needsLeadingNewline = text.length > 0 && !text.endsWith("\n");
    const newText = needsLeadingNewline ? `${text}\n${anchorText}` : text + anchorText;
    setText(newText);
    setPendingCursor(newText.length);
  });

  const currentTypeInfo = availableTypes.find((t) => t.code === messageTypeCode) ?? defaultType;
  const isSecret = messageTypeCode === SECRET_SAY_CODE;
  const secretSayTargets = myself.say.secretSayTargets;

  const remainingCount = currentTypeInfo?.remainingCount ?? null;
  const maxCount = currentTypeInfo?.maxCount ?? null;
  const maxLength = currentTypeInfo?.maxLength ?? 400;
  const maxLine = currentTypeInfo?.maxLine ?? 20;
  const lineCount = text.length === 0 ? 0 : text.split("\n").length;
  const isOverLength = text.length > maxLength;
  const isOverLine = lineCount > maxLine;
  const hasRemaining = remainingCount == null || remainingCount > 0;
  const needsSecretTarget = isSecret && secretTargetCharaId === "";
  const canSubmit =
    !sayMutation.isPending &&
    text.trim().length > 0 &&
    !isOverLength &&
    !isOverLine &&
    hasRemaining &&
    !needsSecretTarget;

  function insertTag(tag: string, kind: "double" | "single") {
    const el = textareaRef.current;
    if (!el) return;
    const start = el.selectionStart;
    const end = el.selectionEnd;
    const before = text.slice(0, start);
    const selected = text.slice(start, end);
    const after = text.slice(end);
    let inserted: string;
    let nextCursor: number;
    if (kind === "double") {
      inserted = `[[${tag}]]${selected}[[/${tag}]]`;
      nextCursor = selected.length > 0 ? start + inserted.length : start + `[[${tag}]]`.length;
    } else {
      inserted = `[[${tag}]]`;
      nextCursor = start + inserted.length;
    }
    setText(before + inserted + after);
    setPendingCursor(nextCursor);
  }

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (!canSubmit) return;
    sayMutation.mutate(
      {
        message: text,
        messageType: messageTypeCode,
        faceType: faceTypeCode || undefined,
        convertDisable: convertDisable || undefined,
        secretSayTargetCharaId:
          isSecret && typeof secretTargetCharaId === "number" ? secretTargetCharaId : undefined,
      },
      {
        onSuccess: () => {
          setText("");
        },
      },
    );
  }

  return (
    <Panel>
      <PanelHeading>
        <h2 className="text-sm m-0">発言</h2>
      </PanelHeading>
      <PanelBody>
        {/* 発言種別タブ (旧 .btn-saytypes 相当) */}
        <div role="radiogroup" aria-label="発言種別" className="flex flex-wrap gap-[2px] mb-1">
          {availableTypes.map((t) => (
            <MessageTypeButton
              key={t.code}
              type={t}
              active={t.code === messageTypeCode}
              onSelect={() => setMessageTypeCode(t.code)}
            />
          ))}
        </div>

        {/* 秘話宛先 (秘話選択時のみ) */}
        {isSecret && (
          <div className="mb-2">
            <label className="block text-[0.95em] mb-1">秘話相手</label>
            <select
              value={secretTargetCharaId}
              onChange={(e) =>
                setSecretTargetCharaId(e.target.value === "" ? "" : Number(e.target.value))
              }
              className="w-full bg-night-900 border border-night-700 rounded-[3px] px-2 py-1 text-[1em]"
            >
              <option value="">秘話相手を選択してください</option>
              {secretSayTargets.map((t) => (
                <option key={t.charaId} value={t.charaId}>
                  {t.name}
                </option>
              ))}
            </select>
          </div>
        )}

        {/* 装飾タグ */}
        <DecorationTagBar onInsert={insertTag} />

        <form onSubmit={submit} className="space-y-2">
          <div className="flex gap-2">
            {/* キャラ画像プレビュー + 表情差分セレクタ */}
            {myself.say.selectableFaceTypes.length > 0 && (
              <div className="flex flex-col items-stretch shrink-0 w-[8em]">
                <img
                  src={
                    myself.say.selectableFaceTypes.find((f) => f.code === faceTypeCode)?.url ??
                    myself.say.selectableFaceTypes[0]?.url
                  }
                  alt={faceTypeCode}
                  loading="lazy"
                  className="border border-night-700 max-w-full"
                  style={{ maxHeight: 120, width: "auto", height: "auto" }}
                />
                <select
                  value={faceTypeCode}
                  onChange={(e) => setFaceTypeCode(e.target.value)}
                  className="mt-1 bg-night-900 border border-night-700 rounded-[3px] px-1 py-0.5 text-[0.95em]"
                >
                  {myself.say.selectableFaceTypes.map((f) => (
                    <option key={f.code} value={f.code}>
                      {f.name}
                    </option>
                  ))}
                </select>
              </div>
            )}

            <textarea
              ref={textareaRef}
              value={text}
              onChange={(e) => setText(e.target.value)}
              rows={6}
              placeholder={`発言を入力 (${maxLength} 文字以内)`}
              className="flex-1 bg-night-900 border border-night-700 rounded-[3px] px-2 py-1.5 text-[1em] focus:outline-none focus:border-mint-500"
              disabled={sayMutation.isPending}
            />
          </div>

          {/* 文字数 / 行数 / 残り回数 (旧画面では右下に並んでいた) */}
          <div className="flex flex-wrap items-center gap-x-3 gap-y-1 text-[0.95em]">
            <span className={isOverLength ? "text-blood-500" : ""}>
              文字数 {text.length} / {maxLength}
            </span>
            <span className={isOverLine ? "text-blood-500" : ""}>
              行数 {lineCount} / {maxLine}
            </span>
            {remainingCount != null && maxCount != null && (
              <span className={remainingCount === 0 ? "text-blood-500" : ""}>
                残り {remainingCount} / {maxCount} 回
              </span>
            )}
            <label className="ml-auto flex items-center gap-1 cursor-pointer">
              <input
                type="checkbox"
                checked={convertDisable}
                onChange={(e) => setConvertDisable(e.target.checked)}
                className="accent-mint-600"
              />
              装飾・変換無効
            </label>
          </div>

          <div className="flex items-center gap-3">
            <button
              type="submit"
              disabled={!canSubmit}
              className="px-[9px] py-[6px] rounded-[3px] border-2 border-bs-success-600 bg-bs-success-500 text-white text-[13px] hover:bg-bs-success-700 hover:border-bs-success-700 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {sayMutation.isPending ? "送信中..." : submitLabel(messageTypeCode)}
            </button>
            {sayMutation.isError && (
              <span className="text-[0.95em] text-blood-500">{sayMutation.error.message}</span>
            )}
          </div>
        </form>
      </PanelBody>
    </Panel>
  );
}

function MessageTypeButton({
  type,
  active,
  onSelect,
}: {
  type: MyselfSayMessageTypeView;
  active: boolean;
  onSelect: () => void;
}) {
  // 旧 .btn-saytypes .btn-success: dark bg #222222 + mint border + mint text
  // active: mint bg + white text
  const base =
    "px-[9px] py-[5px] rounded-[3px] border border-mint-600 text-[0.95em] cursor-pointer transition-colors duration-100";
  const cls = active
    ? `${base} bg-mint-600 text-white`
    : `${base} bg-night-500 text-mint-600 hover:bg-mint-600 hover:text-white`;
  return (
    <button type="button" role="radio" aria-checked={active} onClick={onSelect} className={cls}>
      {type.name}
    </button>
  );
}

/**
 * 旧 say-form.html `data-tag-type=double / single` ボタン群。
 * クリックで textarea の選択範囲を `[[<tag>]]...[[/<tag>]]` でラップ (色タグは tag に
 * `#ff0000` 等が入る)。`single` は `[[]]` を 1 つだけ挿入する。
 */
function DecorationTagBar({
  onInsert,
}: {
  onInsert: (tag: string, kind: "double" | "single") => void;
}) {
  return (
    <div className="flex flex-wrap gap-1 mb-2">
      <TagBtn label="B" tag="b" kind="double" onInsert={onInsert} bold />
      <TagBtn label="S" tag="s" kind="double" onInsert={onInsert} strike />
      <TagBtn label="大" tag="large" kind="double" onInsert={onInsert} />
      <TagBtn label="小" tag="small" kind="double" onInsert={onInsert} />
      <TagBtn label="rb" tag="ruby" kind="double" onInsert={onInsert} />
      <TagBtn label="隠" tag="cw" kind="double" onInsert={onInsert} />
      <TagBtn label="透" tag="tp" kind="double" onInsert={onInsert} />
      {["#ff0000", "#ff8800", "#dddd00", "#00dd00", "#00dddd", "#0000ff", "#ee00ee"].map((c) => (
        <ColorTagBtn key={c} color={c} onInsert={onInsert} />
      ))}
      <button
        type="button"
        onClick={() => onInsert("", "single")}
        className="px-2 py-0.5 rounded-[3px] border border-night-700 bg-night-900 text-[0.95em] hover:border-mint-500"
        title="[[]] を挿入"
      >
        [[]]
      </button>
    </div>
  );
}

function TagBtn({
  label,
  tag,
  kind,
  onInsert,
  bold,
  strike,
}: {
  label: string;
  tag: string;
  kind: "double" | "single";
  onInsert: (tag: string, kind: "double" | "single") => void;
  bold?: boolean;
  strike?: boolean;
}) {
  return (
    <button
      type="button"
      onClick={() => onInsert(tag, kind)}
      className={`px-2 py-0.5 rounded-[3px] border border-night-700 bg-night-900 text-[0.95em] hover:border-mint-500 ${
        bold ? "font-bold" : ""
      } ${strike ? "line-through" : ""}`}
      title={`[[${tag}]]...[[/${tag}]]`}
    >
      {label}
    </button>
  );
}

function ColorTagBtn({
  color,
  onInsert,
}: {
  color: string;
  onInsert: (tag: string, kind: "double" | "single") => void;
}) {
  return (
    <button
      type="button"
      onClick={() => onInsert(color, "double")}
      className="w-6 h-6 rounded-[3px] border border-night-700 hover:border-night-300"
      style={{ backgroundColor: color }}
      title={`[[${color}]]...[[/${color}]]`}
      aria-label={`色タグ ${color}`}
    />
  );
}

function submitLabel(typeCode: string): string {
  switch (typeCode) {
    case "WEREWOLF_SAY":
      return "囁く";
    case "MASON_SAY":
      return "発言する (共鳴)";
    case "LOVERS_SAY":
      return "発言する (恋人)";
    case "TELEPATHY":
      return "念話する";
    case "MONOLOGUE_SAY":
      return "つぶやく";
    case "SECRET_SAY":
      return "秘話する";
    case "GRAVE_SAY":
      return "呻く";
    case "SPECTATE_SAY":
      return "見学発言";
    case "ACTION":
      return "アクション";
    default:
      return "発言する";
  }
}

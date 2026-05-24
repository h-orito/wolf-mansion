import { useEffect, useLayoutEffect, useMemo, useRef, useState } from "react";
import type { MyselfSayMessageTypeView, MyselfView } from "./api";
import { useSayMutation } from "./hooks";
import { useSayAnchorSubscription } from "./SayFormContext";

const SECRET_SAY_CODE = "SECRET_SAY";

/**
 * 発言フォーム (Step 12d で旧 Thymeleaf `say-form.html` 相当を React に復元)。
 *
 * - 発言種別タブ (myself.say.availableMessageTypes)
 * - 表情差分セレクタ (myself.say.selectableFaceTypes、選択中の画像をプレビュー)
 * - 装飾タグボタン (二重タグ / 色タグ / 単発タグ)
 * - アンカー自動入力 (MessageCard クリック → SayFormContext 経由で `>>N\n` 挿入)
 * - 返信ボタン (秘話なら messageType=SECRET_SAY + 宛先 auto-set)
 * - 秘話宛先セレクタ
 * - 変換無効チェック
 * - 残り回数 / 文字数表示 + submit ガード
 *
 * 表示判定 (= 親側で SayForm 自体を出すか) は `myself.say.isAvailableSay && availableMessageTypes 非空`
 * を呼び出し側でチェックしてもらう前提。
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
  // テキスト挿入後にカーソル位置を確実に DOM へ反映するため、React の commit 後に
  // 動く `useLayoutEffect` で setSelectionRange する。`requestAnimationFrame` だと
  // concurrent mode 下で稀に setText の commit より前に走り、カーソル位置が
  // ずれることがあるため避ける。
  const [pendingCursor, setPendingCursor] = useState<number | null>(null);
  useLayoutEffect(() => {
    if (pendingCursor == null) return;
    const el = textareaRef.current;
    if (!el) return;
    el.focus();
    el.setSelectionRange(pendingCursor, pendingCursor);
    setPendingCursor(null);
  }, [pendingCursor, text]);

  // myself が更新されて (= 役職判明 / 死亡 等) 現在の messageTypeCode が消えたら
  // デフォルトに切り替える。発言種別を変えた選択は維持したいので、まだ available
  // ならそのまま残す。
  useEffect(() => {
    if (availableTypes.find((t) => t.code === messageTypeCode)) return;
    setMessageTypeCode(defaultType?.code ?? "NORMAL_SAY");
  }, [availableTypes, defaultType, messageTypeCode]);

  // 表情差分も同様: 現選択が消えたら先頭に戻す
  useEffect(() => {
    if (myself.say.selectableFaceTypes.find((f) => f.code === faceTypeCode)) return;
    setFaceTypeCode(myself.say.selectableFaceTypes[0]?.code ?? "");
  }, [myself.say.selectableFaceTypes, faceTypeCode]);

  // アンカー / 返信を受け取って textarea 末尾に挿入
  useSayAnchorSubscription((req) => {
    if (req.isSecret) {
      // 秘話への返信: messageType を SECRET_SAY に切替、宛先も auto-set
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

  // 残り回数: 制限ありで remainingCount == 0 のときは disable
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
      // 選択あり → 挿入後の選択範囲末尾、無し → 開きタグ直後
      nextCursor = selected.length > 0 ? start + inserted.length : start + `[[${tag}]]`.length;
    } else {
      // 単発タグ ([[...]] を 1 つだけ挿入。`tag` が空なら `[[]]` を挿入する旧画面踏襲)
      inserted = `[[${tag}]]`;
      nextCursor = start + inserted.length;
    }
    setText(before + inserted + after);
    // commit 後に確実に setSelectionRange するため useLayoutEffect 経由で位置反映
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
          // 秘話宛先は連続で同じ相手に送ることが多いので維持する (旧画面踏襲)
        },
      },
    );
  }

  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-2">発言</h2>

      {/* 発言種別タブ */}
      <div role="radiogroup" aria-label="発言種別" className="flex flex-wrap gap-1 mb-2">
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
          <label className="block text-xs text-slate-400 mb-1">秘話相手</label>
          <select
            value={secretTargetCharaId}
            onChange={(e) =>
              setSecretTargetCharaId(e.target.value === "" ? "" : Number(e.target.value))
            }
            className="w-full bg-slate-900 border border-slate-700 rounded px-2 py-1 text-sm text-slate-100"
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
            <div className="flex flex-col items-stretch shrink-0 w-32">
              <img
                src={
                  myself.say.selectableFaceTypes.find((f) => f.code === faceTypeCode)?.url ??
                  myself.say.selectableFaceTypes[0]?.url
                }
                alt={faceTypeCode}
                loading="lazy"
                className="rounded border border-slate-700 max-w-full"
                style={{ maxHeight: 120, width: "auto", height: "auto" }}
              />
              <select
                value={faceTypeCode}
                onChange={(e) => setFaceTypeCode(e.target.value)}
                className="mt-1 bg-slate-900 border border-slate-700 rounded px-1 py-0.5 text-xs text-slate-100"
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
            className="flex-1 bg-slate-900 border border-slate-700 rounded px-3 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500"
            disabled={sayMutation.isPending}
          />
        </div>

        {/* 文字数 / 行数 / 残り回数 */}
        <div className="flex flex-wrap items-center gap-x-3 gap-y-1 text-xs text-slate-400">
          <span className={isOverLength ? "text-rose-300" : ""}>
            文字数 {text.length} / {maxLength}
          </span>
          <span className={isOverLine ? "text-rose-300" : ""}>
            行数 {lineCount} / {maxLine}
          </span>
          {remainingCount != null && maxCount != null && (
            <span className={remainingCount === 0 ? "text-rose-300" : ""}>
              残り {remainingCount} / {maxCount} 回
            </span>
          )}
          <label className="ml-auto flex items-center gap-1 text-slate-300 cursor-pointer">
            <input
              type="checkbox"
              checked={convertDisable}
              onChange={(e) => setConvertDisable(e.target.checked)}
              className="accent-indigo-500"
            />
            装飾・変換無効
          </label>
        </div>

        <div className="flex items-center gap-3">
          <button
            type="submit"
            disabled={!canSubmit}
            className="rounded bg-indigo-500 hover:bg-indigo-400 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium"
          >
            {sayMutation.isPending ? "送信中..." : submitLabel(messageTypeCode)}
          </button>
          {sayMutation.isError && (
            <span className="text-xs text-rose-300">{sayMutation.error.message}</span>
          )}
        </div>
      </form>
    </section>
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
  return (
    <button
      type="button"
      role="radio"
      aria-checked={active}
      onClick={onSelect}
      className={`px-2 py-1 rounded border text-xs ${
        active
          ? "bg-indigo-500 border-indigo-400 text-white"
          : "bg-slate-900 border-slate-700 text-slate-300 hover:border-slate-500"
      }`}
    >
      {type.name}
    </button>
  );
}

/**
 * 旧 say-form.html の `data-tag-type=double / single` ボタン群を Tailwind に移植。
 * クリックで textarea の選択範囲を `[[<tag>]]...[[/<tag>]]` でラップ (色タグは tag に
 * `#ff0000` 等が入る)。`single` は `[[]]` を 1 つ挿入するだけ。
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
      {/* 単発 [[]] (旧画面の data-tag-type=single, data-tag-name=""): カーソル位置に
          [[]] を挿入。ランダムタグ等を後から書く起点として使う */}
      <button
        type="button"
        onClick={() => onInsert("", "single")}
        className="px-2 py-0.5 rounded border bg-slate-900 border-slate-700 text-slate-300 hover:border-slate-500 text-xs"
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
      className={`px-2 py-0.5 rounded border bg-slate-900 border-slate-700 text-slate-300 hover:border-slate-500 text-xs ${
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
      className="w-6 h-6 rounded border border-slate-700 hover:border-slate-400"
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
      // アクション発言は専用 UI (旧 ActionPanel 相当) が未実装で、本 SayForm の
      // 種別タブには通常出ない想定。万一 backend が含めて返した場合は汎用ラベルで動く
      return "アクション";
    default:
      return "発言する";
  }
}


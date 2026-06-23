import { useEffect, useRef, useState } from "react";
import { Link } from "react-router";

import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { selectClass } from "~/components/ui/Input";
import { MESSAGE_STYLES } from "~/components/ui/messageStyles";
import type {
  ParticipantSituationView,
  VillageDetailView,
  VillageSayRequest,
} from "~/features/village/api";
import { useDisplaySettings } from "~/features/village/displaySettings";
import { MessageCard, type ReplyDraft } from "../message/MessageCard";
export type { ReplyDraft };

/** 発言種別ラジオの表示順とラベル (フォーム上の並び)。 */
const SAY_TYPE_ORDER: { code: string; label: string }[] = [
  { code: "WEREWOLF_SAY", label: "囁き" },
  { code: "MASON_SAY", label: "共鳴" },
  { code: "LOVERS_SAY", label: "恋人" },
  { code: "TELEPATHY", label: "念話" },
  { code: "NORMAL_SAY", label: "通常" },
  { code: "GRAVE_SAY", label: "呻き" },
  { code: "SPECTATE_SAY", label: "見学" },
  { code: "MONOLOGUE_SAY", label: "独り言" },
  { code: "SECRET_SAY", label: "秘話" },
];

/** 発言種別 → textarea の背景色 (吹き出しと同じ配色)。 */
const TYPE_TO_STYLE_KEY: Record<string, string> = {
  WEREWOLF_SAY: "message-werewolf",
  MASON_SAY: "message-mason",
  LOVERS_SAY: "message-lover",
  TELEPATHY: "message-telepathy",
  MONOLOGUE_SAY: "message-monologue",
  SECRET_SAY: "message-secret",
  GRAVE_SAY: "message-grave",
  SPECTATE_SAY: "message-spectate",
};

/** 発言種別 → 既定の表情種別コード。 */
const TYPE_TO_FACE: Record<string, string> = {
  NORMAL_SAY: "NORMAL",
  WEREWOLF_SAY: "WEREWOLF",
  MASON_SAY: "MASON",
  LOVERS_SAY: "LOVER",
  TELEPATHY: "SECRET",
  MONOLOGUE_SAY: "MONOLOGUE",
  SECRET_SAY: "SECRET",
  GRAVE_SAY: "GRAVE",
  SPECTATE_SAY: "NORMAL",
};

/** 装飾タグ (選択範囲を囲む)。 */
const DECORATION_TAGS: { name: string; label: string; color?: string }[] = [
  { name: "b", label: "B" },
  { name: "s", label: "S" },
  { name: "large", label: "大" },
  { name: "small", label: "小" },
  { name: "ruby", label: "rb" },
  { name: "cw", label: "隠" },
  { name: "tp", label: "透" },
  { name: "#ff0000", label: "■", color: "#ff0000" },
  { name: "#ff8800", label: "■", color: "#ff8800" },
  { name: "#dddd00", label: "■", color: "#dddd00" },
  { name: "#00dd00", label: "■", color: "#00dd00" },
  { name: "#00dddd", label: "■", color: "#00dddd" },
  { name: "#0000ff", label: "■", color: "#0000ff" },
  { name: "#ee00ee", label: "■", color: "#ee00ee" },
];

const RANDOM_TAGS = ["fortune", "1d6", "or", "who", "allwho", "gwho"];

/**
 * 発言フォーム。種別選択 / 表情 / 装飾 / 文字数・行数・残数のリアルタイム表示 / 確認フロー。
 * 制限超過でも入力はできるが確認ボタンを無効にする。
 */
export function SayPanel({
  village,
  mySituation,
  randomKeywords,
  reply,
  onClearReply,
  onConfirm,
}: {
  village: VillageDetailView;
  mySituation: ParticipantSituationView;
  randomKeywords: string[];
  reply: ReplyDraft | null;
  onClearReply: () => void;
  /** 確認画面へ (リクエスト内容を親へ渡し、プレビュー取得は親が行う) */
  onConfirm: (request: VillageSayRequest) => void;
}) {
  const say = mySituation.say;
  const myself = mySituation.myself;
  const showDecorationButtons = useDisplaySettings((s) => s.showDecorationButtons);
  const selectable = say.selectableMessageTypeList ?? [];
  const images = say.selectableCharaImageList ?? [];

  const defaultType = say.defaultMessageTypeCode ?? selectable[0]?.messageTypeCode ?? "NORMAL_SAY";
  const [messageType, setMessageType] = useState(defaultType);
  const [message, setMessage] = useState("");
  const [faceType, setFaceType] = useState<string>(
    () =>
      faceTypeFor(
        defaultType,
        images.map((i) => i.faceTypeCode),
      ) ??
      images[0]?.faceTypeCode ??
      "NORMAL",
  );
  const [convertDisable, setConvertDisable] = useState(false);
  const [secretTargetCharaId, setSecretTargetCharaId] = useState<string>("");
  const [faceModalOpen, setFaceModalOpen] = useState(false);
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  // 返信・秘話返信からの引き継ぎ (アンカー挿入 / 種別・宛先の切替)
  useEffect(() => {
    if (reply == null) return;
    if (reply.secretTargetCharaId != null) {
      changeType("SECRET_SAY");
      setSecretTargetCharaId(String(reply.secretTargetCharaId));
    } else if (reply.anchorText != null) {
      insertAtCursor(`${reply.anchorText}\n`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [reply]);

  const current = selectable.find((t) => t.messageTypeCode === messageType);
  const restrict = current?.restrict;
  const secretTargets =
    selectable.find((t) => t.messageTypeCode === "SECRET_SAY")?.targetList ?? [];

  const length = message.length;
  const lineCount = message.split("\n").length;
  const maxLength = restrict?.maxLength ?? 400;
  const maxLine = restrict?.maxLine ?? 20;
  const leftCount = restrict?.remainingCount ?? null;
  const maxCount = restrict?.maxCount ?? null;

  const overLimit =
    length > maxLength || lineCount > maxLine || (leftCount != null && leftCount <= 0);
  const submitDisabled =
    overLimit ||
    message.trim().length === 0 ||
    (messageType === "SECRET_SAY" && secretTargetCharaId === "");

  function faceTypeFor(type: string, codes: string[]): string | null {
    const candidate = TYPE_TO_FACE[type];
    return candidate != null && codes.includes(candidate) ? candidate : null;
  }

  const changeType = (type: string) => {
    setMessageType(type);
    const face = faceTypeFor(
      type,
      images.map((i) => i.faceTypeCode),
    );
    if (face != null) setFaceType(face);
  };

  const insertAtCursor = (text: string, wrap?: { close: string }) => {
    const textarea = textareaRef.current;
    if (textarea == null) {
      setMessage((prev) => prev + text);
      return;
    }
    const start = textarea.selectionStart;
    const end = textarea.selectionEnd;
    const value = textarea.value;
    const selected = value.slice(start, end);
    const inserted = wrap == null ? text : `${text}${selected}${wrap.close}`;
    const next = value.slice(0, start) + inserted + value.slice(wrap == null ? start : end);
    setMessage(next);
    requestAnimationFrame(() => {
      textarea.focus();
      const cursor = start + inserted.length;
      textarea.setSelectionRange(cursor, cursor);
    });
  };

  const addDecoration = (name: string) => {
    if (name === "ruby") {
      insertAtCursor("[[ruby]]", { close: "[[rt]][[/rt]][[/ruby]]" });
    } else if (name.startsWith("#")) {
      insertAtCursor(`[[${name}]]`, { close: "[[/#]]" });
    } else {
      insertAtCursor(`[[${name}]]`, { close: `[[/${name}]]` });
    }
  };

  const faceImage = images.find((i) => i.faceTypeCode === faceType) ?? images[0];
  const styleKey = TYPE_TO_STYLE_KEY[messageType];
  const textareaStyle = styleKey != null ? MESSAGE_STYLES[styleKey] : "bg-white text-[#555]";

  const submit = () => {
    onConfirm({
      message,
      messageType,
      faceType,
      convertDisable,
      secretSayTargetCharaId:
        messageType === "SECRET_SAY" && secretTargetCharaId !== ""
          ? Number(secretTargetCharaId)
          : null,
    });
  };

  return (
    <Panel title="発言" storageKey="sayform" fixable>
      <div>
        {myself?.dead.isDead && (
          <ul className="mb-[10px] list-disc rounded border border-[#3498db] py-[9px] pr-[9px] pl-[25px] text-[#3498db]">
            <li>あなたは死亡しました。現世の思い出を語り合いましょう。</li>
            {!village.setting.rule.isVisibleGraveSpectateMessage && (
              <li>墓下では推理発言やCOを行っても問題ありません。</li>
            )}
          </ul>
        )}
        {(!myself?.dead.isDead ||
          (myself?.dead.isDead && village.setting.rule.isVisibleGraveSpectateMessage)) && (
          <ul className="mb-[10px] list-disc rounded border border-[#f39c12] py-[9px] pr-[9px] pl-[25px] text-[#f39c12]">
            {village.setting.rule.isVisibleGraveSpectateMessage && (
              <>
                <li>この村は、墓下や見学発言を生存者が参照できます。</li>
                <li>
                  進行中は、推理、まとめ、および推理に繋がる内容は生存者全員が見られる発言種別で発言しないでください。
                </li>
              </>
            )}
            {!village.setting.rule.isVisibleGraveSpectateMessage && (
              <li>
                進行中は、推理、まとめ、および推理に繋がる内容は通常発言で発言しないでください。
              </li>
            )}
            <li>
              COおよび能力行使結果の発表は生存中の導師と探偵のみ行うことができます。騙りCOも禁止です。
            </li>
          </ul>
        )}

        {myself != null && <p className="mb-[10px]">{myself.name}</p>}

        {/* 発言種別 */}
        <div className="flex flex-wrap">
          {SAY_TYPE_ORDER.filter((t) => selectable.some((s) => s.messageTypeCode === t.code)).map(
            (t) => {
              const active = messageType === t.code;
              return (
                <button
                  key={t.code}
                  type="button"
                  className={`not-first:-ml-px cursor-pointer border border-[#00bc8c] px-[9px] py-[5px] first:rounded-l-[3px] last:rounded-r-[3px] hover:opacity-90 ${
                    active
                      ? "bg-[#00bc8c] text-white shadow-[inset_0_3px_5px_rgba(0,0,0,0.125)]"
                      : "bg-[#222222] text-[#00bc8c]"
                  }`}
                  onClick={() => changeType(t.code)}
                >
                  {t.label}
                </button>
              );
            },
          )}
        </div>

        {/* 秘話相手 */}
        {messageType === "SECRET_SAY" && (
          <div className="mt-[10px]">
            <select
              className={selectClass}
              value={secretTargetCharaId}
              onChange={(e) => setSecretTargetCharaId(e.target.value)}
              aria-label="秘話相手"
            >
              <option value="">選択してください</option>
              {secretTargets.map((target) => (
                <option key={target.charaId} value={target.charaId}>
                  {target.name}
                </option>
              ))}
            </select>
          </div>
        )}

        {/* 装飾タグ・ランダム機能 (表示設定でまとめて非表示にできる) */}
        <div
          className={`mt-[5px] flex-wrap items-center gap-y-[5px] ${
            showDecorationButtons ? "flex" : "hidden"
          }`}
        >
          <div className="flex flex-wrap">
            {DECORATION_TAGS.map((tag) => (
              <button
                key={tag.name}
                type="button"
                className="not-first:-ml-px cursor-pointer border border-[#00bc8c] bg-[#222222] px-[9px] py-[5px] text-[#00bc8c] first:rounded-l-[3px] last:rounded-r-[3px] hover:opacity-90"
                style={tag.color != null ? { color: tag.color } : undefined}
                onClick={() => addDecoration(tag.name)}
              >
                {tag.name === "s" ? <s>あ</s> : tag.label}
              </button>
            ))}
            <button
              type="button"
              className="not-first:-ml-px cursor-pointer border border-[#00bc8c] bg-[#222222] px-[9px] py-[5px] text-[#00bc8c] first:rounded-l-[3px] last:rounded-r-[3px] hover:opacity-90"
              onClick={() => insertAtCursor("[[]]")}
            >
              [[]]
            </button>
          </div>
          <RandomTagSelect
            keywords={randomKeywords}
            onAdd={(tag) => insertAtCursor(`[[${tag}]]`)}
          />
        </div>

        {/* 表情 + 本文 */}
        <div className="mt-[10px] flex">
          <div>
            {faceImage != null && (
              <img
                src={faceImage.url}
                alt={faceImage.faceTypeName}
                width={myself?.chara.size.width ?? 60}
                height={myself?.chara.size.height ?? 77}
                className="cursor-pointer"
                onClick={() => setFaceModalOpen(true)}
              />
            )}
            <select
              className={`${selectClass} mt-[5px]`}
              style={{ maxWidth: myself?.chara.size.width ?? 80 }}
              value={faceType}
              onChange={(e) => setFaceType(e.target.value)}
              aria-label="表情"
            >
              {images.map((image) => (
                <option key={image.faceTypeCode} value={image.faceTypeCode}>
                  {image.faceTypeName}
                </option>
              ))}
            </select>
          </div>
          <textarea
            ref={textareaRef}
            className={`ml-[5px] min-h-[150px] flex-1 rounded border border-[#464545] p-[9px] ${textareaStyle}`}
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            aria-label="発言"
          />
        </div>

        {/* 文字数・行数・残数 / 装飾・変換無効 / 確認画面へ */}
        <div className="mt-[5px]" style={{ marginLeft: (myself?.chara.size.width ?? 55) + 5 }}>
          <span className={overLimit ? "text-[#e74c3c]" : ""}>
            {leftCount != null && maxCount != null && `残り${leftCount}/${maxCount}回, `}
            文字数: {length}/{maxLength}, 行数: {lineCount}/{maxLine}
          </span>
          <br />
          <label className="flex cursor-pointer items-center gap-[5px]">
            <input
              type="checkbox"
              checked={convertDisable}
              onChange={() => setConvertDisable(!convertDisable)}
            />
            装飾・変換無効
          </label>
          <div className="mt-[5px] flex justify-end">
            <Button onClick={submit} disabled={submitDisabled}>
              確認画面へ
            </Button>
          </div>
        </div>
        <div className="mt-[10px] flex justify-end">
          <Link to="/rule#other" target="_blank" className="text-wm-accent hover:underline">
            文字装飾・ランダム機能
          </Link>
        </div>

        {/* 返信元の引用 */}
        {reply != null && (
          <div className="mt-[5px] rounded border border-[#ffff00] bg-[#303030] p-[10px]">
            <div className="mb-[5px] flex justify-end">
              <Button variant="default" size="xs" onClick={onClearReply}>
                ×
              </Button>
            </div>
            <MessageCard
              villageId={village.id}
              message={reply.message}
              randomKeywords={randomKeywords}
            />
          </div>
        )}
      </div>
      {faceModalOpen && (
        <div
          className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto bg-black/50 p-4"
          onClick={() => setFaceModalOpen(false)}
        >
          <div
            className="my-8 w-full max-w-lg rounded-[6px] border border-black/20 bg-[#303030] p-[15px] text-white shadow-lg"
            onClick={(e) => e.stopPropagation()}
          >
            <h4 className="mb-[10px] font-bold">表情選択</h4>
            <div className="flex flex-wrap gap-[5px]">
              {images.map((image) => (
                <div
                  key={image.faceTypeCode}
                  className="inline-block border border-[#464545] p-[5px] text-center"
                >
                  <img
                    src={image.url}
                    alt={image.faceTypeName}
                    width={myself?.chara.size.width ?? 60}
                    height={myself?.chara.size.height ?? 77}
                  />
                  <div>{image.faceTypeName}</div>
                  <Button
                    size="xs"
                    onClick={() => {
                      setFaceType(image.faceTypeCode);
                      setFaceModalOpen(false);
                    }}
                  >
                    選択
                  </Button>
                </div>
              ))}
            </div>
            <div className="mt-[10px] flex justify-end">
              <Button variant="default" onClick={() => setFaceModalOpen(false)}>
                閉じる
              </Button>
            </div>
          </div>
        </div>
      )}
    </Panel>
  );
}

function RandomTagSelect({
  keywords,
  onAdd,
}: {
  keywords: string[];
  onAdd: (tag: string) => void;
}) {
  const [selected, setSelected] = useState(RANDOM_TAGS[0]);
  return (
    <span className="inline-flex items-center gap-[5px]">
      <select
        className={selectClass}
        value={selected}
        onChange={(e) => setSelected(e.target.value)}
        aria-label="ランダム機能"
      >
        {RANDOM_TAGS.map((tag) => (
          <option key={tag} value={tag}>
            {tag}
          </option>
        ))}
        {keywords.map((keyword) => (
          <option key={keyword} value={keyword}>
            {keyword}
          </option>
        ))}
      </select>
      <Button size="sm" onClick={() => onAdd(selected)}>
        <span className="whitespace-nowrap">タグ追加</span>
      </Button>
    </span>
  );
}

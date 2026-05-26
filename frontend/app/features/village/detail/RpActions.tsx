import { useEffect, useRef, useState } from "react";
import type { MyselfFaceTypeView, MyselfView, VillageView } from "./api";
import {
  useAddFaceTypeMutation,
  useChangeNameMutation,
  useFaceTypesMutation,
  useFaceTypesQuery,
  useMemoMutation,
} from "./hooks";
import { Panel, PanelBody, PanelHeading } from "~/components/ui/Panel";

/**
 * RP 系 (キャラ名 / 簡易メモ / 表情差分) の編集パネル。
 *
 * - キャラ名変更: 募集中 + キャラチップが変更を許可 + 自分がまだ変更可能ステータス、のとき。
 * - 簡易メモ: 募集中 + 自分がまだ変更可能ステータス、のとき。
 * - 表情差分: オリジナルキャラチップ村のときのみ。
 *   `/rp/face-types` GET で取得した自キャラ分のみを編集 (PUT) し、画像追加 (POST multipart) も同パネルから行える。
 *
 * 編集可能項目が 1 つも無い場合は何も描画しない。
 */
export function RpActions({
  village,
  myself,
}: {
  village: VillageView;
  myself: MyselfView;
}) {
  const { rp } = myself;
  const showChangeName = rp.isAvailableChangeName;
  const showMemo = rp.isAvailableMemo;
  // canAddFaceType は backend 側で「オリジナルキャラチップ村 + 募集中相当」のときに true。
  // ここでは編集 UI (PUT 系) の表示判定にも使う (backend の編集 PUT は所有者検証のみで
  // 通すため UI 表示が唯一のガード)。
  const showFaceType = rp.canAddFaceType;

  if (!showChangeName && !showMemo && !showFaceType) return null;

  return (
    <Panel>
      <PanelHeading>
        <h2 className="text-[1em] m-0 font-normal">RP</h2>
      </PanelHeading>
      <PanelBody>
        <div className="space-y-4">
          {showChangeName && <ChangeNameForm villageId={village.id} myself={myself} />}
          {showMemo && <MemoForm villageId={village.id} myself={myself} />}
          {showFaceType && <FaceTypesForm villageId={village.id} />}
        </div>
      </PanelBody>
    </Panel>
  );
}

// ---------- キャラ名変更 ----------

function ChangeNameForm({ villageId, myself }: { villageId: number; myself: MyselfView }) {
  const mutation = useChangeNameMutation(villageId);
  const [name, setName] = useState(myself.charaName);
  const [shortName, setShortName] = useState(myself.charaShortName);

  // 他の操作 (例えば管理者操作) で名前が変わったら同期。
  useEffect(() => {
    setName(myself.charaName);
    setShortName(myself.charaShortName);
  }, [myself.charaName, myself.charaShortName]);

  function submit(e: React.FormEvent) {
    e.preventDefault();
    const trimmedName = name.trim();
    const trimmedShort = shortName.trim();
    if (!trimmedName || trimmedShort.length !== 1) return;
    mutation.mutate({ name: trimmedName, shortName: trimmedShort });
  }

  const submittable =
    !mutation.isPending &&
    name.trim().length > 0 &&
    name.trim().length <= 40 &&
    shortName.trim().length === 1;
  const dirty = name !== myself.charaName || shortName !== myself.charaShortName;

  return (
    <form onSubmit={submit} className="space-y-2 first:border-t-0 first:pt-0 border-t border-night-700 pt-3">
      <p className="text-xs opacity-80">キャラ名変更</p>
      <div className="grid grid-cols-[1fr_5rem] gap-2 items-end">
        <Field label="表示名 (40 文字以内)">
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            maxLength={40}
            className={inputClass}
            disabled={mutation.isPending}
          />
        </Field>
        <Field label="略称 (1 文字)">
          <input
            type="text"
            value={shortName}
            onChange={(e) => setShortName(e.target.value)}
            maxLength={1}
            className={inputClass}
            disabled={mutation.isPending}
          />
        </Field>
      </div>
      <div className="flex items-center gap-3">
        <button
          type="submit"
          disabled={!submittable || !dirty}
          className={primaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "名前を変更"}
        </button>
        {mutation.isError && (
          <span className="text-xs text-blood-500">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

// ---------- 簡易メモ ----------

const MEMO_MAX_LENGTH = 20;

function MemoForm({ villageId, myself }: { villageId: number; myself: MyselfView }) {
  const mutation = useMemoMutation(villageId);
  const [memo, setMemo] = useState(myself.memo ?? "");

  useEffect(() => {
    setMemo(myself.memo ?? "");
  }, [myself.memo]);

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (memo.length > MEMO_MAX_LENGTH) return;
    mutation.mutate({ memo });
  }

  const submittable = !mutation.isPending && memo.length <= MEMO_MAX_LENGTH;
  const dirty = memo !== (myself.memo ?? "");

  return (
    <form onSubmit={submit} className="space-y-2 border-t border-night-700 pt-3">
      <p className="text-xs opacity-80">簡易メモ ({MEMO_MAX_LENGTH} 文字以内、自分しか見えない)</p>
      <input
        type="text"
        value={memo}
        onChange={(e) => setMemo(e.target.value)}
        maxLength={MEMO_MAX_LENGTH}
        placeholder="(空文字でクリア)"
        className={inputClass}
        disabled={mutation.isPending}
      />
      <div className="flex items-center gap-3">
        <button
          type="submit"
          disabled={!submittable || !dirty}
          className={primaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "メモを保存"}
        </button>
        <span className="text-xs opacity-60">
          {memo.length} / {MEMO_MAX_LENGTH}
        </span>
        {mutation.isError && (
          <span className="text-xs text-blood-500">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

// ---------- 表情差分 ----------

function FaceTypesForm({ villageId }: { villageId: number }) {
  // RpActions が canAddFaceType=true のときだけマウントするので、ここでは常に enabled.
  const query = useFaceTypesQuery(villageId, true);
  const mutation = useFaceTypesMutation(villageId);

  // ローカル編集状態。backend からの最新が入ってきたら再初期化する。
  // face-types クエリは polling なし (staleTime=60s) なので、編集中に意図しない reset が
  // 起きるのは mutation 成功後の invalidate or window focus refetch のみ。前者は送信済み
  // 状態なので reset で問題なく、後者も「他端末で先に編集した結果に揃える」挙動として
  // 受け入れる。
  const [items, setItems] = useState<MyselfFaceTypeView[]>([]);
  useEffect(() => {
    if (query.data) setItems(query.data.list);
  }, [query.data]);

  function update(idx: number, patch: Partial<MyselfFaceTypeView>) {
    setItems((prev) => prev.map((it, i) => (i === idx ? { ...it, ...patch } : it)));
  }

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (items.length === 0) return;
    // backend は name に @Size(1,5) を持つ。1 件でも違反していたら submit を弾く。
    if (items.some((it) => it.name.trim().length < 1 || it.name.trim().length > 5)) return;
    mutation.mutate({
      faceTypeList: items.map((it) => ({
        code: it.code,
        name: it.name.trim(),
        display: it.isDisplay,
      })),
    });
  }

  const submittable =
    !mutation.isPending &&
    items.length > 0 &&
    items.every((it) => it.name.trim().length >= 1 && it.name.trim().length <= 5);

  return (
    <div className="space-y-4 border-t border-night-700 pt-3">
      <form onSubmit={submit} className="space-y-3">
        <p className="text-xs opacity-80">表情差分編集</p>
        {query.isLoading && <p className="opacity-80 text-sm">読み込み中...</p>}
        {query.isError && (
          <p className="text-blood-500 text-sm">表情差分の取得に失敗しました</p>
        )}
        {query.data && items.length === 0 && (
          <p className="opacity-80 text-sm">編集可能な表情差分がありません</p>
        )}
        {items.length > 0 && (
          <ul className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            {items.map((it, idx) => (
              <li
                key={it.code}
                className="flex gap-3 items-start rounded border border-night-700 p-2 bg-night-900"
              >
                <img
                  src={it.url}
                  alt={it.name}
                  width={60}
                  height={60}
                  className="rounded shrink-0"
                />
                <div className="flex-1 space-y-1">
                  <input
                    type="text"
                    value={it.name}
                    onChange={(e) => update(idx, { name: e.target.value })}
                    maxLength={5}
                    className={inputClass}
                    placeholder="表情名 (1-5 文字)"
                    disabled={mutation.isPending}
                  />
                  <label className="flex items-center gap-1 text-xs text-white">
                    <input
                      type="checkbox"
                      checked={it.isDisplay}
                      onChange={(e) => update(idx, { isDisplay: e.target.checked })}
                      disabled={mutation.isPending}
                    />
                    発言フォームに表示する
                  </label>
                </div>
              </li>
            ))}
          </ul>
        )}
        <div className="flex items-center gap-3">
          <button
            type="submit"
            disabled={!submittable}
            className={primaryButtonClass}
          >
            {mutation.isPending ? "送信中..." : "表情差分を更新"}
          </button>
          {mutation.isError && (
            <span className="text-xs text-blood-500">{mutation.error.message}</span>
          )}
        </div>
      </form>
      <AddFaceTypeForm villageId={villageId} />
    </div>
  );
}

// ---------- 表情差分の追加 (画像アップロード) ----------

/** 旧 Thymeleaf の `add-face-type` 相当。クライアント側で 1〜100KB / 1〜5 文字を先回り検証する。 */
const FACE_TYPE_IMAGE_MAX_BYTES = 100_000;
const FACE_TYPE_NAME_MAX_LENGTH = 5;
/** backend `ALLOWED_IMAGE_EXTS` と揃える (ホワイトリスト)。 */
const FACE_TYPE_IMAGE_ACCEPT = ".png,.jpg,.jpeg,.gif,.webp";
const FACE_TYPE_IMAGE_EXT_PATTERN = /\.(png|jpg|jpeg|gif|webp)$/i;

function AddFaceTypeForm({ villageId }: { villageId: number }) {
  const mutation = useAddFaceTypeMutation(villageId);
  const [name, setName] = useState("");
  const [image, setImage] = useState<File | null>(null);
  // 送信成功時に <input type="file"> を空に戻すための ref。
  // (controlled な file input は React で扱えないので key/ref で reset する)
  const fileInputRef = useRef<HTMLInputElement>(null);

  const trimmedName = name.trim();
  const nameValid =
    trimmedName.length >= 1 && trimmedName.length <= FACE_TYPE_NAME_MAX_LENGTH;
  const imageExtValid = image != null && FACE_TYPE_IMAGE_EXT_PATTERN.test(image.name);
  const imageSizeValid =
    image != null && image.size > 0 && image.size <= FACE_TYPE_IMAGE_MAX_BYTES;
  const imageValid = image != null && imageExtValid && imageSizeValid;
  const submittable = !mutation.isPending && nameValid && imageValid;

  function onFileChange(e: React.ChangeEvent<HTMLInputElement>) {
    const file = e.target.files?.[0] ?? null;
    setImage(file);
  }

  function submit(e: React.FormEvent) {
    e.preventDefault();
    if (!submittable || image == null) return;
    mutation.mutate(
      { faceTypeName: trimmedName, image },
      {
        onSuccess: () => {
          setName("");
          setImage(null);
          if (fileInputRef.current) fileInputRef.current.value = "";
        },
      },
    );
  }

  return (
    <form onSubmit={submit} className="space-y-2 border-t border-night-700 pt-3">
      <p className="text-xs opacity-80">表情差分追加</p>
      <ul className="text-xs opacity-60 list-disc pl-4 space-y-0.5">
        <li>表情差分名は 1〜5 文字。</li>
        <li>画像は 60x60px で表示されるため 60 の倍数解像度推奨。</li>
        <li>100KB を超える画像はアップロードできません。</li>
        <li>対応形式: png / jpg / jpeg / gif / webp。</li>
      </ul>
      <div className="grid grid-cols-[1fr_auto] gap-2 items-end">
        <Field label="表情差分名 (1-5 文字)">
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            maxLength={FACE_TYPE_NAME_MAX_LENGTH}
            className={inputClass}
            placeholder="例: 笑顔"
            disabled={mutation.isPending}
          />
        </Field>
        <Field label="画像">
          <input
            ref={fileInputRef}
            type="file"
            accept={FACE_TYPE_IMAGE_ACCEPT}
            onChange={onFileChange}
            disabled={mutation.isPending}
            className="text-xs text-white file:mr-2 file:rounded file:border-0 file:bg-night-700 file:px-2 file:py-1 file:text-white hover:file:bg-night-600 disabled:opacity-50"
          />
        </Field>
      </div>
      {image != null && !imageValid && (
        <p className="text-xs text-blood-500">
          {!imageExtValid
            ? "対応形式は png / jpg / jpeg / gif / webp のみです"
            : image.size === 0
              ? "選択された画像が空です"
              : "画像サイズは 100KB 以下にしてください"}
        </p>
      )}
      <div className="flex items-center gap-3">
        <button
          type="submit"
          disabled={!submittable}
          className={primaryButtonClass}
        >
          {mutation.isPending ? "送信中..." : "表情差分を追加"}
        </button>
        {mutation.isError && (
          <span className="text-xs text-blood-500">{mutation.error.message}</span>
        )}
      </div>
    </form>
  );
}

// ---------- 部品 ----------

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <div className="space-y-1">
      <label className="text-xs opacity-80">{label}</label>
      {children}
    </div>
  );
}

const inputClass =
  "w-full bg-night-900 border border-night-700 rounded-[3px] px-2 py-1 text-[1em] focus:outline-none focus:border-mint-500 disabled:opacity-50";

const primaryButtonClass =
  "px-[9px] py-[6px] rounded-[3px] border-2 border-bs-success-600 bg-bs-success-500 text-white text-[13px] hover:bg-bs-success-700 hover:border-bs-success-700 disabled:opacity-50 disabled:cursor-not-allowed";

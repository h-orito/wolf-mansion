import { useEffect, useRef, useState } from "react";
import { Controller, useFormContext, useWatch } from "react-hook-form";

import { Button } from "~/components/ui/Button";
import { fieldErrorClass, FormRow } from "~/components/ui/Form";
import { inputClass, selectClass, textareaClass } from "~/components/ui/Input";
import { MultiSelect } from "~/components/ui/MultiSelect";
import { TextLink } from "~/components/ui/TextLink";
import type { Chara } from "~/features/charachips/api";
import { useCharachipDetails, useCharachipList } from "~/features/charachips/useCharachips";
import { assetUrl } from "~/lib/api";
import { RadioRow, RequiredAfterCreationMark, SettingSection } from "./fields";
import type { NewVillageFormInput } from "./schema";

/** オリジナルダミーキャラ画像のサイズ上限 (バイト)。正本は backend `NewVillageFormValidator`。 */
const ORIGINAL_IMAGE_MAX_BYTES = 100000;
const ORIGINAL_IMAGE_SIZE_MESSAGE = "ダミーキャラ画像は100kByte以内の画像を指定してください";

/** キャラチップ設定 (キャラチップ利用 / キャラセット / ダミーキャラと発言)。 */
export function CharachipSection({
  originalImageFile,
  originalImageUrl,
  onSelectOriginalImage,
}: {
  originalImageFile: File | null;
  originalImageUrl: string | null;
  onSelectOriginalImage: (file: File | null) => void;
}) {
  const { control, setValue, getValues, getFieldState, formState } =
    useFormContext<NewVillageFormInput>();
  const shouldOriginalImage = useWatch<NewVillageFormInput, "shouldOriginalImage">({
    name: "shouldOriginalImage",
  });
  const characterSetId = useWatch<NewVillageFormInput, "characterSetId">({
    name: "characterSetId",
  });
  const dummyCharaId = useWatch<NewVillageFormInput, "dummyCharaId">({ name: "dummyCharaId" });
  const { data: charachips } = useCharachipList();
  const { charas, isLoading } = useCharachipDetails(shouldOriginalImage ? [] : characterSetId);
  // キャラセットをユーザーが変更した直後の候補確定を、初期表示時の候補確定と区別する
  // (ユーザー変更時のみ既定発言の上書きを confirm で確認する)
  const manualChangeRef = useRef(false);

  const fillDefaultMessage = (
    name: "dummyJoinMessage" | "dummyDay1Message",
    defaultMessage: string | null | undefined,
    confirmMessage: string,
    manualChanged: boolean,
  ) => {
    if (!defaultMessage) return;
    const current = getValues(name);
    if (current === "") {
      setValue(name, defaultMessage);
    } else if (manualChanged && current !== defaultMessage && window.confirm(confirmMessage)) {
      setValue(name, defaultMessage);
    }
  };

  const applyDummyChara = (chara: Chara, manualChanged: boolean) => {
    setValue("dummyCharaId", chara.id);
    setValue("dummyCharaName", chara.name);
    setValue("dummyCharaShortName", chara.shortName);
    // 複数キャラセット選択時はどのキャラセットの既定発言か曖昧なため自動入力しない
    if (getValues("characterSetId").length >= 2) return;
    fillDefaultMessage(
      "dummyJoinMessage",
      chara.defaultJoinMessage,
      "ダミーキャラの入村発言を上書きしてもよろしいですか？",
      manualChanged,
    );
    fillDefaultMessage(
      "dummyDay1Message",
      chara.defaultFirstdayMessage,
      "ダミーキャラの1日目発言を上書きしてもよろしいですか？",
      manualChanged,
    );
  };

  // キャラ候補が確定/変更されたら選択中ダミーキャラを補正して反映する
  // (候補に残っていればそのまま、いなければ先頭キャラ)
  const charasKey = charas.map((c) => c.id).join(",");
  useEffect(() => {
    if (charas.length === 0) return;
    const manualChanged = manualChangeRef.current;
    manualChangeRef.current = false;
    const currentId = getValues("dummyCharaId");
    const chara = charas.find((c) => c.id === currentId) ?? charas[0];
    applyDummyChara(chara, manualChanged);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [charasKey]);

  const selectedChara = charas.find((c) => c.id === dummyCharaId);
  const characterSetError = getFieldState("characterSetId", formState).error;

  return (
    <SettingSection
      title={
        <>
          キャラチップ設定 <RequiredAfterCreationMark />
        </>
      }
    >
      <RadioRow
        name="shouldOriginalImage"
        label="キャラチップ利用"
        options={[
          { value: false, label: "利用する" },
          { value: true, label: "自分で用意する" },
        ]}
      />
      {shouldOriginalImage ? (
        <>
          <FormRow label="" labelWidth="wide">
            <ul className="list-disc pl-[20px]">
              <li>オリジナル画像を各参加者がアップロードして使用します。</li>
              <li>ダミーキャラ画像は100kByte以内の画像を指定してください。</li>
              <li>
                登録した時点で、
                <TextLink to="/about#original">オリジナルキャラクターおよび画像の登録</TextLink>
                について了承したものとみなします。
              </li>
            </ul>
          </FormRow>
          <OriginalImageRow file={originalImageFile} onSelect={onSelectOriginalImage} />
        </>
      ) : (
        <>
          <FormRow label="キャラセット" labelWidth="wide">
            <Controller
              control={control}
              name="characterSetId"
              render={({ field }) => (
                <MultiSelect
                  ariaLabel="キャラセット"
                  options={(charachips ?? []).map((c) => ({
                    value: String(c.id),
                    label: `${c.name}（${c.designerName}様作）`,
                  }))}
                  value={field.value.map(String)}
                  onChange={(values) => {
                    manualChangeRef.current = true;
                    field.onChange(values.map(Number));
                  }}
                />
              )}
            />
            {characterSetError && <p className={fieldErrorClass}>{characterSetError.message}</p>}
          </FormRow>
          <FormRow label="ダミーキャラ" htmlFor="dummyCharaId" labelWidth="wide">
            <select
              id="dummyCharaId"
              className={selectClass}
              value={dummyCharaId ?? ""}
              onChange={(e) => {
                const chara = charas.find((c) => c.id === Number(e.target.value));
                if (chara) applyDummyChara(chara, true);
              }}
            >
              {isLoading && <option value="">読み込み中</option>}
              {charas.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
          </FormRow>
        </>
      )}
      <DummyCharaTextRow name="dummyCharaName" label="ダミーキャラ名" />
      <DummyCharaTextRow name="dummyCharaShortName" label="ダミーキャラ名略称" />
      <DummyCharaMessageRow
        name="dummyJoinMessage"
        label="ダミーキャラの入村発言"
        chara={selectedChara}
        original={shouldOriginalImage}
        originalImageUrl={originalImageUrl}
      />
      <DummyCharaMessageRow
        name="dummyDay1Message"
        label="ダミーキャラの1日目発言"
        chara={selectedChara}
        original={shouldOriginalImage}
        originalImageUrl={originalImageUrl}
      />
    </SettingSection>
  );
}

/** オリジナルダミーキャラ画像の選択行。サイズ超過はここで弾く (正本はサーバー検証)。 */
function OriginalImageRow({
  file,
  onSelect,
}: {
  file: File | null;
  onSelect: (file: File | null) => void;
}) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [sizeError, setSizeError] = useState(false);
  return (
    <FormRow label="ダミーキャラ画像" labelWidth="wide">
      <div className="flex items-center gap-[10px]">
        <Button
          variant="default"
          onClick={() => {
            inputRef.current?.click();
          }}
        >
          ファイルを選択
        </Button>
        <span>{file ? file.name : "選択されていません"}</span>
      </div>
      <input
        ref={inputRef}
        type="file"
        accept="image/*"
        className="hidden"
        aria-label="ダミーキャラ画像"
        onChange={(e) => {
          const selected = e.target.files?.[0] ?? null;
          if (selected && selected.size > ORIGINAL_IMAGE_MAX_BYTES) {
            setSizeError(true);
            onSelect(null);
            e.target.value = "";
            return;
          }
          setSizeError(false);
          onSelect(selected);
        }}
      />
      {sizeError && <p className={fieldErrorClass}>{ORIGINAL_IMAGE_SIZE_MESSAGE}</p>}
    </FormRow>
  );
}

function DummyCharaTextRow({
  name,
  label,
}: {
  name: "dummyCharaName" | "dummyCharaShortName";
  label: string;
}) {
  const { register, getFieldState, formState } = useFormContext<NewVillageFormInput>();
  const error = getFieldState(name, formState).error;
  return (
    <FormRow label={label} htmlFor={name} labelWidth="wide">
      <input id={name} type="text" className={inputClass} {...register(name)} />
      {error && <p className={fieldErrorClass}>{error.message}</p>}
    </FormRow>
  );
}

function DummyCharaMessageRow({
  name,
  label,
  chara,
  original,
  originalImageUrl,
}: {
  name: "dummyJoinMessage" | "dummyDay1Message";
  label: string;
  chara: Chara | undefined;
  original: boolean;
  originalImageUrl: string | null;
}) {
  const { register, getFieldState, formState } = useFormContext<NewVillageFormInput>();
  const error = getFieldState(name, formState).error;
  return (
    <FormRow label={label} htmlFor={name} labelWidth="wide">
      <div className="flex">
        <div>
          <DummyCharaImage chara={chara} original={original} originalImageUrl={originalImageUrl} />
        </div>
        <div className="ml-[5px] min-h-[77px] flex-1">
          <textarea id={name} className={`${textareaClass} min-h-[77px]`} {...register(name)} />
        </div>
      </div>
      {error && <p className={fieldErrorClass}>{error.message}</p>}
    </FormRow>
  );
}

function DummyCharaImage({
  chara,
  original,
  originalImageUrl,
}: {
  chara: Chara | undefined;
  original: boolean;
  originalImageUrl: string | null;
}) {
  if (original) {
    return (
      <img
        src={originalImageUrl ?? assetUrl("/app/images/placeholder.png")}
        width={60}
        height={60}
        alt=""
      />
    );
  }
  if (!chara) return null;
  const image = chara.images.list.find((i) => i.faceType.code === "NORMAL") ?? chara.images.list[0];
  if (!image) return null;
  return (
    <img src={image.url} width={chara.size.width} height={chara.size.height} alt={chara.name} />
  );
}

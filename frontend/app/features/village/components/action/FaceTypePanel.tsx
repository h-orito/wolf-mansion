import { useState } from "react";

import { AlertList, ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { ButtonRadioGroup } from "~/components/ui/ButtonRadioGroup";
import { FileUpload } from "~/components/ui/FileUpload";
import { VillageFormRow } from "~/components/ui/Form";
import { inputClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  addVillageFaceType,
  modifyVillageFaceTypes,
  type ParticipantSituationView,
} from "~/features/village/api";
import { useVillageId } from "~/features/village/VillageContext";
import { useVillageInvalidate } from "~/features/village/useVillage";
import { useAsyncAction } from "~/lib/useAsyncAction";

export function FaceTypePanel({ mySituation }: { mySituation: ParticipantSituationView }) {
  const images = mySituation.myself?.chara.images.list ?? [];
  return (
    <Panel title="表情差分" storageKey="facetypeform" fixable>
      <div className="space-y-[15px]">
        {images.length > 0 && <ModifyFaceTypesForm key={images.length} images={images} />}
        <AddFaceTypeForm />
      </div>
    </Panel>
  );
}

type CharaImage = {
  faceType: { code: string; name: string };
  url: string;
  isDisplay: boolean;
};

function ModifyFaceTypesForm({ images }: { images: CharaImage[] }) {
  const villageId = useVillageId();
  const invalidate = useVillageInvalidate();
  const [items, setItems] = useState(() =>
    images.map((img) => ({
      code: img.faceType.code,
      name: img.faceType.name,
      isDisplay: img.isDisplay,
      url: img.url,
    })),
  );
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const updateItem = (index: number, patch: Partial<(typeof items)[0]>) => {
    setItems((prev) => prev.map((item, i) => (i === index ? { ...item, ...patch } : item)));
  };

  const submit = () =>
    execute(async () => {
      await modifyVillageFaceTypes(
        villageId,
        items.map((item) => ({ code: item.code, name: item.name, isDisplay: item.isDisplay })),
      );
      showToast("表情差分を更新しました");
      await invalidate();
    }, "表情差分の更新に失敗しました");

  return (
    <div className="space-y-[10px]">
      <strong>表情差分編集</strong>
      <AlertList>
        <li>表情差分名は1～5文字で入力してください。</li>
        <li>
          非表示にした表情差分は発言欄の候補に出てこなくなります（過去の発言の画像は消えません）。
        </li>
      </AlertList>
      <ErrorMessage error={error} />
      <div className="grid grid-cols-1 gap-[10px] sm:grid-cols-2 lg:grid-cols-3">
        {items.map((item, index) => (
          <div key={item.code} className="flex gap-[5px]">
            <img src={item.url} alt={item.name} width={60} height={60} className="shrink-0" />
            <div className="flex flex-1 flex-col gap-[3px]">
              <input
                type="text"
                className={inputClass}
                value={item.name}
                maxLength={5}
                onChange={(e) => updateItem(index, { name: e.target.value })}
                aria-label={`表情差分名 ${item.code}`}
              />
              <ButtonRadioGroup
                options={[
                  { value: true, label: "表示" },
                  { value: false, label: "非表示" },
                ]}
                value={item.isDisplay}
                onChange={(v) => updateItem(index, { isDisplay: v })}
                ariaLabel={`表示切替 ${item.code}`}
                variant="outline"
              />
            </div>
          </div>
        ))}
      </div>
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting || items.some((item) => item.name === "")}>
          表情差分を更新する
        </Button>
      </div>
    </div>
  );
}

function AddFaceTypeForm() {
  const villageId = useVillageId();
  const invalidate = useVillageInvalidate();
  const [faceTypeName, setFaceTypeName] = useState("");
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [uploadKey, setUploadKey] = useState(0);
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await addVillageFaceType(villageId, faceTypeName, imageFile!);
      showToast("表情差分を追加しました");
      setFaceTypeName("");
      setImageFile(null);
      setUploadKey((k) => k + 1);
      await invalidate();
    }, "表情差分の追加に失敗しました");

  return (
    <div className="space-y-[10px]">
      <strong>表情差分追加</strong>
      <AlertList>
        <li>表情差分名は1～5文字で入力してください。</li>
        <li>
          画像は60x60pxで表示されるため、解像度は60x60や120x120など60の倍数の大きさとすることを推奨します。
        </li>
        <li>100kByteを超える画像はアップロードできません。</li>
      </AlertList>
      <ErrorMessage error={error} />
      <VillageFormRow label="表情差分名">
        <input
          type="text"
          className={inputClass}
          value={faceTypeName}
          maxLength={5}
          onChange={(e) => setFaceTypeName(e.target.value)}
          aria-label="表情差分名"
        />
      </VillageFormRow>
      <VillageFormRow label="画像">
        <FileUpload
          key={uploadKey}
          accept="image/*"
          maxSizeBytes={100_000}
          imagePreviewSize={60}
          onSelect={setImageFile}
        />
      </VillageFormRow>
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting || faceTypeName === "" || imageFile == null}>
          表情差分を追加する
        </Button>
      </div>
    </div>
  );
}

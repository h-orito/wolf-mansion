import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { fieldErrorClass, FormRow } from "~/components/ui/Form";
import { selectClass } from "~/components/ui/Input";
import { NOT_PROGRESS_STATUSES } from "~/features/villages/api";
import { useVillages } from "~/features/villages/useVillages";
import { SettingSection } from "./fields";

/**
 * 設定流用。エピローグ/終了/廃村の村を選び、その設定をフォームへ流し込む。
 * 流し込み本体 (設定取得と reset) は親が行う。
 */
export function DivertSection({
  diverting,
  errorMessage,
  onDivert,
}: {
  diverting: boolean;
  errorMessage: string | null;
  onDivert: (villageId: number) => void;
}) {
  const { data } = useVillages({ statuses: NOT_PROGRESS_STATUSES, order: "asc" });
  const villages = data?.villages ?? [];
  const [selectedId, setSelectedId] = useState<number | null>(null);
  const villageId = selectedId ?? villages[0]?.id ?? null;

  return (
    <SettingSection title="設定流用">
      <FormRow label="他の村から流用する" htmlFor="divert-village" labelWidth="wide">
        <div className="min-[768px]:w-1/3">
          <select
            id="divert-village"
            className={selectClass}
            value={villageId ?? ""}
            onChange={(e) => {
              setSelectedId(Number(e.target.value));
            }}
          >
            {villages.map((v) => (
              <option key={v.id} value={v.id}>
                {`${String(v.id).padStart(4, "0")}: ${v.name}`}
              </option>
            ))}
          </select>
        </div>
        <p className="mt-[10px]">
          編集状態は一切保持されず、[村名、開始日時、入村発言、入村パスワード]以外が全て選択した村の設定内容になります。
          <br />
          また、選択した村を作成した時点で実装されていない役職については発言制限が無制限になっているので注意してください。
        </p>
      </FormRow>
      {errorMessage && <p className={fieldErrorClass}>{errorMessage}</p>}
      <div className="mb-[15px] flex justify-end">
        <Button
          disabled={villageId == null || diverting}
          onClick={() => {
            if (villageId != null) onDivert(villageId);
          }}
        >
          流用する
        </Button>
      </div>
    </SettingSection>
  );
}

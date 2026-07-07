import { useEffect, useState } from "react";

import { Button, LinkButton } from "~/components/ui/Button";
import { useVillageId } from "~/features/village/VillageContext";

const STORAGE_KEY = "already_agelimit_confirm";

function confirmedVillages(): string[] {
  try {
    const value = localStorage.getItem(STORAGE_KEY);
    return value == null || value === "" ? [] : value.split(",");
  } catch {
    return [];
  }
}

/**
 * 年齢制限 (R15/R18) の村を初めて表示したときの確認モーダル。
 * 確認済みの村 ID はブラウザに記憶し、次回以降は出さない。
 */
export function AgeLimitModal({
  ageLimit,
  onResolved,
}: {
  ageLimit: string;
  /** 確認済み (表示不要含む) になったら呼ぶ。後続モーダルの抑制解除に使う */
  onResolved?: () => void;
}) {
  const villageId = useVillageId();
  const [open, setOpen] = useState(false);

  useEffect(() => {
    const needsConfirm = !confirmedVillages().includes(String(villageId));
    setOpen(needsConfirm);
    if (!needsConfirm) onResolved?.();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [villageId]);

  if (!open) return null;

  const confirm = () => {
    try {
      const villages = confirmedVillages();
      if (!villages.includes(String(villageId))) villages.push(String(villageId));
      localStorage.setItem(STORAGE_KEY, villages.join(","));
    } catch {
      // 記憶できなくても表示は続行する
    }
    setOpen(false);
    onResolved?.();
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
      <div className="mx-[10px] w-full max-w-[600px] rounded border border-border bg-surface p-[15px] text-white">
        <h5 className="mb-[10px] text-[14px]">年齢制限確認</h5>
        <p className="mb-[15px]">
          この村は年齢制限が <strong className="text-[20px] text-danger">{ageLimit}</strong>{" "}
          に設定されており、
          <br />
          暴力表現や性描写などが含まれる可能性があります。
        </p>
        <div className="flex justify-end gap-[10px] text-[12px]">
          <LinkButton to="/" variant="default">
            表示せず戻る
          </LinkButton>
          <Button onClick={confirm}>表示する</Button>
        </div>
      </div>
    </div>
  );
}

import type { ReactNode } from "react";

import { Button } from "~/components/ui/Button";
import { ModalDialog } from "~/components/ui/Modal";
import { findNormalImage } from "~/features/charachips/charaImage";

/** カードに出すのに必要な最小限のキャラ情報 (Chara / 参加者のどちらからでも作れる)。 */
export type SelectableChara = {
  id: number;
  name: string;
  images: { list: { faceType: { code: string }; url: string }[] };
  size: { width: number; height: number };
};

function CharaSelectCard({ chara, onSelect }: { chara: SelectableChara; onSelect: () => void }) {
  return (
    <div className="border border-border p-[5px] text-center">
      <div className="flex justify-center">
        <img
          src={findNormalImage(chara.images.list)?.url ?? ""}
          alt={chara.name}
          width={chara.size.width}
          height={chara.size.height}
        />
      </div>
      <div>{chara.name}</div>
      <Button size="xs" className="w-full" onClick={onSelect}>
        選択
      </Button>
    </div>
  );
}

/** キャラ画像 + 名前 + 選択ボタンのカードをグリッドに並べる。 */
export function CharaSelectGrid<T extends SelectableChara>({
  charas,
  onSelect,
}: {
  charas: T[];
  onSelect: (chara: T) => void;
}) {
  return (
    <div className="grid grid-cols-2 gap-[5px] sm:grid-cols-3">
      {charas.map((c) => (
        <CharaSelectCard key={c.id} chara={c} onSelect={() => onSelect(c)} />
      ))}
    </div>
  );
}

/**
 * キャラを画像から選ぶモーダルの枠。中身は CharaSelectGrid (必要なら見出し付きで複数) を置く。
 * 選択後に閉じるかどうかは呼び出し側が onSelect で決める。
 */
export function CharaSelectModal({
  open,
  title,
  onClose,
  children,
}: {
  open: boolean;
  title: string;
  onClose: () => void;
  children: ReactNode;
}) {
  return (
    <ModalDialog open={open} onClose={onClose} label={title} size="medium">
      <div className="p-[15px]">
        <h4 className="mb-[10px] font-bold">{title}</h4>
        {children}
        <div className="mt-[10px] flex justify-end">
          <Button variant="default" onClick={onClose}>
            閉じる
          </Button>
        </div>
      </div>
    </ModalDialog>
  );
}

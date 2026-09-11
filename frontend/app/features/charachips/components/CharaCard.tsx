import { StarIcon as StarOutlineIcon } from "@heroicons/react/24/outline";
import { StarIcon as StarSolidIcon } from "@heroicons/react/24/solid";

import type { Chara } from "~/features/charachips/api";

/**
 * キャラ 1 体のカード (全表情画像 + [略称] 名前)。
 * onToggleFavorite を渡すと右上に ★/☆ のお気に入りトグルを表示する (未ログイン時は渡さない)。
 */
export function CharaCard({
  chara,
  isFavorite = false,
  onToggleFavorite,
}: {
  chara: Chara;
  isFavorite?: boolean;
  onToggleFavorite?: () => void;
}) {
  return (
    <div className="relative box-border w-full border border-border p-[5px] min-[768px]:w-1/2">
      {onToggleFavorite && (
        <button
          type="button"
          onClick={onToggleFavorite}
          aria-label={isFavorite ? "お気に入り解除" : "お気に入り登録"}
          aria-pressed={isFavorite}
          title={isFavorite ? "お気に入り解除" : "お気に入り登録"}
          className="absolute top-[5px] right-[5px] cursor-pointer"
        >
          {isFavorite ? (
            <StarSolidIcon className="h-5 w-5 text-yellow-400" />
          ) : (
            <StarOutlineIcon className="h-5 w-5 text-gray-400 hover:text-yellow-400" />
          )}
        </button>
      )}
      <span className="block text-center">
        {chara.images.list.map((img, i) => (
          <img
            key={i}
            src={img.url}
            width={chara.size.width}
            height={chara.size.height}
            alt={chara.name}
            className="inline-block"
          />
        ))}
      </span>
      <span className="block text-center">
        [{chara.shortName}] {chara.name}
      </span>
    </div>
  );
}

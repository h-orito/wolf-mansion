import { Heading } from "~/components/ui/Heading";
import { TextLink } from "~/components/ui/TextLink";
import { PageLayout } from "~/components/layout/PageLayout";
import { RequireAuth } from "~/features/auth/RequireAuth";
import { CharaCard } from "~/features/charachips/components/CharaCard";
import {
  useFavoriteCharachips,
  useToggleFavoriteChara,
} from "~/features/favorite/useFavoriteCharas";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("お気に入りキャラ");
}

export default function FavoriteCharas() {
  return (
    <RequireAuth>
      <FavoriteCharasContent />
    </RequireAuth>
  );
}

function FavoriteCharasContent() {
  const { data: charachips, isLoading } = useFavoriteCharachips();
  const { toggle: toggleFavorite, error: favoriteError } = useToggleFavoriteChara();
  const chips = charachips?.list ?? [];

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>お気に入りキャラ</Heading>

        <ul className="mb-[10px] list-disc pl-[20px]">
          <li>お気に入りキャラはあなただけが閲覧でき、他のユーザーには公開されません。</li>
          <li>
            入村時に「お気に入りから選択」ボタンで、お気に入りキャラの中から参加キャラを選べます。
          </li>
        </ul>

        {favoriteError != null && <p className="mb-[5px] text-danger">{favoriteError}</p>}

        {!isLoading && chips.length === 0 && (
          <p>
            お気に入りキャラはいません。
            <TextLink to="/chara-group">キャラチップ一覧</TextLink>
            から各キャラチップの詳細画面を開くと、キャラをお気に入りに登録できます。
          </p>
        )}

        {chips.map((chip) => (
          <div key={chip.id} className="mb-[10px]">
            <Heading as="h2">
              <TextLink to={`/chara-group/${chip.id}`}>{chip.name}</TextLink>
            </Heading>
            <div className="flex flex-wrap">
              {chip.charas.list.map((chara) => (
                <CharaCard
                  key={chara.id}
                  chara={chara}
                  isFavorite
                  onToggleFavorite={() => toggleFavorite(chara.id, true)}
                />
              ))}
            </div>
          </div>
        ))}
      </div>
    </PageLayout>
  );
}

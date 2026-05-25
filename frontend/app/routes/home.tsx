import { Link } from "react-router";
import type { Route } from "./+types/home";
import {
  BookOpenIcon,
  BookmarkIcon,
  ClipboardDocumentListIcon,
  InformationCircleIcon,
  LockClosedIcon,
  LockOpenIcon,
  MegaphoneIcon,
  PencilSquareIcon,
  PlusIcon,
  QuestionMarkCircleIcon,
  UserIcon,
  UsersIcon,
  WrenchIcon,
} from "@heroicons/react/24/outline";
import { useLogoutMutation, useMeQuery } from "~/features/auth/hooks";
import { fetchVillages, type VillagesView } from "~/features/village/api";
import { useVillagesQuery } from "~/features/village/hooks";
import { ssrFetch } from "~/lib/api/client";
import { WolfMansionLogo } from "~/components/ui/WolfMansionLogo";
import {
  MenuSection,
  MenuTileRow,
} from "~/components/ui/MenuSection";
import { MenuTileLink, MenuTileButton } from "~/components/ui/MenuTile";
import { cn } from "~/components/ui/cn";
import { VillageTag, villageTagLevel } from "~/components/ui/VillageTag";
import { PageFooter } from "~/components/layout/PageFooter";

const TOP_STATUSES = ["募集中", "進行中", "エピローグ"] as const;

/**
 * MenuTile icon の共通サイズ。本番 glyphicon は font-size 12px (= 12px × 12px box)。
 */
const ICON_CLASS = "w-[12px] h-[12px]";

export function meta(_: Route.MetaArgs) {
  return [
    { title: "WOLF MANSION 〜人狼館の事件簿村〜" },
    { name: "description", content: "人狼ゲーム WOLF MANSION" },
    { property: "og:title", content: "WOLF MANSION" },
    { property: "og:image", content: "/wolf-mansion/img/ogp-top.png" },
  ];
}

export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  try {
    const villages = await fetchVillages({ statuses: [...TOP_STATUSES] }, api);
    return { villages };
  } catch {
    return { villages: { list: [] } satisfies VillagesView };
  }
}

/**
 * 旧 templates/index.html を React で復元 (Step 13b で本番一致に修正)。
 *
 * 旧画面の構造:
 *   1. top.jpg hero + anima ロゴ (左下) + ユーザID (右下、ログイン時のみ)
 *   2. メインメニュー: About / Intro / Announce / Rule / FAQ / Skill の 6 タイル (2 行 × 3)
 *   3. 登録/ログイン:
 *      - 未ログイン: Register (ID 登録) / Login の 2-up
 *      - ログイン中: MyPage / ChangePassword / Logout の 3-up
 *   4. 開催中の村 (table)
 *   5. 村一覧 / 村作成:
 *      - 未ログイン: Village list 単独 (col-sm-12)
 *      - ログイン中: Village list + Create Village (col-sm-6 × 2)
 *   6. ユーザー: User list (= /players、常時表示)
 *   7. キャラチップ: Character list (= /charachips、常時表示)
 *
 * 未実装ページ (about / intro / announce / rule / faq / new-player) は
 * placeholder ルートで「準備中」を返す (routes.ts 参照、Step 13e で実コンテンツ)。
 */
export default function Home({ loaderData }: Route.ComponentProps) {
  const meQuery = useMeQuery();
  const user = meQuery.data?.user;
  const logoutMutation = useLogoutMutation();

  const villagesQuery = useVillagesQuery(
    { statuses: [...TOP_STATUSES] },
    loaderData.villages,
  );
  const villages = villagesQuery.data?.list ?? [];

  return (
    <main className="max-w-[1170px] mx-auto">
      {/* --- 1. hero --- */}
      <div className="relative w-full mb-[15px]">
        <img
          src="/wolf-mansion/img/top.jpg"
          alt=""
          aria-hidden
          className="w-full block"
        />
        <span className="absolute bottom-0 left-[1.6em]">
          <WolfMansionLogo size="lg" block />
        </span>
        {user && (
          <span className="absolute bottom-[0.4em] right-[1.6em] text-white text-[1em]">
            ユーザID: {user.userId}
          </span>
        )}
      </div>

      {/* --- 2. メインメニュー (6 tiles, 旧 index.html と同じ並び) --- */}
      <MenuSection ariaLabel="メインメニュー">
        {/* 旧 h100px 行: 中央寄せキャッチコピー。本番 .h5 = 15px / weight 400 */}
        <div className="h-[100px] flex items-center justify-center px-[15px]">
          <h2 className="m-0 text-center text-[15px] font-normal leading-[1.1]">
            状況のみで推理・説得する、新しい人狼
          </h2>
        </div>
        {/* 旧 h100px 行 + style="padding-left:40px;padding-right:40px;word-break:break-word;line-height:1.5em"
            <br class="hidden-xs"> で sm+ では指定位置改行、xs では自然折返し */}
        <div
          className="h-[100px] flex items-center justify-center px-[40px] text-center leading-[1.5em]"
          style={{ wordBreak: "break-word" }}
        >
          <p className="m-0">
            WOLF MANSION では、
            <br className="hidden sm:inline" />
            占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と
            <br className="hidden sm:inline" />
            【投票】の 2 つを使って推理・説得する{" "}
            <br className="hidden sm:inline" />
            「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。
          </p>
        </div>
        <MenuTileRow cols={3}>
          <MenuTileLink
            to="/about"
            icon={<InformationCircleIcon className={ICON_CLASS} />}
            label="本サイトは"
            sublabel="About"
          />
          <MenuTileLink
            to="/intro"
            icon={<QuestionMarkCircleIcon className={ICON_CLASS} />}
            label="人狼館の事件簿村"
            sublabel="Introduction"
          />
          <MenuTileLink
            to="/announce"
            icon={<MegaphoneIcon className={ICON_CLASS} />}
            label="お知らせ"
            sublabel="Announce"
          />
        </MenuTileRow>
        <MenuTileRow cols={3}>
          <MenuTileLink
            to="/rule"
            icon={<BookOpenIcon className={ICON_CLASS} />}
            label="ルール"
            sublabel="Rule"
          />
          <MenuTileLink
            to="/faq"
            icon={<QuestionMarkCircleIcon className={ICON_CLASS} />}
            label="よくある質問"
            sublabel="FAQ"
          />
          <MenuTileLink
            to="/skills"
            icon={<BookmarkIcon className={ICON_CLASS} />}
            label="役職一覧"
            sublabel="Skill"
          />
        </MenuTileRow>
      </MenuSection>

      {/* --- 3. 登録 / ログイン --- */}
      <MenuSection title={<>登録 / ログイン</>}>
        {user ? (
          <MenuTileRow cols={3}>
            <MenuTileLink
              to="/me"
              icon={<UserIcon className={ICON_CLASS} />}
              label="マイページ"
              sublabel="My Page"
            />
            <MenuTileLink
              to={`/players/${encodeURIComponent(user.userId)}#password`}
              icon={<WrenchIcon className={ICON_CLASS} />}
              label="パスワード変更"
              sublabel="Change Password"
            />
            <MenuTileButton
              icon={<LockClosedIcon className={ICON_CLASS} />}
              label="ログアウト"
              sublabel="Logout"
              disabled={logoutMutation.isPending}
              onClick={() => logoutMutation.mutate()}
            />
          </MenuTileRow>
        ) : (
          <MenuTileRow cols={2}>
            <MenuTileLink
              to="/new-player"
              icon={<PencilSquareIcon className={ICON_CLASS} />}
              label="ID 登録"
              sublabel="Register"
            />
            <MenuTileLink
              to="/login"
              icon={<LockOpenIcon className={ICON_CLASS} />}
              label="ログイン"
              sublabel="Login"
            />
          </MenuTileRow>
        )}
      </MenuSection>

      {/* --- 4. 開催中の村 (本番計測準拠) --- */}
      <MenuSection title={<>開催中の村</>}>
        {villages.length === 0 ? (
          <p className="text-center py-4 text-white opacity-70">
            現在、開催中の村はありません
          </p>
        ) : (
          // 旧 .top-menu-selectable-area: bg #0b162a + border 1px #333
          <div className="bg-night-950 border border-night-700">
            <div className="w-full overflow-x-auto">
              <table className="w-full border-collapse text-[12px]">
                <tbody>
                  {villages.map((v) => {
                    const cellCls =
                      // td padding 0 + border 1px #464545 + hover で row 全体が mint に
                      "p-0 border border-[#464545] " +
                      "group-hover:bg-night-800 group-hover:text-mint-500";
                    return (
                      <tr key={v.id} className="group cursor-pointer transition-colors">
                        {/* 旧 col-sm-1 (8.3% ≒ 95px @ 1170 container) */}
                        <td className={cn(cellCls, "w-[8.333%] text-center")}>
                          <Link
                            to={`/villages/${v.id}`}
                            className="block p-[5px] text-white no-underline group-hover:text-mint-500"
                          >
                            {String(v.number).padStart(4, "0")}
                          </Link>
                        </td>
                        <td className={cn(cellCls, "text-left")}>
                          <Link
                            to={`/villages/${v.id}`}
                            className="block p-[5px] text-white no-underline group-hover:text-mint-500"
                          >
                            <VillageTag level={villageTagLevel(v.statusName)}>
                              {v.statusName}
                            </VillageTag>
                            {v.name}
                          </Link>
                        </td>
                        {/* 旧 col-sm-2 (16.6% ≒ 190px) */}
                        <td className={cn(cellCls, "w-[16.667%] text-center")}>
                          <Link
                            to={`/villages/${v.id}`}
                            className="block p-[5px] text-white no-underline group-hover:text-mint-500"
                          >
                            {v.participantCount}人
                          </Link>
                        </td>
                        <td className={cn(cellCls, "w-[16.667%] text-center")}>
                          <Link
                            to={`/villages/${v.id}`}
                            className="block p-[5px] text-white no-underline group-hover:text-mint-500"
                          >
                            {v.statusName}
                          </Link>
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
          </div>
        )}
      </MenuSection>

      {/* --- 5 & 6. 村一覧/村作成 + ユーザー (旧 col-sm-6 で 2 カラム横並び) --- */}
      <div className="grid grid-cols-1 sm:grid-cols-2">
        <MenuSection title={<>村一覧 / 村作成</>}>
          <MenuTileRow cols={user ? 2 : 1}>
            <MenuTileLink
              to="/villages"
              icon={<ClipboardDocumentListIcon className={ICON_CLASS} />}
              label="全村一覧"
              sublabel="Village list"
            />
            {user && (
              <MenuTileLink
                to="/new-village"
                icon={<PlusIcon className={ICON_CLASS} />}
                label="村を建てる"
                sublabel="Create Village"
              />
            )}
          </MenuTileRow>
        </MenuSection>

        <MenuSection title={<>ユーザー</>}>
          <MenuTileRow cols={1}>
            <MenuTileLink
              to="/players"
              icon={<UsersIcon className={ICON_CLASS} />}
              label="一覧"
              sublabel="User list"
            />
          </MenuTileRow>
        </MenuSection>
      </div>

      {/* --- 7. キャラチップ (常時表示、本番踏襲) --- */}
      <MenuSection title={<>キャラチップ</>}>
        <MenuTileRow cols={1}>
          <MenuTileLink
            to="/charachips"
            icon={<ClipboardDocumentListIcon className={ICON_CLASS} />}
            label="一覧"
            sublabel="Character list"
          />
        </MenuTileRow>
      </MenuSection>

      <PageFooter />
    </main>
  );
}

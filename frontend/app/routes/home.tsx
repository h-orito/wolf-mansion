import { Link } from "react-router";
import type { Route } from "./+types/home";
import {
  BookOpenIcon,
  ClipboardDocumentListIcon,
  DocumentTextIcon,
  LockClosedIcon,
  LockOpenIcon,
  PlusIcon,
  UserGroupIcon,
  UserIcon,
  UsersIcon,
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
import { Table, TableResponsive } from "~/components/ui/Table";
import { VillageTag } from "~/components/ui/VillageTag";

/** MenuTile icon の共通サイズ。文字サイズ拡大に追従するよう em 単位で。 */
const ICON_CLASS = "w-[2em] h-[2em]";

const TOP_STATUSES = ["募集中", "進行中", "エピローグ"] as const;

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
 * 旧 templates/index.html を React 上で復元。
 *
 * 旧画面の構造:
 *   1. top.jpg hero + anima ロゴ (左下) + ユーザID (右下、ログイン時のみ)
 *   2. メインメニュー (3-up tile grid)
 *   3. 登録/ログイン (ログイン状態で出し分け)
 *   4. 開催中の村 (table)
 *   5. 村一覧 / 村作成 (2-up tile)
 *   6. プレイヤー (1-up tile)
 *   7. キャラチップ (1-up tile)
 *
 * 旧 about / intro / announce / rule / faq / new-player は React 側で未実装の
 * ため、復元対象から除外 (HANDOFF と整合)。Step 13a スコープ外。
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
    <div className="max-w-screen-lg mx-auto">
      {/* --- 1. hero --- */}
      <div className="relative w-full mb-4">
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

      {/* --- 2. メインメニュー --- */}
      <MenuSection>
        <p className="text-center text-[1.17em] mb-1">
          状況のみで推理・説得する、新しい人狼
        </p>
        <p className="text-center mb-4 leading-[1.5em] px-4">
          WOLF MANSION では、占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と【投票】の
          2 つを使って推理・説得する「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。
        </p>
        <MenuTileRow cols={3}>
          <MenuTileLink
            to="/skills"
            icon={<BookOpenIcon className={ICON_CLASS} />}
            label="役職一覧"
            sublabel="Skill"
          />
          <MenuTileLink
            to="/charachips"
            icon={<UserGroupIcon className={ICON_CLASS} />}
            label="キャラチップ"
            sublabel="Character list"
          />
          <MenuTileLink
            to="/village-records"
            icon={<DocumentTextIcon className={ICON_CLASS} />}
            label="終了村一覧"
            sublabel="Village record"
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
              to={`/players/${encodeURIComponent(user.userId)}`}
              icon={<DocumentTextIcon className={ICON_CLASS} />}
              label="戦績"
              sublabel="Records"
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
              to="/login"
              icon={<LockOpenIcon className={ICON_CLASS} />}
              label="ログイン"
              sublabel="Login"
            />
            <MenuTileLink
              to="/players"
              icon={<UsersIcon className={ICON_CLASS} />}
              label="プレイヤー一覧"
              sublabel="Players"
            />
          </MenuTileRow>
        )}
      </MenuSection>

      {/* --- 4. 開催中の村 --- */}
      <MenuSection title={<>開催中の村</>}>
        {villages.length === 0 ? (
          <p className="text-center py-4 text-white opacity-70">
            現在、開催中の村はありません
          </p>
        ) : (
          <TableResponsive>
            <Table>
              <tbody>
                {villages.map((v) => (
                  <tr
                    key={v.id}
                    className="hover:bg-night-800 hover:text-mint-500 cursor-pointer transition-colors"
                  >
                    <td className="w-[3em] text-right">{v.number}</td>
                    <td>
                      <Link
                        to={`/villages/${v.id}`}
                        className="block text-white no-underline hover:text-mint-500"
                      >
                        <VillageTag level={villageTagLevel(v.statusName)}>
                          {v.statusName}
                        </VillageTag>
                        {v.name}
                      </Link>
                    </td>
                    <td className="w-[6em] text-right text-[0.95em]">
                      {v.spectatorCount > 0
                        ? `${v.participantCount} (${v.spectatorCount})`
                        : v.participantCount}
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </TableResponsive>
        )}
      </MenuSection>

      {/* --- 5. 村一覧 / 村作成 --- */}
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

      {/* --- 6. プレイヤー --- */}
      {user && (
        <MenuSection title={<>プレイヤー</>}>
          <MenuTileRow cols={1}>
            <MenuTileLink
              to="/players"
              icon={<UsersIcon className={ICON_CLASS} />}
              label="プレイヤー一覧"
              sublabel="Players"
            />
          </MenuTileRow>
        </MenuSection>
      )}
    </div>
  );
}

/** 旧画面の village-tag.success / danger の出し分け。 */
function villageTagLevel(statusName: string): "success" | "danger" | "default" {
  switch (statusName) {
    case "募集中":
      return "success";
    case "廃村":
      return "danger";
    default:
      return "default";
  }
}

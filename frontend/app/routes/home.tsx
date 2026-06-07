import {
  ArrowRightStartOnRectangleIcon,
  BookOpenIcon,
  InformationCircleIcon,
  ListBulletIcon,
  LockClosedIcon,
  MegaphoneIcon,
  PencilIcon,
  PlusIcon,
  QuestionMarkCircleIcon,
  UserIcon,
  WrenchIcon,
} from "@heroicons/react/24/outline";

import { Footer } from "~/components/layout/Footer";
import { logout } from "~/features/auth/api";
import { useInvalidateMe, useMe } from "~/features/auth/useMe";
import { MenuSection, TileAnchor, TileButton, TileRoute } from "~/features/home/MenuTile";
import { NOT_FINISHED_STATUSES, type SimpleVillageView } from "~/features/village/api";
import { participateNumLabel, villageListTags, villageNumber } from "~/features/village/format";
import { useVillages } from "~/features/village/useVillages";
import { legacyUrl } from "~/lib/api";
import type { Route } from "./+types/home";

export function meta(_: Route.MetaArgs) {
  const description =
    "WOLF MANSIONでは、占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と【投票】 の2つを使って推理・説得する 「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。";
  return [
    { title: "WOLF MANSION 〜人狼館の事件簿村〜" },
    { name: "description", content: description },
    { property: "og:site_name", content: "WOLF MANSION" },
    { property: "og:type", content: "website" },
    { property: "og:url", content: "https://wolfort.net/wolf-mansion/" },
    { property: "og:title", content: "WOLF MANSION" },
    { property: "og:description", content: description },
    // OGP 画像は本文のトップ画像と同じく backend (/wolf-mansion-api) 配信の静的アセット。
    // 静的アセットを frontend へ移管する Step 10/11 で /wolf-mansion 側へ更新する (08-step-plan 繰り越し事項)。
    {
      property: "og:image",
      content: "https://wolfort.net/wolf-mansion-api/app/images/ogp-top.png",
    },
    { name: "twitter:card", content: "summary_large_image" },
    { name: "twitter:site", content: "@ort_dev" },
  ];
}

export default function Home() {
  const { me } = useMe();
  // トップは未終了 (募集中/進行中/エピローグ) の村を表示。村一覧 API を status code で絞って共有利用する。
  const { data: villageData } = useVillages(NOT_FINISHED_STATUSES);
  const invalidateMe = useInvalidateMe();

  const onLogout = async () => {
    try {
      await logout();
    } finally {
      // 公開ページなので遷移は不要。me を取り直して未ログインへ収束させる (canCreateVillage も me 由来)。
      await invalidateMe();
    }
  };

  const villages = villageData?.villages ?? [];
  // 村作成可否はプレイヤーの情報 (me 由来)。匿名は false。
  const canCreateVillage = me?.canCreateVillage ?? false;

  return (
    // ページ地色は既存 `:8091` の body 背景 (#222) を全幅・全高で再現する。
    // body 自体は変えない (旧方針で作った認証画面 (明色) を壊さないため。認証画面は step-3.6 で忠実再現)。
    <div className="min-h-screen bg-wm-base text-xs">
      {/* 既存 Bootstrap3 .container と同じレスポンシブ最大幅 (768→750 / 992→970 / 1200→1170)。 */}
      <div className="mx-auto w-full min-[768px]:max-w-[750px] min-[992px]:max-w-[970px] min-[1200px]:max-w-[1170px]">
        {/* トップ画像 + ロゴ + (ログイン中) ユーザID。
            既存はトップ画像も col の 15px padding で帯と同じ幅 (横 15px インセット) になる。 */}
        <div className="mb-[15px] px-[15px]">
          <div className="relative">
            <img src={legacyUrl("/app/images/top.jpg")} alt="WOLF MANSION" className="w-full" />
            <span className="font-anima absolute bottom-0 left-5 text-2xl leading-6 text-white">
              <span className="text-wm-danger">W</span>OLF
              <br />
              <span className="text-wm-danger">M</span>ANSION
            </span>
            {me && (
              <span className="absolute right-5 bottom-1 text-white">ユーザID: {me.name}</span>
            )}
          </div>
        </div>

        {/* サイト紹介 + ナビ (見出し・説明は 100px 高の中央行。:8091 再現) */}
        <section className="bg-wm-band bg-clip-content p-[15px] text-center text-white">
          <div className="flex min-h-[100px] items-center justify-center">
            <h2 className="text-[15px] font-normal">状況のみで推理・説得する、新しい人狼</h2>
          </div>
          <div className="flex min-h-[100px] items-center justify-center px-10">
            <p className="leading-relaxed break-words">
              WOLF MANSIONでは、
              <br className="hidden sm:inline" />
              占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と
              <br className="hidden sm:inline" />
              【投票】 の2つを使って推理・説得する <br className="hidden sm:inline" />
              「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。
            </p>
          </div>
          <div className="grid grid-cols-3">
            <TileAnchor
              href={legacyUrl("/about")}
              icon={InformationCircleIcon}
              jp="本サイトは"
              en="About"
            />
            <TileAnchor
              href={legacyUrl("/intro")}
              icon={QuestionMarkCircleIcon}
              jp="人狼館の事件簿村"
              en="Introduction"
            />
            <TileAnchor
              href={legacyUrl("/announce")}
              icon={MegaphoneIcon}
              jp="お知らせ"
              en="Announce"
            />
            <TileAnchor href={legacyUrl("/rule")} icon={BookOpenIcon} jp="ルール" en="Rule" />
            <TileAnchor
              href={legacyUrl("/faq")}
              icon={QuestionMarkCircleIcon}
              jp="よくある質問"
              en="FAQ"
            />
            <TileAnchor href={legacyUrl("/skill")} icon={BookOpenIcon} jp="役職一覧" en="Skill" />
          </div>
        </section>

        {/* 登録/ログイン */}
        <MenuSection title="登録/ログイン">
          {me ? (
            <div className="grid grid-cols-3">
              <TileRoute to="/mypage" icon={UserIcon} jp="マイページ" en="My Page" />
              <TileRoute
                to="/change-password"
                icon={WrenchIcon}
                jp="パスワード変更"
                en="Change Password"
              />
              <TileButton
                onClick={onLogout}
                icon={ArrowRightStartOnRectangleIcon}
                jp="ログアウト"
                en="Logout"
              />
            </div>
          ) : (
            <div className="grid grid-cols-2">
              <TileRoute to="/signup" icon={PencilIcon} jp="ID登録" en="Register" />
              <TileRoute to="/login" icon={LockClosedIcon} jp="ログイン" en="Login" />
            </div>
          )}
        </MenuSection>

        {/* 開催中の村 */}
        <MenuSection title="開催中の村">
          <div className="overflow-x-auto bg-wm-tile">
            <table className="w-full border-collapse text-white">
              <tbody>
                {villages.map((v) => (
                  <VillageRow key={v.id} village={v} />
                ))}
              </tbody>
            </table>
          </div>
        </MenuSection>

        {/* 村一覧/村作成 + ユーザー (PC では横並び) */}
        <div className="flex flex-col sm:flex-row">
          <div className="sm:flex-1">
            <MenuSection title="村一覧/村作成">
              <div className={canCreateVillage ? "grid grid-cols-2" : "grid grid-cols-1"}>
                <TileAnchor
                  href={legacyUrl("/village-list")}
                  icon={ListBulletIcon}
                  jp="村一覧"
                  en="Village list"
                />
                {canCreateVillage && (
                  <TileAnchor
                    href={legacyUrl("/new-village")}
                    icon={PlusIcon}
                    jp="村を建てる"
                    en="Create Village"
                  />
                )}
              </div>
            </MenuSection>
          </div>
          <div className="sm:flex-1">
            <MenuSection title="ユーザー">
              <TileAnchor
                href={legacyUrl("/user-list")}
                icon={ListBulletIcon}
                jp="一覧"
                en="User list"
              />
            </MenuSection>
          </div>
        </div>

        {/* キャラチップ */}
        <MenuSection title="キャラチップ">
          <TileAnchor
            href={legacyUrl("/chara-group")}
            icon={ListBulletIcon}
            jp="一覧"
            en="Character list"
          />
        </MenuSection>

        <Footer />
      </div>
    </div>
  );
}

function VillageRow({ village }: { village: SimpleVillageView }) {
  const url = legacyUrl(`/village/${village.id}`);
  // 既存 (:8091) は番号/人数/状態が中央寄せ、村名のみ左寄せ (`.top-menu-inner` の text-align:center + td.text-left)。
  const cell = "border border-wm-band p-0";
  const link = "block p-[5px] text-white no-underline hover:text-wm-accent";
  return (
    <tr className="border border-wm-band hover:bg-wm-tile-hover">
      <td className={`${cell} text-center`}>
        <a href={url} className={link}>
          {villageNumber(village.id)}
        </a>
      </td>
      <td className={`${cell} text-left`}>
        <a href={url} className={link}>
          {villageListTags(village).map((tag) => (
            <span
              key={tag.name}
              className={`mr-1 rounded border px-1 ${
                tag.danger ? "border-wm-danger text-wm-danger" : "border-wm-accent text-wm-accent"
              }`}
            >
              {tag.name}
            </span>
          ))}
          {village.name}
        </a>
      </td>
      <td className={`${cell} text-center`}>
        <a href={url} className={link}>
          {participateNumLabel(village)}
        </a>
      </td>
      <td className={`${cell} text-center`}>
        <a href={url} className={link}>
          {village.status.name}
        </a>
      </td>
    </tr>
  );
}

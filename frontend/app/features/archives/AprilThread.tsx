import { Fragment } from "react";
import { Link } from "react-router";

import { logout } from "~/features/auth/api";
import { useInvalidateMe, useMe } from "~/features/auth/useMe";
import { NOT_FINISHED_STATUSES } from "~/features/villages/api";
import { participateNumLabel, villageListTags, villageNumber } from "~/features/villages/format";
import { useVillages } from "~/features/villages/useVillages";
import type { AprilPost, AprilSegment } from "./posts";

/** スレ内リンクの色 (明色地なので素のリンク色)。 */
const threadLink = "text-[blue] underline";

/** 2ch スレ風アーカイブ 1 ページ分 (スレタイトル + 投稿一覧)。 */
export function AprilThread({ title, posts }: { title: string; posts: AprilPost[] }) {
  return (
    <div className="font-aahub mb-[10px] bg-[#eeeeee] p-[10px] text-[12px] leading-[16px] text-black">
      <p className="mb-[10.5px] text-[16px] text-wm-danger">{title}</p>
      {posts.map((post) => (
        <ThreadPost key={post.no} post={post} />
      ))}
    </div>
  );
}

function ThreadPost({ post }: { post: AprilPost }) {
  const Body = post.bodyTag;
  return (
    <div id={post.anchorId} className="mb-[10px]">
      <div>
        {post.no <= 9 ? `${post.no} ` : `${post.no}`}:{" "}
        <span className="text-[green]">{post.name}</span>
        {post.datetime && (
          <>
            {" "}
            : {post.datetime} ID:
            {post.isOwner ? <span className="text-wm-danger">{post.posterId}</span> : post.posterId}
          </>
        )}
      </div>
      <Body className={post.bodyTag === "p" ? "mb-[10.5px] pl-[20px]" : "pl-[20px]"}>
        {post.body.map((seg, i) => (
          <Segment key={i} seg={seg} />
        ))}
      </Body>
    </div>
  );
}

function Segment({ seg }: { seg: AprilSegment }) {
  if ("anchor" in seg) {
    return (
      <a href={`#${seg.target}`} className={threadLink}>
        &gt;&gt;{seg.anchor}
      </a>
    );
  }
  if ("to" in seg) {
    return (
      <Link to={seg.to} className={threadLink}>
        {seg.text}
      </Link>
    );
  }
  if ("kind" in seg) {
    if (seg.kind === "authLinks") return <AuthLinks />;
    if (seg.kind === "villageList") return <VillageLines />;
    return <CreateVillageLink />;
  }
  return <MultilineText text={seg.text} />;
}

function MultilineText({ text }: { text: string }) {
  return text.split("\n").map((line, i) => (
    <Fragment key={i}>
      {i > 0 && <br />}
      {line}
    </Fragment>
  ));
}

/** ログイン状態で変わるリンク群。 */
function AuthLinks() {
  const { me } = useMe();
  const invalidateMe = useInvalidateMe();

  const onLogout = async () => {
    try {
      await logout();
    } finally {
      // 公開ページなので遷移は不要。me を取り直して未ログイン表示へ収束させる。
      await invalidateMe();
    }
  };

  if (me) {
    return (
      <div>
        あといま表示しているお前のIDは「{me.name}」
        <br />
        <Link to="/mypage" className={threadLink}>
          マイページ
        </Link>
        <br />
        <Link to="/change-password" className={threadLink}>
          パスワード変更
        </Link>
        <br />
        <button type="button" onClick={onLogout} className={`cursor-pointer ${threadLink}`}>
          ログアウト
        </button>
        <br />
      </div>
    );
  }
  return (
    <div>
      新規ID登録:{" "}
      <Link to="/signup" className={threadLink}>
        ttps://wolfort.net/wolf-mansion/new-player
      </Link>
      <br />
      ログイン:{" "}
      <Link to="/login" className={threadLink}>
        ttps://wolfort.net/wolf-mansion/login
      </Link>
      <br />
    </div>
  );
}

/** 未終了村の一覧行。トップと同じ村一覧 API (昇順) を使う。 */
function VillageLines() {
  const { data } = useVillages({ statuses: NOT_FINISHED_STATUSES, order: "asc" });
  const villages = data?.villages ?? [];
  return villages.map((v) => (
    <Fragment key={v.id}>
      <span>
        {villageNumber(v.id)}. {v.name}
      </span>{" "}
      {villageListTags(v).map((tag) => (
        <span key={tag.name}>({tag.name})</span>
      ))}{" "}
      <span>({v.status.name})</span> <span>({participateNumLabel(v)})</span>:{" "}
      <Link to={`/village/${v.id}`} className={threadLink}>
        ttps://wolfort.net/wolf-mansion/village/{v.id}
      </Link>
      <br />
    </Fragment>
  ));
}

/** 村作成リンク。村を建てられるユーザーにのみ出す。 */
function CreateVillageLink() {
  const { me } = useMe();
  if (!me?.canCreateVillage) return null;
  return (
    <span>
      村作成:{" "}
      <Link to="/new-village" className={threadLink}>
        ttps://wolfort.net/wolf-mansion/new-village
      </Link>
      <br />
    </span>
  );
}

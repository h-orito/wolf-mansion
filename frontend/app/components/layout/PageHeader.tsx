import { Link } from "react-router";
import { useMeQuery } from "~/features/auth/hooks";
import { WolfMansionLogo } from "~/components/ui/WolfMansionLogo";

/**
 * 旧 templates/layout/header.html の再現。
 *
 *   <div class="col-sm-12 col-xs-12 top-image">
 *     <a th:href="@{/}">
 *       <img th:src="@{/app/images/top-small.jpg}" style="width:100%;">
 *       <span class="font-anima" style="color:#fff;position:absolute;bottom:0;left:20px;font-size:16px;">
 *         <span style="color:#ff0000">W</span>OLF <span style="color:#ff0000">M</span>ANSION
 *       </span>
 *       <span th:if="${user!=null}" style="color:#fff;position:absolute;bottom:0;right:20px;">
 *         ユーザID: hoge
 *       </span>
 *     </a>
 *   </div>
 *
 * 旧画面と同じく **全ページ上部** に出す。/ への戻りリンクとしても機能する。
 */
export function PageHeader() {
  const meQuery = useMeQuery();
  const user = meQuery.data?.user;
  return (
    <div className="relative w-full mb-4">
      <Link
        to="/"
        aria-label="トップに戻る"
        className="block relative no-underline"
      >
        <img
          src="/wolf-mansion/img/top-small.jpg"
          alt=""
          aria-hidden
          className="w-full block"
        />
        <span className="absolute bottom-0 left-[1.6em]">
          <WolfMansionLogo size="sm" />
        </span>
        {user && (
          <span className="absolute bottom-0 right-[1.6em] text-white text-[1em]">
            ユーザID: {user.userId}
          </span>
        )}
      </Link>
    </div>
  );
}

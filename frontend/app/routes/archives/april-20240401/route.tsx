import { Link } from "react-router";

import { PageLayout } from "~/components/layout/PageLayout";
import { Heading } from "~/components/ui/Heading";
import { assetUrl } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("エイプリルフール企画 (2024/04/01)");
}

export default function April20240401() {
  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>エイプリルフール企画 (2024/04/01)</Heading>
        <p className="mb-[10px]">
          2024年4月1日には、トップページに以下の画像が表示されていました。それぞれの画像から該当する村に飛ぶことができます。
        </p>
        <div className="flex flex-col gap-[15px]">
          <Link to="/village/609">
            <img src={assetUrl("/app/images/april-top.png")} alt="609村の部屋割り" className="max-w-full" />
          </Link>
          <Link to="/village/552">
            <img src={assetUrl("/app/images/april-top2.png")} alt="552村の部屋割り" className="max-w-full" />
          </Link>
        </div>
      </div>
    </PageLayout>
  );
}

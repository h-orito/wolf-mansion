import { Fragment } from "react";

import { PageLayout } from "~/components/layout/PageLayout";
import { Heading } from "~/components/ui/Heading";
import { siteMeta } from "~/lib/meta";
import { aprilFoolDescriptions } from "./descriptions";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("エイプリルフール企画 (2026/04/01)");
}

export default function April20260401() {
  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>エイプリルフール企画 (2026/04/01)</Heading>
        <p className="mb-[10px]">
          2026年4月1日には、トップページを開くたびに以下の役職説明がランダムに1つ表示されていました。
        </p>
        {aprilFoolDescriptions.map((description, i) => (
          <div key={i} className="mb-[10px] rounded border border-white p-[5px]">
            <p>
              {description.map((segment, j) =>
                typeof segment === "string" ? (
                  <Fragment key={j}>{segment}</Fragment>
                ) : (
                  <strong key={j}>{segment.strong}</strong>
                ),
              )}
            </p>
          </div>
        ))}
      </div>
    </PageLayout>
  );
}

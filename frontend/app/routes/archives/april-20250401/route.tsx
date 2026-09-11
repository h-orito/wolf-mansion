import { PageLayout } from "~/components/layout/PageLayout";
import { AprilThread } from "~/features/archives/AprilThread";
import { siteMeta } from "~/lib/meta";
import { posts, threadTitle } from "./posts";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta();
}

export default function April20250401() {
  return (
    <PageLayout header={false}>
      <AprilThread title={threadTitle} posts={posts} />
    </PageLayout>
  );
}

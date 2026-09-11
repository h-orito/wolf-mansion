import { Fragment } from "react";

import { Heading } from "~/components/ui/Heading";
import { TextLink } from "~/components/ui/TextLink";
import { PageLayout } from "~/components/layout/PageLayout";
import { siteMeta } from "~/lib/meta";
import { releases, type ReleaseSegment } from "./releases";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("お知らせ");
}

export default function Announce() {
  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>リリースノート</Heading>
        <ul className="mb-[10.5px] list-disc pl-[20px]">
          {releases.map((release, i) => (
            <li key={i} className="mb-[10px]">
              <Segments segments={release.lead} />
              {release.items.length > 0 && (
                <ul className="list-[circle] pl-[20px]">
                  {release.items.map((item, j) => (
                    <li key={j}>
                      <Segments segments={item} />
                    </li>
                  ))}
                </ul>
              )}
            </li>
          ))}
        </ul>
      </div>
    </PageLayout>
  );
}

function Segments({ segments }: { segments: ReleaseSegment[] }) {
  return segments.map((seg, i) =>
    typeof seg === "string" ? (
      <Fragment key={i}>{seg}</Fragment>
    ) : (
      <TextLink key={i} to={seg.to}>
        {seg.text}
      </TextLink>
    ),
  );
}

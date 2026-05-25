import type { Route } from "./+types/intro";
import { PlaceholderPage, placeholderMeta } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return placeholderMeta("人狼館の事件簿村ルール");
}

export default function Intro() {
  return (
    <main className="max-w-[1170px] mx-auto">
      <PlaceholderPage
        title="人狼館の事件簿村"
        englishTitle="Introduction"
        description="占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と【投票】の 2 つを使って推理・説得する「人狼館の事件簿村」ルールの紹介。"
      />
    </main>
  );
}

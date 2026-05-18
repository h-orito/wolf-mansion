import type { Route } from "./+types/home";

export function meta(_: Route.MetaArgs) {
  return [
    { title: "wolf-mansion" },
    { name: "description", content: "人狼ゲーム wolf-mansion" },
    { property: "og:title", content: "wolf-mansion" },
    { property: "og:image", content: "/wolf-mansion/img/ogp-top.png" },
  ];
}

export default function Home() {
  return (
    <main className="min-h-screen bg-slate-900 text-slate-100 flex flex-col items-center justify-center px-6 py-16">
      <img
        src="/wolf-mansion/img/top.jpg"
        alt="wolf-mansion"
        className="w-full max-w-2xl rounded-lg shadow-xl"
      />
      <h1 className="mt-8 text-4xl font-bold tracking-wide">wolf-mansion</h1>
      <p className="mt-4 text-slate-300">人狼ゲーム (移行作業中)</p>
      <p className="mt-8 text-sm text-slate-400">
        フロントエンドの初期セットアップが完了しました。今後のステップで画面を実装していきます。
      </p>
    </main>
  );
}

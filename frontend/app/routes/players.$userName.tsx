import { Link } from "react-router";
import type { Route } from "./+types/players.$userName";
import { fetchPlayerDetail } from "~/features/player/api";
import { usePlayerDetailQuery } from "~/features/player/hooks";
import { PasswordChangeForm } from "~/features/player/PasswordChangeForm";
import { PlayerProfileForm } from "~/features/player/PlayerProfileForm";
import { PlayerRecords } from "~/features/player/PlayerRecords";
import { ssrFetch } from "~/lib/api/client";

export function meta({ params }: Route.MetaArgs) {
  return [{ title: `${params.userName} - プレイヤー詳細 - wolf-mansion` }];
}

export async function loader({ params, request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  const res = await api(`/api/v1/players/${encodeURIComponent(params.userName)}`);
  if (res.status === 404) {
    throw new Response("プレイヤーが見つかりません", { status: 404 });
  }
  if (!res.ok) {
    // 5xx 等は CSR で再 fetch させる: 最低限の placeholder を返す
    return { detail: null as Awaited<ReturnType<typeof fetchPlayerDetail>> | null, userName: params.userName };
  }
  const detail = (await res.json()) as Awaited<ReturnType<typeof fetchPlayerDetail>>;
  return { detail, userName: params.userName };
}

export default function PlayerDetailPage({ loaderData }: Route.ComponentProps) {
  const { userName } = loaderData;
  const detailQuery = usePlayerDetailQuery(userName, loaderData.detail ?? undefined);
  const detail = detailQuery.data ?? loaderData.detail;

  if (!detail) {
    return (
      <main className="min-h-screen bg-slate-900 text-slate-100">
        <section className="max-w-3xl mx-auto px-6 py-10">
          <p className="text-sm text-slate-400">読み込み中…</p>
        </section>
      </main>
    );
  }

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-3xl mx-auto px-6 py-10 space-y-6">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold">{detail.name}</h1>
          <Link to="/players" className="text-sm text-slate-400 hover:text-slate-200">
            ← プレイヤー一覧
          </Link>
        </div>

        <ProfileBlock detail={detail} />

        {detail.isSelf && (
          <>
            <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-3">
              <h2 className="text-lg font-bold">プロフィール編集</h2>
              <PlayerProfileForm
                userName={detail.name}
                initialTwitter={detail.twitterUserName}
                initialIntroduction={detail.introduction}
              />
            </section>

            <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-3">
              <h2 className="text-lg font-bold">パスワード変更</h2>
              <PasswordChangeForm />
            </section>
          </>
        )}

        <PlayerRecords detail={detail} />
      </section>
    </main>
  );
}

function ProfileBlock(props: { detail: NonNullable<Route.ComponentProps["loaderData"]["detail"]> }) {
  const { detail } = props;
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-2">
      <h2 className="text-lg font-bold">プロフィール</h2>
      <dl className="space-y-1 text-sm">
        <div className="flex gap-2">
          <dt className="text-slate-400 w-20 shrink-0">Twitter</dt>
          <dd className="text-slate-100">
            {detail.twitterUserName ? (
              <a
                href={`https://twitter.com/${encodeURIComponent(detail.twitterUserName)}`}
                target="_blank"
                rel="noreferrer noopener"
                className="text-indigo-300 hover:text-indigo-200 underline"
              >
                @{detail.twitterUserName}
              </a>
            ) : (
              <span className="text-slate-500">未設定</span>
            )}
          </dd>
        </div>
        <div className="flex gap-2">
          <dt className="text-slate-400 w-20 shrink-0">自己紹介</dt>
          <dd className="text-slate-100 whitespace-pre-wrap break-words">
            {detail.introduction ? detail.introduction : <span className="text-slate-500">未設定</span>}
          </dd>
        </div>
      </dl>
    </section>
  );
}

import { Link } from "react-router";
import type { Route } from "./+types/charachips.$id";
import { fetchCharachipDetail } from "~/features/meta/api";
import { useCharachipDetailQuery } from "~/features/meta/hooks";
import { ssrFetch } from "~/lib/api/client";

export function meta({ data }: Route.MetaArgs) {
  const name = data?.charachip?.name ?? "キャラチップ";
  return [{ title: `${name} - wolf-mansion` }];
}

export async function loader({ request, params }: Route.LoaderArgs) {
  const id = Number(params.id);
  if (!Number.isFinite(id)) throw new Response("invalid charachip id", { status: 400 });
  const api = ssrFetch(request);
  const charachip = await fetchCharachipDetail(id, api).catch(() => null);
  if (!charachip) throw new Response("charachip not found", { status: 404 });
  return { id, charachip };
}

export default function CharachipDetail({ loaderData }: Route.ComponentProps) {
  const { id, charachip: initial } = loaderData;
  const query = useCharachipDetailQuery(id, initial);
  const charachip = query.data ?? initial;

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-4xl mx-auto px-6 py-10 space-y-6">
        <header className="flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold">{charachip.name}</h1>
            <p className="text-xs text-slate-400">
              作: {charachip.designerName}
              {charachip.descriptionUrl && (
                <>
                  {" / "}
                  <a
                    href={charachip.descriptionUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="underline hover:text-indigo-300"
                  >
                    説明ページ
                  </a>
                </>
              )}
            </p>
            <p className="text-xs text-slate-500">
              キャラ名変更: {charachip.isAvailableChangeName ? "可" : "不可"}
            </p>
          </div>
          <Link to="/charachips" className="text-sm text-slate-400 hover:text-slate-200">
            ← キャラチップ一覧
          </Link>
        </header>

        <ul className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
          {charachip.charas.map((c) => (
            <li
              key={c.id}
              className="rounded-xl bg-slate-800/40 border border-slate-700 p-3 flex flex-col items-center gap-2"
            >
              <img
                src={c.defaultImageUrl}
                width={c.imageWidth}
                height={c.imageHeight}
                alt={c.name}
                className="bg-slate-900/60 rounded"
              />
              <p className="text-sm text-center truncate w-full">{c.name}</p>
              <p className="text-xs text-slate-400">[{c.shortName}]</p>
            </li>
          ))}
        </ul>
      </section>
    </main>
  );
}

import { useMemo, useState } from "react";
import { Link } from "react-router";
import type { Route } from "./+types/skills";
import { fetchSkillCatalog, type SkillCatalogView } from "~/features/meta/api";
import { useSkillCatalogQuery, useSkillSearchQuery } from "~/features/meta/hooks";
import { ssrFetch } from "~/lib/api/client";

export function meta(_: Route.MetaArgs) {
  return [{ title: "役職一覧 - wolf-mansion" }];
}

export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  const catalog = await fetchSkillCatalog(api).catch(() => null);
  return { catalog };
}

export default function SkillsPage({ loaderData }: Route.ComponentProps) {
  const fallback: SkillCatalogView = { camps: [], tags: [] };
  const initial = loaderData.catalog ?? fallback;
  const query = useSkillCatalogQuery(initial);
  const catalog = query.data ?? initial;

  // 絞り込みフィルタ (タグ複数選択 + 名前部分一致)
  const [selectedTags, setSelectedTags] = useState<string[]>([]);
  const [keyword, setKeyword] = useState("");
  const searchParams = useMemo(
    () => ({
      tags: selectedTags.length === 0 ? undefined : selectedTags.join(","),
      name: keyword.trim() || undefined,
    }),
    [selectedTags, keyword],
  );
  const hasFilter = selectedTags.length > 0 || keyword.trim() !== "";
  const search = useSkillSearchQuery(searchParams, hasFilter);
  const matchedCodes = new Set((search.data ?? []).map((c) => c.toLowerCase()));

  function toggleTag(tag: string) {
    setSelectedTags((prev) =>
      prev.includes(tag) ? prev.filter((t) => t !== tag) : [...prev, tag],
    );
  }

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-4xl mx-auto px-6 py-10 space-y-6">
        <header className="flex items-center justify-between">
          <h1 className="text-2xl font-bold">役職一覧</h1>
          <Link to="/" className="text-sm text-slate-400 hover:text-slate-200">
            ← トップへ
          </Link>
        </header>

        <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-3">
          <h2 className="text-sm text-slate-300">絞り込み</h2>
          <label className="block text-xs text-slate-400">
            名前
            <input
              type="text"
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              placeholder="部分一致 (例: 占い)"
              className="mt-1 w-full bg-slate-900 border border-slate-700 rounded px-2 py-1 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500"
            />
          </label>
          {catalog.tags.length > 0 && (
            <div className="space-y-1">
              <p className="text-xs text-slate-400">タグ (複数選択可、OR 条件)</p>
              <div className="flex flex-wrap gap-1">
                {catalog.tags.map((tag) => {
                  const active = selectedTags.includes(tag);
                  return (
                    <button
                      key={tag}
                      type="button"
                      onClick={() => toggleTag(tag)}
                      className={
                        "rounded-full px-2.5 py-0.5 text-xs border transition " +
                        (active
                          ? "bg-indigo-500/30 border-indigo-400 text-indigo-100"
                          : "border-slate-600 text-slate-300 hover:border-slate-400")
                      }
                    >
                      {tag}
                    </button>
                  );
                })}
              </div>
            </div>
          )}
          {hasFilter && (
            <p className="text-xs text-slate-500">
              {search.isLoading
                ? "絞り込み中..."
                : `該当 ${matchedCodes.size} 件`}
            </p>
          )}
        </section>

        <div className="space-y-6">
          {catalog.camps.map((camp) => {
            const skills = hasFilter
              ? camp.skills.filter((s) => matchedCodes.has(s.code.toLowerCase()))
              : camp.skills;
            if (hasFilter && skills.length === 0) return null;
            return (
              <section
                key={camp.campCode}
                className="rounded-xl bg-slate-800/40 border border-slate-700 p-4"
              >
                <h3 className="text-sm font-medium text-amber-200 mb-3">
                  {camp.campName} ({skills.length})
                </h3>
                <ul className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-2">
                  {skills.map((s) => (
                    <li
                      key={s.code}
                      className="border border-slate-700 rounded px-2 py-1 text-sm flex items-center gap-2"
                    >
                      <span className="font-mono text-xs text-slate-400 w-5 shrink-0">
                        {s.shortName}
                      </span>
                      <span className="truncate">{s.name}</span>
                    </li>
                  ))}
                </ul>
              </section>
            );
          })}
        </div>
      </section>
    </main>
  );
}

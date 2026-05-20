import { Link, useNavigate } from "react-router";
import type { Route } from "./+types/villages.$id.settings";
import { fetchSettingsForm, fetchVillage } from "~/features/village/detail/api";
import { useSettingsFormQuery, useVillageQuery } from "~/features/village/detail/hooks";
import { SettingsForm } from "~/features/village/detail/SettingsForm";
import { ssrFetch } from "~/lib/api/client";

/**
 * 村設定変更画面 (creator 専用ルート、Step 8f)。
 *
 * SSR loader で村と設定フォームをまとめて取りに行く。失敗時 (= 認可なし / 村なし) は
 * loader 内で 403/404 を投げる。
 *
 * 進行中以降は backend 側の `assertModifySetting` で 400 になるが、UI でも先回りして
 * フォーム表示前にステータスを判定し「現在は変更できません」を表示する。
 */
export function meta({ data }: Route.MetaArgs) {
  const name = data?.village?.name ?? "村設定";
  return [{ title: `${name} - 設定変更` }];
}

export async function loader({ request, params }: Route.LoaderArgs) {
  const villageId = Number(params.id);
  if (!Number.isFinite(villageId)) {
    throw new Response("invalid village id", { status: 400 });
  }
  const api = ssrFetch(request);
  const village = await fetchVillage(villageId, api).catch(() => null);
  if (!village) throw new Response("village not found", { status: 404 });
  // 設定フォームは creator 以外 (= 未認証含む) なら 400 になる。
  // SSR の段階で取れなければ catch して null を返し、クライアント側で「creator のみ」表示にする。
  const settingsForm = await fetchSettingsForm(villageId, api).catch(() => null);
  return { villageId, village, settingsForm };
}

export default function VillageSettings({ loaderData }: Route.ComponentProps) {
  const { villageId, village: initialVillage, settingsForm: initialForm } = loaderData;
  const navigate = useNavigate();

  const villageQuery = useVillageQuery(villageId, initialVillage);
  const village = villageQuery.data ?? initialVillage;
  const isPrologue = village.statusCode === "IN_PREPARATION";

  // creator 認可が無いと initialForm は null になる。クライアントの再 fetch も
  // どうせ失敗するので、creator っぽいときだけ enable する (= isCreator)。
  const settingsQuery = useSettingsFormQuery(villageId, village.isCreator);
  const settingsForm = settingsQuery.data ?? initialForm;

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-3xl mx-auto px-6 py-8 space-y-4">
        <header className="flex items-center justify-between">
          <h1 className="text-xl font-bold">
            村設定変更 <span className="text-slate-400 text-sm">#{village.number} {village.name}</span>
          </h1>
          <Link to={`/villages/${villageId}`} className="text-sm text-slate-400 hover:text-slate-200">
            ← 村画面
          </Link>
        </header>

        {!settingsForm ? (
          <p className="text-sm text-rose-300">
            この村の設定を編集する権限がありません (creator のみ)。
            <button
              type="button"
              onClick={() => navigate(`/villages/${villageId}`)}
              className="ml-3 underline"
            >
              村画面に戻る
            </button>
          </p>
        ) : !isPrologue ? (
          <p className="text-sm text-amber-300">
            プロローグ中以外は設定を変更できません (現在: {village.statusName})。
          </p>
        ) : (
          <SettingsForm villageId={villageId} initial={settingsForm} />
        )}
      </section>
    </main>
  );
}

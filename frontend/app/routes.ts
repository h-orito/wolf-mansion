import { type RouteConfig, index, layout, route } from "@react-router/dev/routes";

export default [
  index("routes/home.tsx"),
  route("login", "routes/login.tsx"),
  route("villages", "routes/villages._index.tsx"),
  route("villages/:id", "routes/villages.$id.tsx"),
  route("villages/:id/settings", "routes/villages.$id.settings.tsx"),
  route("villages/:id/scrap", "routes/villages.$id.scrap.tsx"),
  route("players", "routes/players._index.tsx"),
  // NOTE: 認証不要。`isSelf=true` のときだけ編集 UI が出る (backend が viewer JWT を読んで判定)。
  route("players/:userName", "routes/players.$userName.tsx"),
  route("charachips", "routes/charachips._index.tsx"),
  route("charachips/:id", "routes/charachips.$id.tsx"),
  route("skills", "routes/skills.tsx"),
  route("village-records", "routes/village-records.tsx"),
  // 本番 wolfort.net に存在し React 未移植のページ用 placeholder (Step 13b 追加、
  // 13e で実コンテンツに置き換え予定)。home の MenuTile から参照されるため、
  // 404 を避ける目的で「準備中」スタブを返す
  route("about", "routes/about.tsx"),
  route("intro", "routes/intro.tsx"),
  route("announce", "routes/announce.tsx"),
  route("rule", "routes/rule.tsx"),
  route("faq", "routes/faq.tsx"),
  route("new-player", "routes/new-player.tsx"),
  // 認証必須エリア
  layout("routes/_auth.tsx", [
    route("me", "routes/me.tsx"),
    route("new-village", "routes/new-village.tsx"),
  ]),
] satisfies RouteConfig;

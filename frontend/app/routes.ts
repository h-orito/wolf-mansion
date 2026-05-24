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
  // 認証必須エリア
  layout("routes/_auth.tsx", [
    route("me", "routes/me.tsx"),
    route("new-village", "routes/new-village.tsx"),
  ]),
] satisfies RouteConfig;

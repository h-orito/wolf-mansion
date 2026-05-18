import { type RouteConfig, index, layout, route } from "@react-router/dev/routes";

export default [
  index("routes/home.tsx"),
  route("login", "routes/login.tsx"),
  route("villages", "routes/villages._index.tsx"),
  // 認証必須エリア
  layout("routes/_auth.tsx", [
    route("me", "routes/me.tsx"),
  ]),
] satisfies RouteConfig;

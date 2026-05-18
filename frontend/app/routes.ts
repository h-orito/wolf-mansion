import { type RouteConfig, index, layout, route } from "@react-router/dev/routes";

export default [
  index("routes/home.tsx"),
  route("login", "routes/login.tsx"),
  // 認証必須エリア
  layout("routes/_auth.tsx", [
    route("me", "routes/me.tsx"),
  ]),
] satisfies RouteConfig;

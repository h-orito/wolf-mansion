import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
  index("routes/home/route.tsx"),
  route("intro", "routes/intro/route.tsx"),
  route("village-list", "routes/village-list/route.tsx"),
  route("skill", "routes/skill/route.tsx"),
  route("rule", "routes/rule/route.tsx"),
  route("login", "routes/login.tsx"),
  route("signup", "routes/signup.tsx"),
  route("mypage", "routes/mypage.tsx"),
  route("change-password", "routes/change-password.tsx"),
] satisfies RouteConfig;

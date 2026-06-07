import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
  index("routes/home/route.tsx"),
  route("login", "routes/login.tsx"),
  route("signup", "routes/signup.tsx"),
  route("mypage", "routes/mypage.tsx"),
  route("change-password", "routes/change-password.tsx"),
] satisfies RouteConfig;

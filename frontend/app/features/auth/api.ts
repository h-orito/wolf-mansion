import type { components } from "~/lib/api/generated";
import { browserFetch } from "~/lib/api/client";

export type MeResponse = components["schemas"]["MeResponse"];
export type UserPayload = components["schemas"]["UserPayload"];
export type LoginBody = components["schemas"]["LoginBody"];

export async function fetchMe(): Promise<MeResponse> {
  const res = await browserFetch("/api/v1/auth/me", { method: "GET" });
  if (!res.ok) throw new Error(`auth/me failed: ${res.status}`);
  return res.json();
}

export async function login(body: LoginBody): Promise<MeResponse> {
  const res = await browserFetch("/api/v1/auth/login", {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (res.status === 401) {
    throw new InvalidCredentialsError();
  }
  if (!res.ok) throw new Error(`login failed: ${res.status}`);
  return res.json();
}

export async function logout(): Promise<void> {
  const res = await browserFetch("/api/v1/auth/logout", { method: "POST" });
  if (!res.ok && res.status !== 204) throw new Error(`logout failed: ${res.status}`);
}

export class InvalidCredentialsError extends Error {
  constructor() {
    super("invalid credentials");
    this.name = "InvalidCredentialsError";
  }
}

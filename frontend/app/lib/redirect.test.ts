import { describe, expect, it } from "vitest";
import { sanitizeRedirect } from "./redirect";

describe("sanitizeRedirect", () => {
  it("null/空文字/undefined は / を返す", () => {
    expect(sanitizeRedirect(null)).toBe("/");
    expect(sanitizeRedirect(undefined)).toBe("/");
    expect(sanitizeRedirect("")).toBe("/");
  });

  it("正しい in-app パスはそのまま返す", () => {
    expect(sanitizeRedirect("/me")).toBe("/me");
    expect(sanitizeRedirect("/villages/123")).toBe("/villages/123");
    expect(sanitizeRedirect("/villages/123?day=2")).toBe("/villages/123?day=2");
  });

  it("空に近いパスでも / に正規化される", () => {
    expect(sanitizeRedirect("/")).toBe("/");
  });

  describe("open redirect 防御", () => {
    it("protocol-relative URL は弾く (//evil.com)", () => {
      expect(sanitizeRedirect("//evil.example.com")).toBe("/");
      expect(sanitizeRedirect("//evil.example.com/path")).toBe("/");
    });

    it("encoded protocol-relative URL も弾く (%2F%2Fevil)", () => {
      expect(sanitizeRedirect("%2F%2Fevil.example.com")).toBe("/");
      expect(sanitizeRedirect("%2f%2fevil.example.com")).toBe("/");
    });

    it("absolute URL は弾く", () => {
      expect(sanitizeRedirect("http://evil.example.com")).toBe("/");
      expect(sanitizeRedirect("https://evil.example.com/path")).toBe("/");
    });

    it("javascript: スキームは弾く", () => {
      expect(sanitizeRedirect("javascript:alert(1)")).toBe("/");
      expect(sanitizeRedirect("javascript:%0Aalert(1)")).toBe("/");
    });

    it("data: スキームは弾く", () => {
      expect(sanitizeRedirect("data:text/html,<script>alert(1)</script>")).toBe("/");
    });

    it("制御文字を含む文字列は弾く", () => {
      expect(sanitizeRedirect("/path\nFoo")).toBe("/");
      expect(sanitizeRedirect("/path\tFoo")).toBe("/");
      expect(sanitizeRedirect("/path\r\nLocation: x")).toBe("/");
    });

    it("/ で始まらないパス (相対パスや絶対URLの一部) は弾く", () => {
      expect(sanitizeRedirect("me")).toBe("/");
      expect(sanitizeRedirect("foo/bar")).toBe("/");
    });

    it("@ を含む URL (user:pass@host) も弾く", () => {
      expect(sanitizeRedirect("//user@evil.example.com")).toBe("/");
    });
  });
});

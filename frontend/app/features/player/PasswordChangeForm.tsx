import { useState, type FormEvent } from "react";
import { useChangePasswordMutation } from "./hooks";

/**
 * 自分のパスワード変更フォーム。
 *
 * backend 側は新パスワード + 確認用 2 つだけを受け取る (旧実装互換、現パスワード再入力なし)。
 * 文字数 3〜12、英数字のみ、というルールも backend と揃える。
 */
export function PasswordChangeForm() {
  const mutation = useChangePasswordMutation();
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [done, setDone] = useState(false);
  const [clientError, setClientError] = useState<string | null>(null);

  function validate(): string | null {
    if (password.length < 3 || password.length > 12) {
      return "パスワードは英数字 3〜12 文字で入力してください。";
    }
    if (!/^[a-zA-Z0-9]+$/.test(password)) {
      return "パスワードは英数字のみ使用できます。";
    }
    if (password !== confirmPassword) {
      return "確認用パスワードが一致しません。";
    }
    return null;
  }

  async function onSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setDone(false);
    const err = validate();
    if (err) {
      setClientError(err);
      return;
    }
    setClientError(null);
    try {
      await mutation.mutateAsync({ password, confirmPassword });
      setDone(true);
      setPassword("");
      setConfirmPassword("");
    } catch {
      // mutation.error で表示
    }
  }

  const errorMessage =
    clientError ??
    (mutation.error ? "パスワード変更に失敗しました。入力を見直すか、しばらく経ってから再試行してください。" : null);

  return (
    <form onSubmit={onSubmit} className="space-y-3">
      <div className="space-y-1">
        <label htmlFor="new-password" className="block text-sm font-medium text-slate-300">
          新しいパスワード
        </label>
        <input
          id="new-password"
          type="password"
          value={password}
          autoComplete="new-password"
          onChange={(e) => setPassword(e.target.value)}
          className="w-full rounded-md bg-slate-900 border border-slate-600 px-3 py-2 text-slate-100 focus:border-indigo-400 focus:outline-none"
          placeholder="英数字 3〜12 文字"
        />
      </div>

      <div className="space-y-1">
        <label htmlFor="confirm-password" className="block text-sm font-medium text-slate-300">
          確認用パスワード
        </label>
        <input
          id="confirm-password"
          type="password"
          value={confirmPassword}
          autoComplete="new-password"
          onChange={(e) => setConfirmPassword(e.target.value)}
          className="w-full rounded-md bg-slate-900 border border-slate-600 px-3 py-2 text-slate-100 focus:border-indigo-400 focus:outline-none"
        />
      </div>

      {errorMessage && (
        <p role="alert" className="text-sm text-red-400">
          {errorMessage}
        </p>
      )}
      {done && (
        <p role="status" className="text-sm text-emerald-400">
          パスワードを変更しました。
        </p>
      )}

      <button
        type="submit"
        disabled={mutation.isPending}
        className="rounded-md bg-rose-600 hover:bg-rose-500 disabled:bg-slate-600 disabled:cursor-not-allowed px-5 py-2 font-semibold transition"
      >
        {mutation.isPending ? "変更中..." : "パスワードを変更"}
      </button>
    </form>
  );
}

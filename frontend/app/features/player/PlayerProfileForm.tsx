import { useEffect, useState, type FormEvent } from "react";
import { useUpdateProfileMutation } from "./hooks";

/**
 * 自分のプロフィール (Twitter / 自己紹介) 編集フォーム。
 *
 * 親 (`players.$userName.tsx`) で `isSelf=true` のときのみ描画される前提。
 * 成功すると detail / me cache が invalidate される → 親側で最新値が再 fetch される。
 */
export function PlayerProfileForm(props: {
  userName: string;
  initialTwitter: string | null | undefined;
  initialIntroduction: string | null | undefined;
}) {
  const { userName, initialTwitter, initialIntroduction } = props;
  const mutation = useUpdateProfileMutation(userName);
  const [twitter, setTwitter] = useState(initialTwitter ?? "");
  const [introduction, setIntroduction] = useState(initialIntroduction ?? "");
  const [done, setDone] = useState(false);

  // detail 再 fetch で初期値が変わった場合は同期する (自身の編集成功後など)
  useEffect(() => {
    setTwitter(initialTwitter ?? "");
  }, [initialTwitter]);
  useEffect(() => {
    setIntroduction(initialIntroduction ?? "");
  }, [initialIntroduction]);

  async function onSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setDone(false);
    try {
      await mutation.mutateAsync({
        twitterUserName: twitter.trim() === "" ? null : twitter.trim(),
        introduction: introduction.trim() === "" ? null : introduction.trim(),
      });
      setDone(true);
    } catch {
      // mutation.error で表示する
    }
  }

  return (
    <form onSubmit={onSubmit} className="space-y-3">
      <div className="space-y-1">
        <label htmlFor="twitter" className="block text-sm font-medium text-slate-300">
          Twitter ユーザ名
        </label>
        <input
          id="twitter"
          type="text"
          value={twitter}
          maxLength={50}
          onChange={(e) => setTwitter(e.target.value)}
          className="w-full rounded-md bg-slate-900 border border-slate-600 px-3 py-2 text-slate-100 focus:border-indigo-400 focus:outline-none"
          placeholder="例: my_handle (@ なし)"
        />
      </div>

      <div className="space-y-1">
        <label htmlFor="introduction" className="block text-sm font-medium text-slate-300">
          自己紹介
        </label>
        <textarea
          id="introduction"
          value={introduction}
          maxLength={2000}
          rows={4}
          onChange={(e) => setIntroduction(e.target.value)}
          className="w-full rounded-md bg-slate-900 border border-slate-600 px-3 py-2 text-slate-100 focus:border-indigo-400 focus:outline-none"
          placeholder="他プレイヤーに見せる自己紹介 (2000 文字まで)"
        />
        <p className="text-xs text-slate-500">{introduction.length} / 2000</p>
      </div>

      {mutation.error && (
        <p role="alert" className="text-sm text-red-400">
          更新に失敗しました。入力を見直すか、しばらく経ってから再試行してください。
        </p>
      )}
      {done && (
        <p role="status" className="text-sm text-emerald-400">
          プロフィールを更新しました。
        </p>
      )}

      <button
        type="submit"
        disabled={mutation.isPending}
        className="rounded-md bg-indigo-500 hover:bg-indigo-400 disabled:bg-slate-600 disabled:cursor-not-allowed px-5 py-2 font-semibold transition"
      >
        {mutation.isPending ? "更新中..." : "プロフィールを更新"}
      </button>
    </form>
  );
}

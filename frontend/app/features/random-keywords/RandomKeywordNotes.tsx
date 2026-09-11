import {
  RANDOM_KEYWORD_MAX_LENGTH,
  RANDOM_KEYWORD_MESSAGE_MAX_LENGTH,
  RANDOM_KEYWORD_MIN_LENGTH,
} from "~/api/constants";

/** 作成・編集フォーム共通の注意書き。文字数は生成定数 (backend 由来) を使う。 */
export function RandomKeywordNotes() {
  return (
    <ul className="mb-[10.5px] list-disc pl-[20px]">
      <li className="mb-[10px]">キーワードは半角英語のみで、orとwhoを含むことはできません。</li>
      <li className="mb-[10px]">
        キーワードは{RANDOM_KEYWORD_MIN_LENGTH}文字以上{RANDOM_KEYWORD_MAX_LENGTH}
        文字以内、変換後文字列はそれぞれ{RANDOM_KEYWORD_MESSAGE_MAX_LENGTH}
        文字以内で入力してください。
      </li>
      <li className="mb-[10px]">[[キーワード]]→変換後文字列 になります</li>
    </ul>
  );
}

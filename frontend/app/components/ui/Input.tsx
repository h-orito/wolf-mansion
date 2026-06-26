/**
 * 入力欄のクラス定義。ダーク地で見えるよう入力欄のみ明色 (白地・薄枠) にする。
 * フォーム部品はダークテーマ画面共通でこれを使う。
 */

const baseClass = "rounded border border-gray-400 bg-white px-[10px] py-[5px] text-[#555555]";

/** 1 行テキスト入力 (フォーム行いっぱいに広げる)。 */
export const inputClass = `min-h-[30px] w-full ${baseClass}`;

/** 1 行テキスト入力 (インライン配置用。幅は内容・呼び出し側に任せる)。 */
export const inlineInputClass = `min-h-[30px] ${baseClass}`;

/** 複数行テキスト入力。 */
export const textareaClass = `w-full ${baseClass}`;

/** セレクトボックス (フォーム行いっぱいに広げる)。 */
export const selectClass = `min-h-[30px] w-full ${baseClass}`;

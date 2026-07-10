/**
 * キャラ画像リストから通常差分を選ぶ。通常差分を持たない場合
 * (オリジナルキャラチップは faceType.code が独自 ID になる) は先頭にフォールバックする。
 */
export function findNormalImage<T extends { faceType: { code: string } }>(
  images: T[] | undefined,
): T | undefined {
  return images?.find((i) => i.faceType.code === "NORMAL") ?? images?.[0];
}

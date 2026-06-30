import { get, ref, set } from "firebase/database";

import { getFirebaseDb } from "~/lib/firebase";
import type { PlayerMemo } from "./types";

export async function fetchPlayerMemo(
  playerId: number,
  villageId: number,
): Promise<PlayerMemo | null> {
  const db = getFirebaseDb();
  if (!db) return null;
  const snapshot = await get(ref(db, `players/${playerId}/village/${villageId}/memo`));
  return snapshot.val() ?? null;
}

export async function savePlayerMemo(
  playerId: number,
  villageId: number,
  memo: PlayerMemo,
): Promise<void> {
  const db = getFirebaseDb();
  if (!db) return;
  await set(ref(db, `players/${playerId}/village/${villageId}/memo`), memo);
}

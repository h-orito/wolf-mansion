import { useCallback, useRef } from "react";

import { useInvalidateVillage } from "~/features/village/useVillage";

export function useRefresh(villageId: number, initializers: Array<() => void>) {
  const invalidate = useInvalidateVillage(villageId);
  const initRef = useRef(initializers);
  initRef.current = initializers;

  const refresh = useCallback(async () => {
    await invalidate();
    requestAnimationFrame(() => {
      for (const init of initRef.current) init();
    });
  }, [invalidate]);

  return { refresh, invalidate };
}

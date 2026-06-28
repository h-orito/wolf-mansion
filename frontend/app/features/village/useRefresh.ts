import { createContext, useCallback, useContext, useEffect, useRef } from "react";

import { useInvalidateVillage } from "~/features/village/useVillage";

type UnregisterFn = () => void;
type RegisterFn = (initializer: () => void) => UnregisterFn;

export const RefreshContext = createContext<RegisterFn | null>(null);

export function useRefresh(villageId: number) {
  const invalidate = useInvalidateVillage(villageId);
  const initializersRef = useRef<Array<() => void>>([]);

  const register = useCallback<RegisterFn>((fn) => {
    initializersRef.current = [...initializersRef.current, fn];
    return () => {
      initializersRef.current = initializersRef.current.filter((f) => f !== fn);
    };
  }, []);

  const refresh = useCallback(async () => {
    await invalidate();
    requestAnimationFrame(() => {
      for (const init of initializersRef.current) init();
    });
  }, [invalidate]);

  return { refresh, invalidate, register };
}

export function useRegisterRefresh(initializer: () => void) {
  const register = useContext(RefreshContext);
  const ref = useRef(initializer);
  ref.current = initializer;

  useEffect(() => {
    if (register == null) return;
    return register(() => ref.current());
  }, [register]);
}

import { createContext, useCallback, useContext, useEffect, useRef, useState } from "react";

import { useInvalidateVillage } from "~/features/village/useVillage";

type UnregisterFn = () => void;
type RegisterFn = (initializer: () => void) => UnregisterFn;

export const RefreshContext = createContext<RegisterFn | null>(null);

export function useRefresh(villageId: number) {
  const invalidate = useInvalidateVillage(villageId);
  const initializersRef = useRef<Array<() => void>>([]);
  const [initSignal, setInitSignal] = useState(0);

  const register = useCallback<RegisterFn>((fn) => {
    initializersRef.current = [...initializersRef.current, fn];
    return () => {
      initializersRef.current = initializersRef.current.filter((f) => f !== fn);
    };
  }, []);

  useEffect(() => {
    if (initSignal === 0) return;
    for (const init of initializersRef.current) init();
  }, [initSignal]);

  const refresh = useCallback(async () => {
    await invalidate();
    setInitSignal((n) => n + 1);
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

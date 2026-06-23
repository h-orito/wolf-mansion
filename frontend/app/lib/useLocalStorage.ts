import { useCallback, useSyncExternalStore } from "react";

const listeners = new Map<string, Set<() => void>>();

function notify(key: string) {
  listeners.get(key)?.forEach((cb) => cb());
}

function subscribe(key: string, cb: () => void) {
  let set = listeners.get(key);
  if (set == null) {
    set = new Set();
    listeners.set(key, set);
  }
  set.add(cb);
  return () => {
    set.delete(cb);
    if (set.size === 0) listeners.delete(key);
  };
}

export function useLocalStorage(key: string, defaultValue: string): [string, (v: string) => void] {
  const sub = useCallback((cb: () => void) => subscribe(key, cb), [key]);
  const value = useSyncExternalStore(
    sub,
    () => localStorage.getItem(key) ?? defaultValue,
    () => defaultValue,
  );
  const setValue = useCallback(
    (v: string) => {
      localStorage.setItem(key, v);
      notify(key);
    },
    [key],
  );
  return [value, setValue];
}

export function useLocalStorageBool(key: string, defaultValue: boolean): [boolean, () => void] {
  const [raw, setRaw] = useLocalStorage(key, String(defaultValue));
  const value = raw === "true";
  const toggle = useCallback(() => setRaw(String(!value)), [value, setRaw]);
  return [value, toggle];
}

import { createContext, useCallback, useContext, useEffect, useMemo, useRef } from "react";

/**
 * MessageCard から SayForm に「アンカー挿入 / 返信開始」を伝えるための薄い context。
 *
 * 旧 Thymeleaf 画面は jQuery で global handler を貼っていたが、React では
 * 親 component で provider を置き、SayForm が subscribe / MessageCard が trigger する
 * 形にする。pub/sub にすることで provider 自体は state を持たず再 render しない。
 */
export interface SayAnchorRequest {
  /** 旧画面の `>>` / `>>*` / `>>s` 等の prefix (MessageCard の typeCode → MESSAGE_STYLES から引く) */
  anchorPrefix: string;
  messageNumber: number;
  /** secret-say 由来か (= 返信 UI から呼ばれたか)。true のとき SayForm は messageType=SECRET_SAY に切替 */
  isSecret: boolean;
  /** 秘話の宛先キャラ ID (返信元の発言者 charaId)。`isSecret=true` のときのみ意味あり */
  secretTargetCharaId?: number;
}

type Listener = (req: SayAnchorRequest) => void;

interface SayFormContextValue {
  subscribe: (listener: Listener) => () => void;
  request: (req: SayAnchorRequest) => void;
}

const SayFormContextImpl = createContext<SayFormContextValue | null>(null);

export function SayFormProvider({ children }: { children: React.ReactNode }) {
  const listenersRef = useRef<Set<Listener>>(new Set());

  const subscribe = useCallback((listener: Listener) => {
    listenersRef.current.add(listener);
    return () => {
      listenersRef.current.delete(listener);
    };
  }, []);

  const request = useCallback((req: SayAnchorRequest) => {
    listenersRef.current.forEach((l) => l(req));
  }, []);

  const value = useMemo<SayFormContextValue>(() => ({ subscribe, request }), [subscribe, request]);

  return <SayFormContextImpl.Provider value={value}>{children}</SayFormContextImpl.Provider>;
}

/** SayForm 側で使う subscribe フック。listener が変わっても 1 subscribe で済む。 */
export function useSayAnchorSubscription(listener: Listener) {
  const ctx = useContext(SayFormContextImpl);
  const listenerRef = useRef(listener);
  listenerRef.current = listener;
  useEffect(() => {
    if (!ctx) return;
    return ctx.subscribe((req) => listenerRef.current(req));
  }, [ctx]);
}

/** MessageCard 側で使う dispatcher。provider が無いと no-op。 */
export function useSayFormRequester(): (req: SayAnchorRequest) => void {
  const ctx = useContext(SayFormContextImpl);
  return useCallback(
    (req: SayAnchorRequest) => {
      ctx?.request(req);
    },
    [ctx],
  );
}

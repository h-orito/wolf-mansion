import type { SerializeOptions } from 'cookie'
import { getCookie, setCookie, deleteCookie } from 'cookies-next/client'
import { useContext, useSyncExternalStore } from 'react'
import { CookieContext } from './CookieContext'

type Listener = () => void

// hooksが複数回呼び出されても有効なように、listenerはglobalで管理
const globalCookieListeners = new Set<Listener>()

export function useCookieStore(key: string) {
  const serverCookie = useContext(CookieContext)

  const value = useSyncExternalStore<string | undefined>(
    (listener: Listener) => {
      globalCookieListeners.add(listener)
      return () => globalCookieListeners.delete(listener)
    },
    () => getCookie(key),
    () => serverCookie[key]
  )

  return {
    getValue: () => {
      return value
    },
    setValue: (value?: string, options?: SerializeOptions) => {
      setCookie(key, value, options ?? {})
      globalCookieListeners.forEach((l) => l())
    },
    deleteValue: () => {
      deleteCookie(key)
      globalCookieListeners.forEach((l) => l())
    }
  }
}

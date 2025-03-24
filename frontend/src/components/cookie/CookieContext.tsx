'use client'

import { createContext, FC, PropsWithChildren } from 'react'

export type CookieValue = Partial<Record<string, string>>

export const CookieContext = createContext<CookieValue>({})

export const CookieProvider: FC<PropsWithChildren<{ value: CookieValue }>> = ({
  value,
  children
}) => {
  return (
    <CookieContext.Provider value={value}>{children}</CookieContext.Provider>
  )
}

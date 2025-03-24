'use server'

import { cookies } from 'next/headers'
import { FC, PropsWithChildren } from 'react'
import { CookieProvider } from './CookieContext'

const getServerCookies = async () => {
  const resolvedCookie = await cookies()
  return Object.fromEntries(
    resolvedCookie
      .getAll()
      .values()
      .map((cookieValue) => {
        return [cookieValue.name, cookieValue.value]
      })
  )
}

export const ServerCookie: FC<PropsWithChildren> = async ({ children }) => {
  const value = await getServerCookies()
  return <CookieProvider value={value}>{children}</CookieProvider>
}

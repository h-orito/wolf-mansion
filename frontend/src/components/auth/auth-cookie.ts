export const cookieKeys = {
  jsessionId: 'JSESSONID'
}

// const options = {
//   path: '/wolf-mansion',
//   secure: process.env.NODE_ENV === 'production',
//   sameSite: process.env.NODE_ENV === 'production',
//   maxAge: 60 * 60 * 24 * 365
// }

// client component向け
// export const getJsessionId = async (): Promise<string | null> => {
//   return Cookies.get(cookieKeys.jsessionId) ?? null
// }

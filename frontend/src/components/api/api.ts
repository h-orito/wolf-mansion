// import { getJsessionId } from '../auth/auth-cookie'
import { cookies } from 'next/headers'
import { useCookieStore } from '../cookie/useCookieValue'

const getRequest = async <T, U>(
  path: string,
  params?: T,
  options?: RequestInit
): Promise<U> => {
  return request(path, 'GET', null, params, options)
}

const postRequest = async <T, U>(
  path: string,
  body?: T,
  options?: RequestInit
): Promise<U> => {
  return request(path, 'POST', body, null, options)
}

const putRequest = async <T, U>(
  path: string,
  body?: T,
  options?: RequestInit
): Promise<U> => {
  return request(path, 'PUT', body, null, options)
}

const deleteRequest = async <T, U>(
  path: string,
  body?: T,
  options?: RequestInit
): Promise<U> => {
  return request(path, 'DELETE', body, null, options)
}

const request = async <T, U>(
  path: string,
  method: 'GET' | 'POST' | 'PUT' | 'DELETE',
  body?: T,
  params?: T,
  options?: RequestInit
): Promise<U> => {
  let headers = {}
  if (typeof window !== 'undefined') {
    // const jsessionId = useCookieStore('JSESSIONID').getValue()
    // if (!!jsessionId) {
    //   headers = {
    //     cookies: `JSESSIONID=${jsessionId}`
    //   }
    // }
  } else {
    const cookieStore = await cookies()
    headers = {
      cookies: cookieStore.toString()
    }
  }
  const bodyJson = body && JSON.stringify(body)
  let url = `${process.env.NEXT_PUBLIC_API_BASE}${path}`
  if (params) {
    // nullが'null'として出力されてしまうため、nullをfilteringする
    const p = filterNullProperties(params)
    const query = new URLSearchParams(p)
    url += `?${query}`
  }

  try {
    const response = await fetch(url, {
      method,
      headers: {
        'Content-Type': 'application/json',
        ...headers
      },
      cache: 'no-cache',
      body: bodyJson,
      ...options
    })
    if (response.status !== 200) {
      console.log(response)
      throw new Error('API Error')
    }
    // 空の場合があるので json() でなく text() で取得
    const responseStr = await response.text()
    const json = responseStr === '' ? {} : JSON.parse(responseStr)
    return json as U
  } catch (error) {
    console.error(error)
    throw error
  }
}

function filterNullProperties(obj: any) {
  return Object.keys(obj).reduce(
    (retObj, key) =>
      obj[key] === null || obj[key] === undefined
        ? retObj // null プロパティは無視
        : Object.assign(retObj, { [key]: obj[key] }), // null でないプロパティのみretObjに追加
    {}
  )
}

export { deleteRequest, getRequest, postRequest, putRequest }
function useCookieValue(arg0: string) {
  throw new Error('Function not implemented.')
}

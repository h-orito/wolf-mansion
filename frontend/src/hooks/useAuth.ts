'use client'

import { getRequest, postRequest, putRequest } from '@/components/api/api'
import {
  ChangePasswordPutAPIRequest,
  LoginPostAPIResponse,
  MyselfAPIResponse,
  PlayerView
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { useEffect, useState } from 'react'

// ログインフォームの型
export type LoginFormData = {
  userId: string
  password: string
}

// ID登録フォームの型
export type RegisterFormData = {
  userId: string
  password: string
}

// パスワード変更フォームの型
export type ChangePasswordFormData = {
  password: string
  confirmPassword: string
}

// 認証関数の戻り値の型
export type AuthResult = {
  success: boolean
  isLoading: boolean
  errorMessage: string | null
}

// 認証フックの戻り値の型
export type UseAuthReturn = {
  user: PlayerView | null
  isLoading: boolean
  error: string | null
  login: (data: LoginFormData) => Promise<AuthResult>
  register: (data: RegisterFormData) => Promise<AuthResult>
  logout: () => Promise<AuthResult>
  changePassword: (data: ChangePasswordFormData) => Promise<AuthResult>
}

export const useAuth = (): UseAuthReturn => {
  const [user, setUser] = useState<PlayerView | null>(null)
  const [isLoading, setIsLoading] = useState<boolean>(true)
  const [error, setError] = useState<string | null>(null)

  // 初期化時にユーザー情報を取得
  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getRequest<null, MyselfAPIResponse>(
          '/api/myself'
        )
        setUser(userData?.player ?? null)
      } catch (err) {
        // APIエラーの場合はユーザー情報なしとして扱う（未ログイン状態）
        setUser(null)
      } finally {
        setIsLoading(false)
      }
    }

    fetchUser()
  }, [])

  // ログイン処理
  const login = async (data: LoginFormData): Promise<AuthResult> => {
    setIsLoading(true)
    setError(null)

    try {
      const userData = await postRequest<LoginFormData, LoginPostAPIResponse>(
        '/api/login',
        data
      )
      const player = userData.player ?? null
      setUser(player)
      if (!player) {
        const errorMessage =
          'ログインに失敗しました。ユーザーIDまたはパスワードが正しくありません。'
        setError(errorMessage)
        return {
          success: false,
          isLoading: false,
          errorMessage
        }
      }
      return {
        success: true,
        isLoading: false,
        errorMessage: null
      }
    } catch (err) {
      const errorMessage = 'ログインに失敗しました。'
      setError(errorMessage)
      return {
        success: false,
        isLoading: false,
        errorMessage
      }
    } finally {
      setIsLoading(false)
    }
  }

  // ID登録処理
  const register = async (data: RegisterFormData): Promise<AuthResult> => {
    setIsLoading(true)
    setError(null)

    try {
      await postRequest<RegisterFormData, void>('/api/new-player', data)
      // 登録成功後、自動的にログイン
      return await login(data)
    } catch (err) {
      const errorMessage =
        'ID登録に失敗しました。既に使用されているIDか、サーバーエラーが発生しました。'
      setError(errorMessage)
      return {
        success: false,
        isLoading: false,
        errorMessage
      }
    } finally {
      setIsLoading(false)
    }
  }

  // ログアウト処理
  const logout = async (): Promise<AuthResult> => {
    setIsLoading(true)
    setError(null)

    try {
      await postRequest<null, void>('/api/logout', null)
      setUser(null)
      return {
        success: true,
        isLoading: false,
        errorMessage: null
      }
    } catch (err) {
      const errorMessage = 'ログアウトに失敗しました。'
      setError(errorMessage)
      return {
        success: false,
        isLoading: false,
        errorMessage
      }
    } finally {
      setIsLoading(false)
    }
  }

  // パスワード変更処理
  const changePassword = async (
    data: ChangePasswordFormData
  ): Promise<AuthResult> => {
    setIsLoading(true)
    setError(null)

    // パスワードと確認用パスワードが一致するか確認
    if (data.password !== data.confirmPassword) {
      const errorMessage = 'パスワードと確認用パスワードが一致しません。'
      setError(errorMessage)
      setIsLoading(false)
      return {
        success: false,
        isLoading: false,
        errorMessage
      }
    }

    try {
      await putRequest<ChangePasswordPutAPIRequest, void>(
        '/api/change-password',
        {
          password: data.password,
          confirmPassword: data.confirmPassword
        }
      )
      return {
        success: true,
        isLoading: false,
        errorMessage: null
      }
    } catch (err) {
      const errorMessage = 'パスワード変更に失敗しました。'
      setError(errorMessage)
      return {
        success: false,
        isLoading: false,
        errorMessage
      }
    } finally {
      setIsLoading(false)
    }
  }

  return {
    user,
    isLoading,
    error,
    login,
    register,
    logout,
    changePassword
  }
}

'use client'

import Alert from '@/components/ui/Alert'
import Button from '@/components/ui/Button'
import Modal from '@/components/ui/Modal'
import { LoginFormData, useAuth } from '@/hooks/useAuth'
import { useState } from 'react'
import { useForm } from 'react-hook-form'

type LoginModalProps = {
  isOpen: boolean
  onClose: () => void
}

const LoginModal = ({ isOpen, onClose }: LoginModalProps) => {
  const { login, error: authError } = useAuth()
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset
  } = useForm<LoginFormData>()

  const onSubmit = async (data: LoginFormData) => {
    setIsSubmitting(true)
    setError(null)

    try {
      const result = await login(data)
      if (result.success) {
        reset()
        onClose()
        // ログイン状態をAuthSectionに反映させるためにページをリロード
        window.location.reload()
      } else {
        setError(result.errorMessage)
      }
    } catch (err) {
      setError('ログイン処理中にエラーが発生しました。')
    } finally {
      setIsSubmitting(false)
    }
  }

  // モーダルが閉じられたときにフォームとエラーをリセット
  const handleClose = () => {
    reset()
    setError(null)
    onClose()
  }

  return (
    <Modal isOpen={isOpen} onClose={handleClose} title='ログイン'>
      <form onSubmit={handleSubmit(onSubmit)} className='space-y-4'>
        {/* エラーメッセージ */}
        {error && <Alert variant='danger'>{error}</Alert>}

        <Alert variant='warning'>
          ユーザーIDやパスワードを忘れた場合は管理者までお知らせください。
        </Alert>

        {/* ユーザーID入力フィールド */}
        <div>
          <label
            htmlFor='userId'
            className='mb-1 block text-sm font-medium text-gray-300'
          >
            ユーザーID
          </label>
          <input
            id='userId'
            type='text'
            className='input-style'
            placeholder='ユーザーIDを入力'
            {...register('userId', {
              required: 'ユーザーIDは必須です',
              minLength: {
                value: 3,
                message: 'ユーザーIDは3文字以上で入力してください'
              },
              maxLength: {
                value: 12,
                message: 'ユーザーIDは12文字以下で入力してください'
              },
              pattern: {
                value: /^[a-zA-Z][a-zA-Z0-9\-_]*$/,
                message:
                  'ユーザーIDは英字で始まり、英数字・ハイフン・アンダースコアのみ使用可能です'
              }
            })}
          />
          {errors.userId && (
            <p className='mt-1 text-xs text-red-500'>{errors.userId.message}</p>
          )}
        </div>

        {/* パスワード入力フィールド */}
        <div>
          <label
            htmlFor='password'
            className='mb-1 block text-sm font-medium text-gray-300'
          >
            パスワード
          </label>
          <input
            id='password'
            type='password'
            className='input-style'
            placeholder='パスワードを入力'
            {...register('password', {
              required: 'パスワードは必須です',
              minLength: {
                value: 3,
                message: 'パスワードは3文字以上で入力してください'
              },
              maxLength: {
                value: 72,
                message: 'パスワードは72文字以下で入力してください'
              },
              pattern: {
                value: /^[\x20-\x7E]+$/,
                message: 'パスワードに使用できない文字が含まれています'
              }
            })}
          />
          {errors.password && (
            <p className='mt-1 text-xs text-red-500'>
              {errors.password.message}
            </p>
          )}
        </div>

        {/* ログインボタン */}
        <div className='flex justify-end pt-2'>
          <Button type='submit' variant='primary' isLoading={isSubmitting}>
            ログイン
          </Button>
        </div>
      </form>
    </Modal>
  )
}

export default LoginModal

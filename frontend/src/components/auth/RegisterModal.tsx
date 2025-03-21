'use client'

import Alert from '@/components/ui/Alert'
import Button from '@/components/ui/Button'
import Modal from '@/components/ui/Modal'
import { RegisterFormData, useAuth } from '@/hooks/useAuth'
import { useState } from 'react'
import { useForm } from 'react-hook-form'

type RegisterModalProps = {
  isOpen: boolean
  onClose: () => void
}

const RegisterModal = ({ isOpen, onClose }: RegisterModalProps) => {
  const { register: registerUser, error: authError } = useAuth()
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset
  } = useForm<RegisterFormData>()

  const onSubmit = async (data: RegisterFormData) => {
    setIsSubmitting(true)
    setError(null)

    try {
      const result = await registerUser(data)
      if (result.success) {
        reset()
        onClose()
        // 登録成功時にページをリロードして認証状態を更新
        window.location.reload()
      } else {
        setError(result.errorMessage)
      }
    } catch (err) {
      setError('ID登録処理中にエラーが発生しました。')
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
    <Modal isOpen={isOpen} onClose={handleClose} title='ID登録'>
      <form onSubmit={handleSubmit(onSubmit)} className='space-y-4'>
        {/* エラーメッセージ */}
        {error && <Alert variant='danger'>{error}</Alert>}

        {/* 注意書き */}
        <Alert variant='warning'>
          ユーザーIDとパスワードは忘れないようにしてください。万が一パスワードを忘れた場合は管理者までお知らせください。
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
            className='focus:border-primary w-full rounded-md border border-gray-600 bg-gray-700 px-3 py-2 text-white placeholder-gray-400 focus:outline-none'
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

        {/* 登録ボタン */}
        <div className='flex justify-end pt-2'>
          <Button type='submit' variant='primary' isLoading={isSubmitting}>
            ID登録
          </Button>
        </div>
      </form>
    </Modal>
  )
}

export default RegisterModal

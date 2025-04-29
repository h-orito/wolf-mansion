'use client'

import Alert from '@/components/ui/Alert'
import Button from '@/components/ui/Button'
import Modal from '@/components/ui/Modal'
import { ChangePasswordFormData, useAuth } from '@/hooks/useAuth'
import { useState } from 'react'
import { useForm } from 'react-hook-form'

type ChangePasswordModalProps = {
  isOpen: boolean
  onClose: () => void
}

const ChangePasswordModal = ({ isOpen, onClose }: ChangePasswordModalProps) => {
  const { changePassword, error: authError } = useAuth()
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
    watch
  } = useForm<ChangePasswordFormData>()

  const password = watch('password')

  const onSubmit = async (data: ChangePasswordFormData) => {
    setIsSubmitting(true)
    setError(null)
    setSuccess(false)

    try {
      const result = await changePassword(data)
      if (result.success) {
        setSuccess(true)
        reset()
        // 成功メッセージを表示した後、3秒後に自動的にモーダルを閉じる
        setTimeout(() => {
          handleClose()
        }, 3000)
      } else {
        setError(result.errorMessage)
      }
    } catch (err) {
      setError('パスワード変更処理中にエラーが発生しました。')
    } finally {
      setIsSubmitting(false)
    }
  }

  // モーダルが閉じられたときにフォームとエラーをリセット
  const handleClose = () => {
    reset()
    setError(null)
    setSuccess(false)
    onClose()
  }

  return (
    <Modal isOpen={isOpen} onClose={handleClose} title='パスワード変更'>
      <form onSubmit={handleSubmit(onSubmit)} className='space-y-4'>
        {/* エラーメッセージ */}
        {error && <Alert variant='danger'>{error}</Alert>}

        {/* 成功メッセージ */}
        {success && (
          <Alert variant='primary'>パスワードが正常に変更されました。</Alert>
        )}

        {/* 注意書き */}
        <Alert variant='warning'>
          パスワードは忘れないようにしてください。万が一パスワードを忘れた場合は管理者までお知らせください。
        </Alert>

        {/* 新しいパスワード入力フィールド */}
        <div>
          <label
            htmlFor='password'
            className='mb-1 block text-sm font-medium text-gray-300'
          >
            新しいパスワード
          </label>
          <input
            id='password'
            type='password'
            className='input-style'
            placeholder='新しいパスワードを入力'
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

        {/* パスワード確認入力フィールド */}
        <div>
          <label
            htmlFor='confirmPassword'
            className='mb-1 block text-sm font-medium text-gray-300'
          >
            パスワード確認
          </label>
          <input
            id='confirmPassword'
            type='password'
            className='focus:border-primary w-full rounded-md border border-gray-600 bg-white px-3 py-2 text-black placeholder-gray-400 focus:outline-none'
            placeholder='新しいパスワードを再入力'
            {...register('confirmPassword', {
              required: '確認用パスワードは必須です',
              validate: (value) =>
                value === password || 'パスワードが一致しません'
            })}
          />
          {errors.confirmPassword && (
            <p className='mt-1 text-xs text-red-500'>
              {errors.confirmPassword.message}
            </p>
          )}
        </div>

        {/* 変更ボタン */}
        <div className='flex justify-end pt-2'>
          <Button
            type='submit'
            variant='primary'
            isLoading={isSubmitting}
            disabled={success}
          >
            パスワード変更
          </Button>
        </div>
      </form>
    </Modal>
  )
}

export default ChangePasswordModal

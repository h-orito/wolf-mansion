'use client'

import ChangePasswordModal from '@/components/auth/ChangePasswordModal'
import LoginModal from '@/components/auth/LoginModal'
import RegisterModal from '@/components/auth/RegisterModal'
import { useAuth } from '@/hooks/useAuth'
import {
  faKey,
  faRightToBracket,
  faUser,
  faUserMinus,
  faUserPlus
} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Link from 'next/link'
import { useState } from 'react'

const AuthSection = () => {
  const { user, logout, isLoading } = useAuth()
  const [showLoginModal, setShowLoginModal] = useState(false)
  const [showRegisterModal, setShowRegisterModal] = useState(false)
  const [showChangePasswordModal, setShowChangePasswordModal] = useState(false)

  // ログアウト処理
  const handleLogout = async () => {
    const result = await logout()
    if (result.success) {
      // ページをリロードしてログアウト状態を反映
      window.location.reload()
    }
  }

  return (
    <div className='mt-8 bg-[#333333]'>
      <div className='p-8'>
        <div className='flex justify-center'>
          {isLoading
            ? '読み込み中...'
            : user
              ? 'マイページ / 設定'
              : '登録 / ログイン'}
        </div>
      </div>

      {isLoading ? (
        // ローディング中の表示
        <div className='grid grid-cols-3'>
          <div className='col-span-3 flex justify-center p-4 text-gray-400'>
            読み込み中...
          </div>
        </div>
      ) : user ? (
        // ログイン状態の表示
        <div className='grid grid-cols-3'>
          {/* マイページボタン */}
          <Link href={`/user/${user.name}`}>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-[var(--primary)]'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faUser} className='h-4 w-4' />
              </div>
              <p>マイページ</p>
              <p>MyPage</p>
            </div>
          </Link>

          {/* パスワード変更ボタン */}
          <div
            onClick={() => setShowChangePasswordModal(true)}
            className='flex w-full cursor-pointer flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-[var(--primary)]'
          >
            <div className='flex justify-center'>
              <FontAwesomeIcon icon={faKey} className='h-4 w-4' />
            </div>
            <p>パスワード変更</p>
            <p>Change Password</p>
          </div>

          {/* ログアウトボタン */}
          <div
            onClick={handleLogout}
            className='flex w-full cursor-pointer flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-[var(--primary)]'
          >
            <div className='flex justify-center'>
              <FontAwesomeIcon icon={faUserMinus} className='h-4 w-4' />
            </div>
            <p>ログアウト</p>
            <p>Logout</p>
          </div>
        </div>
      ) : (
        // 未ログイン状態の表示
        <div className='grid grid-cols-2'>
          {/* ログインボタン */}
          <div
            onClick={() => setShowLoginModal(true)}
            className='flex w-full cursor-pointer flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-[var(--primary)]'
          >
            <div className='flex justify-center'>
              <FontAwesomeIcon icon={faRightToBracket} className='h-4 w-4' />
            </div>
            <p>ログイン</p>
            <p>Login</p>
          </div>

          {/* ID登録ボタン */}
          <div
            onClick={() => setShowRegisterModal(true)}
            className='flex w-full cursor-pointer flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-[var(--primary)]'
          >
            <div className='flex justify-center'>
              <FontAwesomeIcon icon={faUserPlus} className='h-4 w-4' />
            </div>
            <p>ID登録</p>
            <p>Register</p>
          </div>
        </div>
      )}

      {/* モーダルコンポーネント */}
      <LoginModal
        isOpen={showLoginModal}
        onClose={() => setShowLoginModal(false)}
      />
      <RegisterModal
        isOpen={showRegisterModal}
        onClose={() => setShowRegisterModal(false)}
      />
      <ChangePasswordModal
        isOpen={showChangePasswordModal}
        onClose={() => setShowChangePasswordModal(false)}
      />
    </div>
  )
}

export default AuthSection

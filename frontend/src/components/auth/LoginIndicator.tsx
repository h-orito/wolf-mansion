'use client'

import { useAuth } from '@/hooks/useAuth'
import { faUser, faUserPlus } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Link from 'next/link'
import { useState } from 'react'
import ChangePasswordModal from './ChangePasswordModal'
import LoginModal from './LoginModal'
import RegisterModal from './RegisterModal'

const LoginIndicator = () => {
  const { user, logout, isLoading } = useAuth()
  const [showMenu, setShowMenu] = useState(false)
  const [showLoginModal, setShowLoginModal] = useState(false)
  const [showRegisterModal, setShowRegisterModal] = useState(false)
  const [showChangePasswordModal, setShowChangePasswordModal] = useState(false)

  // メニューの表示/非表示を切り替える
  const toggleMenu = () => setShowMenu(!showMenu)

  // ログアウト処理
  const handleLogout = async () => {
    const result = await logout()
    if (result.success) {
      setShowMenu(false)
      window.location.reload()
    }
  }

  return (
    <>
      {/* インジケーターボタン（右上配置） */}
      <div
        className={`fixed top-5 right-5 z-50 flex cursor-pointer items-center justify-center rounded-full bg-[#0b162a] text-white shadow-lg transition-colors hover:bg-[#162a4a] ${
          user ? 'h-10 px-4' : 'h-10 w-10'
        }`}
        onClick={toggleMenu}
      >
        {isLoading ? (
          <div className='h-4 w-4 animate-spin rounded-full border-2 border-t-transparent' />
        ) : user ? (
          <>
            <FontAwesomeIcon icon={faUser} className='mr-2 h-4 w-4' />
            <span className='text-sm'>{user.name}</span>
          </>
        ) : (
          <FontAwesomeIcon icon={faUserPlus} className='h-4 w-4' />
        )}
      </div>

      {/* ポップアップメニュー（インジケーターの下に表示） */}
      {showMenu && (
        <div className='fixed top-16 right-5 z-50 w-48 rounded bg-[#222222] p-2 shadow-lg'>
          {isLoading ? (
            <div className='p-2 text-center text-sm'>読み込み中...</div>
          ) : user ? (
            <>
              <Link href={`/user/${user.name}`}>
                <div className='cursor-pointer p-2 text-sm hover:bg-[#333333]'>
                  マイページ
                </div>
              </Link>
              <div
                className='cursor-pointer p-2 text-sm hover:bg-[#333333]'
                onClick={() => {
                  setShowChangePasswordModal(true)
                  setShowMenu(false)
                }}
              >
                パスワード変更
              </div>
              <div
                className='cursor-pointer p-2 text-sm hover:bg-[#333333]'
                onClick={handleLogout}
              >
                ログアウト
              </div>
            </>
          ) : (
            <>
              <div
                className='cursor-pointer p-2 text-sm hover:bg-[#333333]'
                onClick={() => {
                  setShowLoginModal(true)
                  setShowMenu(false)
                }}
              >
                ログイン
              </div>
              <div
                className='cursor-pointer p-2 text-sm hover:bg-[#333333]'
                onClick={() => {
                  setShowRegisterModal(true)
                  setShowMenu(false)
                }}
              >
                ID登録
              </div>
            </>
          )}
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
    </>
  )
}

export default LoginIndicator

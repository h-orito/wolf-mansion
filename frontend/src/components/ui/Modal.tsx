'use client'

import { faXmark } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useEffect, useRef, useState } from 'react'

type ModalProps = {
  isOpen: boolean
  onClose: () => void
  title: string
  children: React.ReactNode
}

const Modal = ({ isOpen, onClose, title, children }: ModalProps) => {
  const modalRef = useRef<HTMLDivElement>(null)

  // ESCキーでモーダルを閉じる
  useEffect(() => {
    const handleEscKey = (event: KeyboardEvent) => {
      if (event.key === 'Escape' && isOpen) {
        onClose()
      }
    }

    document.addEventListener('keydown', handleEscKey)
    return () => {
      document.removeEventListener('keydown', handleEscKey)
    }
  }, [isOpen, onClose])

  // モーダル内外のクリック状態を追跡
  const [isMouseDownOnModal, setIsMouseDownOnModal] = useState(false)

  // マウスダウン時の処理
  const handleMouseDown = (e: React.MouseEvent<HTMLDivElement>) => {
    // クリック開始時点がモーダル内かどうかをチェック
    setIsMouseDownOnModal(modalRef.current?.contains(e.target as Node) || false)
  }

  // マウスアップ時の処理
  const handleMouseUp = (e: React.MouseEvent<HTMLDivElement>) => {
    // クリック開始位置がモーダル外で、かつクリック終了位置もモーダル外の場合のみモーダルを閉じる
    if (
      !isMouseDownOnModal &&
      modalRef.current &&
      !modalRef.current.contains(e.target as Node)
    ) {
      onClose()
    }
  }

  if (!isOpen) return null

  return (
    <div
      className='fixed inset-0 z-50 flex items-center justify-center bg-black/30 backdrop-blur-sm'
      onMouseDown={handleMouseDown}
      onMouseUp={handleMouseUp}
    >
      <div
        ref={modalRef}
        className='w-full max-w-md rounded-lg bg-[#222222]/90 p-6 shadow-lg backdrop-blur-sm md:max-w-2xl lg:max-w-4xl'
      >
        <div className='mb-4 flex items-center justify-between'>
          <h2 className='text-xl font-bold text-white'>{title}</h2>
          <button
            onClick={onClose}
            className='rounded-full p-1 text-gray-400 hover:bg-gray-700 hover:text-white'
          >
            <FontAwesomeIcon icon={faXmark} className='h-5 w-5' />
          </button>
        </div>
        <div>{children}</div>
      </div>
    </div>
  )
}

export default Modal

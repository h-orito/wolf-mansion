'use client'

import { faUsers } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Link from 'next/link'

export default function UserListLink() {
  return (
    <div className='mt-8 bg-[#333333]'>
      <div className='p-8'>
        <div className='flex justify-center'>ユーザー</div>
      </div>
      <div className='grid grid-cols-1'>
        {/* ユーザー一覧ボタン */}
        <Link href='/user-list'>
          <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-[var(--primary)]'>
            <div className='flex justify-center'>
              <FontAwesomeIcon icon={faUsers} className='h-4 w-4' />
            </div>
            <p>ユーザー一覧</p>
            <p>User list</p>
          </div>
        </Link>
      </div>
    </div>
  )
}

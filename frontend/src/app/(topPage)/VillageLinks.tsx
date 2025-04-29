'use client'

import { getRequest } from '@/components/api/api'
import { MyselfAPIResponse } from '@/lib/openapi-typescript/wolf-mansion/types'
import { faList, faPlus } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Link from 'next/link'
import { useEffect, useState } from 'react'

export default function VillageLinks() {
  const [canCreateVillage, setCanCreateVillage] = useState(false)
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    const fetchMyselfData = async () => {
      try {
        const myselfData = await getRequest<null, MyselfAPIResponse>(
          '/api/myself'
        )
        setCanCreateVillage(myselfData?.player?.canCreateVillage || false)
      } catch (err) {
        // APIエラーの場合は村作成権限なしとして扱う
        setCanCreateVillage(false)
      } finally {
        setIsLoading(false)
      }
    }

    fetchMyselfData()
  }, [])

  return (
    <div className='mt-8 bg-[#333333]'>
      <div className='p-8'>
        <div className='flex justify-center'>村一覧 / 作成</div>
      </div>
      <div
        className={`grid ${canCreateVillage ? 'grid-cols-2' : 'grid-cols-1'}`}
      >
        {/* 村一覧ボタン */}
        <Link href='/village-list'>
          <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-[var(--primary)]'>
            <div className='flex justify-center'>
              <FontAwesomeIcon icon={faList} className='h-4 w-4' />
            </div>
            <p>村一覧</p>
            <p>Village list</p>
          </div>
        </Link>

        {/* 村作成ボタン（canCreateVillageがtrueの場合のみ表示） */}
        {canCreateVillage && (
          <Link href='/new-village'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-[var(--primary)]'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faPlus} className='h-4 w-4' />
              </div>
              <p>村作成</p>
              <p>Create village</p>
            </div>
          </Link>
        )}
      </div>
    </div>
  )
}

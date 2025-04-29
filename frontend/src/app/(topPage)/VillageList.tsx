'use client'

import { VillageListVillage } from '@/lib/openapi-typescript/wolf-mansion/types'
import Link from 'next/link'

type Props = {
  villages: VillageListVillage[]
}

export default function VillageList({ villages }: Props) {
  const displayVillages = villages.slice().sort((v1, v2) => {
    return v1.villageId - v2.villageId // 村番号昇順
  })
  return (
    <div className='mt-8 bg-[#333333]'>
      <div className='p-8'>
        <div className='flex justify-center'>村一覧</div>
      </div>
      <div>
        <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3'>
          {/* 村一覧 */}
          {displayVillages.map((village) => (
            <Link
              key={village.villageId}
              href={`/village/${village.villageId}`}
              className='block'
              rel='noopener noreferrer'
            >
              <div className='flex h-full flex-col overflow-hidden rounded-md border border-gray-700 bg-[#0b162a] p-4 transition-colors hover:border-[var(--primary)]'>
                <div className='mb-2'>
                  <h2 className='text-base font-semibold'>
                    {village.villageNumber}. {village.villageName}
                  </h2>
                </div>

                <div className='mb-2 flex flex-wrap gap-1'>
                  {village.tags &&
                    village.tags.map((tag, index) => (
                      <span
                        key={index}
                        className={`rounded border px-2 py-0.5 text-xs ${
                          tag.level === 'danger'
                            ? 'border-red-500 text-red-500'
                            : 'border-primary text-primary'
                        }`}
                      >
                        {tag.name}
                      </span>
                    ))}
                </div>

                <div className='mt-auto flex justify-between text-xs text-gray-400'>
                  <div>参加人数: {village.participateNum}</div>
                  <div>ステータス: {village.status}</div>
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  )
}

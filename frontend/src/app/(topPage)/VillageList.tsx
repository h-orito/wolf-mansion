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
      <div className='overflow-x-auto'>
        <div className='w-full'>
          {/* 村一覧 */}
          {displayVillages.map((village) => (
            <Link
              key={village.villageId}
              href={`/village/${village.villageId}`}
              className='block border border-[#333333] bg-[#0b162a] hover:border-green-500'
              rel='noopener noreferrer'
            >
              <div className='grid grid-cols-12 items-center'>
                <div className='col-span-12 p-2 text-left text-xs leading-4 text-white md:col-span-6'>
                  {village.villageNumber}. {village.villageName}
                </div>
                <div className='col-span-12 p-2 text-center text-xs leading-4 text-white md:col-span-3'>
                  <div className='flex flex-wrap justify-center gap-1'>
                    {village.tags &&
                      village.tags.map((tag, index) => (
                        <span
                          key={index}
                          className={`rounded border px-2 py-0.5 text-xs ${
                            tag.level === 'danger'
                              ? 'border-red-500 text-red-500'
                              : 'border-green-500 text-green-500'
                          }`}
                        >
                          {tag.name}
                        </span>
                      ))}
                  </div>
                </div>
                <div className='col-span-6 p-2 text-center text-xs leading-4 text-white md:col-span-1'>
                  {village.participateNum}
                </div>
                <div className='col-span-6 p-2 text-center text-xs leading-4 text-white md:col-span-2'>
                  {village.status}
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  )
}

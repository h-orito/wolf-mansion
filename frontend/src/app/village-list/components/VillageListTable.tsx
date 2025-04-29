'use client'

import { VillageListVillage } from '@/lib/openapi-typescript/wolf-mansion/types'
import Link from 'next/link'

type Props = {
  villages: VillageListVillage[]
}

export default function VillageListTable({ villages }: Props) {
  const displayVillages = villages.slice().sort((v1, v2) => {
    return v2.villageId - v1.villageId // 村番号降順
  })

  return (
    <div className='overflow-x-auto'>
      <table className='table'>
        <thead>
          <tr className=''>
            <th className='th text-left'>村名</th>
            <th className='th text-center'>タグ</th>
            <th className='th text-right'>人数</th>
            <th className='th text-center'>ステータス</th>
          </tr>
        </thead>
        <tbody>
          {displayVillages.map((village) => (
            <tr key={village.villageId} className='border border-[#444444]'>
              <td className='td'>
                <Link
                  href={`/village/${village.villageId}`}
                  className='link-style'
                  rel='noopener noreferrer'
                >
                  {village.villageNumber}. {village.villageName}
                </Link>
              </td>
              <td className='td'>
                <div className='flex flex-wrap justify-center gap-1'>
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
              </td>
              <td className='td text-right'>{village.participateNum}</td>
              <td className='td text-center'>{village.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

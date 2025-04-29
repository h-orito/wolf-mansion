import { Chara } from '@/lib/openapi-typescript/wolf-mansion/types'
import Image from 'next/image'

type CharaCardProps = {
  chara: Chara
}

export default function CharaCard({ chara }: CharaCardProps) {
  // 画像URLが https:// で始まっていない場合、https://wolfort.net を追加
  const getFullImageUrl = (url: string) => {
    if (!url) return ''
    return url.startsWith('https://') ? url : `https://wolfort.net${url}`
  }

  // キャラ名を [shortName] name 形式で表示
  const displayName = chara.shortName
    ? `[${chara.shortName}] ${chara.name}`
    : chara.name

  return (
    <div className='overflow-hidden rounded-md border border-gray-700 bg-[#222222] p-4'>
      <h3 className='mb-3 text-lg font-semibold'>{displayName}</h3>

      {chara.images.list.length > 0 ? (
        <div className='flex flex-wrap'>
          {chara.images.list.map((image, index) => (
            <div
              key={index}
              className='relative'
              style={{
                height: `${chara.size.height}px`,
                width: `${chara.size.width}px`
              }}
            >
              <Image
                src={getFullImageUrl(image.url)}
                alt={`${chara.name}の画像 ${index + 1}`}
                fill
                className='object-contain'
                loading='lazy'
                sizes={`${chara.size.width}px`}
              />
            </div>
          ))}
        </div>
      ) : (
        <p className='text-gray-500'>画像なし</p>
      )}
    </div>
  )
}

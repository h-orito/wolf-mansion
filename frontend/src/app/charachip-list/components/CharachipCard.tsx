import { Chara, Charachip } from '@/lib/openapi-typescript/wolf-mansion/types'
import Image from 'next/image'
import Link from 'next/link'

type CharachipCardProps = {
  charachip: Charachip
}

export default function CharachipCard({ charachip }: CharachipCardProps) {
  // defaultJoinMessageがあるキャラを一人だけ選択
  const dummyChara: Chara | undefined =
    charachip.charas.list.find(
      (chara) => chara.defaultJoinMessage && chara.defaultJoinMessage.length > 0
    ) || charachip.charas.list[0] // 見つからない場合は最初のキャラを使用

  // 画像URLが https:// で始まっていない場合、https://wolfort.net を追加
  const getFullImageUrl = (url: string) => {
    if (!url) return ''
    return url.startsWith('https://') ? url : `https://wolfort.net${url}`
  }

  return (
    <Link href={`/charachip/${charachip.id}`}>
      <div className='flex cursor-pointer flex-col overflow-hidden rounded-md border border-gray-700 bg-[#222222] p-4 transition-colors hover:border-[var(--primary)]'>
        <div>
          <h2 className='text-lg font-semibold'>{charachip.name}</h2>
          <p className='text-sm text-gray-400'>
            作者: {charachip.designer?.name || '不明'}
          </p>
          <p className='text-sm text-gray-400'>
            キャラ数: {charachip.charas.list.length}人
          </p>
        </div>

        <div className='flex flex-1 items-center justify-center'>
          {dummyChara && dummyChara.images.list.length > 0 ? (
            <div
              className='relative'
              style={{
                maxHeight: '100px',
                maxWidth: '100%',
                height: `${dummyChara.size.height}px`,
                width: `${dummyChara.size.width}px`
              }}
            >
              <Image
                src={getFullImageUrl(dummyChara.images.list[0].url)}
                alt={`${dummyChara.name}のサンプル画像`}
                fill
                className='object-contain'
              />
            </div>
          ) : (
            <div className='text-gray-500'>画像なし</div>
          )}
        </div>
      </div>
    </Link>
  )
}

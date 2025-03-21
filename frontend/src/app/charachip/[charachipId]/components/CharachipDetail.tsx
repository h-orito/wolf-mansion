'use client'

import { getRequest } from '@/components/api/api'
import {
  CharaGroupAPIResponse,
  Charachip
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { faList } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Link from 'next/link'
import { useEffect, useState } from 'react'
import CharaCard from './CharaCard'

type CharachipDetailProps = {
  charachipId: string
}

export default function CharachipDetail({ charachipId }: CharachipDetailProps) {
  const [charachip, setCharachip] = useState<Charachip | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    fetchCharachip()
  }, [charachipId])

  const fetchCharachip = async () => {
    setIsLoading(true)
    setError(null)
    try {
      const response = await getRequest<null, CharaGroupAPIResponse>(
        `/api/chara-group/${charachipId}`
      )
      setCharachip(response)
    } catch (error) {
      console.error('キャラチップの取得に失敗しました', error)
      setError('キャラチップの取得に失敗しました')
    } finally {
      setIsLoading(false)
    }
  }

  if (isLoading) {
    return (
      <div className='content-section'>
        <h1 className='section-title'>
          <FontAwesomeIcon icon={faList} className='text-primary h-5 w-5' />
          キャラチップ詳細
        </h1>
        <p>Loading...</p>
      </div>
    )
  }

  if (error || !charachip) {
    return (
      <div className='content-section'>
        <h1 className='section-title'>
          <FontAwesomeIcon icon={faList} className='text-primary h-5 w-5' />
          キャラチップ詳細
        </h1>
        <p className='text-red-500'>
          {error || 'キャラチップが見つかりませんでした'}
        </p>
      </div>
    )
  }

  return (
    <div className='content-section'>
      <h1 className='section-title'>
        <FontAwesomeIcon icon={faList} className='text-primary h-5 w-5' />
        {charachip.name}
      </h1>

      <div className='mb-6 space-y-2'>
        <p className='text-sm'>
          <span className='font-semibold'>作者:</span>{' '}
          {charachip.designer?.name || '不明'}様
        </p>
        <p className='text-sm'>
          <span className='font-semibold'>肩書・名称変更:</span>{' '}
          {charachip.isAvailableChangeName ? '可能' : '不可'}
        </p>
        {charachip.descriptionUrl && (
          <p className='text-sm'>
            <span className='font-semibold'>作者様HP:</span>{' '}
            <Link
              href={charachip.descriptionUrl}
              target='_blank'
              rel='noopener noreferrer'
              className='link-style'
            >
              {charachip.descriptionUrl}
            </Link>
          </p>
        )}
      </div>

      <h2 className='mb-4 text-xl font-semibold'>キャラ一覧</h2>
      <div className='grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3'>
        {charachip.charas.list.map((chara) => (
          <CharaCard key={chara.id} chara={chara} />
        ))}
      </div>
    </div>
  )
}

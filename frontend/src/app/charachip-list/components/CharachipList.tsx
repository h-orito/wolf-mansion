'use client'

import { getRequest } from '@/components/api/api'
import {
  CharaGroupsAPIResponse,
  Charachip
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { faList } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useEffect, useState } from 'react'
import CharachipCard from './CharachipCard'

export default function CharachipList() {
  const [charachips, setCharachips] = useState<Charachip[]>([])
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    fetchCharachips()
  }, [])

  const fetchCharachips = async () => {
    setIsLoading(true)
    try {
      const response = await getRequest<null, CharaGroupsAPIResponse>(
        '/api/chara-groups'
      )
      setCharachips(response.list)
    } catch (error) {
      console.error('キャラチップの取得に失敗しました', error)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className='content-section'>
      <h1 className='section-title'>
        <FontAwesomeIcon icon={faList} className='text-primary h-5 w-5' />
        キャラチップ一覧
      </h1>

      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <div className='grid grid-cols-2 gap-2 md:grid-cols-2 md:gap-4 lg:grid-cols-3'>
          {charachips.map((charachip) => (
            <CharachipCard key={charachip.id} charachip={charachip} />
          ))}
        </div>
      )}
    </div>
  )
}

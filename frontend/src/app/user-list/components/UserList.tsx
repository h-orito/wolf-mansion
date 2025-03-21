'use client'

import UserListTable from '@/app/user-list/components/UserListTable'
import UserSearchForm, {
  SearchFormData
} from '@/app/user-list/components/UserSearchForm'
import { getRequest } from '@/components/api/api'
import Pagination from '@/components/ui/Pagination'
import {
  Paging,
  Player,
  PlayerSearchAPIRequest,
  PlayerSearchAPIResponse
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { faList } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useEffect, useState } from 'react'

export const UserList = () => {
  const [paging, setPaging] = useState<Paging | null>(null)
  const [players, setPlayers] = useState<Player[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [currentPage, setCurrentPage] = useState(1)

  useEffect(() => {
    fetchUsers({} as SearchFormData)
  }, [])

  const fetchUsers = async (data: SearchFormData) => {
    setIsLoading(true)
    try {
      const { userName, pageNum = 1 } = data
      // ユーザー一覧データを取得
      const params: any = {
        pageNum
      }
      if (userName) {
        params.playerName = userName
      }

      const response = await getRequest<
        PlayerSearchAPIRequest,
        PlayerSearchAPIResponse
      >('/api/player/search', params)

      setPlayers(response.players)
      setPaging(response)
      setCurrentPage(response.currentPageNum)
    } finally {
      setIsLoading(false)
    }
  }

  const handleSearch = (data: SearchFormData) => {
    fetchUsers(data)
  }

  const handlePageChange = (page: number) => {
    fetchUsers({
      pageNum: page
    })
  }

  return (
    <div className='content-section'>
      <h1 className='section-title'>
        <FontAwesomeIcon icon={faList} className='text-primary h-5 w-5' />
        ユーザー一覧
      </h1>

      {/* 検索フォーム */}
      <div className='mb-6'>
        <UserSearchForm onSearch={handleSearch} currentPage={currentPage} />
      </div>

      {/* ユーザー一覧 */}
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <>
          {/* ページネーション */}
          {paging && (
            <Pagination
              currentPage={currentPage}
              pageNumList={paging.pageNumList}
              isExistPrePage={paging.isExistPrePage}
              isExistNextPage={paging.isExistNextPage}
              onPageChange={handlePageChange}
              totalPages={paging.allPageCount}
            />
          )}
          <UserListTable players={players} />
          {/* ページネーション */}
          {paging && (
            <Pagination
              currentPage={currentPage}
              pageNumList={paging.pageNumList}
              isExistPrePage={paging.isExistPrePage}
              isExistNextPage={paging.isExistNextPage}
              onPageChange={handlePageChange}
              totalPages={paging.allPageCount}
            />
          )}
        </>
      )}
    </div>
  )
}

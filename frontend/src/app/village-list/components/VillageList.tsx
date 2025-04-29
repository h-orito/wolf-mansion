'use client'

import VillageListTable from '@/app/village-list/components/VillageListTable'
import VillageSearchForm, {
  SearchFormData
} from '@/app/village-list/components/VillageSearchForm'
import { getRequest } from '@/components/api/api'
import {
  VillageListVillage,
  VillageSearchAPIRequest,
  VillageSearchAPIResponse
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { faList } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useEffect, useState } from 'react'

export default function VillageList() {
  const [villageList, setVillageList] = useState<VillageListVillage[]>([])

  useEffect(() => {
    fetchVillages({} as SearchFormData)
  }, [])

  const fetchVillages = async (data: SearchFormData) => {
    const { villageName, charachipIds, skills, isRandomOrg } = data
    // 村一覧データを取得
    const params: any = {}
    if (charachipIds && charachipIds.length > 0) {
      params.charachipIds = charachipIds
    }
    if (skills && skills.length > 0) {
      params.skills = skills
    }
    if (isRandomOrg && isRandomOrg !== 'both') {
      params.isRandomOrg = isRandomOrg === 'true'
    }

    const response = await getRequest<
      VillageSearchAPIRequest,
      VillageSearchAPIResponse
    >('/api/village/search', params)

    let villageList = response.villageList
    if (villageName && villageName.length > 0) {
      villageList = response.villageList.filter((v) =>
        v.villageName.includes(villageName)
      )
    }
    setVillageList(villageList)
  }

  const handleSearch = (data: SearchFormData) => {
    fetchVillages(data)
  }

  return (
    <div className='content-section'>
      <h1 className='section-title'>
        <FontAwesomeIcon icon={faList} className='text-primary h-5 w-5' />
        村一覧
      </h1>

      {/* 検索フォーム */}
      <div className='mb-6'>
        <VillageSearchForm onSearch={handleSearch} />
      </div>

      {/* 村一覧 */}
      <VillageListTable villages={villageList} />
    </div>
  )
}

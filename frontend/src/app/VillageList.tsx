'use client'

import { getRequest } from '@/components/api/api'
import {
  VillageListContent,
  VillageListRequest,
  VillageListResponse
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { useEffect, useState } from 'react'

export default function VillageList() {
  const [villageListContent, setVillageListContent] =
    useState<VillageListContent | null>(null)

  useEffect(() => {
    const fetch = async () => {
      const response = await getRequest<
        VillageListRequest,
        VillageListResponse
      >('/api/village-list')
      setVillageListContent(response)
      console.log(response)
    }
    fetch()
  }, [])

  return <div>this is village list.</div>
}

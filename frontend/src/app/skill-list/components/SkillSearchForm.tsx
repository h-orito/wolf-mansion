'use client'

import { getRequest } from '@/components/api/api'
import Button from '@/components/ui/Button'
import InputMultiSelect from '@/components/ui/form/InputMultiSelect'
import InputSelect from '@/components/ui/form/InputSelect'
import InputText from '@/components/ui/form/InputText'
import Panel from '@/components/ui/Panel'
import {
  VillageListVillage,
  VillageSearchAPIRequest,
  VillageSearchAPIResponse
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { useEffect, useMemo, useState } from 'react'
import { FormProvider, useForm } from 'react-hook-form'
import { GroupBase, StylesConfig } from 'react-select'

type Props = {
  onSearch: (data: SearchParams) => void
  skillTags: string[]
}

export interface SearchParams {
  name?: string
  tags?: string[]
  villageId?: number
  keyword?: string
}

export default function SkillSearchForm({ onSearch, skillTags }: Props) {
  const [villages, setVillages] = useState<VillageListVillage[]>([])
  const [selectedTags, setSelectedTags] = useState<string[]>([])
  const [selectedVillageId, setSelectedVillageId] = useState<number | null>(
    null
  )

  const methods = useForm<SearchParams>({
    defaultValues: {
      name: '',
      tags: [],
      villageId: undefined,
      keyword: ''
    }
  })
  const { handleSubmit } = methods

  useEffect(() => {
    // 固定編成の村一覧を取得
    const fetchVillages = async () => {
      try {
        const response = await getRequest<
          VillageSearchAPIRequest,
          VillageSearchAPIResponse
        >('/api/village/search', { isRandomOrg: false })
        setVillages(response.villageList)
      } catch (error) {
        console.error('村一覧の取得に失敗しました', error)
      }
    }

    fetchVillages()
  }, [])

  const onSubmit = async (data: SearchParams) => {
    onSearch({
      ...data,
      tags: selectedTags,
      villageId: selectedVillage ? selectedVillage.villageId : undefined
    })
  }

  const handleReset = () => {
    methods.reset({
      name: '',
      tags: [],
      villageId: undefined,
      keyword: ''
    })
    setSelectedTags([])
    setSelectedVillageId(null)
    onSearch({})
  }

  const selectedVillage = useMemo(
    () => villages.find((v) => v.villageId === selectedVillageId),
    [villages, selectedVillageId]
  )

  const selectStyle: StylesConfig<any, false, GroupBase<any>> = {
    menu: (provided) => ({
      ...provided,
      width: 'auto',
      minWidth: '100%'
    }),
    menuList: (provided) => ({
      ...provided,
      maxHeight: '300px'
    }),
    option: (provided, state) => ({
      ...provided,
      whiteSpace: 'normal',
      wordWrap: 'break-word',
      backgroundColor: state.isFocused ? '#f0f0f0' : '#ffffff',
      color: '#333333',
      ':active': {
        backgroundColor: '#f0f0f0'
      }
    }),
    control: (provided) => ({
      ...provided,
      backgroundColor: '#ffffff',
      borderColor: '#cccccc'
    }),
    singleValue: (provided) => ({
      ...provided,
      color: '#333333'
    }),
    input: (provided) => ({
      ...provided,
      color: '#333333'
    }),
    placeholder: (provided) => ({
      ...provided,
      color: '#777777'
    })
  }

  return (
    <Panel title='役職検索'>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)} className='space-y-4'>
          <div className='mb-2'>
            <label
              htmlFor='name'
              className='block text-sm font-medium text-white'
            >
              役職名:
            </label>
            <InputText name='name' placeholder='役職名を入力' />
          </div>

          <div className='mb-2'>
            <label
              htmlFor='tags'
              className='block text-sm font-medium text-white'
            >
              特徴:
            </label>
            <InputMultiSelect
              name='tags'
              options={skillTags.map((tag) => ({
                value: tag,
                label: tag
              }))}
              values={selectedTags}
              setValue={setSelectedTags}
              placeholder='特徴を選択'
            />
          </div>

          <div className='mb-2'>
            <label
              htmlFor='villageId'
              className='block text-sm font-medium text-white'
            >
              村:
            </label>
            <InputSelect
              name='villageId'
              options={[
                { label: '指定なし', value: null },
                ...villages.map((village) => ({
                  label: `${village.villageNumber}. ${village.villageName}`,
                  value: village.villageId
                }))
              ]}
              value={
                selectedVillage
                  ? {
                      label: `${selectedVillage.villageNumber}.${selectedVillage.villageName}`,
                      value: selectedVillage.villageId
                    }
                  : null
              }
              setValue={setSelectedVillageId}
              placeholder='村を選択'
            />
          </div>

          <div className='mb-2'>
            <label
              htmlFor='name'
              className='block text-sm font-medium text-white'
            >
              キーワード:
            </label>
            <InputText
              name='keyword'
              placeholder='説明に含まれる文字列を入力'
            />
          </div>

          <div className='flex justify-end space-x-2'>
            <Button type='button' onClick={handleReset} variant='secondary'>
              リセット
            </Button>
            <Button type='submit'>検索</Button>
          </div>
        </form>
      </FormProvider>
    </Panel>
  )
}

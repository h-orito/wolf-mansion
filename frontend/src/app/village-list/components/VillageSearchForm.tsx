'use client'
import { getRequest } from '@/components/api/api'
import Button from '@/components/ui/Button'
import InputMultiSelect from '@/components/ui/form/InputMultiSelect'
import InputRadioGroup from '@/components/ui/form/InputRadioGroup'
import InputText from '@/components/ui/form/InputText'
import Panel from '@/components/ui/Panel'
import {
  Charachip,
  Skill,
  SkillSearchAPIRequest,
  SkillSearchAPIResponse
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { useEffect, useState } from 'react'
import { FormProvider, useForm } from 'react-hook-form'

type Props = {
  onSearch: (data: SearchFormData) => void
}

export interface SearchFormData {
  villageName: string
  charachipIds: number[]
  skills: string[]
  isRandomOrg: string
}

export default function VillageSearchForm({ onSearch }: Props) {
  const [charachipList, setCharachipList] = useState<Charachip[]>([])
  const [selectedCharachipIds, setSelectedCharachipIds] = useState<number[]>([])
  const [skillList, setSkillList] = useState<Skill[]>([])
  const [selectedSkills, setSelectedSkills] = useState<string[]>([])
  const [isRandomOrg, setIsRandomOrg] = useState<string>('both')

  const methods = useForm<SearchFormData>({
    defaultValues: {
      villageName: '',
      charachipIds: [],
      skills: [],
      isRandomOrg: 'both'
    }
  })
  const { handleSubmit } = methods

  useEffect(() => {
    // キャラチップ一覧を取得
    const fetchCharachipList = async () => {
      const response = await getRequest<{}, { list: Charachip[] }>(
        '/api/chara-groups'
      )
      setCharachipList(response.list)
    }

    // 役職一覧を取得
    const fetchSkillList = async () => {
      const response = await getRequest<
        SkillSearchAPIRequest,
        SkillSearchAPIResponse
      >('/api/skill/search')
      setSkillList(response)
    }

    fetchCharachipList()
    fetchSkillList()
  }, [])

  const onSubmit = async (data: SearchFormData) => {
    onSearch({
      ...data,
      charachipIds: selectedCharachipIds,
      skills: selectedSkills,
      isRandomOrg
    })
  }

  return (
    <Panel title='検索'>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)} className='space-y-4'>
          <div className='mb-2'>
            <label
              htmlFor='villageName'
              className='block text-sm font-medium text-white'
            >
              村名:
            </label>
            <InputText name='villageName' />
          </div>

          <div className='mb-2'>
            <label
              htmlFor='charachip'
              className='block text-sm font-medium text-white'
            >
              キャラチップ:
            </label>
            <InputMultiSelect
              name='charachip'
              options={charachipList.map((charachip) => ({
                value: charachip.id,
                label: charachip.name
              }))}
              values={selectedCharachipIds}
              setValue={setSelectedCharachipIds}
            />
          </div>

          <div className='mb-2'>
            <label
              htmlFor='skill'
              className='block text-sm font-medium text-white'
            >
              役職:
            </label>
            <InputMultiSelect
              name='skill'
              options={skillList.map((s) => ({
                value: s.code,
                label: s.name
              }))}
              values={selectedSkills}
              setValue={setSelectedSkills}
            />
          </div>

          <div className='mb-2'>
            <label
              htmlFor='isRandomOrg'
              className='block text-sm font-medium text-white'
            >
              編成:
            </label>
            <InputRadioGroup
              name='random-org'
              options={[
                {
                  label: '両方',
                  value: 'both'
                },
                {
                  label: '闇鍋',
                  value: 'true'
                },
                {
                  label: '固定',
                  value: 'false'
                }
              ]}
              value={isRandomOrg}
              setValue={setIsRandomOrg}
            />
          </div>

          <div className='flex justify-end'>
            <Button type='submit'>検索</Button>
          </div>
        </form>
      </FormProvider>
    </Panel>
  )
}

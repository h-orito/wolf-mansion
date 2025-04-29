'use client'

import { getRequest } from '@/components/api/api'
import {
  Skill,
  SkillSearchAPIRequest
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { useMemo, useState } from 'react'
import SkillSearchForm, { SearchParams } from './SkillSearchForm'
import Criminals from './camps/Criminals'
import Foxes from './camps/Foxes'
import Lovers from './camps/Lovers'
import Villagers from './camps/Villagers'
import Werewolves from './camps/Werewolves'
import { groupSkillsByCamp, SkillsByCamp } from './skill'

type Props = {
  skills: Skill[]
  skillTags: string[]
}

export default function SkillList({ skills: initialSkills, skillTags }: Props) {
  const [skills, setSkills] = useState(initialSkills)
  const filteredSkillCamps = useMemo(() => groupSkillsByCamp(skills), [skills])

  // 検索条件に基づいて役職をフィルタリング
  const handleSearch = async (searchParams: SearchParams) => {
    const response = await getRequest<SkillSearchAPIRequest, Skill[]>(
      '/api/skill/search',
      {
        tags: searchParams.tags,
        name: searchParams.name,
        villageId: searchParams.villageId
      }
    )
    const s = response
      .filter((skill) => skill.shortName !== 'お')
      .filter((skill) => {
        if (searchParams.keyword) {
          // 説明文にキーワードが含まれているか
          return document
            .getElementById(`skill-${skill.code.toLowerCase()}`)
            ?.textContent?.includes(searchParams.keyword)
        }
        return true
      })
    setSkills(s)
  }

  // 役職をクリックした時の処理
  const handleSkillClick = (skill: Skill) => {
    // スクロール処理
    const element = document.getElementById(`skill-${skill.code.toLowerCase()}`)
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' })
    }
  }

  return (
    <div>
      <SkillSearchForm onSearch={handleSearch} skillTags={skillTags} />

      <div className='mt-8'>
        {/* 陣営ごとの役職一覧 */}
        <div>
          <h2 className='mb-4 text-xl font-bold'>役職一覧</h2>

          {filteredSkillCamps.length === 0 ? (
            <p className='text-gray-400'>
              検索条件に一致する役職がありません。
            </p>
          ) : (
            filteredSkillCamps
              .filter((sc) => sc.skills.length > 0)
              .map(({ camp, skills }: SkillsByCamp) => (
                <div key={camp.code} className='mb-6'>
                  <h3 className='mb-2 text-lg font-semibold'>{camp.name}</h3>
                  <div>
                    {skills.map((skill, index) => (
                      <span key={skill.code}>
                        <button
                          onClick={() => handleSkillClick(skill)}
                          className='link-style cursor-pointer'
                        >
                          {skill.name}
                        </button>
                        {index < skills.length - 1 && (
                          <span className='text-gray-400'> / </span>
                        )}
                      </span>
                    ))}
                  </div>
                </div>
              ))
          )}
        </div>

        {/* 役職説明 */}
        <div>
          <h2 className='mb-4 text-xl font-bold'>役職説明</h2>
          <Villagers skills={skills} />
          <Werewolves skills={skills} />
          <Foxes skills={skills} />
          <Lovers skills={skills} />
          <Criminals skills={skills} />
        </div>
      </div>
    </div>
  )
}

import { getRequest } from '@/components/api/api'
import Breadcrumb from '@/components/ui/Breadcrumb'
import {
  Skill,
  SkillTagListAPIResponse
} from '@/lib/openapi-typescript/wolf-mansion/types'
import { Metadata } from 'next'
import SkillList from './components/SkillList'

export const metadata: Metadata = {
  title: '役職一覧 | WOLF MANSION',
  description: 'WOLF MANSIONに登場する役職の一覧と説明'
}

export default async function SkillListPage() {
  // 役職一覧を取得
  const skills = await getRequest<void, Skill[]>('/api/skill/search')

  // 役職タグ一覧を取得
  const skillTags = await getRequest<void, SkillTagListAPIResponse>(
    '/api/skill-tag-list'
  )

  return (
    <main className='container mx-auto px-4 py-8'>
      <Breadcrumb items={[{ label: '役職一覧' }]} />

      <h1 className='mb-6 text-2xl font-bold'>役職一覧</h1>

      <div className='mb-8'>
        <p className='mb-4'>人狼館の事件簿村に登場する役職の一覧と説明です。</p>
      </div>

      <SkillList
        skills={skills.filter((skill) => skill.shortName !== 'お')}
        skillTags={skillTags}
      />
    </main>
  )
}

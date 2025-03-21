import Alert from '@/components/ui/Alert'
import { Skill } from '@/lib/openapi-typescript/wolf-mansion/types'

type SkillCardProps = {
  skill: Skill
  shortDescription: string
  description: React.ReactNode
}

export default function SkillCard({
  skill,
  shortDescription,
  description
}: SkillCardProps) {
  return (
    <div
      id={`skill-${skill.code}`}
      className='mb-4 overflow-hidden rounded-md border border-gray-700 bg-[#333333] transition-colors hover:border-[var(--primary)]'
    >
      <div className='p-4'>
        <div className='mb-2 flex items-center border-b border-gray-700'>
          <span className='mr-2 font-bold'>【{skill.shortName}】</span>
          <h3 className='font-semibold'>{skill.name}</h3>
        </div>
        <div className='pt-3'>
          <Alert className='mb-3 break-words whitespace-pre-wrap'>
            {shortDescription}
          </Alert>
          <ul className='list-content'>{description}</ul>
        </div>
      </div>
    </div>
  )
}

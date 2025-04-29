import { Camp, Skill } from '@/lib/openapi-typescript/wolf-mansion/types'

const camps: Camp[] = [
  {
    code: 'VILLAGER',
    name: '村人陣営'
  },
  {
    code: 'WEREWOLF',
    name: '人狼陣営'
  },
  {
    code: 'FOX',
    name: '狐陣営'
  },
  {
    code: 'LOVERS',
    name: '恋人陣営'
  },
  {
    code: 'CRIMINAL',
    name: '愉快犯陣営'
  }
]

export type SkillsByCamp = {
  camp: Camp
  skills: Skill[]
}

export const groupSkillsByCamp = (skills: Skill[]): SkillsByCamp[] =>
  camps.map((camp) => {
    return {
      camp: camp,
      skills: skills.filter((skill) => skill.camp.code === camp.code)
    } as SkillsByCamp
  })

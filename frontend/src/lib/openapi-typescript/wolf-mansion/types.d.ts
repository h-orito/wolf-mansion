import type { components, paths } from './schema'

// myself
type MyselfAPIResponse =
  paths['/api/myself']['get']['responses']['200']['content']['*/*']
// new-player
type NewPlayerPostAPIRequest =
  paths['/api/new-player']['post']['requestBody']['content']['application/json']
type NewPlayerPostAPIResponse =
  paths['/api/new-player']['post']['responses']['200']['content']['*/*']
// login
type LoginPostAPIRequest =
  paths['/api/login']['post']['requestBody']['content']['application/json']
type LoginPostAPIResponse =
  paths['/api/login']['post']['responses']['200']['content']['*/*']
// change-password
type ChangePasswordPutAPIRequest =
  paths['/api/change-password']['put']['requestBody']['content']['application/json']

type PlayerView = components['schemas']['PlayerView']

type PlayerSearchAPIRequest =
  paths['/api/player/search']['get']['parameters']['query']['form']
type PlayerSearchAPIResponse =
  paths['/api/player/search']['get']['responses']['200']['content']['*/*']

type Player = components['schemas']['PlayerContent']
type Paging = Omit<PlayerSearchAPIResponse, 'players'>

// village
type VillageSearchAPIRequest =
  paths['/api/village/search']['get']['parameters']['query']['form']
type VillageSearchAPIResponse =
  paths['/api/village/search']['get']['responses']['200']['content']['*/*']
type VillageListContent = components['schemas']['VillageListContent']
type VillageListVillage = components['schemas']['VillageListVillage']

// charachip
type CharaGroupsAPIResponse =
  paths['/api/chara-groups']['get']['responses']['200']['content']['*/*']
type CharaGroupAPIResponse =
  paths['/api/chara-group/{charaGroupId}']['get']['responses']['200']['content']['*/*']

type Charachip = components['schemas']['Charachip']
type Chara = components['schemas']['Chara']

// skill
type SkillSearchAPIRequest =
  paths['/api/skill/search']['get']['parameters']['query']['form']
type SkillSearchAPIResponse =
  paths['/api/skill/search']['get']['responses']['200']['content']['*/*']
type SkillTagListAPIResponse =
  paths['/api/skill-tag-list']['get']['responses']['200']['content']['*/*']

type Skill = components['schemas']['Skill']
type Camp = Pick<components['schemas']['Camp'], 'code' | 'name'>

import type { components, paths } from './schema'

type VillageSearchRequest =
  paths['/api/village/search']['get']['parameters']['query']['form']
type VillageSearchResponse =
  paths['/api/village/search']['get']['responses']['200']['content']['*/*']
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
type VillageListContent = components['schemas']['VillageListContent']
type VillageListVillage = components['schemas']['VillageListVillage']

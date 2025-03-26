import type { paths, components } from './schema'

type VillageListRequest =
  paths['/api/village-list']['get']['parameters']['query']
type VillageListResponse =
  paths['/api/village-list']['get']['responses']['200']['content']['*/*']

type VillageListContent = components['schemas']['VillageListContent']

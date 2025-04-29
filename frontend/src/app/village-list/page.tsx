import Breadcrumb from '@/components/ui/Breadcrumb'
import VillageList from './components/VillageList'

export default function VillageListPage() {
  return (
    <div className='mt-8'>
      <Breadcrumb items={[{ label: '村一覧' }]} />

      <VillageList />
    </div>
  )
}

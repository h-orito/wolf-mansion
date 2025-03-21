import Breadcrumb from '@/components/ui/Breadcrumb'
import CharachipList from './components/CharachipList'

export default function CharachipsPage() {
  return (
    <div>
      <Breadcrumb items={[{ label: 'キャラチップ一覧' }]} />
      <CharachipList />
    </div>
  )
}

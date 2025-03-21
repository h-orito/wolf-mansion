import Breadcrumb from '@/components/ui/Breadcrumb'
import { UserList } from './components/UserList'

export default function UserListPage() {
  return (
    <div>
      <Breadcrumb items={[{ label: 'ユーザー一覧' }]} />
      <UserList />
    </div>
  )
}

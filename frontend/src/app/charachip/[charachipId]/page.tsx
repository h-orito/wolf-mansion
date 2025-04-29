import Breadcrumb from '@/components/ui/Breadcrumb'
import CharachipDetail from './components/CharachipDetail'

export default function CharachipDetailPage({
  params
}: {
  params: { charachipId: string }
}) {
  const charachipId = params.charachipId

  return (
    <div>
      <Breadcrumb
        items={[
          { label: 'キャラチップ一覧', href: '/charachips' },
          { label: 'キャラチップ詳細' }
        ]}
      />
      <CharachipDetail charachipId={charachipId} />
    </div>
  )
}

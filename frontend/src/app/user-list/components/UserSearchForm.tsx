'use client'
import Button from '@/components/ui/Button'
import InputText from '@/components/ui/form/InputText'
import Panel from '@/components/ui/Panel'
import { FormProvider, useForm } from 'react-hook-form'

type Props = {
  onSearch: (data: SearchFormData) => void
  currentPage?: number
}

export interface SearchFormData {
  userName?: string
  pageNum?: number
}

export default function UserSearchForm({ onSearch, currentPage = 1 }: Props) {
  const methods = useForm<SearchFormData>({
    defaultValues: {
      userName: '',
      pageNum: currentPage
    }
  })
  const { handleSubmit } = methods

  const onSubmit = async (data: SearchFormData) => {
    onSearch({
      ...data,
      pageNum: currentPage
    })
  }

  return (
    <Panel title='検索'>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)} className='space-y-4'>
          <div className='mb-2'>
            <label
              htmlFor='userName'
              className='block text-sm font-medium text-white'
            >
              ユーザー名:
            </label>
            <InputText name='userName' />
          </div>

          <div className='flex justify-end'>
            <Button type='submit'>検索</Button>
          </div>
        </form>
      </FormProvider>
    </Panel>
  )
}

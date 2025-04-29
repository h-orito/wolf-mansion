'use client'

import { Dispatch, SetStateAction } from 'react'
import Select, { MultiValue } from 'react-select'

type Props = {
  name: string
  options: Array<Option>
  values: any[]
  setValue: Dispatch<SetStateAction<any[]>>
  placeholder?: string
  disabled?: boolean
}

type Option = {
  label: string
  value: any
}

export default function InputMultiSelect({
  name,
  options,
  values,
  setValue,
  placeholder,
  disabled
}: Props) {
  const handleChange = (newValue: MultiValue<Option>) => {
    setValue(newValue.map((v) => v.value))
  }

  // valuesに一致するoptionを探す
  const selectedOptions = options.filter((o: Option) =>
    values.includes(o.value)
  )

  return (
    // Hydration failed because the server rendered HTML didn't match the client. が出るためinstanceIdを指定
    // see https://github.com/JedWatson/react-select/issues/5459
    <div suppressHydrationWarning className='flex justify-center'>
      <Select
        instanceId={`${name}_multi-select`}
        className='input-select'
        value={selectedOptions}
        options={options}
        placeholder={placeholder ?? '選択してください'}
        isMulti
        isSearchable
        onChange={handleChange}
        isDisabled={disabled}
        styles={{
          menu: (provided) => ({
            ...provided,
            width: 'auto',
            minWidth: '100%'
          }),
          menuList: (provided) => ({
            ...provided,
            maxHeight: '300px'
          }),
          option: (provided, state) => ({
            ...provided,
            whiteSpace: 'normal',
            wordWrap: 'break-word',
            backgroundColor: state.isFocused ? '#f0f0f0' : '#ffffff',
            color: '#333333',
            ':active': {
              backgroundColor: '#f0f0f0'
            }
          }),
          control: (provided) => ({
            ...provided,
            backgroundColor: '#ffffff',
            borderColor: '#cccccc'
          }),
          singleValue: (provided) => ({
            ...provided,
            color: '#333333'
          }),
          input: (provided) => ({
            ...provided,
            color: '#333333'
          }),
          placeholder: (provided) => ({
            ...provided,
            color: '#777777'
          })
        }}
        menuPortalTarget={
          typeof document !== 'undefined' ? document.body : null
        }
        menuPosition='fixed'
      />
    </div>
  )
}

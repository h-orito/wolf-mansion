'use client'

import { RegisterOptions, useFormContext } from 'react-hook-form'

interface Props {
  name: string
  className?: string
  placeholder?: string
  registerOptions?: RegisterOptions
}

export default function InputText(props: Props) {
  const { register } = useFormContext()
  const { name, className, placeholder, registerOptions } = props
  return (
    <input
      type='text'
      className={`input-style ${className ?? ''}`}
      placeholder={placeholder}
      {...register(name, registerOptions)}
    />
  )
}

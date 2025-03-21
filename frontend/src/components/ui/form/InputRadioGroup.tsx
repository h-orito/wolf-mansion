'use client'

type Props = {
  className?: string
  name: string
  options: Array<Option>
  value: any
  setValue: (value: any) => void
  disabled?: boolean
}

type Option = {
  label: string
  value: any
}

export default function InputRadioGroup({
  className,
  name,
  options,
  value,
  setValue,
  disabled
}: Props) {
  return (
    <div className={`flex ${className ?? ''}`}>
      {options.map((option, index) => {
        const roundClass =
          index === 0
            ? 'rounded-l-sm border-l'
            : index === options.length - 1
              ? 'rounded-r-sm border-r'
              : 'border'
        const checkedClass = value === option.value ? 'input-active' : ''
        return (
          <div key={index}>
            <input
              type='radio'
              name={`${name}_radio_${index}`}
              className='hidden'
              value={option.value}
              id={`${name}_radio_${index}`}
              checked={value === option.value}
              onChange={(e: any) => setValue(e.target.value)}
              disabled={disabled}
            />
            <label
              className={`input-radio cursor-pointer border-y ${checkedClass} ${roundClass}`}
              htmlFor={`${name}_radio_${index}`}
            >
              {option.label}
            </label>
          </div>
        )
      })}
    </div>
  )
}

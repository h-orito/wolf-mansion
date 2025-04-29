'use client'

import { ButtonHTMLAttributes, ReactNode } from 'react'

type ButtonVariant = 'primary' | 'secondary' | 'danger'
type ButtonSize = 'sm' | 'md' | 'lg'

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  children: ReactNode
  variant?: ButtonVariant
  size?: ButtonSize
  isLoading?: boolean
  fullWidth?: boolean
}

const Button = ({
  children,
  variant = 'primary',
  size = 'md',
  isLoading = false,
  fullWidth = false,
  className = '',
  disabled,
  ...props
}: ButtonProps) => {
  // バリアントに基づくスタイル
  const variantStyles = {
    primary: 'bg-primary hover:bg-green-700 text-white',
    secondary: 'bg-secondary hover:bg-gray-700 text-white',
    danger: 'bg-danger hover:bg-red-700 text-white'
  }

  // サイズに基づくスタイル
  const sizeStyles = {
    sm: 'px-2 py-1 text-xs',
    md: 'px-4 py-2 text-sm',
    lg: 'px-6 py-3 text-base'
  }

  // 無効状態のスタイル
  const disabledStyle = 'opacity-50 cursor-not-allowed'

  // 幅のスタイル
  const widthStyle = fullWidth ? 'w-full' : ''

  return (
    <button
      className={`rounded-md font-medium transition-colors ${
        variantStyles[variant]
      } ${sizeStyles[size]} ${widthStyle} ${
        disabled || isLoading ? disabledStyle : 'cursor-pointer'
      } ${className}`}
      disabled={disabled || isLoading}
      {...props}
    >
      {isLoading ? (
        <div className='flex items-center justify-center'>
          <svg
            className='mr-2 h-4 w-4 animate-spin'
            viewBox='0 0 24 24'
            fill='none'
            xmlns='http://www.w3.org/2000/svg'
          >
            <circle
              className='opacity-25'
              cx='12'
              cy='12'
              r='10'
              stroke='currentColor'
              strokeWidth='4'
            ></circle>
            <path
              className='opacity-75'
              fill='currentColor'
              d='M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z'
            ></path>
          </svg>
          処理中...
        </div>
      ) : (
        children
      )}
    </button>
  )
}

export default Button

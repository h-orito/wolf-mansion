'use client'

import { ReactNode } from 'react'

type AlertVariant =
  | 'primary'
  | 'secondary'
  | 'success'
  | 'danger'
  | 'warning'
  | 'info'

interface AlertProps {
  children: ReactNode
  variant?: AlertVariant
  className?: string
}

const Alert = ({
  children,
  variant = 'primary',
  className = ''
}: AlertProps) => {
  // バリアントに基づくスタイル（テキストと枠線の色のみ）
  const variantStyles = {
    primary: 'border-[#00bc8c] text-[#00bc8c]',
    secondary: 'border-gray-600 text-gray-300',
    success: 'border-green-500 text-green-500',
    danger: 'border-red-500 text-red-500',
    warning: 'border-yellow-500 text-yellow-500',
    info: 'border-blue-500 text-blue-500'
  }

  return (
    <div
      className={`rounded-md border bg-gray-800 p-3 text-sm ${variantStyles[variant]} ${className}`}
    >
      {children}
    </div>
  )
}

export default Alert

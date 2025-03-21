'use client'

import { ReactNode } from 'react'

type AlertVariant = 'primary' | 'secondary' | 'danger' | 'warning' | 'info'

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
    primary: 'border-white text-white',
    secondary: 'border-secondary text-secondary',
    danger: 'border-danger text-danger',
    warning: 'border-warning text-warning',
    info: 'border-info text-info'
  }

  return (
    <div
      className={`rounded-md border p-3 text-sm ${variantStyles[variant]} ${className}`}
    >
      {children}
    </div>
  )
}

export default Alert

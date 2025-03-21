import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React, { useEffect, useRef, useState } from 'react'

interface PanelProps {
  title: string
  children: React.ReactNode
}

const Panel: React.FC<PanelProps> = ({ title, children }) => {
  const [isOpen, setIsOpen] = useState(false)
  const contentRef = useRef<HTMLDivElement>(null)
  const [contentHeight, setContentHeight] = useState(0)

  useEffect(() => {
    if (contentRef.current) {
      setContentHeight(contentRef.current.scrollHeight)
    }
  }, [children])

  const togglePanel = () => {
    setIsOpen(!isOpen)
  }

  return (
    <div className='w-full rounded-md border border-gray-500 shadow-sm'>
      <div
        className={`bg-secondary flex cursor-pointer items-center justify-between rounded-t-md px-4 py-2 ${isOpen ? 'border-b border-gray-500' : ''}`}
        onClick={togglePanel}
      >
        <h3 className='text-lg font-semibold'>{title}</h3>
        <FontAwesomeIcon
          icon={isOpen ? faChevronUp : faChevronDown}
          className='h-5 w-5 text-white'
        />
      </div>
      <div
        ref={contentRef}
        className={`overflow-hidden bg-[#303030] transition-all duration-300 ease-in-out`}
        style={{ height: isOpen ? contentHeight : 0 }}
      >
        <div className='p-4'>{children}</div>
      </div>
    </div>
  )
}

export default Panel

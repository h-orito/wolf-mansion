import { faChevronRight, faHome } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Link from 'next/link'

export type BreadcrumbItem = {
  label: string
  href?: string
}

type BreadcrumbProps = {
  items: BreadcrumbItem[]
}

export default function Breadcrumb({ items }: BreadcrumbProps) {
  return (
    <nav className='mb-4 py-2' aria-label='breadcrumb'>
      <ol className='flex flex-wrap items-center'>
        <li className='flex items-center text-xs'>
          <Link href='/' className='link-style flex items-center'>
            <FontAwesomeIcon icon={faHome} className='mr-1 h-3 w-3' />
            ホーム
          </Link>
        </li>
        {items.map((item, index) => (
          <li key={index} className='flex items-center text-xs'>
            <FontAwesomeIcon
              icon={faChevronRight}
              className='mx-2 h-3 w-3 text-gray-400'
            />
            {item.href ? (
              <Link href={item.href} className='link-style flex items-center'>
                {item.label}
              </Link>
            ) : (
              <span className='text-gray-300'>{item.label}</span>
            )}
          </li>
        ))}
      </ol>
    </nav>
  )
}

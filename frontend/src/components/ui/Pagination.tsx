'use client'

type Props = {
  currentPage: number
  pageNumList: number[]
  isExistPrePage: boolean
  isExistNextPage: boolean
  onPageChange: (page: number) => void
  totalPages?: number // 最後のページへの遷移用
}

export default function Pagination({
  currentPage,
  pageNumList,
  isExistPrePage,
  isExistNextPage,
  onPageChange,
  totalPages
}: Props) {
  return (
    <div className='my-2 flex justify-end text-xs'>
      <nav className='flex items-center'>
        {/* 最初のページへ */}
        <button
          onClick={() => isExistPrePage && onPageChange(1)}
          disabled={!isExistPrePage}
          className={`border-primary rounded-l border border-r-0 px-3 py-1 ${
            isExistPrePage
              ? 'text-primary hover:bg-primary cursor-pointer hover:text-white'
              : 'cursor-not-allowed bg-gray-600 text-white'
          }`}
        >
          {'<<'}
        </button>
        {/* 前のページへ */}
        <button
          onClick={() => isExistPrePage && onPageChange(currentPage - 1)}
          disabled={!isExistPrePage}
          className={`border-primary border border-r-0 px-3 py-1 ${
            isExistPrePage
              ? 'text-primary hover:bg-primary cursor-pointer hover:text-white'
              : 'cursor-not-allowed bg-gray-600 text-white'
          }`}
        >
          {'<'}
        </button>
        {pageNumList.map((page, index) => (
          <button
            key={page}
            onClick={() => onPageChange(page)}
            className={`border-primary cursor-pointer border border-r-0 px-3 py-1 ${
              page === currentPage
                ? 'bg-primary text-white'
                : 'text-primary hover:bg-primary hover:text-white'
            }`}
          >
            {page}
          </button>
        ))}
        {/* 次のページへ */}
        <button
          onClick={() => isExistNextPage && onPageChange(currentPage + 1)}
          disabled={!isExistNextPage}
          className={`border-primary border border-r-0 px-3 py-1 ${
            isExistNextPage
              ? 'text-primary hover:bg-primary cursor-pointer hover:text-white'
              : 'cursor-not-allowed bg-gray-600 text-white'
          }`}
        >
          {'>'}
        </button>
        {/* 最後のページへ */}
        <button
          onClick={() =>
            isExistNextPage &&
            pageNumList.length > 0 &&
            onPageChange(totalPages ?? pageNumList[pageNumList.length - 1])
          }
          disabled={!isExistNextPage || pageNumList.length === 0}
          className={`border-primary rounded-r border px-3 py-1 ${
            isExistNextPage && pageNumList.length > 0
              ? 'text-primary hover:bg-primary cursor-pointer hover:text-white'
              : 'cursor-not-allowed bg-gray-600 text-white'
          }`}
        >
          {'>>'}
        </button>
      </nav>
    </div>
  )
}

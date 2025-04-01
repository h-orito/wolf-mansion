'use client'

import Modal from './Modal'

type DonationModalProps = {
  isOpen: boolean
  onClose: () => void
}

const DonationModal = ({ isOpen, onClose }: DonationModalProps) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} title='投げ銭'>
      <div className='space-y-6'>
        <div>
          <h3 className='mb-2 text-lg font-medium text-white'>
            Amazonほしいものリスト
          </h3>
          <div className='flex justify-end'>
            <a
              href='https://www.amazon.jp/hz/wishlist/ls/1KZSJAJS1ETW4?ref_=wl_share'
              target='_blank'
              rel='noopener noreferrer'
              className='inline-block rounded bg-blue-600 px-4 py-2 text-white transition-colors hover:bg-blue-700'
            >
              ほしいものリストから贈る
            </a>
          </div>
        </div>

        <div>
          <h3 className='mb-2 text-lg font-medium text-white'>
            Amazonギフト券（Eメールタイプ）
          </h3>
          <div className='flex justify-end'>
            <a
              href='https://www.amazon.co.jp/dp/B004N3APGO'
              target='_blank'
              rel='noopener noreferrer'
              className='inline-block rounded bg-blue-600 px-4 py-2 text-white transition-colors hover:bg-blue-700'
            >
              Amazonギフト券を贈る
            </a>
          </div>
          <p className='mt-2 text-sm text-gray-300'>
            受取人に「wolfort<span className='text-gray-400'>あっと</span>
            googlegroups.com」を指定してください（あっとのところは@に変えてください）。
          </p>
          <p className='mt-1 text-sm text-gray-300'>
            金額は15円以上で自由に変更できます。
          </p>
        </div>

        <div>
          <h3 className='mb-2 text-lg font-medium text-white'>
            Amazonアソシエイト経由で買い物
          </h3>
          <div className='flex justify-end'>
            <a
              href='https://amzn.to/48auG7Q'
              target='_blank'
              rel='noopener noreferrer'
              className='inline-block rounded bg-blue-600 px-4 py-2 text-white transition-colors hover:bg-blue-700'
            >
              Amazonで買い物をする
            </a>
          </div>
          <p className='mt-2 text-sm text-gray-300'>
            ここからAmazonに遷移して何かしらの商品をカートに追加＆購入すると、開発者に若干の紹介料が入ります。
          </p>
        </div>

        <div>
          <h3 className='mb-2 text-lg font-medium text-white'>Pixiv Fanbox</h3>
          <div className='flex justify-end'>
            <a
              href='https://ort.fanbox.cc/'
              target='_blank'
              rel='noopener noreferrer'
              className='inline-block rounded bg-blue-600 px-4 py-2 text-white transition-colors hover:bg-blue-700'
            >
              Fanboxで支援する
            </a>
          </div>
        </div>
      </div>
    </Modal>
  )
}

export default DonationModal

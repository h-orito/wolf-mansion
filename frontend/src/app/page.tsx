import Image from 'next/image'
import Link from 'next/link'

export default function HomePage() {
  return (
    <div>
      <div className='w-full'>
        <Image
          src='/wolf-mansion/images/top.jpg'
          width={1140}
          height={428}
          alt='WOLF MANSION'
        />
      </div>
      <div className='mt-8 bg-[#333333]'>
        <div className='p-8'>
          <div className='flex justify-center'>
            状況のみで推理・説得する、新しい人狼
          </div>
          <div className='flex justify-center text-center mt-6'>
            <p className='text-xs'>
              WOLF MANSIONでは、
              <br className='hidden md:block' />
              占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と
              <br className='hidden md:block' />
              【投票】 の2つを使って推理・説得する
              <br className='hidden md:block' />
              「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。
            </p>
          </div>
        </div>
        <div className='grid grid-cols-3 gap-1'>
          <Link href='/about'></Link>
          <div>本サイトは</div>
          <div>人狼館の事件簿村</div>
          <div>お知らせ</div>
          <div>ルール</div>
          <div>よくある質問</div>
          <div>役職一覧</div>
        </div>
      </div>
    </div>
  )
}

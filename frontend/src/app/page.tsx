import { getRequest } from '@/components/api/api'
import Image from 'next/image'
import Link from 'next/link'
import VillageList from './VillageList'
import { Roboto_Mono } from 'next/font/google'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {
  faBook,
  faBullhorn,
  faCircleInfo,
  faCircleQuestion,
  faQuestion,
  faUser,
  faUserMinus,
  faWrench
} from '@fortawesome/free-solid-svg-icons'

const robotoMono = Roboto_Mono({
  variable: '--font-roboto-mono',
  subsets: ['latin']
})
export default async function HomePage() {
  const villages = await getRequest('/api/village-list')
  return (
    <div>
      <div className='relative w-full'>
        <Image
          src='/wolf-mansion/images/top.jpg'
          width={1140}
          height={428}
          alt='WOLF MANSION'
        />
        <p
          className={`${robotoMono.variable} absolute bottom-1 left-1 text-xl md:text-3xl lg:text-4xl`}
        >
          <span className='text-red-500'>W</span>OLF{' '}
          <span className='text-red-500'>M</span>ANSION
        </p>
      </div>
      <div className='mt-8 bg-[#333333]'>
        <div className='p-8'>
          <div className='flex justify-center'>
            状況のみで推理・説得する、新しい人狼
          </div>
          <div className='mt-6 flex justify-center text-center'>
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
        <div className='grid grid-cols-3'>
          <Link href='/about'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faCircleInfo} className='h-4 w-4' />
              </div>
              <p>本サイトは</p>
              <p>About</p>
            </div>
          </Link>
          <Link href='/about'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              </div>
              <p>人狼館の事件簿村</p>
              <p>Introduction</p>
            </div>
          </Link>
          <Link href='/announce'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faBullhorn} className='h-4 w-4' />
              </div>
              <p>お知らせ</p>
              <p>Announcement</p>
            </div>
          </Link>
          <Link href='/rule'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faBook} className='h-4 w-4' />
              </div>
              <p>ルール</p>
              <p>Rules</p>
            </div>
          </Link>
          <Link href='/faq'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              </div>
              <p>よくある質問</p>
              <p>FAQ</p>
            </div>
          </Link>
          <Link href='/skill'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faQuestion} className='h-4 w-4' />
              </div>
              <p>役職一覧</p>
              <p>Skills</p>
            </div>
          </Link>
        </div>
      </div>
      <div className='mt-8 bg-[#333333]'>
        <div className='p-8'>
          <div className='flex justify-center'>登録 / ログイン</div>
        </div>
        <div className='grid grid-cols-3'>
          <Link href='/about'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faUser} className='h-4 w-4' />
              </div>
              <p>マイページ</p>
              <p>MyPage</p>
            </div>
          </Link>
          <Link href='/about'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faWrench} className='h-4 w-4' />
              </div>
              <p>パスワード変更</p>
              <p>Change Password</p>
            </div>
          </Link>
          <Link href='/logout'>
            <div className='flex w-full flex-col gap-2 border border-[#333333] bg-[#0b162a] p-4 text-center text-xs leading-4 text-white hover:border-green-500'>
              <div className='flex justify-center'>
                <FontAwesomeIcon icon={faUserMinus} className='h-4 w-4' />
              </div>
              <p>ログアウト</p>
              <p>Logout</p>
            </div>
          </Link>
        </div>
      </div>
    </div>
  )
}

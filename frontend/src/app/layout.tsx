import type { Metadata } from 'next'
import { Noto_Sans_JP } from 'next/font/google'
import './globals.css'
import { ServerCookie } from '@/components/cookie/ServerCookie'

const notoSans = Noto_Sans_JP({
  variable: '--font-noto-sans-jp',
  subsets: ['latin']
})

export const metadata: Metadata = {
  title: 'WOLF MANSION',
  description:
    'WOLF MANSIONでは、占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と【投票】 の2つを使って推理・説得する 「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。発言による推理・説得が必要ないため、忙しい方も参加しやすく、ひと味違った人狼ゲームが楽しめます。',
  openGraph: {
    type: 'website',
    locale: 'ja_JP',
    url: 'https://wolfort.dev/wolf-mansion',
    siteName: 'WOLF MANSION',
    images: [
      {
        url: 'https://wolfort.dev/wolf-mansion/images/ogp-top.png',
        width: 674,
        height: 428,
        alt: 'WOLF MANSION'
      }
    ],
    description:
      'WOLF MANSIONでは、占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と【投票】 の2つを使って推理・説得する 「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。'
  },
  twitter: {
    card: 'summary_large_image',
    site: '@ort_dev'
  }
}

export default function RootLayout({
  children
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html>
      <body className={`${notoSans.variable} flex justify-center px-2`}>
        <div className='lg:w-[960px] md:w-[720px] w-full'>
          <ServerCookie>{children}</ServerCookie>
        </div>
      </body>
    </html>
  )
}

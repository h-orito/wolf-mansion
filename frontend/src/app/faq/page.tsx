import Breadcrumb from '@/components/ui/Breadcrumb'
import { faCircleQuestion } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

export default function FAQPage() {
  return (
    <div className='mt-8'>
      <Breadcrumb items={[{ label: 'よくある質問' }]} />
      <div className='content-section'>
        <h1 className='section-title'>
          <FontAwesomeIcon
            icon={faCircleQuestion}
            className='text-primary h-5 w-5'
          />
          よくある質問
        </h1>

        <div className='space-y-6'>
          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              入村できないのですが
            </p>
            <p className='mt-2 pl-6 text-sm'>
              入村できない条件は以下のいずれかです
              <br />
              ・ログインしていない
              <br />
              ・すでに参加（見学）人数が上限に達している
              <br />
              ・突然死したことがあり、入村制限が解除されていない
              <br />
              ・その他国主により入村を制限されている
              <br />
              理由が思い当たらない場合は国主までご連絡ください。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              相談用の村がないようですが勝手に村を建てても良いですか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              ご自由に建てていただいて結構です！歓迎します。
              <br />
              （荒らし防止で、1回以上参加したことがある方のみ建てられます）
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              全員死亡した場合、どの陣営の勝利となりますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>人狼陣営の勝利となります。</p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              徘徊の能力セットでエラーが出ます
            </p>
            <p className='mt-2 pl-6 text-sm'>
              始点と終点だけでなく、鳴らす部屋全てを選択してみてください。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              呪殺と妖狐への襲撃が重なった場合、占い視点で人狼が妖狐を襲撃したことはわかりますか？
              / 人狼視点で呪殺があったことはわかりますか？
            </p>
            <p className='text-primary mt-2 flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              護衛と襲撃が重なった場合、狩人視点で護衛成功かわかりますか？ /
              人狼視点で護衛されたか妖狐噛みなのかわかりますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              あらゆる条件でわからないようにしています。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              恋人陣営役職に狐憑きが付与されている場合勝利条件はどうなりますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              恋絆が付与されていなければ勝利条件は狐陣営の勝利となります。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              村が始まったら背景が暗くなって操作を受け付けない /
              ページ分割しても保存されない /
              「確認しました」を押しても役職確認のメッセージが出る
            </p>
            <p className='mt-2 pl-6 text-sm'>
              お手数ですがwolfort.netのcookieを削除してみてください。「cookie
              削除 ブラウザ名」で検索するとやり方が出てきます。
              <br />
              それでも直らない場合はキャッシュも削除してみてください。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              「当サイトは広告料でサーバー代を賄っています。広告表示にご協力ください。」のメッセージが出る
            </p>
            <p className='mt-2 pl-6 text-sm'>
              広告ブロックのアドオンを入れている等で広告が表示されていない場合に表示されます。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              恋絆がついていて後追い以外の要因で死亡した場合、後追いメッセージは出ますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>出ません。</p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              恋絆が付与された直後に死亡した人も役職欄の恋絆対象一覧に表示されますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>されます。</p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              絡新婦/美人局などの恋絆がつかない恋人陣営は恋絆対象一覧が表示されますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>されません。</p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              絶対人狼に恋絆がついて恋愛対象が死んだ場合、どのような処理になりますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              毎日復活した直後に後追死します。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              絶対人狼の復活条件は絶対人狼以外の人狼が生存していることですが、一匹狼は含まれますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              含まれません（「人狼系役職」に一匹狼は含まれません）。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              一匹狼←→人狼　の襲撃はお互い成功しますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              お互い襲撃耐性がないので、能力が発動すれば成功します。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              自分の発言も含めて発言が何も表示されないの！たすけて！
            </p>
            <p className='mt-2 pl-6 text-sm'>
              自分宛発言で抽出してしまっているかも？元に戻すを押してみてください。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              パン屋が全滅した日に転生者/申し子がパン屋として復活した場合、メッセージはどうなりますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              パン屋の処理が復活よりも後なので、全滅したメッセージは流れず、パンが焼けます。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              転生者/申し子が霊能者として復活した場合、過去の結果を見ることはできますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              過去に霊能者が1名以上生存していた期間の結果を見ることができます。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              転生者/申し子が導師/魔神官/稲荷として復活した場合、過去の結果を見ることはできますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              過去に導師/魔神官/稲荷が1名以上生存していた期間の結果を見ることができます。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              転生者/申し子が検死官として復活した場合、過去の結果を見ることはできますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              過去に検死官が1名以上生存していた期間の結果を見ることができます。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              一度しか能力行使できない役職で能力行使した後にトラックの強制転生の前後で役職が変化した場合、転生後の能力を行使することはできますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              役職ごとに回数管理しているため、その役職で行使していなければ行使できます。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              導師（または村ローカルルールで許可されている導師騙り）はどの程度の騙りまで許されますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              突然死・処刑により死亡していない対象の結果を報告したり、存在しない役職として報告するなど、絶対にありえない結果とするのはNGです。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              探偵（または村ローカルルールで許可されている探偵騙り）はどの程度の騙りまで許されますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              鳴っていない足音の結果を報告したり、存在しない役職として報告するなど、絶対にありえない結果とするのはNGです。調査したのと別の足音を報告するのはOKです。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              占い師が管狐を占って呪殺した場合、管狐の占いは発動しますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              発動します。占い系の能力は、占い処理の開始時点で生存していれば発動します。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              死霊術師や陰陽師が能力行使→処刑死→蘇生者に蘇生された場合、死霊術や降霊は発動しますか？
            </p>
            <p className='mt-2 pl-6 text-sm'>
              発動しません。他者蘇生系の能力は、他者蘇生処理の開始時点で生存している場合のみ発動します。
            </p>
          </div>

          <div>
            <p className='text-primary flex items-center gap-2 font-bold'>
              <FontAwesomeIcon icon={faCircleQuestion} className='h-4 w-4' />
              ルールにもここにも書かれてないことでわからないことがあるのですが
            </p>
            <p className='mt-2 pl-6 text-sm'>
              入村している場合は「@国主」をつけて発言すると国主に発言内容が通知されるようになっているので、お気軽にご質問ください。
              <br />
              国主から秘話で回答します。（秘話オフの村でも国主は秘話を使用できます）
              <br />
              入村していない場合はTwitter
              <a
                href='https://twitter.com/ort_dev'
                target='_blank'
                rel='noopener noreferrer'
                className='link-style'
              >
                @ort_dev
              </a>
              にリプやDM等でご質問ください。
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}

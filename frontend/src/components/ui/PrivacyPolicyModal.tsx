'use client'

import Modal from './Modal'

type PrivacyPolicyModalProps = {
  isOpen: boolean
  onClose: () => void
}

const PrivacyPolicyModal = ({ isOpen, onClose }: PrivacyPolicyModalProps) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} title='プライバシーポリシー'>
      <div className='max-h-[70vh] space-y-6 overflow-y-auto pr-2 text-gray-300'>
        <p className='leading-relaxed'>
          WOLF
          MANSION管理人（以下，「管理人」といいます。）は，本ウェブサイト上で提供するサービス（以下,「本サービス」といいます。）における，ユーザのプライバシー情報の取扱いについて，以下のとおりプライバシーポリシー（以下，「本ポリシー」といいます。）を定めます。
        </p>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第1条（プライバシー情報）
          </h4>
          <ol className='list-decimal space-y-2 pl-6'>
            <li>
              本サービスでは，個人情報保護法にいう「個人情報」を収集しません。
            </li>
            <li>
              本サービスは，ログインに利用したIDやパスワード，ご覧になったページや広告の履歴，ユーザが検索された検索キーワード，ご利用日時，ご利用の方法，ご利用環境，ユーザのIPアドレス，クッキー情報などのプライバシー情報を収集します。
            </li>
          </ol>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第2条（プライバシー情報の収集方法）
          </h4>
          <ol className='list-decimal space-y-2 pl-6'>
            <li>
              本サービスは，ユーザが利用登録をする際にIDやパスワードなどをお尋ねすることがあります。
            </li>
            <li>
              本サービスは，ユーザが各ページを閲覧する際に，ご覧になったページや広告の履歴，ユーザが検索された検索キーワード，ご利用日時，ご利用の方法，ご利用環境，ユーザのIPアドレス，クッキー情報などを収集します。
            </li>
          </ol>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第3条（プライバシー情報を収集・利用する目的）
          </h4>
          <p className='mb-2'>
            本サービスが個人情報を収集・利用する目的は，以下のとおりです。
          </p>
          <ol className='list-decimal space-y-2 pl-6'>
            <li>サービスの提供・運営のため</li>
            <li>
              ユーザからのお問い合わせに回答するため（本人確認を行うことを含む）
            </li>
            <li>メンテナンス，重要なお知らせなど必要に応じたご連絡のため</li>
            <li>
              利用規約に違反したユーザや，不正・不当な目的でサービスを利用しようとするユーザの特定をし，ご利用をお断りするため
            </li>
            <li>
              ユーザにご自身の登録情報の閲覧や変更，削除，ご利用状況の閲覧を行っていただくため
            </li>
            <li>上記の利用目的に付随する目的</li>
          </ol>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第4条（利用目的の変更）
          </h4>
          <p className='leading-relaxed'>
            管理人は，利用目的が変更前と関連性を有すると合理的に認められる場合に限り，プライバシー情報の利用目的を変更するものとします。また，この変更の際，ユーザに通知することなく変更するものとし，これによってユーザに生じた損害について一切の責任を負いません。
          </p>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第5条（個人情報の第三者提供）
          </h4>
          <p className='mb-2 leading-relaxed'>
            管理人は、広告表示やアクセス解析のため、Google AdsenseおよびGoogle
            Analyticsにプライバシー情報を送信しています。
            それ以外の送信先については，次に掲げる場合を除いて，あらかじめユーザの同意を得ることなく提供することはありません。ただし，個人情報保護法その他の法令で認められる場合を除きます。
          </p>
          <ol className='list-decimal space-y-2 pl-6'>
            <li>
              人の生命，身体または財産の保護のために必要がある場合であって，本人の同意を得ることが困難であるとき
            </li>
            <li>
              公衆衛生の向上または児童の健全な育成の推進のために特に必要がある場合であって，本人の同意を得ることが困難であるとき
            </li>
            <li>
              国の機関もしくは地方公共団体またはその委託を受けた者が法令の定める事務を遂行することに対して協力する必要がある場合であって，本人の同意を得ることにより当該事務の遂行に支障を及ぼすおそれがあるとき
            </li>
            <li>
              予め次の事項を告知あるいは公表し，かつ管理人が個人情報保護委員会に届出をしたとき
              <ol className='mt-2 list-decimal space-y-1 pl-6'>
                <li>利用目的に第三者への提供を含むこと</li>
                <li>第三者に提供されるデータの項目</li>
                <li>第三者への提供の手段または方法</li>
                <li>
                  本人の求めに応じて個人情報の第三者への提供を停止すること
                </li>
                <li>本人の求めを受け付ける方法</li>
              </ol>
            </li>
          </ol>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第6条（プライバシー情報の開示）
          </h4>
          <p className='leading-relaxed'>
            ID以外の情報については，原則として開示いたしません。
          </p>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第7条（プライバシー情報の訂正および削除）
          </h4>
          <ol className='list-decimal space-y-2 pl-6'>
            <li>
              ユーザは，管理人の保有する自己の個人情報が誤った情報である場合には，管理人が定める手続きにより，管理人に対して個人情報の訂正，追加または削除（以下，「訂正等」といいます。）を請求することができます。
            </li>
            <li>
              管理人は，ユーザから前項の請求を受けてその請求に応じる必要があると判断した場合には，遅滞なく，当該個人情報の訂正等を行うものとします。
            </li>
            <li>
              管理人は，前項の規定に基づき訂正等を行った場合，または訂正等を行わない旨の決定をしたときは遅滞なく，これをユーザに通知します。
            </li>
          </ol>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第8条（プライバシー情報の利用停止等）
          </h4>
          <ol className='list-decimal space-y-2 pl-6'>
            <li>
              管理人は，本人から，個人情報が，利用目的の範囲を超えて取り扱われているという理由，または不正の手段により取得されたものであるという理由により，その利用の停止または消去（以下，「利用停止等」といいます。）を求められた場合には，遅滞なく必要な調査を行います。
            </li>
            <li>
              前項の調査結果に基づき，その請求に応じる必要があると判断した場合には，遅滞なく，当該プライバシー情報の利用停止等を行います。
            </li>
            <li>
              管理人は，前項の規定に基づき利用停止等を行った場合，または利用停止等を行わない旨の決定をしたときは，遅滞なく，これをユーザに通知します。
            </li>
            <li>
              前2項にかかわらず，利用停止等に多額の費用を有する場合その他利用停止等を行うことが困難な場合であって，ユーザの権利利益を保護するために必要なこれに代わるべき措置をとれる場合は，この代替策を講じるものとします。
            </li>
          </ol>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第9条（プライバシーポリシーの変更）
          </h4>
          <ol className='list-decimal space-y-2 pl-6'>
            <li>
              本ポリシーの内容は，法令その他本ポリシーに別段の定めのある事項を除いて，ユーザに通知することなく，変更することができるものとします。
            </li>
            <li>
              管理人が別途定める場合を除いて，変更後のプライバシーポリシーは，本ウェブサイトに掲載したときから効力を生じるものとします。
            </li>
          </ol>
        </div>

        <div>
          <h4 className='mb-2 text-lg font-medium text-white'>
            第10条（お問い合わせ窓口）
          </h4>
          <p className='leading-relaxed'>
            本ポリシーに関するお問い合わせは，Twitter{' '}
            <a
              href='https://twitter.com/ort_dev'
              target='_blank'
              rel='noopener noreferrer'
              className='text-blue-400 hover:underline'
            >
              @ort_dev
            </a>{' '}
            までお願いいたします。
          </p>
        </div>
      </div>
    </Modal>
  )
}

export default PrivacyPolicyModal

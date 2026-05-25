import { useState } from "react";
import { Modal } from "~/components/ui/Modal";
import { LinkButton } from "~/components/ui/Button";

/**
 * 旧 templates/layout/footer.html を React で復元。
 *
 * - 連絡先テキスト + Twitter @ort_dev リンク
 * - 「投げ銭」リンク → kampa modal
 * - 「プライバシーポリシー」リンク → policy modal
 * - 著作権 + Github リンク
 *
 * 旧画面の Google AdSense は本番限定のため移植対象外 (環境変数による
 * 出し分けが必要になったら別途検討)。
 */
export function PageFooter() {
  const [kampaOpen, setKampaOpen] = useState(false);
  const [policyOpen, setPolicyOpen] = useState(false);

  return (
    <footer className="px-3 py-4 mt-4 text-[0.95em] opacity-80">
      <hr className="border-night-700 mb-3" />
      <p className="leading-[1.6em]">
        要望、改善提案、不具合報告は Twitter{" "}
        <a
          href="https://twitter.com/ort_dev"
          target="_blank"
          rel="noopener noreferrer"
          className="text-blood-600 hover:text-mint-500"
        >
          @ort_dev
        </a>{" "}
        までお願いします。
        <br />
        投げ銭いただける方は{" "}
        <button
          type="button"
          onClick={() => setKampaOpen(true)}
          className="text-blood-600 hover:text-mint-500 underline"
        >
          こちら
        </button>{" "}
        からお願いします。
        <br />
        <button
          type="button"
          onClick={() => setPolicyOpen(true)}
          className="text-blood-600 hover:text-mint-500 underline"
        >
          プライバシーポリシー
        </button>
        <br />
        © 2018- ort (
        <a
          href="https://github.com/h-orito/wolf-mansion"
          target="_blank"
          rel="noopener noreferrer"
          className="text-blood-600 hover:text-mint-500"
        >
          Github
        </a>
        )
      </p>

      <Modal open={kampaOpen} onClose={() => setKampaOpen(false)} title="投げ銭について">
        <KampaContent />
      </Modal>

      <Modal
        open={policyOpen}
        onClose={() => setPolicyOpen(false)}
        title="プライバシーポリシー"
      >
        <PolicyContent />
      </Modal>
    </footer>
  );
}

function KampaContent() {
  return (
    <div className="space-y-3">
      <section>
        <h3 className="text-[1.1em] font-medium mb-1">Amazon ほしい物リストから送る</h3>
        <ul className="list-disc pl-5 mb-2">
          <li>Amazon ほしいものリストから選んで開発者に送ることができます。</li>
        </ul>
        <div className="flex justify-end">
          <LinkButton
            to="https://www.amazon.jp/hz/wishlist/ls/1KZSJAJS1ETW4?ref_=wl_share"
            target="_blank"
            rel="noopener noreferrer"
            variant="success"
          >
            Amazon ほしいものリストから送る
          </LinkButton>
        </div>
      </section>

      <hr className="border-night-700" />

      <section>
        <h3 className="text-[1.1em] font-medium mb-1">
          Amazon ギフト券 (E メールタイプ) を送る
        </h3>
        <ul className="list-disc pl-5 mb-2">
          <li>
            受取人に「wolfortあっとgooglegroups.com」を指定してください (あっとのところは
            @ に変えてください)。
          </li>
          <li>金額は 15 円以上で自由に変更できます。</li>
        </ul>
        <div className="flex justify-end">
          <LinkButton
            to="https://www.amazon.co.jp/dp/B004N3APGO"
            target="_blank"
            rel="noopener noreferrer"
            variant="success"
          >
            Amazon ギフト券 (E メールタイプ) を送る
          </LinkButton>
        </div>
      </section>

      <hr className="border-night-700" />

      <section>
        <h3 className="text-[1.1em] font-medium mb-1">
          Amazon アソシエイト経由で買い物をする
        </h3>
        <ul className="list-disc pl-5 mb-2">
          <li>
            下記から Amazon に遷移してカートに追加 & 購入すると、開発者に若干の紹介料が
            入ります。
          </li>
        </ul>
        <div className="flex justify-end">
          <LinkButton
            to="https://amzn.to/48auG7Q"
            target="_blank"
            rel="noopener noreferrer"
            variant="success"
          >
            Amazon を開く
          </LinkButton>
        </div>
      </section>

      <hr className="border-night-700" />

      <section>
        <h3 className="text-[1.1em] font-medium mb-1">Pixiv Fanbox</h3>
        <div className="flex justify-end">
          <LinkButton
            to="https://ort.fanbox.cc/"
            target="_blank"
            rel="noopener noreferrer"
            variant="success"
          >
            Pixiv Fanbox を開く
          </LinkButton>
        </div>
      </section>

      <hr className="border-night-700" />

      <section>
        <h3 className="text-[1.1em] font-medium mb-1">補足</h3>
        <ul className="list-disc pl-5">
          <li>
            頂いた改善提案、ご要望については投げ銭の有無に関係なく積極的に取り入れていく
            ので、Twitter{" "}
            <a
              href="https://twitter.com/ort_dev"
              target="_blank"
              rel="noopener noreferrer"
              className="text-blood-600 hover:text-mint-500"
            >
              @ort_dev
            </a>{" "}
            までお願いします。
          </li>
        </ul>
      </section>
    </div>
  );
}

function PolicyContent() {
  return (
    <div className="space-y-2 leading-[1.6em]">
      <p>
        WOLF MANSION 管理人 (以下，「管理人」といいます。) は，本ウェブサイト上で
        提供するサービス (以下，「本サービス」といいます。) における，ユーザの
        プライバシー情報の取扱いについて，以下のとおりプライバシーポリシー (以下，
        「本ポリシー」といいます。) を定めます。
      </p>

      <PolicySection title="第 1 条 (プライバシー情報)">
        <ol className="list-decimal pl-6">
          <li>本サービスでは，個人情報保護法にいう「個人情報」を収集しません。</li>
          <li>
            本サービスは，ログインに利用した ID やパスワード，ご覧になったページや
            広告の履歴，ユーザが検索された検索キーワード，ご利用日時，ご利用の方法，
            ご利用環境，ユーザの IP アドレス，クッキー情報などのプライバシー情報を
            収集します。
          </li>
        </ol>
      </PolicySection>

      <PolicySection title="第 2 条 (プライバシー情報の収集方法)">
        <ol className="list-decimal pl-6">
          <li>
            本サービスは，ユーザが利用登録をする際に ID やパスワードなどをお尋ねする
            ことがあります。
          </li>
          <li>
            本サービスは，ユーザが各ページを閲覧する際に，ご覧になったページや広告の
            履歴，ユーザが検索された検索キーワード，ご利用日時，ご利用の方法，ご利用
            環境，ユーザの IP アドレス，クッキー情報などを収集します。
          </li>
        </ol>
      </PolicySection>

      <PolicySection title="第 3 条 (プライバシー情報を収集・利用する目的)">
        <p>本サービスが個人情報を収集・利用する目的は，以下のとおりです。</p>
        <ol className="list-decimal pl-6">
          <li>サービスの提供・運営のため</li>
          <li>ユーザからのお問い合わせに回答するため (本人確認を行うことを含む)</li>
          <li>メンテナンス，重要なお知らせなど必要に応じたご連絡のため</li>
          <li>
            利用規約に違反したユーザや，不正・不当な目的でサービスを利用しようとする
            ユーザの特定をし，ご利用をお断りするため
          </li>
          <li>
            ユーザにご自身の登録情報の閲覧や変更，削除，ご利用状況の閲覧を行って
            いただくため
          </li>
          <li>上記の利用目的に付随する目的</li>
        </ol>
      </PolicySection>

      <PolicySection title="第 4 条 (利用目的の変更)">
        <p>
          管理人は，利用目的が変更前と関連性を有すると合理的に認められる場合に限り，
          プライバシー情報の利用目的を変更するものとします。また，この変更の際，
          ユーザに通知することなく変更するものとし，これによってユーザに生じた損害に
          ついて一切の責任を負いません。
        </p>
      </PolicySection>

      <PolicySection title="第 5 条 (個人情報の第三者提供)">
        <p>
          管理人は、広告表示やアクセス解析のため、Google Adsense および Google
          Analytics にプライバシー情報を送信しています。それ以外の送信先については，
          次に掲げる場合を除いて，あらかじめユーザの同意を得ることなく提供することは
          ありません。ただし，個人情報保護法その他の法令で認められる場合を除きます。
        </p>
        <ol className="list-decimal pl-6">
          <li>
            人の生命，身体または財産の保護のために必要がある場合であって，本人の同意を
            得ることが困難であるとき
          </li>
          <li>
            公衆衛生の向上または児童の健全な育成の推進のために特に必要がある場合で
            あって，本人の同意を得ることが困難であるとき
          </li>
          <li>
            国の機関もしくは地方公共団体またはその委託を受けた者が法令の定める事務を
            遂行することに対して協力する必要がある場合であって，本人の同意を得る
            ことにより当該事務の遂行に支障を及ぼすおそれがあるとき
          </li>
          <li>
            予め次の事項を告知あるいは公表し，かつ管理人が個人情報保護委員会に届出を
            したとき
            <ol className="list-decimal pl-6">
              <li>利用目的に第三者への提供を含むこと</li>
              <li>第三者に提供されるデータの項目</li>
              <li>第三者への提供の手段または方法</li>
              <li>本人の求めに応じて個人情報の第三者への提供を停止すること</li>
              <li>本人の求めを受け付ける方法</li>
            </ol>
          </li>
        </ol>
      </PolicySection>

      <PolicySection title="第 6 条 (プライバシー情報の開示)">
        <p>ID 以外の情報については，原則として開示いたしません。</p>
      </PolicySection>

      <PolicySection title="第 7 条 (プライバシー情報の訂正および削除)">
        <ol className="list-decimal pl-6">
          <li>
            ユーザは，管理人の保有する自己の個人情報が誤った情報である場合には，
            管理人が定める手続きにより，管理人に対して個人情報の訂正，追加または削除
            (以下，「訂正等」といいます。) を請求することができます。
          </li>
          <li>
            管理人は，ユーザから前項の請求を受けてその請求に応じる必要があると判断
            した場合には，遅滞なく，当該個人情報の訂正等を行うものとします。
          </li>
          <li>
            管理人は，前項の規定に基づき訂正等を行った場合，または訂正等を行わない旨の
            決定をしたときは遅滞なく，これをユーザに通知します。
          </li>
        </ol>
      </PolicySection>

      <PolicySection title="第 8 条 (プライバシー情報の利用停止等)">
        <ol className="list-decimal pl-6">
          <li>
            管理人は，本人から，個人情報が，利用目的の範囲を超えて取り扱われている
            という理由，または不正の手段により取得されたものであるという理由により，
            その利用の停止または消去 (以下，「利用停止等」といいます。) を求められた
            場合には，遅滞なく必要な調査を行います。
          </li>
          <li>
            前項の調査結果に基づき，その請求に応じる必要があると判断した場合には，
            遅滞なく，当該プライバシー情報の利用停止等を行います。
          </li>
          <li>
            管理人は，前項の規定に基づき利用停止等を行った場合，または利用停止等を
            行わない旨の決定をしたときは，遅滞なく，これをユーザに通知します。
          </li>
          <li>
            前 2 項にかかわらず，利用停止等に多額の費用を有する場合その他利用停止等を
            行うことが困難な場合であって，ユーザの権利利益を保護するために必要な
            これに代わるべき措置をとれる場合は，この代替策を講じるものとします。
          </li>
        </ol>
      </PolicySection>

      <PolicySection title="第 9 条 (プライバシーポリシーの変更)">
        <ol className="list-decimal pl-6">
          <li>
            本ポリシーの内容は，法令その他本ポリシーに別段の定めのある事項を除いて，
            ユーザに通知することなく，変更することができるものとします。
          </li>
          <li>
            管理人が別途定める場合を除いて，変更後のプライバシーポリシーは，本ウェブ
            サイトに掲載したときから効力を生じるものとします。
          </li>
        </ol>
      </PolicySection>

      <PolicySection title="第 10 条 (お問い合わせ窓口)">
        <p>本ポリシーに関するお問い合わせは，Twitter @ort_dev までお願いいたします。</p>
      </PolicySection>
    </div>
  );
}

function PolicySection({
  title,
  children,
}: {
  title: string;
  children: React.ReactNode;
}) {
  return (
    <section>
      <h3 className="text-[1.05em] font-medium mt-2 mb-1">{title}</h3>
      {children}
    </section>
  );
}

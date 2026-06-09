import { Link } from "react-router";

import { LinkButton } from "~/components/ui/Button";
import { Heading, SubHeading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import { assetUrl } from "~/lib/api";
import { MessageBubble } from "~/components/ui/MessageBubble";
import { SystemMessage } from "~/components/ui/SystemMessage";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  const description =
    "人狼館の事件簿村ルールについて紹介します。占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と【投票】で推理・説得する人狼ゲームです。";
  return [
    { title: "WOLF MANSION | 人狼館の事件簿村ルール" },
    { name: "description", content: description },
    { property: "og:site_name", content: "WOLF MANSION" },
    { property: "og:type", content: "website" },
    { property: "og:url", content: "https://wolfort.net/wolf-mansion/intro" },
    { property: "og:title", content: "WOLF MANSION | 人狼館の事件簿村ルール" },
    { property: "og:description", content: description },
    {
      property: "og:image",
      content: "https://wolfort.net/wolf-mansion/app/images/ogp-top.png",
    },
    { name: "twitter:card", content: "summary_large_image" },
    { name: "twitter:site", content: "@ort_dev" },
  ];
}

export default function Intro() {
  return (
    <PageLayout>
      <div className="px-[15px]">
        <Heading>このページは</Heading>
        <ul className="mb-[10px] list-disc pl-10">
          <li>本サイトで楽しむことができる「人狼館の事件簿村」ルールについて紹介します。</li>
          <li>
            本サイトでのルールについての紹介となりますので、各地で開催されている同名のルールと異なる場合があります。
          </li>
          <li>
            詳細なルールは
            <Link to="/rule" className="text-wm-accent hover:underline">
              ルール
            </Link>
            に記載しています。
          </li>
        </ul>

        <div className="sm:w-1/2">
          <SectionDivider />
          <SectionOpening />
          <SectionDivider />
          <SectionNoCo />
          <SectionDivider />
          <SectionFootstepBasic />
          <SectionDivider />
          <SectionAdjacentRoom />
          <SectionDivider />
          <SectionEmptyRoom />
          <SectionDivider />
          <SectionDeadAbilityUser />
          <SectionDivider />
          <SectionMadmanFox />
          <SectionDivider />
          <SectionEnding />
        </div>
      </div>
    </PageLayout>
  );
}

function SectionDivider() {
  return <hr className="my-[10px] border-wm-band" />;
}

function SectionOpening() {
  return (
    <div className="space-y-5">
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        はい、それでは紹介していきますよ
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        よろしくおねがいしま〜す
      </MessageBubble>
    </div>
  );
}

function SectionNoCo() {
  return (
    <div className="space-y-5">
      <SubHeading>白発言での推理発言やCO禁止</SubHeading>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        まず大事な禁止事項から。
        <br />
        人狼館の事件簿村ルールでは、
        <br />
        <strong>通常発言での推理発言やCOは禁止</strong>となります。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        <strong>神父さんは狼だと思うから【▼神】にするね</strong>
        <br />
        とか、
        <br />
        <strong>【占CO】</strong>
        <br />
        とかしちゃだめってことなのね
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        そうです。ただし、
        <br />
        <strong>導師（役職までわかる霊能者）だけはCOや結果発表してもOK</strong>です。
        <br />
        結果騙りもして大丈夫ですよ。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        そうなのね！じゃあ人外になったら導師騙りするのー！
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        <strong>騙りCOも禁止</strong>なので注意してくださいね。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        残念なのー
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        あ、あとは<strong>独り言や囁きや共鳴会話では推理発言してもOK</strong>ですからね。
        <br />
        生存者全員に見られる発言でなければOKです。
      </MessageBubble>
      <MessageBubble type="message-werewolf" chara="riza" isLeft={true}>
        わーい！相談して襲撃先決めるの！
        <br />
        神父 ジムゾン、今日がお前の命日なのー！
      </MessageBubble>
      <MessageBubble type="message-grave" chara="jimuzon" isLeft={false}>
        ぎゃー！
      </MessageBubble>
      <SystemMessage type="creator">
        ここまでのまとめ
        <br /> <br />
        ・生存者全員が見られる発言では推理発言禁止
        <br />
        ・ただし導師だけはCOや結果報告OK（導師騙りも禁止）
      </SystemMessage>
    </div>
  );
}

function SectionFootstepBasic() {
  return (
    <div className="space-y-5">
      <SubHeading>足音の基本ルール</SubHeading>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        推理発言禁止でどうやって狼を探すなの？
        <br />
        村側辛すぎるの
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        大丈夫です。
        <br />
        人狼館の事件簿村ルール特有の要素があり、
        <br />
        それを頼りに狼を探すことができます。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        そういえば「足音」で推理するって書いてたの！
        <br />
        足音ってなんなの？
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        一つ一つ説明していきますね。まず、1日目になると皆さんに役職と部屋が割り当てられます。
      </MessageBubble>
      <img src={assetUrl("/app/images/intro/intro01.png")} width="200" alt="部屋配置図" />
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        変わった構造の館なの
        <br />
        四方に他の人の部屋があるなの
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        それじゃあ人狼リーザさん、07の部屋にいるゲルトさんを襲撃してみてください。
      </MessageBubble>
      <MessageBubble type="message-werewolf" chara="riza" isLeft={true}>
        わかったなの
        <br />
        楽天家 ゲルト、今日がお前の命日なのー！
      </MessageBubble>
      <SystemMessage type="public-system">
        次の日の朝、楽天家 ゲルトが無残な姿で発見された。
      </SystemMessage>
      <SystemMessage type="public-system">
        館の大広間に集まった村人達は、昨晩聞こえた足音について確認した。
        <br /> <br />
        部屋12、部屋17、部屋18で足音が聞こえた...。
      </SystemMessage>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        おお、足音が鳴ったなの
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        こんな具合で、
        <br />
        人狼や占い師、狩人が能力を行使すると、
        <br />
        次の日の朝に、自分から対象の部屋に向かう際に通過した部屋に足音が響きます。
        <br />
        <br />
        記名投票とこの足音だけを頼りに推理していくのが人狼館の事件簿村ルールの遊び方です。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        19→18→17→12→07を通ったってことなのね
        <br />
        あれ？でも鳴ってる順番は12、17、18になってるの
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        よく気がつきましたね。その通りで、
        <br />
        <strong>通った順でなく、部屋番号の若い順</strong>
        になるのでミスリードに気をつけてくださいね。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        なるほどなの
        <br />
        これ、どの人狼が襲撃するかとか、通る道はランダムになるなの？
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        いえ、
        <strong>
          「どの人狼が襲撃するか」「どの経路を通るか」は人狼がセットすることができます。
        </strong>
        <br />
        もちろん占い師や狩人も、「どの経路を通るか」を選ぶことができますよ。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        よかったなの！
        <br />
        じゃあ、たくさん回り道すればバレないのー
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        残念ですが、<strong>経路は「最短経路で」「最大で1回しか曲がれない」</strong>
        ルールがあります。
        <br />
        さきほどのリーザさんがゲルトを襲撃した例でいうと、
        <br />
        19→18→17→12→07か、19→14→09→08→07の2通りから選ぶことになりますね。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        把握なのー
      </MessageBubble>
      <SystemMessage type="creator">
        ここまでのまとめ
        <br /> <br />
        ・人狼や占い師、狩人が能力を行使すると、次の日の朝に、自分から対象の部屋に向かう際に通過した部屋に足音が響く
        <br />
        ・「誰が襲撃するか」「どの経路を通るか」は能力者が選択可能
        <br />
        ・経路は「最短経路で」「最大で1回しか曲がらずに」通れる経路から選択できる
      </SystemMessage>
    </div>
  );
}

function SectionAdjacentRoom() {
  return (
    <div className="space-y-5">
      <SubHeading>隣接した部屋だと足音は響かない</SubHeading>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        通った部屋で足音が響くって言ってたけど、おとなりの部屋ならどうなるなの？
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        おおリーザさん鋭いですね。やってみましょうか。
      </MessageBubble>
      <img src={assetUrl("/app/images/intro/intro01.png")} width="200" alt="部屋配置図" />
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        それじゃあ人狼リーザさん、14の部屋にいるニコラスさんを襲撃してみてください。
      </MessageBubble>
      <MessageBubble type="message-werewolf" chara="riza" isLeft={true}>
        がってんなの！
        <br />
        旅人 ニコラス、今日がお前の命日なのー！
      </MessageBubble>
      <SystemMessage type="public-system">
        次の日の朝、旅人 ニコラスが無残な姿で発見された。
      </SystemMessage>
      <SystemMessage type="public-system">
        館の大広間に集まった村人達は、昨晩聞こえた足音について確認した。
        <br /> <br />
        足音を聞いたものはいなかった...。
      </SystemMessage>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        鳴らなかったなの！
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        そうです。<strong>隣の部屋に能力を行使した場合は足音は鳴りません。</strong>
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        見落としそうで怖いの。聞こえた足音だけじゃなくて
        <strong>聞こえなかった足音にも注意</strong>しないといけないなの。
      </MessageBubble>
      <SystemMessage type="creator">
        ここまでのまとめ
        <br /> <br />
        ・隣の部屋に能力を行使した場合は部屋を通過していないため、足音は響かない
      </SystemMessage>
    </div>
  );
}

function SectionEmptyRoom() {
  return (
    <div className="space-y-5">
      <SubHeading>生存者のいない部屋では足音は鳴らない</SubHeading>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        次いきますよ。
        <br />
        足音が報告される時点で、<strong>生存者のいない部屋では足音は響きません。</strong>
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        どういうことなの？
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        これも例を交えて説明します。部屋配置はこんな感じにしましょう。
        <br />
        ゲルトさんは死亡しています。
      </MessageBubble>
      <img
        src={assetUrl("/app/images/intro/intro02.png")}
        width="200"
        alt="部屋配置図（死亡者あり）"
      />
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        それじゃあ人狼リーザさん、14の部屋にいるオットーさんを襲撃してみてください。
        <br />
        占い師の私は12,13,14を通って15の部屋にいるトーマスさんを占います。
      </MessageBubble>
      <MessageBubble type="message-werewolf" chara="riza" isLeft={true}>
        ええと・・・
        <br />
        私の襲撃で12,13の部屋で足音が鳴って
        <br />
        神父さんの占いで12,13,14の部屋で鳴るはずなの。
        <br />
        パン屋 オットー、今日がお前の命日なのー！
      </MessageBubble>
      <SystemMessage type="private-system">
        神父 ジムゾンは、木こり トーマスを占った。
        <br />
        木こり トーマスは人間のようだ。
      </SystemMessage>
      <SystemMessage type="public-system">
        次の日の朝、パン屋 オットーが無残な姿で発見された。
      </SystemMessage>
      <SystemMessage type="public-system">
        館の大広間に集まった村人達は、昨晩聞こえた足音について確認した。
        <br /> <br />
        部屋13で足音が聞こえた...。
        <br />
        部屋13で足音が聞こえた...。
      </SystemMessage>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        どういうことなの！？13が二回鳴ってるの！
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        これが、生存者のいない部屋では足音は響かないということです。
        <br />
        リーザさんは12,13を通りましたが、12のゲルトさんが死亡しているので13だけになりました。
        <br />
        また、私の占いも、12,13,14を通りましたが、12のゲルトさんと14のオットーさんが死亡しているので13だけになったのです。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        <strong>その日に死亡した場合も鳴らない</strong>のね
        <br />
        <br />
        あと、同じ部屋が鳴るときは2行出るのね
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        その通りです。ちなみに、リーザさんが16の空き部屋を通って17の私を襲撃すると足音なしになりますよ。
      </MessageBubble>
      <MessageBubble type="message-werewolf" chara="riza" isLeft={true}>
        なるほどなの！
        <br />
        神父 ジムゾン、今日もお前の命日なの！
      </MessageBubble>
      <MessageBubble type="message-grave" chara="jimuzon" isLeft={false}>
        ぎゃー！！
      </MessageBubble>
      <SystemMessage type="public-system">
        館の大広間に集まった村人達は、昨晩聞こえた足音について確認した。
        <br /> <br />
        足音を聞いたものはいなかった...。
      </SystemMessage>
      <SystemMessage type="creator">
        ここまでのまとめ
        <br /> <br />
        ・生存者のいない部屋（死亡した人の部屋、空き部屋）では足音は響かない
      </SystemMessage>
    </div>
  );
}

function SectionDeadAbilityUser() {
  return (
    <div className="space-y-5">
      <SubHeading>能力者が死亡しても足音は響く</SubHeading>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        今度は割と簡単です。
        <br />
        <strong>能力者が処刑や襲撃で死亡しても、次の日に足音が響きます。</strong>
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        一応やってみるなの
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        じゃあさっきの部屋配置でもう一度。
        <br />
        ゲルトさんは死亡しています。
      </MessageBubble>
      <img
        src={assetUrl("/app/images/intro/intro02.png")}
        width="200"
        alt="部屋配置図（死亡者あり）"
      />
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        それじゃあ人狼リーザさん、私を襲撃してください。
        <br />
        占い師の私は18,19を通って20の部屋にいるカタリナさんを占います。
      </MessageBubble>
      <MessageBubble type="message-werewolf" chara="riza" isLeft={true}>
        ひょっとしてドMなの？（ええと・・・）
        <br />
        私の襲撃は足音が鳴らなくて、
        <br />
        神父さんの占いで18,19の部屋で鳴るはずなの。
        <br />
        神父 ジムゾン、今日がお前の命日なのー！
      </MessageBubble>
      <MessageBubble type="message-grave" chara="jimuzon" isLeft={false}>
        心の声が聞こえていますよ。
      </MessageBubble>
      <SystemMessage type="private-system">
        神父 ジムゾンは、羊飼い カタリナを占った。
        <br />
        羊飼い カタリナは人間のようだ。
      </SystemMessage>
      <SystemMessage type="public-system">
        次の日の朝、神父 ジムゾンが無残な姿で発見された。
      </SystemMessage>
      <SystemMessage type="public-system">
        館の大広間に集まった村人達は、昨晩聞こえた足音について確認した。
        <br /> <br />
        部屋18、部屋19で足音が聞こえた...。
      </SystemMessage>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        ちゃんと聞こえたなのー
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        今回は簡単でしたね。
        <br />
        ちなみに、
        <strong>襲撃を担当した人狼が処刑されても、ちゃんとその人狼を起点とした足音が鳴る</strong>
        ので覚えておいてください。
      </MessageBubble>
      <SystemMessage type="creator">
        ここまでのまとめ
        <br /> <br />
        ・セットした足音はセットした人が死亡してもセットした通りに響く
      </SystemMessage>
    </div>
  );
}

function SectionMadmanFox() {
  return (
    <div className="space-y-5">
      <SubHeading>狂人（C国狂人、魔神官、狂信者含む）、妖狐も足音を鳴らせる</SubHeading>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        思ったことがあるの
        <br />
        襲撃された人に向かう足音は限られるから、
        <br />
        すぐに人狼の位置がわかっちゃうと思うの
        <br />
        人外不利じゃないなの？
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        その通りです。
        <br />
        なので、人狼館の事件簿村ルールでは
        <br />
        通常能力を持たない狂人や妖狐が
        <br />
        「好きな場所で足音を鳴らす」ことができます。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        それで人狼を守ったり
        <br />
        自分の身を守ったりできるってことなのね！
        <br />
        でもややこしいなの！
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        このややこしさが面白いのですよ。続けます。
        <br />
        人狼や占い師、狩人とは鳴らせる足音が違うので気をつけてください。
        <br />
        <br />
        ・任意の場所を起点・終点として、直線の足音を鳴らすことができます。
        <br />
        ・曲がる足音は鳴らすことができません。
        <br />
        ・何も鳴らさないこともできます。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        ややこしいなの・・・
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        画像で見た方がわかりやすいかもしれませんね。
      </MessageBubble>
      <div className="flex flex-col gap-[10px] sm:flex-row">
        <img
          src={assetUrl("/app/images/intro/intro03.png")}
          width="200"
          alt="セットできる足音の例"
        />
        <img
          src={assetUrl("/app/images/intro/intro04.png")}
          width="200"
          alt="セットできない足音の例"
        />
      </div>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        画像の例でいうと、1つ目はセットできますが、2つ目はセットできません。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        おおーなんとなくわかったなの！
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        曲がる足音は出せませんが、案外これで撹乱できるものですよ。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        構成次第だけど、占い師、人狼、狩人、狂人、妖狐の最大5つの足音が鳴るなの。推理発言はできないけど推理しがいがあるの！
      </MessageBubble>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        その意気です！
      </MessageBubble>
      <SystemMessage type="creator">
        ここまでのまとめ
        <br /> <br />
        ・狂人と妖狐も足音をセットできる
        <br />
        ・自分の位置に関わらず、任意の位置を起点〜終点として足音を鳴らせる
        <br />
        ・ただし、曲がる足音は鳴らせない
        <br />
        ・鳴らさないこともできる
        <br />
        ・通過した部屋だけでなく、選択した部屋全てで音が鳴る
      </SystemMessage>
    </div>
  );
}

function SectionEnding() {
  return (
    <div className="space-y-5">
      <SubHeading>おわり</SubHeading>
      <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
        以上です！これらのルールをふまえて、
        <br />
        投票と足音で自陣営を勝利に導くのが人狼館の事件簿村ルールの遊び方です。
        <br />
        <br />
        練習問題も用意してあるので、ぜひやってみてください。
      </MessageBubble>
      <MessageBubble type="message-normal" chara="riza" isLeft={true}>
        わーい！やってみるのー！
      </MessageBubble>
      <div className="flex items-center justify-between">
        <LinkButton to="/" variant="default">
          サイトトップへ
        </LinkButton>
        <LinkButton to="/practice">練習問題へ</LinkButton>
      </div>
    </div>
  );
}

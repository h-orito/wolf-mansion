import { useState, type ReactNode } from "react";

import { Button, LinkButton } from "~/components/ui/Button";
import { SubHeading } from "~/components/ui/Heading";
import { MessageBubble } from "~/components/ui/MessageBubble";
import { SystemMessage } from "~/components/ui/SystemMessage";
import { PageLayout } from "~/components/layout/PageLayout";
import { assetUrl } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("人狼館の事件簿村ルール 練習問題");
}

export default function Practice() {
  return (
    <PageLayout>
      <div className="px-[15px] pt-[10px] pb-[10px]">
        <div className="sm:w-1/2">
          <LinkButton to="/intro" variant="default">
            ルール紹介へ戻る
          </LinkButton>

          <SectionDivider />
          <div className="space-y-5">
            <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
              では練習問題をやってみましょう。
              <br />
              ちょっと難しめにしていますよ。
            </MessageBubble>
            <MessageBubble type="message-normal" chara="riza" isLeft={true}>
              がんばるなのー！
            </MessageBubble>
          </div>

          <SectionDivider />
          <div className="space-y-5">
            <SystemMessage type="creator">
              第1問
              <br />
              <br />
              リーザさんは導師です。次のうち、リーザさんがしてはいけない発言は次のうちどれでしょう。
            </SystemMessage>
            <MessageBubble type="message-normal" chara="riza" isLeft={true}>
              1<br />
              ゲルトオオオオオオオオオオ！！
            </MessageBubble>
            <MessageBubble type="message-monologue" chara="riza" isLeft={true}>
              2<br />
              足音は03,04だったの...
              <br />
              たぶん05が人狼だから05に投票するの！
            </MessageBubble>
            <MessageBubble type="message-normal" chara="riza" isLeft={true}>
              3<br />
              導師COなの！でも結果は秘密なの！
            </MessageBubble>
            <MessageBubble type="message-normal" chara="riza" isLeft={true}>
              4<br />
              導師COなの！【年は人間】だったの！
              <br />
              今日は▼屋に票集めてほしいの！
            </MessageBubble>
            <AnswerCollapse>
              答え：4
              <br />
              ・導師でも結果発表以外の推理発言はしてはいけません。
            </AnswerCollapse>
          </div>

          <SectionDivider />
          <div className="space-y-5">
            <SystemMessage type="creator">
              第2問
              <br />
              <br />
              現在2日目、以下の部屋配置と足音でした。
              <br />
              ゲルトを襲撃した人狼の候補となる部屋番号と、占い師の候補となる部屋番号を挙げてください。
              <br />
              ただし、<strong>この村には狩人、狂人、妖狐はいません</strong>。
            </SystemMessage>
            <img
              src={assetUrl("/app/images/intro/intro02.png")}
              width="200"
              alt="部屋配置図（死亡者あり）"
            />
            <SystemMessage type="public-system">
              次の日の朝、楽天家 ゲルトが無残な姿で発見された。
            </SystemMessage>
            <SystemMessage type="public-system">
              館の大広間に集まった村人達は、昨晩聞こえた足音について確認した。
              <br />
              <br />
              部屋13、部屋14で足音が聞こえた...。
              <br />
              部屋04、部屋10で足音が聞こえた...。
            </SystemMessage>
            <AnswerCollapse>
              答え
              <br />
              ・人狼の候補：部屋09,15,19
              <br />
              ・占い師の候補：部屋03,15
              <br />
              <br />
              解説
              <br />
              ・狩人狂人妖狐がいないため、足音を出せるのは
              <br />
              人狼と占い師のみ
              <br />
              <br />
              ・ゲルトが襲撃されたが、ゲルトに向かう足音は
              <br />
              13,14のみ＝13,14が人狼の足音
              <br />
              13,14の足音を出してゲルトを襲撃できるのは、
              <br />
              部屋09,15,19。よって人狼候補は部屋09,15,19。
              <br />
              <br />
              ・消去法で04,10が占い師の足音となる。
              <br />
              この足音は03→04→05→10→15か
              <br />
              15→10→05→04→03のいずれか。
              <br />
              （05は無人なので鳴らない）
              <br />
              よって占い師候補は部屋03,15。
              <br />
              （逆に、占われたのは部屋15,03のいずれか）
            </AnswerCollapse>
          </div>

          <SectionDivider />
          <div className="space-y-5">
            <SystemMessage type="creator">
              第3問
              <br />
              <br />
              現在2日目、以下の部屋配置と足音でした。
              <br />
              ゲルトを襲撃した人狼の候補となる部屋番号と、占い師の候補となる部屋番号を挙げてください。
              <br />
              ただし、<strong>この村には妖狐はいません</strong>。(
              <strong>狩人、狂人はいます</strong>)
            </SystemMessage>
            <img
              src={assetUrl("/app/images/intro/intro02.png")}
              width="200"
              alt="部屋配置図（死亡者あり）"
            />
            <SystemMessage type="public-system">
              次の日の朝、楽天家 ゲルトが無残な姿で発見された。
            </SystemMessage>
            <SystemMessage type="public-system">
              館の大広間に集まった村人達は、昨晩聞こえた足音について確認した。
              <br />
              <br />
              部屋13、部屋14で足音が聞こえた...。
              <br />
              部屋04、部屋10で足音が聞こえた...。
            </SystemMessage>
            <AnswerCollapse>
              答え
              <br />
              ・人狼の候補：部屋07,09,11,13,15,17,19
              <br />
              ・占い師の候補：部屋03,15
              <br />
              <br />
              解説
              <br />
              ・狩人は2日目は護衛できないため足音はなし。
              <br />
              つまり足音を出せるのは占い師、狂人、人狼で、
              <br />
              足音は2種類しか鳴っていないため、
              <br />
              「足音なし」が1つ含まれている。
              <br />
              <br />
              ・04,10の足音は、
              <br />
              狂人は曲がる足音を出せないのと
              <br />
              ゲルトに向かう足音になっていないため
              <br />
              占い師の足音で確定。
              <br />
              <br />
              ・消去法で、「足音なし」と「13,14」が
              <br />
              狂人もしくは人狼のものとなる。
              <br />
              これ以上は特定できないため、
              <br />
              「足音なし」＝ゲルトに隣接した部屋が候補、
              <br />
              「13,14」＝2問目と同じ
              <br />
              部屋07,11,13,17 + 部屋09,15,19
              <br />
              が人狼の足音候補となる。
            </AnswerCollapse>
          </div>

          <SectionDivider />
          <div className="space-y-5">
            <SubHeading>おわり</SubHeading>
            <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
              3問だけですが以上です。
              <br />
              会話していないのに人狼の位置がある程度掴めたり、
              <br />
              COできないのに占い師がCOしたり
              <br />
              なかなか面白いでしょう？
            </MessageBubble>
            <MessageBubble type="message-normal" chara="riza" isLeft={true}>
              おもしろいのー！早く実戦やってみたいの！
            </MessageBubble>
            <MessageBubble type="message-normal" chara="jimuzon" isLeft={false}>
              実際の村のログを見て雰囲気を掴んだり、初心者村に参加してみるとより理解が進むかもしれませんね。
              <br />
              誰でも村を建てられますので、自分で集めてももちろんOKです！
            </MessageBubble>
            <MessageBubble type="message-normal" chara="riza" isLeft={true}>
              最後まで読んでくれてありがとうなの！
            </MessageBubble>
            <LinkButton to="/" variant="default">
              サイトトップへ
            </LinkButton>
          </div>
        </div>
      </div>
    </PageLayout>
  );
}

function SectionDivider() {
  return <hr className="my-[10px] border-wm-band" />;
}

/** 「答えを開く」ボタンで解答の表示/非表示を切り替える。 */
function AnswerCollapse({ children }: { children: ReactNode }) {
  const [open, setOpen] = useState(false);
  return (
    <div>
      <Button className="text-[13px]" onClick={() => setOpen((prev) => !prev)}>
        答えを開く
      </Button>
      {open && (
        <div className="mt-5">
          <SystemMessage type="creator">{children}</SystemMessage>
        </div>
      )}
    </div>
  );
}

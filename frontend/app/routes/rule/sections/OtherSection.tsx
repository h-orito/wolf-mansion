import { useState } from "react";
import { Link } from "react-router";

import { SkillMessage } from "~/components/ui/SkillMessage";

export function OtherSection() {
  return (
    <ul id="other" className="mb-[10.5px] list-disc pl-[40px]">
      <RandomMessage />
      <MessageDecorate />
      <MessageType />
      <CallOwner />
    </ul>
  );
}

function RandomMessage() {
  return (
    <li id="random-message">
      発言ランダム機能
      <ul className="list-disc pl-[20px]">
        <li>
          [[XdY]]でY面ダイスをX個振れます。
          <ul className="list-disc pl-[20px]">
            <li>
              [[2d6]] → (3)(5)<span className="text-[10px]">[[2d6]]</span>
            </li>
            <li>X:1~9, Y:1~99999</li>
          </ul>
        </li>
        <li>
          [[fortune]]で0~100までの数字をランダムで表示します。
          <ul className="list-disc pl-[20px]">
            <li>
              [[fortune]] → 99<span className="text-[10px]">[[fortune]]</span>
            </li>
          </ul>
        </li>
        <li>
          [[XXXorYYYorZZZ]]でorで区切った文字列のいずれかをランダムで表示します。
          <ul className="list-disc pl-[20px]">
            <li>
              [[オレサマorゲルトorマルカジリ]] → マルカジリ
              <span className="text-[10px]">[[オレサマorゲルトorマルカジリ]]</span>
            </li>
          </ul>
        </li>
        <li>
          [[who]]で生存者のいずれかをランダムで表示します。
          <ul className="list-disc pl-[20px]">
            <li>
              [[who]] → 司書 クララ<span className="text-[10px]">[[who]]</span>
            </li>
          </ul>
        </li>
        <li>
          [[allwho]]で見学含む参加者のいずれかをランダムで表示します。
          <ul className="list-disc pl-[20px]">
            <li>
              [[allwho]] → 楽天家 ゲルト<span className="text-[10px]">[[allwho]]</span>
            </li>
          </ul>
        </li>
        <li>
          [[gwho]]で死者のいずれかをランダムで表示します。
          <ul className="list-disc pl-[20px]">
            <li>
              [[gwho]] → 楽天家 ゲルト<span className="text-[10px]">[[gwho]]</span>
            </li>
          </ul>
        </li>
        <li>
          他にも、
          <Link to="/random-message" className="text-wm-accent hover:underline">
            ユーザー定義のランダム発言
          </Link>
          を登録して使用することができます。
        </li>
      </ul>
    </li>
  );
}

function MessageDecorate() {
  return (
    <li id="message-decorate">
      文字装飾機能
      <ul className="list-disc pl-[20px]">
        <li>
          [[#ff0000]]文字列[[/#]]で文字に色をつけられます。
          <ul className="list-disc pl-[20px]">
            <li>
              [[#ff0000]]文字列[[/#]] → <span style={{ color: "#ff0000" }}>文字列</span>
            </li>
          </ul>
        </li>
        <li>
          [[large]]文字列[[/large]]で文字を大きくできます。
          <ul className="list-disc pl-[20px]">
            <li>
              [[large]]文字列[[/large]] → <span style={{ fontSize: "16px" }}>文字列</span>
            </li>
          </ul>
        </li>
        <li>
          [[small]]文字列[[/small]]で文字を小さくできます。
          <ul className="list-disc pl-[20px]">
            <li>
              [[small]]文字列[[/small]] → <span style={{ fontSize: "10px" }}>文字列</span>
            </li>
          </ul>
        </li>
        <li>
          [[b]]文字列[[/b]]で文字を太くできます。
          <ul className="list-disc pl-[20px]">
            <li>
              [[b]]文字列[[/b]] → <strong>文字列</strong>
            </li>
          </ul>
        </li>
        <li>
          [[s]]文字列[[/s]]で文字に打ち消し線をつけられます。
          <ul className="list-disc pl-[20px]">
            <li>
              [[s]]文字列[[/s]] → <span style={{ textDecoration: "line-through" }}>文字列</span>
            </li>
          </ul>
        </li>
        <li>
          [[ruby]]文字列[[rt]]ルビ[[/rt]][[/ruby]]でルビを振れます。
          <ul className="list-disc pl-[20px]">
            <li>
              [[ruby]]文字列[[rt]]ルビ[[/rt]][[/ruby]] →{" "}
              <ruby>
                文字列<rt>ルビ</rt>
              </ruby>
            </li>
          </ul>
        </li>
        <li>
          [[netabare]]文字列[[/netabare]]で文字を黒背景で隠し、クリックで表示できる状態にします。
          <ul className="list-disc pl-[20px]">
            <li>
              [[netabare]]文字列[[/netabare]] → <Netabare>文字列</Netabare>
            </li>
            <li>cwでも同じ効果になります。</li>
            <li>
              [[cw]]文字列[[/cw]] → <Netabare>文字列</Netabare>
            </li>
          </ul>
        </li>
        <li>
          [[tp]]文字列[[/tp]]で文字を透明背景で隠し、クリックで表示できる状態にします。
          <ul className="list-disc pl-[20px]">
            <li>
              [[tp]]文字列[[/tp]] → <Transparency>文字列</Transparency>
            </li>
          </ul>
        </li>
      </ul>
    </li>
  );
}

const CHAR_IMAGE = "https://wolfort.dev/wmansion/1/geruto.png";

function MessageTypeExample({
  messageType,
  text,
  hasImage = true,
  imageStyle,
}: {
  messageType: string;
  text: string;
  hasImage?: boolean;
  imageStyle?: React.CSSProperties;
}) {
  return (
    <div className="mb-[5px]">
      <div className="flex">
        {hasImage && (
          <div
            className="shrink-0"
            style={{
              width: 50,
              height: 77,
              background: `url('${CHAR_IMAGE}')`,
              backgroundRepeat: "no-repeat",
              ...imageStyle,
            }}
          />
        )}
        <div className={hasImage ? "ml-[5px] min-h-[77px] flex-1" : "w-full"}>
          <SkillMessage messageType={messageType} content={text} />
        </div>
      </div>
    </div>
  );
}

function MessageType() {
  return (
    <li id="message-type">
      発言種別
      <div className="my-[5px]">
        <MessageTypeExample
          messageType="message-normal"
          text="通常発言です。全ての人が参照できます。"
        />
        <MessageTypeExample
          messageType="message-monologue"
          text="独り言です。進行中は自分だけが参照できます。"
        />
        <MessageTypeExample
          messageType="message-werewolf"
          text="囁き発言です。進行中は人狼とC国狂人だけが参照できます。"
        />
        <MessageTypeExample
          messageType="message-mason"
          text="共鳴発言です。進行中は共鳴者だけが参照できます。"
        />
        <MessageTypeExample
          messageType="message-lover"
          text="恋人発言です。進行中は恋絆が付与されている人だけが参照できます。"
        />
        <MessageTypeExample
          messageType="message-telepathy"
          text="念話です。進行中は妖狐系役職、背徳者、狐憑きの人だけが参照できます。"
        />
        <MessageTypeExample
          messageType="message-owl"
          text={
            "梟が囁き、共鳴発言、恋人発言、念話を参照するとき、この色になります。\n名前も「地獄耳」固定となるため、誰がどの種別で発言したかはわかりません。"
          }
          imageStyle={{
            background: "transparent",
            border: "1px solid #fff",
            borderRadius: 5,
          }}
        />
        <MessageTypeExample
          messageType="message-grave"
          text="墓下発言です。進行中は死亡者と見学者だけが参照できます。（村の設定によっては、生存者も参照できます。）"
        />
        <MessageTypeExample
          messageType="message-spectate"
          text="見学発言です。進行中は死亡者と見学者だけが参照できます。（村の設定によっては、生存者も参照できます。）"
        />
        <MessageTypeExample
          messageType="message-public-system"
          text="システムメッセージです。全ての人が参照できます。"
          hasImage={false}
        />
        <MessageTypeExample
          messageType="message-private-system"
          text="システムメッセージです。進行中は参照できません。"
          hasImage={false}
        />
        <MessageTypeExample
          messageType="message-private-seer"
          text="占い師向けシステムメッセージです。進行中は占い師本人しか参照できません。"
          hasImage={false}
        />
        <MessageTypeExample
          messageType="message-private-psychic"
          text="霊能者向けシステムメッセージです。進行中は霊能者本人しか参照できません。"
          hasImage={false}
        />
        <MessageTypeExample
          messageType="message-private-investigate"
          text="探偵向けシステムメッセージです。進行中は探偵本人しか参照できません。"
          hasImage={false}
        />
        <MessageTypeExample
          messageType="message-private-werewolf"
          text="人狼向けシステムメッセージです。進行中は人狼系役職しか参照できません。"
          hasImage={false}
        />
        <MessageTypeExample
          messageType="message-private-lover"
          text="恋人向けシステムメッセージです。進行中は恋人系役職しか参照できません。"
          hasImage={false}
        />
        <MessageTypeExample
          messageType="message-private-fox"
          text="妖狐向けシステムメッセージです。進行中は妖狐系役職しか参照できません。"
          hasImage={false}
        />
        <MessageTypeExample
          messageType="message-creator"
          text="村建て発言です。全ての人が参照できます。"
          hasImage={false}
        />
      </div>
    </li>
  );
}

function CallOwner() {
  return (
    <li id="call-owner">
      国主の召喚方法
      <ul className="list-disc pl-[20px]">
        <li>
          「＠国主」「@国主」をつけて発言すると、国主に発言内容が通知されます。（発言者、役職、発言種別等は通知されないので、必要であれば添えて発言してください。）
        </li>
        <li>もしくは、twitterで話しかけるとそれなりの速度で反応します。</li>
      </ul>
    </li>
  );
}

function Netabare({ children }: { children: React.ReactNode }) {
  const [revealed, setRevealed] = useState(false);
  return (
    <span
      className={`cursor-pointer bg-black ${revealed ? "text-white" : "text-black"}`}
      onClick={() => setRevealed(!revealed)}
    >
      {children}
    </span>
  );
}

function Transparency({ children }: { children: React.ReactNode }) {
  const [revealed, setRevealed] = useState(false);
  return (
    <span
      className={`cursor-pointer ${revealed ? "text-white" : "text-transparent"}`}
      onClick={() => setRevealed(!revealed)}
    >
      {children}
    </span>
  );
}

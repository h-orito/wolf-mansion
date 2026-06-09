/**
 * 役職説明データ (rule/skill.html から自動生成)。
 * 再生成: cd frontend && node scripts/extract-skill-descriptions.mjs
 *
 * code (小文字) → 説明項目リストの Record。
 * 役職名・略称・陣営名は API (SimpleSkillView) から取得するため含めない。
 */

export type DescriptionItem =
  | { type: "text"; content: string }
  | { type: "message"; messageType: string; content: string };

export const skillDescriptions: Record<string, DescriptionItem[]> = {
  villager: [
    {
      type: "text",
      content: "特別な能力はありません。",
    },
  ],
  seer: [
    {
      type: "text",
      content:
        "毎晩一人を占い、人間か人狼かを知ることができます。\n占うと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず占い対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "妖狐を占った場合死亡させることができます。（「人間」と判定されます。）",
    },
    {
      type: "text",
      content:
        "呪狼を占った場合、自身が死亡します。ただし、呪狼が突然死や処刑されていた場合は死亡しません。",
    },
    {
      type: "message",
      messageType: "message-private-seer",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを占った。\n[02長]村長 ヴァルターは人間のようだ。",
    },
  ],
  wise: [
    {
      type: "text",
      content:
        "毎晩一人を占い、その人の役職を知ることができます。\n占うと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず占い対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "妖狐を占った場合死亡させることができます。",
    },
    {
      type: "text",
      content:
        "呪狼を占った場合、自身が死亡します。ただし、呪狼が突然死や処刑されていた場合は死亡しません。",
    },
    {
      type: "message",
      messageType: "message-private-seer",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを占った。\n[02長]村長 ヴァルターは智狼のようだ。",
    },
  ],
  astrologer: [
    {
      type: "text",
      content:
        "毎晩一人を指定して占い、その人の部屋を中心とした3x3の部屋にいる人の役職構成を知ることができます。\n占うと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "知ることができる役職構成はすでに死亡している人も含められます。",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず占い対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content:
        "妖狐を中心に占った場合死亡させることができます。（中心でなく周辺の部屋にいた場合は死亡しません）",
    },
    {
      type: "text",
      content:
        "呪狼を中心に占った場合、自身が死亡します。ただし、呪狼が突然死や処刑されていた場合は死亡しません。（中心でなく周辺の部屋にいた場合は死亡しません）",
    },
    {
      type: "text",
      content: "曇天者の能力行使により、能力が発動しないことがあります（詳細は曇天者を参照）。",
    },
    {
      type: "message",
      messageType: "message-private-seer",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターのあたりを占った。\nこのあたりには、村人が1名、占い師が2名、人狼が3名、共有者が2名、妖狐が1名いるようだ。",
    },
  ],
  flowerseer: [
    {
      type: "text",
      content:
        "毎晩一人を占い、恋をしているかを知ることができます。\n占うと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "対象が恋絆を持っていなく、恋人陣営であるだけの場合は恋をしていない判定となります（例. 絡新婦）。",
    },
    {
      type: "text",
      content:
        "対象が恋絆を持っていなく、恋をされているだけの場合は恋をしていない判定となります（例.\nストーカーにストーキングされている）。",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず占い対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "妖狐を占った場合死亡させることができます。",
    },
    {
      type: "text",
      content:
        "呪狼を占った場合、自身が死亡します。ただし、呪狼が突然死や処刑されていた場合は死亡しません。",
    },
    {
      type: "message",
      messageType: "message-private-seer",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを占った。\n[02長]村長 ヴァルターは恋をしているようだ。",
    },
  ],
  sixthsensor: [
    {
      type: "text",
      content:
        "毎晩一人を指定して占い、その人の部屋を中心とした3x3の部屋にいる人の勝敗判定陣営構成を知ることができます。\n占うと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "知ることができる勝敗判定陣営構成はすでに死亡している人も含められます。",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず占い対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content:
        "妖狐を中心に占った場合死亡させることができます。（中心でなく周辺の部屋にいた場合は死亡しません）",
    },
    {
      type: "text",
      content:
        "呪狼を中心に占った場合、自身が死亡します。ただし、呪狼が既に死亡している場合は死亡しません。（中心でなく周辺の部屋にいた場合も死亡しません）",
    },
    {
      type: "message",
      messageType: "message-private-seer",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターのあたりを占った。\nこのあたりには、村人陣営に与する者が1名、人狼陣営に与する者が2名、狐陣営に与する者が1名、恋人陣営に与する者が5名いるようだ。",
    },
  ],
  rememberseer: [
    {
      type: "text",
      content:
        "2日目以降、毎晩死者一人を占い、その人の役職を知ることができます。\n占うと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず占い対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "妖狐を占っても死亡させることはできません。",
    },
    {
      type: "text",
      content: "呪狼を占っても死亡しません。",
    },
    {
      type: "message",
      messageType: "message-private-seer",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを占った。\n[02長]村長 ヴァルターは賢者のようだ。",
    },
  ],
  detectseer: [
    {
      type: "text",
      content:
        "毎晩一人を占い、役職の所属する陣営と、現在の勝敗判定陣営が異なるかを知ることができます。\n占うと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず占い対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "妖狐を占った場合死亡させることができます。",
    },
    {
      type: "text",
      content:
        "呪狼を占った場合、自身が死亡します。ただし、呪狼が突然死や処刑されていた場合は死亡しません。",
    },
    {
      type: "text",
      content:
        "役職の所属する陣営と現在の勝敗判定陣営のみが判定材料となるため、他のステータスが付与されていても検知できません。",
    },
    {
      type: "text",
      content:
        "たとえば、恋絆と狐憑きと狂気と信念がついたストーカーは「陣営変化していないようだ」になります。",
    },
    {
      type: "message",
      messageType: "message-private-seer",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを占った。\n[02長]村長 ヴァルターは陣営変化しているようだ。",
    },
  ],
  hunter: [
    {
      type: "text",
      content:
        "毎晩自分以外の一人を人狼の襲撃から守ることができます。\n護衛すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "処刑により死亡した場合、足音は響きますが、護衛は行われません。",
    },
    {
      type: "text",
      content: "襲撃により死亡した場合、護衛は実行され、足音も響きます。",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず護衛対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "護衛手応えはありません。",
    },
    {
      type: "text",
      content:
        "設定で連続護衛不可となっている村では、同一人物を2日連続で護衛することができません。",
    },
    {
      type: "text",
      content: "1日目はセットできません。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを護衛している。",
    },
  ],
  wanderer: [
    {
      type: "text",
      content: "一度護衛した人物は護衛の成功失敗に関わらず護衛対象に選ぶことができません。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "他は狩人と同様です。",
    },
  ],
  detective: [
    {
      type: "text",
      content:
        "毎晩、昨晩響いた足音のうち一つを調査し、その足音を響かせた人の役職を知ることができます。",
    },
    {
      type: "text",
      content:
        "例.\n2日目に響いた足音のうち1つを選んでセットすると、3日目朝にどの役職が鳴らした足音かを知ることができます。",
    },
    {
      type: "text",
      content: "足音「なし」については調査できません。",
    },
    {
      type: "text",
      content:
        "例. 狂人がセットしなかった、隣接した部屋への襲撃、生存者がいないため響く足音がなくなったなど",
    },
    {
      type: "text",
      content: "足音が1つも響かなかった日は能力をセットすることはできません。",
    },
    {
      type: "text",
      content: "全く同じ足音が2つ以上響いていた場合、そのうちどれか1つがランダムで調査されます。",
    },
    {
      type: "text",
      content:
        "例.\n人狼、狂人の足音がどちらも12,13で12,13を調査対象とした場合、片方の調査結果だけが得られます。（=結果は人狼or狂人となります）",
    },
    {
      type: "text",
      content: "探偵の調査により足音が発生することはありません。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "1日目は足音がないためセットできません。",
    },
    {
      type: "message",
      messageType: "message-private-investigate",
      content:
        "[01楽]楽天家 ゲルトは、昨日響いた足音01,02,03について調査した。\n01,02,03の足音を響かせたのは呪狼のようだ。",
    },
  ],
  observer: [
    {
      type: "text",
      content:
        "毎晩、昨晩響いた足音のうち一つを調査し、その足音を響かせたのが誰かを知ることができます。",
    },
    {
      type: "text",
      content:
        "例.\n2日目に響いた足音のうち1つを選んでセットすると、3日目朝に誰が鳴らした足音かを知ることができます。",
    },
    {
      type: "text",
      content: "他は探偵と同じです。",
    },
    {
      type: "message",
      messageType: "message-private-investigate",
      content:
        "[01楽]楽天家 ゲルトは、昨日響いた足音01,02,03について調査した。\n01,02,03の足音を響かせたのは[02長]村長 ヴァルターのようだ。",
    },
  ],
  trapper: [
    {
      type: "text",
      content:
        "2日目以降、全日を通して1回だけ、生存者がいる任意の部屋にその晩限りの罠を設置することができます。",
    },
    {
      type: "text",
      content: "罠を設置すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "罠が設置された部屋を通過した人は無惨な死体となって発見されます。",
    },
    {
      type: "text",
      content:
        "罠を設置した晩に、罠師、設置された部屋の人、通過した人のいずれが死亡しても、通過さえしていれば罠は作動します。",
    },
    {
      type: "text",
      content:
        "例外として、冤罪者の足音は罠の処理後に発生するため、冤罪者の立てた足音では罠が作動しません。",
    },
    {
      type: "text",
      content: "ただし、罠師が突然死していた場合は罠は無効になります。",
    },
    {
      type: "text",
      content:
        "一度も能力を使用しないこともできます。また、能力を使ったかどうかは自分以外にはわかりません。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターの部屋に罠を設置した。",
    },
  ],
  medium: [
    {
      type: "text",
      content:
        "処刑された・突然死した人物が人間であったか人狼であったかを知ることができます。（妖狐は人間判定です）",
    },
    {
      type: "text",
      content: "霊能能力を持った人が1名以上生存していれば霊能メッセージが表示されます。",
    },
    {
      type: "message",
      messageType: "message-private-psychic",
      content: "[01楽]楽天家 ゲルトは人間のようだ。",
    },
  ],
  guru: [
    {
      type: "text",
      content: "処刑された・突然死した人物の役職を知ることができます。",
    },
    {
      type: "text",
      content:
        "導師能力を持った人が1名以上生存していれば導師メッセージが表示されます（導師、魔神官、稲荷など）。",
    },
    {
      type: "message",
      messageType: "message-private-psychic",
      content: "[01楽]楽天家 ゲルトは妖狐のようだ。",
    },
  ],
  coroner: [
    {
      type: "text",
      content: "無惨な死体となった人物の死因を知ることができます。",
    },
    {
      type: "text",
      content: "死因は襲撃、呪殺、爆死、罠死、雑魚のいずれかで表記されます。",
    },
    {
      type: "text",
      content:
        "呪狼を占ったなどの逆呪殺についても呪殺と表記されるため、呪殺なのか逆呪殺なのかはわかりません。",
    },
    {
      type: "message",
      messageType: "message-private-psychic",
      content: "[01楽]楽天家 ゲルトの死因は、呪殺のようだ。",
    },
  ],
  bakery: [
    {
      type: "text",
      content: "毎日おいしいパンを焼くことができます。",
    },
    {
      type: "text",
      content: "パン屋が1名以上生存していると、朝に専用のシステムメッセージが表示されます。",
    },
    {
      type: "text",
      content: "パン屋が全員死亡すると、朝に専用のシステムメッセージが表示されます。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "パン屋がおいしいパンを焼いてくれたそうです。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "今日からはもうおいしいパンが食べられません。",
    },
  ],
  mason: [
    {
      type: "text",
      content: "共鳴者、共有者にしか聞こえない会話が可能です。",
    },
  ],
  listenmason: [
    {
      type: "text",
      content: "共鳴者、共有者同士にしか聞こえない会話が聞こえます。",
    },
    {
      type: "text",
      content: "共鳴者とは異なり、話すことはできません。",
    },
  ],
  luckyman: [
    {
      type: "text",
      content: "投票で最多票が複数いる場合、必ず自分以外から処刑者が選ばれます。",
    },
    {
      type: "text",
      content: "革命中も同様で、最少票が複数いる場合、必ず自分以外から処刑者が選ばれます。",
    },
  ],
  commander: [
    {
      type: "text",
      content:
        "2日目以降毎日、日中に一度だけ生存者を選んで指を差し、「（指揮官名）は、（対象）を指差した。」と全員に知らせることができます。",
    },
    {
      type: "text",
      content:
        "このメッセージは煽動者/騙狐の能力と同じ内容となるため、メッセージを見た人からは、指揮官/煽動者/騙狐のどの能力なのか判断できません。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを指差した。",
    },
  ],
  fantasist: [
    {
      type: "text",
      content: "毎晩、自分の部屋で足音が響きます。",
    },
  ],
  sleepwalker: [
    {
      type: "text",
      content: "毎晩、自分の部屋から死者含む誰かの部屋までの足音が響きます。",
    },
    {
      type: "text",
      content:
        "占いや襲撃と同じ方式（＝通過した部屋のみ響く）なので、自分と対象の部屋は鳴りませんし、隣の部屋が対象になった場合は無音となります。",
    },
    {
      type: "text",
      content: "自動で設定されるため、どの部屋に向かったか知ることはできません。",
    },
    {
      type: "text",
      content: "通常の足音と同じなので、罠や爆弾、画鋲や箪笥も発動します。",
    },
  ],
  falsecharges: [
    {
      type: "text",
      content:
        "毎晩、自分の部屋からいずれかの無惨な死体の部屋までの足音が響きます（ダミー襲撃の日も鳴ります）。",
    },
    {
      type: "text",
      content: "無惨な死体が無い場合は足音は響きません。",
    },
    {
      type: "text",
      content:
        "占いや襲撃と同じ方式（＝通過した部屋のみ響く）なので、自分と対象の部屋は鳴りませんし、隣の部屋が対象になった場合は無音となります。",
    },
    {
      type: "text",
      content: "冤罪者が複数いる場合、対象となる無惨な死体は毎回ランダムで選ばれます。",
    },
    {
      type: "text",
      content:
        "冤罪者の足音は特例で各能力処理の後に発生するため、死亡していれば響きませんし、罠や爆弾、画鋲や箪笥は発動しません。",
    },
    {
      type: "text",
      content:
        "前日まで別の役職だった場合、冤罪者になった当日は足音が響きません（トラック転生など）。",
    },
  ],
  reincarnation: [
    {
      type: "text",
      content: "死亡すると、ランダムな役職で生き返ります。",
    },
    {
      type: "text",
      content: "選ばれる役職候補については、転生を参照ください。",
    },
    {
      type: "text",
      content:
        "処理順の「復活」の後の処理は転生後の役職で行われます。例えば、パン屋として生き返った場合専用メッセージが流れますし、冤罪者は冤罪の足音が響きます。",
    },
    {
      type: "text",
      content:
        "夢遊病者や妄想癖は「能力行使と足音のデフォルト登録」で翌日の足音が登録されるので、生き返った当日は足音が響きません。",
    },
    {
      type: "text",
      content:
        "絶対人狼や勇者として生き返った場合、絶対人狼や勇者である旨のメッセージが表示されます。",
    },
    {
      type: "text",
      content:
        "梟がこれまで存在しない村で梟として生き返った場合、梟が存在する旨のメッセージが表示されます。",
    },
    {
      type: "text",
      content:
        "この梟のメッセージは全員が生き返った後に表示されるため、最後に生き返った人が梟というわけではありません（生き返った人のいずれかが梟）。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "不思議なことに、[01楽]楽天家 ゲルトが生き返った。",
    },
  ],
  heavenchild: [
    {
      type: "text",
      content: "死亡すると、ランダムな村人陣営役職で生き返ります。",
    },
    {
      type: "text",
      content:
        "選ばれる役職候補については、転生を参照ください（この中から更に村人陣営で絞られます）。",
    },
    {
      type: "text",
      content:
        "処理順の「復活」の後の処理は転生後の役職で行われます。例えば、パン屋として生き返った場合専用メッセージが流れますし、冤罪者は冤罪の足音が響きます。",
    },
    {
      type: "text",
      content:
        "夢遊病者や妄想癖は「能力行使と足音のデフォルト登録」で翌日の足音が登録されるので、生き返った当日は足音が響きません。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "不思議なことに、[01楽]楽天家 ゲルトが生き返った。",
    },
  ],
  wallpuncher: [
    {
      type: "text",
      content:
        "2日目以降の毎晩、自分の上下左右いずれかの部屋の壁を殴り、その部屋の人を人狼の襲撃から守ることができます。",
    },
    {
      type: "text",
      content: "護衛成功失敗に関わらず、一度選んだ対象は再度壁殴り対象に選ぶことができません。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "護衛成功しても手応えはありません。",
    },
    {
      type: "text",
      content: "襲撃で死亡しませんが、上下左右の部屋に生存者がいなくなると孤独死（後追死）します。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターの部屋の壁を殴っている。",
    },
  ],
  resuscitator: [
    {
      type: "text",
      content: "3日目以降に一度だけ、死亡者を選んで蘇生させることができます。",
    },
    {
      type: "text",
      content: "蘇生すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "蘇生しても恋絆や狐憑きなどのステータスは削除されません。",
    },
    {
      type: "text",
      content:
        "対象が同棲者の場合は蘇生が失敗し、対象は死亡したままとなります（蘇生メッセージも表示されません）。",
    },
    {
      type: "text",
      content: "蘇生後に後追い処理があるため、条件を満たしている場合、蘇生直後に即死します。",
    },
    {
      type: "text",
      content: "蘇生メッセージは死霊術師/陰陽師/海王者による蘇生と同一となります。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "不思議なことに、[01楽]楽天家 ゲルトが生き返った。",
    },
  ],
  dyingpointer: [
    {
      type: "text",
      content: "2日目以降毎日、死に際に指差す相手を選択できます。",
    },
    {
      type: "text",
      content:
        "死亡すると、「（不止者名）は、（対象）を指差した。」と全員に知らせることができます。",
    },
    {
      type: "text",
      content: "蘇生後も指差す相手を選択できます。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "止まるんじゃねぇぞ...\n（AA略）\n[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを指差した。",
    },
  ],
  insurancer: [
    {
      type: "text",
      content: "2日目以降に1回だけ、対象を選択して保険を勧めることができます。",
    },
    {
      type: "text",
      content: "保険を勧めると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "保険を勧めると、対象にステータス「保険」が付与されます（対象にも通知されます）。",
    },
    {
      type: "text",
      content: "対象に選択できるのは、自分以外の生存者です。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽] 楽天家 ゲルトは、[02長] 村長\nヴァルターに保険を勧めた。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "あなたは、[01楽] 楽天家\nゲルトに保険を勧められた。",
    },
  ],
  executioner: [
    {
      type: "text",
      content: "処刑開始時点で執行人が1人生存しているごとに1人処刑人数が追加されます。",
    },
    {
      type: "text",
      content: "被投票数が0票の人は処刑されません。",
    },
  ],
  lawyer: [
    {
      type: "text",
      content: "弁護士が投票した人物は得票数が-2されます（弁護士自身の票はカウントされません）。",
    },
    {
      type: "text",
      content: "被投票数が1票以上の人がいない場合、処刑は行われません。",
    },
    {
      type: "text",
      content:
        "個別の投票ログは変化せず、総得票数のみが減るため、誰が減らしたのか知ることはできません。",
    },
    {
      type: "text",
      content: "0票以下となった場合、「（被投票者名）、0票」と表示されます。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "[01楽]楽天家 ゲルト → [02長]村長 ヴァルター\n[02長]村長 ヴァルター → [02長]村長 ヴァルター\n[03娘]村娘 パメラ → [02長]村長 ヴァルター\n[04旅]旅人 ニコラス → [02長]村長 ヴァルター",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[02長]村長 ヴァルター、1票\n[02長]村長 ヴァルターは村人達の手により処刑された。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "[01楽]楽天家 ゲルト → [02長]村長 ヴァルター\n[02長]村長 ヴァルター → [02長]村長 ヴァルター\n[03娘]村娘 パメラ → [02長]村長 ヴァルター",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[02長]村長 ヴァルター、0票\n本日は処刑が行われなかった。",
    },
  ],
  mayor: [
    {
      type: "text",
      content: "市長が投票した人物は得票数が+2されます（市長自身の票と合わせて2票）。",
    },
    {
      type: "text",
      content:
        "個別の投票ログは変化せず、総得票数のみが増えるため、誰が2票投じたのか知ることはできません。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "[01楽]楽天家 ゲルト → [02長]村長 ヴァルター\n[02長]村長 ヴァルター → [02長]村長 ヴァルター\n[03娘]村娘 パメラ → [03娘]村娘 パメラ\n[04旅]旅人 ニコラス → [03娘]村娘 パメラ",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "[02長]村長 ヴァルター、3票\n[03娘]村娘 パメラ、2票\n[02長]村長 ヴァルターは村人達の手により処刑された。",
    },
  ],
  matagi: [
    {
      type: "text",
      content:
        "2日目以降に1回だけ、1人を狩猟することができます。狩猟すると、襲撃者の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "ただし、マタギ同士で狩猟が発生し、自身が死亡した場合は、狩猟が実行されます（複数マタギがいる場合、能力行使の順番はランダムです）。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "狩猟に成功すると、対象を襲撃死させることができます。",
    },
    {
      type: "text",
      content: "狩猟の成功可否や同棲者の処理については、人狼の襲撃と同様です。",
    },
    {
      type: "text",
      content: "ただし、人狼の襲撃と異なり、対象に襲撃耐性があっても襲撃死させることができます。",
    },
    {
      type: "text",
      content:
        "また、狩猟は獣を狩る目的であるため、人狼系役職でも妖狐系役職でも一匹狼/暴狼でもない対象を狩猟成功した場合、責任を感じて自身に発砲（襲撃死・護衛貫通）します（対象が死亡している場合は失敗扱いです）。",
    },
    {
      type: "text",
      content: "マタギ自身は狩猟に成功したかどうか知ることはできません。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは猟銃を構え、[02長]村長 ヴァルターに向かって発砲した。",
    },
  ],
  soundproofer: [
    {
      type: "text",
      content: "「生存者、足音の報告」時点で防音者である人の部屋では足音が発生しません。",
    },
    {
      type: "text",
      content: "罠や爆弾は関係なく発動します。",
    },
  ],
  pastor: [
    {
      type: "text",
      content:
        "1回だけ1人を指定し、恋絆と狐憑きと狂気を除去したうえで「信念」状態にすることができます。",
    },
    {
      type: "text",
      content: "説得すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "恋絆の除去は破局者の破局と同じ処理となるため、対象が同棲者の場合は相方同棲者への恋絆が付与されたまま信念が付与されます。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを説得し、仲間に引き入れた。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "あなたは、[01楽]楽天家 ゲルトに説得され、平和を望むようになりました。",
    },
  ],
  omniscience: [
    {
      type: "text",
      content: "2日目以降、全日を通して1回だけ、生存者全員の役職構成を知ることができます。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content:
        "[01楽]楽天家 ゲルトは、この村の全容を明らかにした。\nこの村には、村人が1名、導師が1名、風来狩人が2名、妄想癖が1名、全知者が1名、呪狼が2名、智狼が1名生存しているようだ。",
    },
  ],
  anpanman: [
    {
      type: "text",
      content: "パン屋か闇パン屋が1名でも生存している場合、死亡しても生き返ります。",
    },
    {
      type: "text",
      content: "パン屋と闇パン屋が1名も生存していない場合、後追死します。",
    },
    {
      type: "text",
      content:
        "パン屋が1名も生存しておらず、闇パン屋が1名以上生存している状態でこの役職の能力により生き返ると、闇堕ちし、恋絆/狐憑きが解除され、狂気が付与されます（他役職の能力により付与されている場合も除去されます）。",
    },
    {
      type: "text",
      content:
        "闇パン屋が1名も生存しておらず、パン屋が1名以上生存している状態でこの役職の能力により生き返ると、正義の心を取り戻し、恋絆/狐憑き/狂気が解除され、信念が付与されます（他役職の能力により付与されている場合も除去されます）。",
    },
    {
      type: "text",
      content:
        "闇堕ち/正義を取り戻した際のメッセージは餡麺麭者本人のみが確認でき、狂気の有無にかかわらず表示されます。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "餡麺麭！新しい顔よ！それーっ！\n不思議なことに、[01楽]楽天家 ゲルトが生き返った。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、新しい顔がもらえなくなってしまい、顔がふやけて衰弱死した。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "[01楽]楽天家 ゲルトは、闇堕ちした。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "[01楽]楽天家 ゲルトは、正義の心を取り戻した。",
    },
  ],
  hiyasichuka: [
    {
      type: "text",
      content: "特別な能力はありませんが、1回だけ、始まることができます。",
    },
    {
      type: "text",
      content: "セットすると、翌日に始まります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、始まった。",
    },
  ],
  chikuwa: [
    {
      type: "text",
      content: "特別な能力はありませんが、1回だけ、挟まることができます。",
    },
    {
      type: "text",
      content: "セットすると、翌日に挟まります。",
    },
  ],
  emotion: [
    {
      type: "text",
      content: "特別な能力はありませんが、何回でも、終わることができます。",
    },
    {
      type: "text",
      content: "セットすると、翌日に終わります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、終わった。",
    },
  ],
  hero: [
    {
      type: "text",
      content:
        "2日目以降に1回だけ、1人を討伐することができます。討伐すると、襲撃者の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "出現時点で（1日目開始時点や、転生など）勇者である旨が公開されます。",
    },
    {
      type: "text",
      content: "能力未使用かつ現在の勝敗判定陣営が村人陣営の場合、死亡しても生き返ります。",
    },
    {
      type: "text",
      content: "この役職が処刑対象となった場合、追加でもう1名処刑されます（執行者と同一効果）。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "討伐に成功すると、対象を襲撃死させることができます。",
    },
    {
      type: "text",
      content: "討伐に成功すると、役目を終えるため自害（襲撃死・護衛貫通）します。",
    },
    {
      type: "text",
      content: "自害メッセージは公開されるため、全員が視認できます。",
    },
    {
      type: "text",
      content: "討伐の成功可否や同棲者の処理については、人狼の襲撃と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを討伐した。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、役目を終え、自害した。",
    },
  ],
  torturer: [
    {
      type: "text",
      content:
        "2日目以降に1回だけ、1人を指定して拷問し、生存している仲間の位置（勝敗判定陣営が同じ人）を2人まで知ることができます。\n拷問すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "判明する仲間は対象自身を含みません。",
    },
    {
      type: "text",
      content: "同陣営の仲間が2人以上いる場合、ランダムに2人が選ばれます。",
    },
    {
      type: "text",
      content: "同陣営の仲間が1人しかいない場合、1人のみ判明します。",
    },
    {
      type: "text",
      content: "同陣営の仲間がいない場合、仲間はいない旨が通知されます。",
    },
    {
      type: "text",
      content: "拷問された側には、誰に拷問されたかが通知されます。",
    },
    {
      type: "text",
      content:
        "登場した時点で能力が公開される役職（絶対人狼、勇者）は拷問に屈しません（拷問は失敗し、仲間の情報は得られません）。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを拷問した。\n[02長]村長 ヴァルターの仲間には、[03農]農夫 モーリッツと[05樵]木こり トーマスがいるようだ。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを拷問したが、[02長]村長 ヴァルターは拷問に屈しなかった。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "あなたは、[01楽]楽天家 ゲルトに拷問されました。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトがあなたを拷問しましたが、あなたは屈しませんでした。",
    },
  ],
  werewolf: [
    {
      type: "text",
      content:
        "人狼系役職とC国狂人同士にしか聞こえない会話が可能です。\nまた、毎晩一人を襲撃することができます。\n襲撃すると、襲撃者の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "突然死や処刑により死亡した場合も襲撃担当者が変わることなく襲撃は実行されます（襲撃なしにはなりません）。（生存している人狼が0名になった場合は襲撃は実行されません）",
    },
    {
      type: "text",
      content:
        "設定で同一人狼による連続襲撃不可となっている村では、人狼が複数生存している場合、2日連続で同じ人狼が襲撃することはできません。",
    },
    {
      type: "text",
      content: "日付更新時の襲撃者およびセット先はランダムです。",
    },
    {
      type: "text",
      content:
        "各人狼が個別に襲撃者/襲撃対象/足音をセットでき、襲撃者/襲撃対象/足音全体の組み合わせで最多票となる内容で襲撃が実行されます。",
    },
    {
      type: "text",
      content: "最多票となる組み合わせが複数ある場合は処刑と同様、ランダムで選択されます。",
    },
    {
      type: "text",
      content: "他人の襲撃セット内容は知ることができません。",
    },
    {
      type: "text",
      content: "採用された襲撃セット内容は能力セット欄の履歴で確認することができます。",
    },
    {
      type: "text",
      content: "自身が過去日にセットした内容はエピローグまで表示されません。",
    },
    {
      type: "text",
      content: "「襲撃なし」はありません。必ず襲撃対象を選択することになります。",
    },
    {
      type: "text",
      content: "襲撃失敗の場合、護衛なのか妖狐襲撃なのかは判別できません。",
    },
    {
      type: "text",
      content: "処刑された人物と同一人物を襲撃対象として選択していた場合も襲撃失敗になります。",
    },
    {
      type: "text",
      content:
        "「●●！今日がお前の命日だ！」の発言者は襲撃者となります（襲撃時点で襲撃者が死亡していることがありますが、わかりやすさを優先します）。",
    },
  ],
  cursewolf: [
    {
      type: "text",
      content: "占われると、占い能力を行使した側が死亡します。",
    },
    {
      type: "text",
      content:
        "ただし、突然死や処刑されていた場合、占われていても占い能力を行使した人は死亡しません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
  ],
  wisewolf: [
    {
      type: "text",
      content:
        "自身が襲撃を担当し、襲撃成功すると、襲撃対象が何の役職であったか知ることができます。",
    },
    {
      type: "text",
      content: "ただし、自身が突然死や処刑されていた場合は襲撃対象の役職を知ることができません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは強運者だったようだ。",
    },
  ],
  absolutewolf: [
    {
      type: "text",
      content: "出現時点で（1日目開始時点や、転生など）絶対人狼である旨が公開されます。",
    },
    {
      type: "text",
      content: "他の絶対人狼を除く人狼系役職が1名でも生存している場合、死亡しても生き返ります。",
    },
    {
      type: "text",
      content: "ただし、恋絆が付与されていて後追い対象が死亡している場合、復活直後に後追死します。",
    },
    {
      type: "text",
      content: "この役職が処刑対象となった場合、追加でもう1名処刑されます（執行者と同一効果）。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは絶対人狼のようだ。",
    },
  ],
  fuwolf: [
    {
      type: "text",
      content: "自分の上下左右の部屋のみ襲撃対象とすることができます。",
    },
    {
      type: "text",
      content:
        "上下左右の部屋に襲撃対象が存在しない場合、通常の人狼と同様、生存者全員を対象とすることができます。",
    },
    {
      type: "text",
      content: "1日目は上記制約にとらわれずダミーを襲撃することができます。",
    },
    {
      type: "text",
      content:
        "襲撃者がこの役職で、護衛もしくは襲撃耐性により襲撃が失敗した場合、襲撃者が人狼に変化します。\n対象が襲撃時点で死亡していたり、護衛なしで同棲者の能力による不在での失敗の場合は変化しません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは、人狼に成った。",
    },
  ],
  ginwolf: [
    {
      type: "text",
      content:
        "将棋の銀が移動できる（左上、上、右上、左下、右下）部屋のみ襲撃対象とすることができます。",
    },
    {
      type: "text",
      content:
        "上記部屋に襲撃対象が存在しない場合、通常の人狼と同様、生存者全員を対象とすることができます。",
    },
    {
      type: "text",
      content: "1日目は上記制約にとらわれずダミーを襲撃することができます。",
    },
    {
      type: "text",
      content:
        "襲撃者がこの役職で、護衛もしくは襲撃耐性により襲撃が失敗した場合、襲撃者が人狼に変化します。\n対象が襲撃時点で死亡していたり、護衛なしで同棲者の能力による不在での失敗の場合は変化しません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは、人狼に成った。",
    },
  ],
  kinwolf: [
    {
      type: "text",
      content:
        "将棋の金が移動できる（左上、上、右上、左、右、下）部屋のみ襲撃対象とすることができます。",
    },
    {
      type: "text",
      content:
        "上記部屋に襲撃対象が存在しない場合、通常の人狼と同様、生存者全員を対象とすることができます。",
    },
    {
      type: "text",
      content: "1日目は上記制約にとらわれずダミーを襲撃することができます。",
    },
    {
      type: "text",
      content:
        "襲撃者がこの役職で、護衛もしくは襲撃耐性により襲撃が失敗した場合、襲撃者が人狼に変化します。\n対象が襲撃時点で死亡していたり、護衛なしで同棲者の能力による不在での失敗の場合は変化しません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは、人狼に成った。",
    },
  ],
  hishawolf: [
    {
      type: "text",
      content: "自分の部屋から見て直線上の部屋のみ襲撃対象とすることができます。",
    },
    {
      type: "text",
      content:
        "直線上の部屋に襲撃対象が存在しない場合、通常の人狼と同様、生存者全員を対象とすることができます。",
    },
    {
      type: "text",
      content: "1日目は上記制約にとらわれずダミーを襲撃することができます。",
    },
    {
      type: "text",
      content:
        "襲撃者がこの役職で、護衛もしくは襲撃耐性により襲撃が失敗した場合、襲撃者が人狼に変化します。\n対象が襲撃時点で死亡していたり、護衛なしで同棲者の能力による不在での失敗の場合は変化しません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは、人狼に成った。",
    },
  ],
  kakuwolf: [
    {
      type: "text",
      content: "自分の部屋から見て斜め方向の部屋のみ襲撃対象とすることができます。",
    },
    {
      type: "text",
      content:
        "斜め方向の部屋に襲撃対象が存在しない場合、通常の人狼と同様、生存者全員を対象とすることができます。",
    },
    {
      type: "text",
      content: "1日目は上記制約にとらわれずダミーを襲撃することができます。",
    },
    {
      type: "text",
      content: "足音は通常通り響きます（斜めに移動できるわけではありません）。",
    },
    {
      type: "text",
      content:
        "襲撃者がこの役職で、護衛もしくは襲撃耐性により襲撃が失敗した場合、襲撃者が人狼に変化します。\n対象が襲撃時点で死亡していたり、護衛なしで同棲者の能力による不在での失敗の場合は変化しません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは、人狼に成った。",
    },
  ],
  kingwolf: [
    {
      type: "text",
      content: "自分の周囲8部屋のみ襲撃対象とすることができます。",
    },
    {
      type: "text",
      content:
        "上記部屋に襲撃対象が存在しない場合、通常の人狼と同様、生存者全員を対象とすることができます。",
    },
    {
      type: "text",
      content: "1日目は上記制約にとらわれずダミーを襲撃することができます。",
    },
    {
      type: "text",
      content:
        "襲撃者がこの役職で、護衛もしくは襲撃耐性により襲撃が失敗した場合、襲撃者が人狼に変化します。\n対象が襲撃時点で死亡していたり、護衛なしで同棲者の能力による不在での失敗の場合は変化しません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは、人狼に成った。",
    },
  ],
  silentwolf: [
    {
      type: "text",
      content: "必ず足音が発生しない対象のみ襲撃対象とすることができます。",
    },
    {
      type: "text",
      content:
        "2通りのルートを選択できる場合は、どちらのルートも通過する部屋に生存者が1名も存在しないことが条件になります。",
    },
    {
      type: "text",
      content:
        "上記条件で襲撃対象が存在しない場合、通常の人狼と同様、生存者全員を対象とすることができます（周囲が全員狼の場合など）。",
    },
    {
      type: "text",
      content: "1日目は上記制約にとらわれずダミーを襲撃することができます（足音は発生します）。",
    },
    {
      type: "text",
      content: "静狼が襲撃を担当し、通過する部屋に蘇生等があった場合は、通常通り足音が発生します。",
    },
    {
      type: "text",
      content:
        "襲撃者が静狼で、護衛もしくは襲撃耐性により襲撃が失敗した場合、襲撃者が呪狼に変化します。\n対象が襲撃時点で死亡していたり、護衛なしで同棲者の能力による不在での失敗の場合は変化しません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは、静かに懐から藁人形を取り出し、呪狼となった。",
    },
  ],
  hardwolf: [
    {
      type: "text",
      content: "襲撃耐性を持っている人狼です。",
    },
  ],
  listenwolf: [
    {
      type: "text",
      content: "囁くことはできませんが、人狼の囁きを聞くことができます。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
  ],
  smellwolf: [
    {
      type: "text",
      content: "あなたが発生させた足音を調査されると、調査能力を行使した側が死亡（呪殺）します。",
    },
    {
      type: "text",
      content: "呪狼の呪殺と異なり、調査時点であなたが死亡していても死亡させられます。",
    },
    {
      type: "text",
      content: "死亡する際、調査能力を行使した側に専用メッセージが表示されます。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "[01楽]楽天家 ゲルトは、足跡に残った臭さのあまり、即死した。",
    },
  ],
  emperorwolf: [
    {
      type: "text",
      content: "あなたが処刑されると、その日あなたに投票した人は開票後に「不敬」が付与されます。",
    },
    {
      type: "text",
      content: "同日に同時に処刑された人には付与されません。",
    },
    {
      type: "text",
      content:
        "不敬が付与される際、メッセージは表示されないため、投票した人は不敬が付与されていることを知ることはできません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
  ],
  dissectwolf: [
    {
      type: "text",
      content: "自身が襲撃を担当し、襲撃成功すると、襲撃対象の勝敗判定陣営を知ることができます。",
    },
    {
      type: "text",
      content:
        "ただし、自身が突然死や処刑されていた場合は襲撃対象の勝敗判定陣営を知ることができません。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "message",
      messageType: "message-private-werewolf",
      content: "[01楽]楽天家 ゲルトは村人に与していたようだ。",
    },
  ],
  conanwolf: [
    {
      type: "text",
      content: "人狼の囁き発言をすると、「ラーーン！」と変換されてしまいます。",
    },
    {
      type: "text",
      content: "ただし、10%の確率で元のまま発言できます（行単位判定で、襲撃メッセージを含む）。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "text",
      content: "ラーーーーーーン！！！！",
    },
  ],
  gourmetwolf: [
    {
      type: "text",
      content:
        "襲撃を担当し、襲撃に成功すると、専用の発言をします（バリエーションがあり、ランダムで選択されます）。",
    },
    {
      type: "text",
      content: "襲撃を担当し、襲撃に失敗すると、専用の発言をします（1種類です）。",
    },
    {
      type: "text",
      content: "つまり、襲撃成功可否を知ることができます。",
    },
    {
      type: "text",
      content: "他は人狼と同様です。",
    },
    {
      type: "text",
      content: "ほー いいじゃないか こういうのでいいんだよ こういうので",
    },
    {
      type: "text",
      content: "がーんだな…出鼻をくじかれた",
    },
  ],
  rampagewolf: [
    {
      type: "text",
      content:
        "2日目以降、毎晩一人を襲撃することができます。\n襲撃すると、襲撃者の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "人狼陣営の一匹狼です。人狼の囁きは見えませんし、話せません（勝敗判定カウントのみ人狼カウントです）。",
    },
    {
      type: "text",
      content:
        "一匹狼と異なり、「襲撃なし」はできません。必ず対象を選択して襲撃することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "他は一匹狼と同様です。",
    },
  ],
  madman: [
    {
      type: "text",
      content:
        "毎晩任意の部屋からその直線上の部屋に向かって徘徊し、徘徊した部屋に足音を響かせることが可能です。",
    },
    {
      type: "text",
      content: "「足音なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時の足音セットは「なし」です。",
    },
  ],
  cmadman: [
    {
      type: "text",
      content: "人狼とC国狂人同士にしか聞こえない会話が可能です。",
    },
    {
      type: "text",
      content: "他は狂人と同様です。",
    },
  ],
  listenmadman: [
    {
      type: "text",
      content: "囁くことはできませんが、人狼の囁きを聞くことができます。",
    },
    {
      type: "text",
      content: "他は狂人と同様です。",
    },
  ],
  evilmedium: [
    {
      type: "text",
      content: "導師能力を持つ狂人です。",
    },
    {
      type: "text",
      content:
        "導師能力を持った人が1名以上生存していれば導師メッセージが表示されます（導師、魔神官、稲荷など）。",
    },
    {
      type: "text",
      content: "徘徊も可能です。",
    },
    {
      type: "text",
      content: "他は狂人と同様です。",
    },
  ],
  fanatic: [
    {
      type: "text",
      content:
        "人狼が誰なのかを知ることができます。（人狼からは自分が狂信者であることはわかりません）",
    },
    {
      type: "text",
      content: "他は狂人と同様です。",
    },
  ],
  instigator: [
    {
      type: "text",
      content:
        "人狼が誰なのかを知ることができます。（人狼からは自分が煽動者であることはわかりません）",
    },
    {
      type: "text",
      content:
        "2日目以降毎日、日中に一度だけ生存者を選んで指を差し、「（煽動者名）は、（対象）を指差した。」と全員に知らせることができます。",
    },
    {
      type: "text",
      content:
        "このメッセージは指揮官/騙狐の能力と同じ内容となるため、メッセージを見た人からは、指揮官/煽動者/騙狐のどの能力なのか判断できません。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを指差した。",
    },
  ],
  necromancer: [
    {
      type: "text",
      content: "蘇生者の人狼陣営版で、以下に挙げる点以外は蘇生者と同じです。",
    },
    {
      type: "text",
      content: "対象を役職「黙狼」として蘇生させます。",
    },
    {
      type: "text",
      content:
        "対象が同棲者の場合は蘇生が失敗し、対象は死亡したままとなります（蘇生メッセージも表示されません）。",
    },
    {
      type: "text",
      content: "既に対象が蘇生済みの場合は対象の役職は変化しません。",
    },
    {
      type: "text",
      content: "蘇生メッセージは蘇生者/陰陽師/海王者による蘇生と同一となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "不思議なことに、[01楽]楽天家 ゲルトが生き返った。",
    },
  ],
  blackbox: [
    {
      type: "text",
      content: "2回まで投票箱を隠すことができます。",
    },
    {
      type: "text",
      content:
        "投票箱を隠すと、誰が誰に投票したか、誰に何票入ったかが隠蔽され、処刑された人のみが公表されます。",
    },
    {
      type: "text",
      content: "黒箱者も投票の詳細を知ることはできません。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content:
        "[01楽]楽天家 ゲルト → [02長]村長 ヴァルター\n[02長]村長 ヴァルター → [02長]村長 ヴァルター\n[03娘]村娘 パメラ → [03娘]村娘 パメラ\n[04旅]旅人 ニコラス → [03娘]村娘 パメラ",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content:
        "[02長]村長 ヴァルター、2票\n[03娘]村娘 パメラ、2票\n[02長]村長 ヴァルターは村人達の手により処刑された。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "何者かに投票箱を隠されてしまったようだ。\n[02長]村長 ヴァルターは村人達の手により処刑された。",
    },
  ],
  bar: [
    {
      type: "text",
      content:
        "1回だけ、自分に投票したことがある人から1名を選び、殴打することができます。\n殴打すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "殴打に成功すると対象は襲撃死します。",
    },
    {
      type: "text",
      content:
        "ただし、バールのようなもの同士で殴打が発生し、自身が死亡した場合は、殴打が実行されます。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "殴打成功失敗に関わる処理は人狼の襲撃と同様です。",
    },
    {
      type: "text",
      content:
        "自分に投票してきた人が能力行使対象となるため、投票を黒箱者に投票を隠されていても、自分に投票してきた人がわかります。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "[02長]村長 ヴァルターは、投票の恨みを晴らすべく、[03娘]村娘 パメラを殴打した。",
    },
  ],
  resenter: [
    {
      type: "text",
      content: "毎日、自分に投票してきた人の役職構成を知ることができます。",
    },
    {
      type: "text",
      content: "処刑されなかった場合のみメッセージが発生します。",
    },
    {
      type: "text",
      content:
        "投票を黒箱者に投票を隠されていても、自分に投票してきた人の役職構成を知ることができます。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content:
        "[01楽]楽天家 ゲルトは、自分に投票した村人の素性を調べた。\n投票してきたのは、村人が1名、占い師が2名、人狼が3名、共鳴者が2名、妖狐が1名のようだ。",
    },
  ],
  separator: [
    {
      type: "text",
      content: "1回だけ1人を指定し、対象から他者への恋絆を消去することができます。",
    },
    {
      type: "text",
      content: "破局させると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "同棲者同士の恋絆は消去することができません（他の恋絆のみが消去されます）。",
    },
    {
      type: "text",
      content: "他者から対象への恋絆は消去されません。\n例. 恋絆は以下のようになります。",
    },
    {
      type: "text",
      content: "破局前：A ⇄ 対象",
    },
    {
      type: "text",
      content: "破局後：A → 対象",
    },
    {
      type: "text",
      content:
        "以下に当てはまる場合、失敗となり、対象には能力を使用した旨が通知されません。（再使用もできません）\n対象が死亡していた場合",
    },
    {
      type: "text",
      content: "対象に恋絆が付与されていない場合",
    },
    {
      type: "text",
      content: "※対象が同棲者で、相方同棲者への恋絆しか存在しない場合も失敗",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを破局させた。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[02長]村長 ヴァルターは、[01楽]楽天家 ゲルトに破局させられてしまった。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを破局させようとしたが、[02長]村長\nヴァルターは恋をしていなかった。",
    },
  ],
  abetter: [
    {
      type: "text",
      content: "1回だけ1人を指定し、恋絆と狐憑きを除去したうえで「狂気」状態にすることができます。",
    },
    {
      type: "text",
      content:
        "恋絆の除去は破局者の破局と同じ処理となるため、対象が同棲者の場合は相方同棲者への恋絆が付与されたまま狂気が付与されます。",
    },
    {
      type: "text",
      content: "唆すと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを唆し、仲間に引き入れた。",
    },
    {
      type: "message",
      messageType: "message-private",
      content: "あなたは、[01楽]楽天家 ゲルトに唆され、人狼に与するものとなりました。",
    },
  ],
  evildetective: [
    {
      type: "text",
      content: "人狼陣営の探偵です。",
    },
  ],
  freezer: [
    {
      type: "text",
      content: "2日目以降、全日を通して1回だけ戦闘力を発揮することができます。",
    },
    {
      type: "text",
      content:
        "戦闘力を発揮すると投票先に53万票を投じることができます（自身の票と合わせて53万票）。",
    },
  ],
  boss: [
    {
      type: "text",
      content: "人狼陣営の市長です。",
    },
  ],
  guilter: [
    {
      type: "text",
      content:
        "全日を通して2回だけ、自分以外の生存者から1名を選び、対象の部屋からいずれかの無惨な死体の部屋までの足音を響かせることができます（ダミー襲撃の日も可能）。",
    },
    {
      type: "text",
      content: "能力行使の際、濡衣者から対象への足音は発生しません。",
    },
    {
      type: "text",
      content: "夜の間に濡衣者が死亡した場合は行使失敗となり、足音が発生しません。",
    },
    {
      type: "text",
      content: "夜の間に対象が死亡していても足音が発生します。",
    },
    {
      type: "text",
      content: "濡衣者は、結果としてどの足音が発生したか把握することができません。",
    },
    {
      type: "text",
      content:
        "足音を発生させた人は濡衣者となります。つまり、探偵系役職が調査すると、結果は対象でなく濡衣者となります。",
    },
    {
      type: "text",
      content: "その他は冤罪者と同じです。つまり、罠/爆弾/画鋲/箪笥などは発動しません。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターに濡れ衣を着せた。",
    },
  ],
  evilbakery: [
    {
      type: "text",
      content: "人狼陣営のパン屋です。",
    },
    {
      type: "text",
      content:
        "パン屋か闇パン屋が1名以上生存していると、朝に専用のシステムメッセージが表示されます。",
    },
    {
      type: "text",
      content: "パン屋と闇パン屋が全員死亡すると、朝に専用のシステムメッセージが表示されます。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "パン屋がおいしいパンを焼いてくれたそうです。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "今日からはもうおいしいパンが食べられません。",
    },
  ],
  cloudy: [
    {
      type: "text",
      content: "2回まで曇天にすることができます。",
    },
    {
      type: "text",
      content:
        "曇天状態になると、その日の占星術が失敗となり、占星術結果や呪殺・逆呪殺が発生しなくなります。",
    },
    {
      type: "text",
      content:
        "占星術が失敗しても、セットされた占星術の足音は響きます。また、罠や爆弾等も起動します。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "[01楽]楽天家 ゲルトは、空いっぱいに雲を発生させた。",
    },
    {
      type: "message",
      messageType: "message-private-seer",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターのあたりを占おうとしたが、本日は曇天のため占星術を行えなかった。",
    },
  ],
  lover: [
    {
      type: "text",
      content:
        "1日目開始時点で、恋人同士で恋絆が結ばれます。（恋人は必ず偶数人数で、恋絆は2人ずつペアになります。）",
    },
  ],
  cohabiter: [
    {
      type: "text",
      content:
        "1日目開始時点で、同棲者同士で恋絆が結ばれます。（同棲者は必ず偶数人数で、恋絆は2人ずつペアになります。）",
    },
    {
      type: "text",
      content: "毎晩、どちらの部屋で過ごすか選ぶことができます。",
    },
    {
      type: "text",
      content: "「指定なし」はありません。必ずどちらかの部屋を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content: "どちらかが突然死または処刑されていた場合は部屋を移動しません。",
    },
    {
      type: "text",
      content: "選ばなかった部屋（不在の部屋）に襲撃があった場合、襲撃は失敗します。",
    },
    {
      type: "text",
      content: "選ばなかった部屋（不在の部屋）に爆弾設置があり、爆発しても、同棲者は死亡しません。",
    },
    {
      type: "text",
      content:
        "選んだ部屋に襲撃があった場合、襲撃対象が護衛されていればもう1人の同棲者が護衛されていなくても襲撃は失敗します。",
    },
    {
      type: "text",
      content:
        "選んだ部屋に襲撃があり、襲撃が成功した場合、どちらも襲撃死します。（後追死ではありません）",
    },
    {
      type: "text",
      content:
        "選んだ部屋に爆弾設置があり、爆発した場合、どちらも爆死します。（後追死ではありません）",
    },
    {
      type: "text",
      content: "占いは部屋移動前に行われるため、普段通り占い対象の結果が得られます。",
    },
  ],
  courtship: [
    {
      type: "text",
      content:
        "1回だけ1人を指定し、自身と指定した人同士で恋絆を結ぶことができます。（相手にも通知されます）",
    },
    {
      type: "text",
      content: "求愛すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "「指定なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターに求愛した。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[02長]村長 ヴァルターは、[01楽]楽天家 ゲルトに求愛された。",
    },
  ],
  stalker: [
    {
      type: "text",
      content:
        "1回だけ1人を指定し、自身から指定した人への恋絆を結ぶことができます。（相手には通知されません）",
    },
    {
      type: "text",
      content:
        "ストーキングすると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "恋絆を結んだ際、その人の役職を知ることができます。",
    },
    {
      type: "text",
      content: "「指定なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターをストーキングし始めた。\n[02長]村長 ヴァルターは導師のようだ。",
    },
  ],
  cheatlover: [
    {
      type: "text",
      content:
        "毎晩1人を指定し、今までの恋を忘れ、指定した人に浮気して恋絆を結ぶことができます。（相手には通知されません）",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず対象を選択することになります。",
    },
    {
      type: "text",
      content:
        "ただし、一度選んだ対象は選択することができなくなり（風来護衛と同じです）、選択できる対象がいなくなると「対象なし」となります。",
    },
    {
      type: "text",
      content:
        "また、陣営変化を受け、勝敗判定陣営が恋人陣営でない場合も浮気できず、強制的に「対象なし」となります。",
    },
    {
      type: "text",
      content: "浮気すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "浮気すると、自身から他者への恋絆が全て外れ（他者から自身への恋絆は除去されません）、自身から指定した人への恋絆が付与されます。",
    },
    {
      type: "text",
      content: "1日目は恋をしていないため、2日目朝から恋をして、3日目朝から浮気します。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターに浮気した。",
    },
  ],
  jorogumo: [
    {
      type: "text",
      content:
        "1回だけ1人を指定し、自身への恋絆を結ぶことができます（自身から相手への恋絆は付与されません）。",
    },
    {
      type: "text",
      content: "誘惑すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "対象は翌日朝に「〜は〜に誘惑され、恋をしてしまった。」と表示され、恋絆が結ばれたことを知ることができます。",
    },
    {
      type: "text",
      content:
        "このメッセージは美人局や魅惑の人魚の能力と同じ内容となるため、恋絆を結ばれた側からは、どの能力によるメッセージなのか判断できません。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを誘惑した。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[02長]村長 ヴァルターは、[01楽]楽天家 ゲルトに誘惑され、恋をしてしまった。",
    },
  ],
  badgergame: [
    {
      type: "text",
      content:
        "1回だけ1人を指定し、自身への恋絆を結ぶことができます（自身から相手への恋絆は付与されません）。",
    },
    {
      type: "text",
      content: "誘惑すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "対象は翌日朝に「〜は〜に誘惑され、恋をしてしまった。」と表示され、恋絆が結ばれたことを知ることができます。",
    },
    {
      type: "text",
      content:
        "このメッセージは絡新婦や魅惑の人魚の能力と同じ内容となるため、恋絆を結ばれた側からは、どの能力によるメッセージなのか判断できません。",
    },
    {
      type: "text",
      content:
        "また、対象は翌々日朝に強面の男に襲撃されます（人狼による襲撃と同じ判定となるため、護衛等あれば死亡しません）。",
    },
    {
      type: "text",
      content:
        "ただし、美人局が死亡している場合は襲撃が発生しません（ただし恋絆が付与されているため、後追いします）。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを誘惑した。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[02長]村長 ヴァルターは、[01楽]楽天家 ゲルトに誘惑され、恋をしてしまった。",
    },
  ],
  mermaid: [
    {
      type: "text",
      content:
        "2日目以降、1回だけナマ足を出し、その晩に自身へ投票してきた人に、自身への恋絆を結ぶことができます（自身から相手への恋絆は付与されません）。",
    },
    {
      type: "text",
      content:
        "対象は翌日朝に「〜は〜に誘惑され、恋をしてしまった。」と表示され、恋絆が結ばれたことを知ることができます。",
    },
    {
      type: "text",
      content:
        "このメッセージは絡新婦や美人局の能力と同じ内容となるため、恋絆を結ばれた側からは、どの能力によるメッセージなのか判断できません。",
    },
    {
      type: "text",
      content:
        "処刑開始時点で魅惑の人魚が生存していれば能力は発動し、処刑前に恋絆が付与されるため、その投票により魅惑の人魚が処刑された場合、直後に投票者は後追いします。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[01楽]楽天家 ゲルトは、出すとこ出してたわわになった。",
    },
    {
      type: "message",
      messageType: "message-private-lover",
      content: "[02長]村長 ヴァルターは、[01楽]楽天家 ゲルトに誘惑され、恋をしてしまった。",
    },
  ],
  poseidon: [
    {
      type: "text",
      content: "蘇生者の恋人陣営版で、以下に挙げる点以外は蘇生者と同じです。",
    },
    {
      type: "text",
      content: "対象を役職「魅惑の人魚」として蘇生させます。",
    },
    {
      type: "text",
      content: "生存者が10名以上いる状態でしか能力セットできません。",
    },
    {
      type: "text",
      content:
        "対象が同棲者の場合は蘇生が失敗し、対象は死亡したままとなります（蘇生メッセージも表示されません）。",
    },
    {
      type: "text",
      content: "既に対象が蘇生済みの場合は対象の役職は変化しません。",
    },
    {
      type: "text",
      content: "蘇生メッセージは蘇生者/死霊術師/陰陽師による蘇生と同一となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "不思議なことに、[01楽]楽天家 ゲルトが生き返った。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
  ],
  mimidoshima: [
    {
      type: "text",
      content: "特別な能力はありませんが、恋人会話を聞くことができます。",
    },
    {
      type: "text",
      content: "恋絆が付与されなければ恋人会話で話すことはできません。",
    },
  ],
  normie: [
    {
      type: "text",
      content: "夢遊病者同様、毎晩、自分の部屋から死者含む誰かの部屋までの足音が響きます。",
    },
    {
      type: "text",
      content: "無惨な死体となった場合、爆発し、自分の部屋の周囲8部屋にいる人が爆死します。",
    },
    {
      type: "text",
      content:
        "爆弾魔の爆弾と同様、爆発範囲にいても不在の同棲者は死亡せず、逆に爆発範囲に来ている同棲者は同時に爆死します。",
    },
    {
      type: "text",
      content:
        "リア充が複数存在しており、他のリア充の爆発による死亡で自分が死亡しても、自分は爆発しません。",
    },
    {
      type: "text",
      content: "つまり、リア充処理開始時点で生存していれば爆発しません。",
    },
    {
      type: "text",
      content: "恋絆が付与されなければ恋人会話で話すことはできません。",
    },
    {
      type: "message",
      messageType: "message-public",
      content: "リア充の[01楽]楽天家 ゲルトは、爆発した。",
    },
  ],
  fox: [
    {
      type: "text",
      content: "人狼に襲撃されても死亡しませんが、占われると死亡します。",
    },
    {
      type: "text",
      content:
        "毎晩任意の部屋からその直線上の部屋に向かって徘徊し、徘徊した部屋に足音を響かせることが可能です。",
    },
    {
      type: "text",
      content: "「足音なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時の足音セットは「なし」です。",
    },
    {
      type: "text",
      content:
        "処理順は処刑→占い→襲撃となるため、占い師が処刑された場合、妖狐に占いをセットしていても妖狐は死亡しません。逆に、占い師が襲撃された場合でも妖狐に占いをセットしていた場合は妖狐は死亡します。",
    },
    {
      type: "text",
      content: "他の妖狐系役職を知ることができます。",
    },
    {
      type: "text",
      content: "念話を聞くことができます（話せるのは仙狐と念力が付与されている人のみです）。",
    },
  ],
  cheaterfox: [
    {
      type: "text",
      content: "以下に挙げる点以外は妖狐と同じです。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "text",
      content: "1回だけ1人を指定し、恋絆を除去したうえで「狐憑き」状態にすることができます。",
    },
    {
      type: "text",
      content:
        "恋絆の除去は破局者の破局と同じ処理となるため、対象が同棲者の場合は相方同棲者への恋絆が付与されたまま狐憑きが付与されます。",
    },
    {
      type: "text",
      content: "誑かすと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "念話を聞くことができます（話せるのは仙狐と念力が付与されている人のみです）。",
    },
    {
      type: "message",
      messageType: "message-private-fox",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを誑かし、仲間に引き入れた。",
    },
    {
      type: "message",
      messageType: "message-private-fox",
      content: "あなたは、[01楽]楽天家 ゲルトに誑かされ、妖狐に与するものとなりました。",
    },
  ],
  gonfox: [
    {
      type: "text",
      content: "毎日栗を拾って、みんなの部屋の前に届けます。",
    },
    {
      type: "text",
      content:
        "ごんが1名以上生存していると、朝に「みんなの部屋の前に、栗が（生存ごん数）個ずつ置かれていた。」と表示されます。",
    },
    {
      type: "text",
      content: "ごんが当日に1名以上死亡すると、朝に専用メッセージが表示されます 。",
    },
    {
      type: "text",
      content: "状況によっては、上記2メッセージ両方が表示されることもあります。",
    },
    {
      type: "text",
      content: "人狼に襲撃されても死亡しませんが、占われると死亡します。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "text",
      content: "他の妖狐系役職を知ることができます。",
    },
    {
      type: "text",
      content: "念話を聞くことができます（話せるのは仙狐と念力が付与されている人のみです）。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "みんなの部屋の前に、栗が3個ずつ置かれていた。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "ごん、お前だったのか。いつも栗をくれたのは。",
    },
  ],
  hermitfox: [
    {
      type: "text",
      content:
        "妖狐系役職、狐憑き、および背徳者、陰陽師にしか聞こえない妖狐発言が可能です（話せるのは仙狐と念力が付与されている人のみです）。",
    },
    {
      type: "text",
      content: "他は妖狐と同じです。徘徊も可能です。",
    },
  ],
  kudafox: [
    {
      type: "text",
      content: "賢者能力を持つ妖狐です。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "text",
      content: "他は妖狐と同じです。",
    },
    {
      type: "text",
      content:
        "ただし、妖狐系を占った場合も、呪殺することはありません（呪狼を占った場合は逆呪殺されます）。",
    },
  ],
  inari: [
    {
      type: "text",
      content: "導師能力を持つ妖狐です。",
    },
    {
      type: "text",
      content:
        "導師能力を持った人が1名以上生存していれば導師メッセージが表示されます（導師、魔神官、稲荷など）。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "text",
      content: "他は妖狐と同じです。",
    },
  ],
  trickfox: [
    {
      type: "text",
      content: "指揮官能力を持つ妖狐です。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "text",
      content: "他は妖狐と同じです。",
    },
    {
      type: "text",
      content:
        "メッセージは指揮官/煽動者の能力と同じ内容となるため、メッセージを見た人からは、指揮官/煽動者/騙狐のどの能力なのか判断できません。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターを指差した。",
    },
  ],
  nightfox: [
    {
      type: "text",
      content: "襲撃されると、襲撃者の恋絆が除去され、狐憑きを付与します。",
    },
    {
      type: "text",
      content:
        "人狼による襲撃、一匹狼/暴狼による単独襲撃のみが対象です。マタギや強面の男性による襲撃は含まれません。",
    },
    {
      type: "text",
      content: "妖狐と同様襲撃耐性があるため、襲撃されても夜狐は死亡しません。",
    },
    {
      type: "text",
      content:
        "夜狐が既に死亡している場合や、夜狐が護衛されている場合は恋絆の除去や狐憑きの付与は行われません。",
    },
    {
      type: "text",
      content: "また、襲撃者に恋絆が付与されている場合も、恋絆の除去や狐憑きの付与は行われません。",
    },
    {
      type: "text",
      content: "狐憑きを付与すると、襲撃された夜狐と襲撃者に専用メッセージが表示されます。",
    },
    {
      type: "text",
      content: "他は妖狐と同じです。徘徊も可能です。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターに取り憑いた。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "あなたは、[01楽]楽天家 ゲルトに取り憑かれた。",
    },
  ],
  telefox: [
    {
      type: "text",
      content: "毎晩、妖狐1名（自分を含む）を指定してステータス「念力」を付与できます。",
    },
    {
      type: "text",
      content: "念力を付与すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "徘徊能力はありません。",
    },
    {
      type: "text",
      content: "他は妖狐と同じです。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターに念力を付与した。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "あなたは、念力を付与された。",
    },
  ],
  immoral: [
    {
      type: "text",
      content: "以下に挙げる点以外は狂人と同じです。",
    },
    {
      type: "text",
      content:
        "妖狐系役職が誰なのかを知ることができます。（妖狐系役職からは自分が背徳者であることはわかりません）",
    },
    {
      type: "text",
      content:
        "妖狐系役職が全滅すると、後追いで死亡します。（陣営変化している場合は後追いしません）",
    },
    {
      type: "text",
      content: "念話を聞くことができます（話せるのは仙狐と念力が付与されている人のみです）。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[02長]村長 ヴァルターは、妖狐の後を追い、いなくなってしまった。",
    },
  ],
  onmyoji: [
    {
      type: "text",
      content:
        "妖狐系役職が誰なのかを知ることができます。（妖狐系役職からは自分が陰陽師であることはわかりません）",
    },
    {
      type: "text",
      content:
        "妖狐系役職が全滅すると、後追いで死亡します。（陣営変化している場合は後追いしません）",
    },
    {
      type: "text",
      content: "念話を聞くことができます（話せるのは仙狐と念力が付与されている人のみです）。",
    },
    {
      type: "text",
      content: "蘇生者の妖狐陣営版で、以下に挙げる点以外は蘇生者と同じです。",
    },
    {
      type: "text",
      content: "対象を役職「妖狐」として蘇生させます。",
    },
    {
      type: "text",
      content:
        "対象が同棲者の場合は蘇生が失敗し、対象は死亡したままとなります（蘇生メッセージも表示されません）。",
    },
    {
      type: "text",
      content: "既に対象が蘇生済みの場合は対象の役職は変化しません。",
    },
    {
      type: "text",
      content: "蘇生メッセージは蘇生者/死霊術師/海王者による蘇生と同一となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "不思議なことに、[01楽]楽天家 ゲルトが生き返った。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[02長]村長 ヴァルターは、妖狐の後を追い、いなくなってしまった。",
    },
  ],
  curser: [
    {
      type: "text",
      content:
        "妖狐系役職が誰なのかを知ることができます。（妖狐系役職からは自分が呪縛者であることはわかりません）",
    },
    {
      type: "text",
      content:
        "妖狐系役職が全滅すると、後追いで死亡します。（陣営変化している場合は後追いしません）",
    },
    {
      type: "text",
      content: "念話を聞くことができます（話せるのは仙狐と念力が付与されている人のみです）。",
    },
    {
      type: "text",
      content: "1回だけ1人を指定し、「呪縛符」状態にすることができます（対象には通知されません）。",
    },
    {
      type: "text",
      content: "能力行使すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "対象に選択できるのは、自分以外の生存者です。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽] 楽天家 ゲルトは、[02長] 村長 ヴァルターに呪縛符を貼り付けた。",
    },
  ],
  cursecounter: [
    {
      type: "text",
      content:
        "妖狐系役職が誰なのかを知ることができます。（妖狐系役職からは自分が反呪者であることはわかりません）",
    },
    {
      type: "text",
      content:
        "妖狐系役職が全滅すると、後追いで死亡します。（陣営変化している場合は後追いしません）",
    },
    {
      type: "text",
      content: "念話を聞くことができます（話せるのは仙狐と念力が付与されている人のみです）。",
    },
    {
      type: "text",
      content: "1回だけ1人を指定し、「反呪符」状態にすることができます（対象には通知されません）。",
    },
    {
      type: "text",
      content: "能力行使すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "対象に選択できるのは、自分以外の生存者です。",
    },
    {
      type: "text",
      content: "「対象なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽] 楽天家 ゲルトは、[02長] 村長 ヴァルターに反呪符を貼り付けた。",
    },
  ],
  bomber: [
    {
      type: "text",
      content:
        "2日目以降、全日を通して1回だけ、生存者がいる任意の部屋にその晩限りの爆弾を設置することができます。",
    },
    {
      type: "text",
      content: "爆弾を設置すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "爆弾が設置された部屋を誰かが通過した場合、通過した人と設置された部屋の人が無惨な死体となって発見されます。",
    },
    {
      type: "text",
      content:
        "爆弾を設置した晩に、爆弾魔、設置された部屋の人、通過した人のいずれが死亡しても、通過さえしていれば爆弾は作動します。",
    },
    {
      type: "text",
      content:
        "例外として、冤罪者の足音は爆弾の処理後に発生するため、冤罪者の立てた足音では爆弾が作動しません。",
    },
    {
      type: "text",
      content: "ただし、爆弾魔が突然死していた場合は爆弾は無効になります。",
    },
    {
      type: "text",
      content:
        "爆弾を設置したのに爆弾が作動しなかった場合、不完全燃焼で自身が自爆し死亡し、無残な死体として発見されます。",
    },
    {
      type: "text",
      content:
        "爆弾を設置しないままエピローグを迎えると、不完全燃焼で自身が自爆し死亡し、無残な死体として発見されます。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "人狼に襲撃されても死亡しません。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターの部屋に爆弾を設置した。",
    },
  ],
  owl: [
    {
      type: "text",
      content:
        "この役職が含まれていると、1日目開始時点で「この村には強力な聴力を持つ者がいるようだ。」と表示されます。",
    },
    {
      type: "text",
      content:
        "人狼の囁き、共鳴発言、恋人発言、念話など役職専用の会話を会話種別や誰が発言したかがわからない状態で覗くことができます。",
    },
    {
      type: "text",
      content: "人狼の襲撃メッセージも覗けるため、襲撃先も知ることができます。",
    },
    {
      type: "text",
      content: "愉快犯陣営で、占霊判定はいずれも人間となります。また、人狼の襲撃で死亡します。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "text",
      content:
        "進行中のみ、発言抽出が特殊仕様となります。\n発言種別について、共鳴発言、囁き、念話、恋人発言のいずれかを選択し、かつ全員を表示（もしくは全員を未選択）にすると、共鳴発言、囁き、念話、恋人発言全てを選択したものとして抽出が行われます（どの発言種別であったか判明してしまうのを防止するため）。",
    },
    {
      type: "text",
      content:
        "発言種別について、共鳴発言、囁き、念話、恋人発言のいずれかを選択し、かつ一部参加者のみ表示にすると、共鳴発言、囁き、念話、恋人発言全てを選択しなかったものとして抽出が行われます（誰が発言したか判明してしまうのを防止するため）。",
    },
  ],
  fruitsbasket: [
    {
      type: "text",
      content:
        "2日目以降、全日を通して1回だけ「フルーツバスケット！」と叫び、全員の部屋をシャッフルすることができます。",
    },
    {
      type: "text",
      content:
        "能力発動時、「XXは「フルーツバスケット！」と叫んだ。」と表記されるため、果実籠であることが公表されます。",
    },
    {
      type: "text",
      content:
        "部屋の移動は日付更新処理の最後に行われるため、他能力や足音は移動前のものがそのまま適用されます。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "[01楽]楽天家 ゲルトは突然「フルーツバスケット！」と叫んだ。\nなんと、全員の部屋がシャッフルされてしまった。\n（以下略）",
    },
  ],
  lonewolf: [
    {
      type: "text",
      content:
        "2日目以降、毎晩一人を襲撃することができます。\n襲撃すると、襲撃者の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "ただし、一匹狼同士で襲撃が発生し、自身が死亡した場合は、襲撃が実行されます。",
    },
    {
      type: "text",
      content: "村の設定で連続襲撃なしに設定されていても毎日襲撃することができます。",
    },
    {
      type: "text",
      content: "「襲撃なし」が可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "襲撃成功失敗に関わる処理は人狼の襲撃と同様です。",
    },
    {
      type: "text",
      content: "一匹狼同士で誰が一匹狼であるかを知ることはできません。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
  ],
  rainbow: [
    {
      type: "text",
      content: "毎晩生存者一人を選択し、翌日1日間文字色を虹色にすることができます。",
    },
    {
      type: "text",
      content: "虹色に塗ると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "自分を選択することもできますし、指定なしも可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content:
        "更新時に「（虹色にされた人）は、虹色になった。」という公開メッセージが表示されます。",
    },
    {
      type: "text",
      content: "梟から地獄耳で見た発言は虹色になりません。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、虹色になった。",
    },
  ],
  loudspeaker: [
    {
      type: "text",
      content: "毎晩生存者一人を選択し、翌日1日間発言を大文字にすることができます。",
    },
    {
      type: "text",
      content: "拡声器を渡すと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "自分を選択することもできますし、指定なしも可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content:
        "更新時に「（拡声器を渡された人）の部屋に拡声器が置かれていた。」という公開メッセージが表示されます。",
    },
    {
      type: "text",
      content: "梟から地獄耳で見た発言は大声になりません。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトの部屋に拡声器が置かれていた。",
    },
  ],
  tatsuya: [
    {
      type: "text",
      content:
        "毎晩生存者一人を選択し、翌日1日間発言を濁点付き（かつ大文字＆太字）にすることができます。",
    },
    {
      type: "text",
      content: "濁点をつけると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "自分を選択することもできますし、指定なしも可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content:
        "更新時に「（濁点をつけられた人）は、叫ばずにはいられない！」という公開メッセージが表示されます。",
    },
    {
      type: "text",
      content:
        "虹職人や拡声者と異なり、独り言や秘話、梟から地獄耳で見た発言も含めて全てが濁点になります。",
    },
    {
      type: "text",
      content: "文字装飾やランダムタグも濁点になるため、使用できません。",
    },
    {
      type: "text",
      content:
        "同時に濁点、道化、伝説の殺し屋状態になった場合は、伝説の殺し屋→道化師→濁点の順に付与されます。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、叫ばずにはいられない！",
    },
  ],
  clown: [
    {
      type: "text",
      content:
        "毎晩生存者一人を選択し、翌日1日間、発言各行の語尾にトランプの絵文字を付与することができます。",
    },
    {
      type: "text",
      content: "能力を行使すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "自分を選択することもできますし、指定なしも可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "濁点者同様、独り言や秘話、梟から地獄耳で見た発言も含め付与されます。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "text",
      content:
        "同時に濁点、道化、伝説の殺し屋状態になった場合は、伝説の殺し屋→道化師→濁点の順に付与されます。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、道化を演じたくなった。",
    },
  ],
  legendassassin: [
    {
      type: "text",
      content:
        "毎晩生存者一人を選択し、翌日1日間、発言各行にプロの殺し屋特有の語尾を付与することができます。",
    },
    {
      type: "text",
      content: "能力を行使すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "自分を選択することもできますし、指定なしも可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content: "濁点者同様、独り言や秘話、梟から地獄耳で見た発言も含め付与されます。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "text",
      content:
        "同時に濁点、道化、伝説の殺し屋状態になった場合は、伝説の殺し屋→道化師→濁点の順に付与されます。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトに、プロとしての意識が芽生えた──────",
    },
  ],
  translator: [
    {
      type: "text",
      content: "毎晩生存者一人を選択し、翌日1日間発言を再翻訳状態にすることができます。",
    },
    {
      type: "text",
      content:
        "再翻訳状態になると、通常発言のみ、一度他言語（約100言語からランダム選択）に翻訳後、再度日本語に翻訳された内容で発言されます。",
    },
    {
      type: "text",
      content:
        "再翻訳状態にすると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "自分を選択することもできますし、指定なしも可能です。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content:
        "更新時に「（再翻訳状態になった人）が突然他国の言葉しか話せなくなり、有識者に翻訳してもらうことになった。」という公開メッセージが表示されます。",
    },
    {
      type: "text",
      content: "再翻訳後に文字装飾やランダムの処理を行うため、実質使用できません。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "[01楽]楽天家 ゲルトが突然他国の言葉しか話せなくなり、有識者に翻訳してもらうことになった。",
    },
  ],
  truck: [
    {
      type: "text",
      content: "2日目以降に一度だけ、対象を強制的に転生させることができます。",
    },
    {
      type: "text",
      content: "転生させると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "自分の部屋から見て直線上の部屋のみ強制転生対象とすることができます。",
    },
    {
      type: "text",
      content: "「対象なし」が可能で、日付更新時のセット先は「対象なし」です。",
    },
    {
      type: "text",
      content:
        "転生した場合、「〜の部屋に異世界転生トラックが突っ込んだ。〜は転生してしまった。」と公開メッセージが表示されます。",
    },
    {
      type: "text",
      content:
        "以下に当てはまる場合、転生せず、メッセージも表示されません。\n対象が死亡していた場合",
    },
    {
      type: "text",
      content: "対象が同棲者で、生存している相方の部屋に行っていて不在だった場合",
    },
    {
      type: "text",
      content:
        "対象が生存している同棲者で、生存している相方が対象の部屋に来ていた場合、相方の同棲者も転生します（メッセージも表示）。",
    },
    {
      type: "text",
      content:
        "転生した場合、対象は一度襲撃死（護衛・襲撃耐性無効）し、同時に転生者と同様、ランダム役職で復活します。",
    },
    {
      type: "text",
      content: "選ばれる役職候補については、転生を参照ください。",
    },
    {
      type: "text",
      content:
        "強制転生以降の処理順で行うはずだった能力行使（例：フルーツバスケット）も、転生で違う役職になった場合、能力行使されなくなります。",
    },
    {
      type: "text",
      content:
        "絶対人狼や勇者として転生した場合、絶対人狼や勇者である旨のメッセージが表示されます。",
    },
    {
      type: "text",
      content:
        "梟がこれまで存在しない村で梟として転生した場合、梟が存在する旨のメッセージが表示されます。",
    },
    {
      type: "text",
      content:
        "この梟のメッセージは全員が転生した後に表示されるため、最後に転生した人が梟というわけではありません（転生した人のいずれかが梟）。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "[01楽]楽天家 ゲルトの部屋に異世界転生トラックが突っ込んだ。\n[01楽]楽天家 ゲルトは、転生してしまった。",
    },
  ],
  runawaytruck: [
    {
      type: "text",
      content: "初期配役では編成されず、転生や強制転生でしか出現しません。",
    },
    {
      type: "text",
      content:
        "2日目以降に一度だけ、自身の部屋から直線上の部屋の全生存者（上下左右全員）を強制的に転生させることができます。",
    },
    {
      type: "text",
      content:
        "「対象なし」が可能で、日付更新時のセット先は「対象なし」です（能力行使時は自分自身を指定）。",
    },
    {
      type: "text",
      content:
        "転生した場合、それぞれの対象に、「〜の部屋に異世界転生トラックが突っ込んだ。〜は転生してしまった。」と公開メッセージが表示されます。",
    },
    {
      type: "text",
      content: "各対象に対しての転生処理はトラックと同様です。",
    },
    {
      type: "text",
      content: "人狼に襲撃されても死亡しません。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content:
        "[01楽]楽天家 ゲルトの部屋に異世界転生トラックが突っ込んだ。\n[01楽]楽天家 ゲルトは、転生してしまった。",
    },
  ],
  baba: [
    {
      type: "text",
      content: "この役職のままエピローグを迎えると、必ず敗北します。",
    },
    {
      type: "text",
      content: "対象を選択し、その相手と役職を交換することができます。",
    },
    {
      type: "text",
      content: "役職交換すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content:
        "選べる対象は、ダミー以外かつ一度もババになったことがない生存者です。\n他のババおよびそのババと役職交換してババになった人も選べません。",
    },
    {
      type: "text",
      content: "「対象なし」が可能で、日付更新時のセット先は「対象なし」です。",
    },
    {
      type: "text",
      content:
        "ババのまま死亡したり選べる対象がいなくなった場合、役職交換できないため、必ず敗北します。",
    },
    {
      type: "text",
      content: "以下に当てはまる場合、役職交換に失敗します。\n対象が死亡していた場合",
    },
    {
      type: "text",
      content: "対象が同棲者、絶対人狼、勇者の場合",
    },
    {
      type: "text",
      content: "役職交換に成功しても失敗しても、公開メッセージは表示されません。",
    },
    {
      type: "text",
      content:
        "役職交換に成功しても、結果として自分がババになっている可能性があります。\n例. 複数のババが同じ対象と役職交換した結果、ババとババの交換になった",
    },
    {
      type: "text",
      content: "例. 強制転生でババになった対象と役職を交換した",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターにババを押し付けた。",
    },
  ],
  winner: [
    {
      type: "text",
      content: "この役職のままエピローグを迎えると、必ず勝利します。",
    },
    {
      type: "text",
      content: "対象を選択し、その相手と役職を交換することができます。",
    },
    {
      type: "text",
      content: "役職交換すると、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "選べる対象は、下記を除く生存者です。\nダミー",
    },
    {
      type: "text",
      content:
        "当選者になったことがある人（他の当選者およびその人が交換して当選者になった人も選べません）",
    },
    {
      type: "text",
      content: "絶対人狼、勇者",
    },
    {
      type: "text",
      content: "「対象なし」はありません。必ず対象を選択することになります。",
    },
    {
      type: "text",
      content: "日付更新時のセット先はランダムです。",
    },
    {
      type: "text",
      content:
        "当選者のまま死亡したり選べる対象がいなくなった場合、役職交換できないため、必ず勝利します。",
    },
    {
      type: "text",
      content: "以下に当てはまる場合、役職交換に失敗します。\n対象が死亡していた",
    },
    {
      type: "text",
      content: "対象が同棲者、絶対人狼、勇者の場合",
    },
    {
      type: "text",
      content: "役職交換に成功しても失敗しても、公開メッセージは表示されません。",
    },
    {
      type: "text",
      content:
        "役職交換に成功しても、結果として自分が当選者になっている可能性があります。\n例. 複数の当選者が同じ対象と役職交換した結果、当選者と当選者の交換になった",
    },
    {
      type: "text",
      content: "例. 強制転生で当選者になった対象と役職を交換した",
    },
    {
      type: "message",
      messageType: "message-private-system",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターに当選権利を譲った。",
    },
  ],
  thiefcat: [
    {
      type: "text",
      content:
        "1回だけ1人を指定し、対象から他者への恋絆を消去したうえで自分への恋絆を付与することができます。",
    },
    {
      type: "text",
      content: "心を盗むと、自分の部屋から対象の部屋に向かう際通過した部屋に足音が響きます。",
    },
    {
      type: "text",
      content: "同棲者同士の恋絆は消去することができません（他の恋絆のみが消去されます）。",
    },
    {
      type: "text",
      content: "他者から対象への恋絆は消去されません。\n例. 恋絆は以下のようになります。",
    },
    {
      type: "text",
      content: "前：A ⇄ 対象 泥棒猫",
    },
    {
      type: "text",
      content: "後：A → 対象 → 泥棒猫",
    },
    {
      type: "text",
      content: "自分から対象への恋絆は付与されません。",
    },
    {
      type: "text",
      content:
        "以下に当てはまる場合、失敗となり、自分への恋絆は付与されません。（再使用もできません）\n対象が死亡していた場合",
    },
    {
      type: "text",
      content: "対象に恋絆が付与されていない場合",
    },
    {
      type: "text",
      content: "※対象が同棲者で、相方同棲者への恋絆しか存在しない場合も失敗",
    },
    {
      type: "text",
      content: "失敗した場合は対象には能力を使用した旨が通知されません。",
    },
    {
      type: "text",
      content: "複数の泥棒猫が同時に能力を使用した場合、恋泥棒同士の処理順はランダムです。",
    },
    {
      type: "text",
      content: "日付更新時のセット先は「なし」です。",
    },
    {
      type: "text",
      content:
        "能力を使用しないままエピローグを迎えると、禁断症状で自害し、無残な死体として発見されます。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターの心を盗んだ。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content:
        "[02長]村長 ヴァルターは、[01楽]楽天家 ゲルトに心を盗まれ、これまでの相手を忘れて恋をしてしまった。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content:
        "[01楽]楽天家 ゲルトは、[02長]村長 ヴァルターの心を盗もうとしたが、[02長]村長 ヴァルターは恋をしていなかった。",
    },
  ],
  pushpin: [
    {
      type: "text",
      content: "画鋲の部屋を通過した人は画鋲を踏んでしまい雑魚死（無惨な死体扱い）します。",
    },
    {
      type: "text",
      content: "画鋲処理時点で画鋲が死亡していれば不発となります。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "text",
      content: "画鋲は、踏んだ人を認知できます。",
    },
    {
      type: "text",
      content: "画鋲を踏んだ人は、画鋲で死亡したことを認知できません。",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、画鋲を踏んでしまい、即死した。",
    },
  ],
  drawers: [
    {
      type: "text",
      content: "箪笥の部屋を通過した人は小指をぶつけてしまい雑魚死（無惨な死体扱い）します。",
    },
    {
      type: "text",
      content: "箪笥処理時点で箪笥が死亡していれば不発となります。",
    },
    {
      type: "text",
      content: "最後まで生存していると追加勝利となります。",
    },
    {
      type: "text",
      content: "箪笥は、小指をぶつけた人を認知できません。",
    },
    {
      type: "text",
      content:
        "小指をぶつけた人は、箪笥で死亡したことを認知できます。（誰が箪笥であるかは認知できません）",
    },
    {
      type: "message",
      messageType: "message-private-ability",
      content: "[01楽]楽天家 ゲルトは、箪笥に小指をぶつけ、即死した。",
    },
  ],
  royalty: [
    {
      type: "text",
      content: "王族に投票した人は開票後に「不敬」が付与されます。",
    },
    {
      type: "text",
      content: "その投票で王族が処刑されても付与されます。",
    },
    {
      type: "text",
      content: "その投票で処刑された人には付与されません。",
    },
    {
      type: "text",
      content:
        "不敬が付与される際、メッセージは表示されないため、投票した人は不敬が付与されていることを知ることはできません。",
    },
  ],
  revolutionary: [
    {
      type: "text",
      content: "1回だけ、革命を宣言することができます。",
    },
    {
      type: "text",
      content:
        "革命を宣言すると、次の日の投票が少数決となり、1票以上で最も票数の少ない人が処刑されます。",
    },
    {
      type: "text",
      content:
        "例. 1日目に能力セット→2日目に革命宣言→2日目にセットした投票が3日目に開示される際、少数決となる",
    },
    {
      type: "text",
      content:
        "少数決となる日の処刑時点で革命者が死亡していた場合革命は失敗し、通常通り多数決となります。",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "[01楽]楽天家 ゲルトは、革命を宣言した！",
    },
    {
      type: "message",
      messageType: "message-public-system",
      content: "革命が成功し、本日は少数票の者を処刑することとなった。",
    },
    {
      type: "text",
      content: "村人陣営\n直感者：導師の勝敗判定陣営版",
    },
    {
      type: "text",
      content: "幽霊：2日目に死亡する。霊界から投票できる",
    },
    {
      type: "text",
      content: "ヒステリー：同じ人が3回自分の部屋で足音を鳴らすと鳴らした人が死亡する",
    },
    {
      type: "text",
      content: "目撃者：自分の部屋を通過した人の役職を知ることができる",
    },
    {
      type: "text",
      content: "潔癖症：自分の部屋で足音が響いたら死亡",
    },
    {
      type: "text",
      content: "塗装工：任意の部屋にペンキを塗ることができ、その部屋の通過者が朝に公表される",
    },
    {
      type: "text",
      content: "聖母：一度だけ自分の命を犠牲にして任意の人を生き返らせることができる",
    },
    {
      type: "text",
      content: "夜逃屋：一度だけ、空き部屋に移動できる（永続）",
    },
    {
      type: "text",
      content: "イタコ：墓下会話を、誰が発言したかわからない状態で覗くことができる",
    },
    {
      type: "text",
      content: "侍：護衛能力。護衛成功した場合襲撃者と相打ちで両方死亡する。",
    },
    {
      type: "text",
      content: "反撃者：襲撃されると自身が襲撃死、襲撃者が反撃死する",
    },
    {
      type: "text",
      content:
        "マンボウ：自分に能力を使用されたり自分の部屋で足音が鳴ると死亡するが、3回までマンボウとして生き返ることができる",
    },
    {
      type: "text",
      content:
        "名探偵：1回だけ一人を指定して人狼系もしくは妖狐系だった場合翌日公表することができる。外れていたら死ぬ。",
    },
    {
      type: "text",
      content: "逆張りオタク：自分が投票した人が死亡すると自分も死亡する",
    },
    {
      type: "text",
      content: "波動者：一度だけ、対象のステータスをすべて消せる",
    },
    {
      type: "text",
      content: "愚者：思い込み賢者。編成されている役職からランダムで結果が得られる",
    },
    {
      type: "text",
      content: "音波者：足音を鳴らした人の役職構成を知ることができる",
    },
    {
      type: "text",
      content: "ゴーレム：2回まで「瞑想」を使用可能。使用中は自身に護衛効果が付き、得票数が0になる",
    },
    {
      type: "text",
      content: "聖痕者：絶対人狼のように、出現すると聖痕者が誰であるか公開される",
    },
    {
      type: "text",
      content: "大魔道士：転生でしか出現しない。賢者と導師の能力を持つ",
    },
    {
      type: "text",
      content:
        "開示者：一度だけ、翌日処刑された投票の陣営内訳（勝敗判定陣営でなく役職の陣営）を公開できる",
    },
    {
      type: "text",
      content:
        "倍知者：4日目以降毎日、日付の倍数の部屋にいる生存者の役職構成を知ることができる（例えば4日目なら4,8,12,..の部屋の生存者）",
    },
    {
      type: "text",
      content:
        "鉄槌者：一度だけ、3x3範囲の「役職の所属する陣営と勝敗判定陣営が変わっている人」を襲撃死させる",
    },
    {
      type: "text",
      content: "脈々者：毎日、現在の村人カウント数がわかる",
    },
    {
      type: "text",
      content: "無罪者：一度だけ、指定した人の得票数を0にできる",
    },
    {
      type: "text",
      content: "人狼陣営\n鋭狼：襲撃失敗時に襲撃対象の役職を知ることができる",
    },
    {
      type: "text",
      content: "冥狼：墓下会話を、誰が発言したかわからない状態で覗くことができる狼",
    },
    {
      type: "text",
      content: "鎌鼬：一度だけ足音なしで襲撃できる",
    },
    {
      type: "text",
      content: "鰐狼：襲撃耐性がある対象しか噛めない狼",
    },
    {
      type: "text",
      content: "牙狼：襲撃耐性を貫通する。護衛は有効",
    },
    {
      type: "text",
      content: "覚醒狂人：覚醒前は能力なしだが、自分の部屋で足音が響くとC国狂人になる狂人",
    },
    {
      type: "text",
      content: "？？？：一度だけ、対象の恋絆、狐憑き、信念を除去し、狂気を付与して蘇生させる",
    },
    {
      type: "text",
      content: "尸術師：保険屋の人狼陣営版。狂気を付与する",
    },
    {
      type: "text",
      content: "消音者：一度だけ、翌日の足音を全て聞こえない状態にすることができる（黒箱の足音版）",
    },
    {
      type: "text",
      content: "爆弾岩：無惨死すると自分を中心に3*3の人が爆死する",
    },
    {
      type: "text",
      content: "共犯者：人狼陣営の共鳴者",
    },
    {
      type: "text",
      content: "囁狂人：囁き発言ができるが囁き発言が見えない狂人",
    },
    {
      type: "text",
      content: "？？？：保険者の黙狼蘇生版",
    },
    {
      type: "text",
      content: "悪徳弁護士：弁護士の人狼陣営版",
    },
    {
      type: "text",
      content: "反復者：昨晩響いた足音を選び、翌日同じ足音を鳴らせる",
    },
    {
      type: "text",
      content: "予告者：「次回、xx死す」を表示し、翌日に貫通襲撃死させられる",
    },
    {
      type: "text",
      content: "火災報知器：リア充や爆弾魔が生存している場合、朝に専用のメッセージが表示される",
    },
    {
      type: "text",
      content: "半狼人：申し子の人狼陣営版",
    },
    {
      type: "text",
      content: "恋人陣営\n居候：毎晩滞在先を選べる。同棲者とほぼ同じ。",
    },
    {
      type: "text",
      content:
        "？？？：一度だけ、対象の狐憑き、狂気、信念を除去し、自身への恋絆を付与して蘇生させる",
    },
    {
      type: "text",
      content: "思春期：申し子の恋人陣営版",
    },
    {
      type: "text",
      content: "妖狐陣営\n妲己：死亡時に指定した対象の役職で転生できる。転生時に狐憑き状態になる",
    },
    {
      type: "text",
      content: "縛狐：自身の死亡時に能力で指定していた人を道連れにする妖狐",
    },
    {
      type: "text",
      content: "叡狐：足音を鳴らした部屋の役職構成を占える妖狐",
    },
    {
      type: "text",
      content: "浮狐：爆死・罠死・雑魚死しない",
    },
    {
      type: "text",
      content: "狐火：一度だけ、対象の恋絆、狂気、信念を除去し、狐憑きを付与して蘇生させる",
    },
    {
      type: "text",
      content: "？？？：保険者の妖狐蘇生版",
    },
    {
      type: "text",
      content: "結界師：2回まで呪殺護衛できる。逆呪殺は護衛できない",
    },
    {
      type: "text",
      content: "仔狐：申し子の狐陣営版",
    },
    {
      type: "text",
      content: "天狗：一度だけ直線上の相手を選んで襲撃できる",
    },
    {
      type: "text",
      content: "憑狐：魅惑の人魚の狐憑き版",
    },
    {
      type: "text",
      content: "愉快犯陣営\n風見鶏：2日に1回、対象と同じ勝敗判定陣営になれる",
    },
    {
      type: "text",
      content:
        "予想屋：毎日、明日生存していそうな人を指定する。翌朝死亡していたら自分も死亡。生存で勝利",
    },
    {
      type: "text",
      content: "般若：恋人発言が聞こえる。自分が生存かつ恋人陣営の全滅が勝利条件",
    },
    {
      type: "text",
      content: "イケボ：発言がオシャレな文体になる",
    },
    {
      type: "text",
      content: "生肉：N日以内に襲撃死すると勝利",
    },
    {
      type: "text",
      content: "？？？：対象と同じ陣営の役職に転生できる",
    },
    {
      type: "text",
      content: "応援隊：1日目に指定した人が勝利していたら勝利",
    },
    {
      type: "text",
      content: "復讐者：1日目に指定した人が死亡かつ自分が最後まで生存していたら勝利",
    },
    {
      type: "text",
      content: "画竜点睛者：愉快犯に転生できる。転生しないと敗北",
    },
    {
      type: "text",
      content: "リア充絶対殺すマン：マタギの恋絆版",
    },
    {
      type: "text",
      content: "転勤族：毎日空き部屋に移動する",
    },
    {
      type: "text",
      content: "現場猫：指揮官の愉快犯陣営版",
    },
    {
      type: "text",
      content: "切磋琢磨：1日目に指定した人と自分が両方最後まで生存していたら勝利",
    },
    {
      type: "text",
      content: "後継者：王族が死亡していると、王族に役職変化する",
    },
    {
      type: "text",
      content:
        "迷惑系リア充：転生でしか出現しない。爆発すると周囲8部屋を蘇生＋強制転生させるリア充",
    },
    {
      type: "text",
      content: "海苔弁：発言装飾系で、指定した相手の発言を常にcw状態にする",
    },
    {
      type: "text",
      content: "魔法陣者：1回だけ魔法陣を設置でき、自分の部屋を通過した人を強制転生させる",
    },
    {
      type: "text",
      content: "マッドサイエンティスト：蘇生者の強制転生版",
    },
    {
      type: "text",
      content: "弾祭者：黒箱者に投票を隠された際生存していると、得票数上位5名が公開される",
    },
    {
      type: "text",
      content:
        "賭博者：相手を指定するとダイスを振ることができ、クリティカルなら襲撃し、ファンブルの場合自死する",
    },
  ],
};

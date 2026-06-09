type CampGroup = {
  campCode: string;
  campName: string;
  skills: { code: string; name: string }[];
};

export function CampSection({ campGroups }: { campGroups: CampGroup[] }) {
  return (
    <div>
      <CampTable campGroups={campGroups} />
      <EpilogueCondition />
      <PersonalWinCondition />
      <CampWinCondition />
    </div>
  );
}

function CampTable({ campGroups }: { campGroups: CampGroup[] }) {
  return (
    <div id="camp" className="overflow-x-auto">
      <h3 className="my-[10.5px] text-[14px] font-bold">陣営</h3>
      <table className="w-full border-collapse text-[12px]">
        <thead>
          <tr className="border-b border-[#464545]">
            <th className="p-[5px] text-left align-middle">陣営</th>
            <th className="p-[5px] text-left align-middle">陣営に所属する役職</th>
          </tr>
        </thead>
        <tbody>
          {campGroups.map((camp) => (
            <tr key={camp.campCode} className="border-b border-[#464545]">
              <td className="p-[5px] align-middle">{camp.campName}</td>
              <td className="p-[5px] align-middle">
                {camp.skills.map((skill, i) => (
                  <span key={skill.code}>
                    {i > 0 && <br />}
                    {skill.name}
                  </span>
                ))}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function EpilogueCondition() {
  return (
    <div id="epilogue-condition" className="overflow-x-auto">
      <h3 className="my-[10.5px] text-[14px] font-bold">終了条件</h3>
      <p className="mb-[10.5px]">
        以下のいずれかを満たすとエピローグを迎え、勝敗判定が行われます。
      </p>
      <ul className="mb-[10.5px] list-disc pl-[40px]">
        <li>夜明け時点で生存している人狼カウント数≧生存している人間カウント数</li>
        <li>夜明け時点で生存している人狼カウント数が0人かつ生存している人間カウント数が1人以上</li>
      </ul>
      <p className="mb-[10.5px]">
        ※ここでいう「人狼カウント」「人間カウント」の判定は「占霊判定、勝敗時のカウント」を参照してください。
      </p>
    </div>
  );
}

function PersonalWinCondition() {
  return (
    <div id="personal-wincondition" className="overflow-x-auto">
      <h3 className="my-[10.5px] text-[14px] font-bold">個人ごとの勝敗判定</h3>
      <table className="w-full border-collapse text-[12px]">
        <thead>
          <tr className="border-b border-[#464545]">
            <th className="p-[5px] text-left align-middle">
              個人ごとの勝敗判定（上にあるものほど優先度高）
            </th>
            <th className="p-[5px] text-left align-middle">勝利条件</th>
          </tr>
        </thead>
        <tbody>
          {[
            ["役職がババ", "必ず敗北"],
            ["役職が当選者", "必ず勝利"],
            ["ステータス「恋絆」が付与されている", "恋人陣営の勝利"],
            ["ステータス「狐憑き」が付与されている", "妖狐陣営の勝利"],
            ["ステータス「狂気」が付与されている", "人狼陣営の勝利"],
            ["ステータス「信念」が付与されている", "村人陣営の勝利"],
            ["役職が愉快犯陣営に属している", "最後まで生存"],
            ["それ以外", "役職が属する陣営の勝利"],
          ].map(([condition, result]) => (
            <tr key={condition} className="border-b border-[#464545]">
              <td className="p-[5px] align-middle">{condition}</td>
              <td className="p-[5px] align-middle">{result}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function CampWinCondition() {
  return (
    <div id="camp-wincondition" className="overflow-x-auto">
      <h3 className="my-[10.5px] text-[14px] font-bold">陣営の勝敗判定</h3>
      <table className="w-full border-collapse text-[12px]">
        <thead>
          <tr className="border-b border-[#464545]">
            <th colSpan={2} className="p-[5px] text-left align-middle">
              陣営の勝敗判定（同時に満たした場合は上にあるものほど優先度高）
            </th>
          </tr>
          <tr className="border-b border-[#464545]">
            <th className="p-[5px] text-left align-middle">陣営</th>
            <th className="p-[5px] text-left align-middle">勝利条件</th>
          </tr>
        </thead>
        <tbody>
          {[
            ["恋人陣営", "恋人陣営役職か恋絆を付与されている人が1名以上生存している"],
            ["妖狐陣営", "妖狐系役職が1名以上生存している"],
            ["人狼陣営", "夜明け時点で生存している人狼カウント数≧生存している人間カウント数"],
            [
              "村人陣営",
              "夜明け時点で生存している人狼カウント数が0人かつ生存している人間カウント数が1人以上",
            ],
            [
              "愉快犯陣営",
              "なし（個人ごとに追加勝利判定を行うため、愉快犯陣営自体が勝利することはない）",
            ],
          ].map(([camp, condition]) => (
            <tr key={camp} className="border-b border-[#464545]">
              <td className="p-[5px] align-middle">{camp}</td>
              <td className="p-[5px] align-middle">{condition}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

const ROOMS: { count: string; layout: string }[] = [
  { count: "8人(4x3)", layout: "＿部部＿\n部部部部\n＿部部＿" },
  { count: "9人(4x3)", layout: "＿部部＿\n部部部部\n＿部部部" },
  { count: "10人(4x3)", layout: "部部部＿\n部部部部\n＿部部部" },
  { count: "11人(5x3)", layout: "＿部部部＿\n部部部部部\n＿部部部＿" },
  { count: "12人(4x4)", layout: "＿部部＿\n部部部部\n部部部部\n＿部部＿" },
  { count: "13人(4x4)", layout: "＿部部＿\n部部部部\n部部部部\n＿部部部" },
  { count: "14人(5x4)", layout: "＿＿部部＿\n部部部部部\n部部部部部\n＿部部＿＿" },
  { count: "15人(5x4)", layout: "＿＿部部＿\n部部部部部\n部部部部部\n＿部部部＿" },
  { count: "16人(5x4)", layout: "＿部部部＿\n部部部部部\n部部部部部\n＿部部部＿" },
  { count: "17人(5x4)", layout: "＿部部部＿\n部部部部部\n部部部部部\n＿部部部部" },
  { count: "18人(6x4)", layout: "＿＿部部部＿\n部部部部部部\n部部部部部部\n＿部部部＿＿" },
  { count: "19人(6x4)", layout: "＿部部部部＿\n部部部部部部\n部部部部部部\n＿部部部＿＿" },
  { count: "20人(6x4)", layout: "＿部部部部＿\n部部部部部部\n部部部部部部\n＿部部部部＿" },
];

export function RoomSection() {
  return (
    <div id="room" className="overflow-x-auto">
      <p className="mb-[10.5px]">21名〜99名の部屋構成については管理者までお問い合わせください。</p>
      <table className="w-full border-collapse text-[12px]">
        <thead>
          <tr className="border-b border-border">
            <th className="p-[5px] text-left align-middle">人数</th>
            <th className="p-[5px] text-left align-middle">
              空き部屋の位置
              <br />
              (部：部屋 ＿：空き部屋)
            </th>
          </tr>
        </thead>
        <tbody>
          {ROOMS.map((room) => (
            <tr key={room.count} className="border-b border-border">
              <td className="p-[5px] align-middle">{room.count}</td>
              <td className="p-[5px] align-middle font-mono">
                {room.layout.split("\n").map((line, i) => (
                  <span key={i}>
                    {i > 0 && <br />}
                    {line}
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

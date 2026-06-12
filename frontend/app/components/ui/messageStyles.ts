/**
 * メッセージ種別 → 吹き出しの配色。村画面の発言ログとルール/役職ページの例文表示で共有する。
 */
export const MESSAGE_STYLES: Record<string, string> = {
  "message-private-seer": "bg-[#334033] border-[#34a865] text-[#eee]",
  "message-private-ability": "bg-[#333] border-[#ccc] text-[#eee]",
  "message-private-investigate": "bg-[#403333] border-[#a86534] text-[#eee]",
  "message-private-psychic": "bg-[#333340] border-[#3465a8] text-[#eee]",
  "message-public-system": "bg-transparent border-[#e3e3e3] text-white",
  "message-private-system": "bg-[#333] border-[#ccc] text-[#eee]",
  "message-private-werewolf": "bg-[#403333] border-[#a83434] text-[#eee]",
  "message-werewolf": "bg-[#f2aeae] border-[#f2aeae] text-[#333]",
  "message-private": "bg-transparent border-[#e3e3e3] text-white",
  "message-private-lover": "bg-[#403333] border-[#f9318f] text-[#eee]",
  "message-public": "bg-transparent border-[#e3e3e3] text-white",
  "message-private-fox": "bg-[#403333] border-[#c9c934] text-[#eee]",
  "message-normal": "bg-white border-white text-[#555]",
  "message-monologue": "bg-[#aaa] border-[#b5b5b5] text-[#333]",
  "message-secret": "bg-[#a9a] border-[#b5b5b5] text-[#333]",
  "message-mason": "bg-[#aef2ae] border-[#aef2ae] text-[#333]",
  "message-lover": "bg-[#f2cece] border-[#f2cece] text-[#c22]",
  "message-telepathy": "bg-[#f2f2ae] border-[#f2f2ae] text-[#c20]",
  "message-owl": "bg-[#aeaef2] border-[#aeaef2] text-[#333]",
  "message-grave": "bg-[#a9edf7] border-[#a9edf7] text-[#333]",
  "message-spectate": "bg-[#ffdea9] border-[#ffdea9] text-[#333]",
  "message-creator": "bg-transparent border-[#00bc8c] text-[#00bc8c]",
  "message-action": "bg-[#232355] border-[#e3e3e3] text-white",
  "message-announce": "bg-transparent border-[#ff2222] text-[#ff2222]",
};

export const DEFAULT_MESSAGE_STYLE = "bg-transparent border-[#e3e3e3] text-white";

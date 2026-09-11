#!/usr/bin/env bash
set -euo pipefail

# Claude Code Stop hook.
# 応答終了時、frontend の .ts/.tsx に未コミット変更があれば pnpm typecheck を実行し、
# 失敗していたら decision:block で続行を強制する（typecheck が red のまま
# 「既存の問題」として放置して終了する、を機械的に防ぐ）。
#
# - 同一 diff で成功済みなら再実行しない（会話ターンごとの無駄な tsc を避ける）
# - 連続 MAX_BLOCKS 回 block しても直らなければ、警告を出して停止を許可し、
#   同一 diff の間は再度 block しない（無限ループ・毎ターン nag の防止）

MAX_BLOCKS=2

INPUT=$(cat)

PROJECT_ROOT="${CLAUDE_PROJECT_DIR:-}"
[[ -z "$PROJECT_ROOT" ]] && PROJECT_ROOT=$(echo "$INPUT" | jq -r '.cwd // empty')
[[ -z "$PROJECT_ROOT" ]] && exit 0

FRONTEND_DIR="$PROJECT_ROOT/frontend"
[[ -d "$FRONTEND_DIR" && -x "$FRONTEND_DIR/node_modules/.bin/tsc" ]] || exit 0

cd "$PROJECT_ROOT"

# frontend の .ts/.tsx に変更（追跡済み変更 or 未追跡新規）がなければ何もしない
CHANGED=$(git status --porcelain -- frontend 2>/dev/null | grep -E '\.tsx?$' || true)
[[ -z "$CHANGED" ]] && exit 0

STATE_DIR="$PROJECT_ROOT/.context/typecheck-hook"
mkdir -p "$STATE_DIR"

# 現在の frontend 変更内容のハッシュ（未追跡ファイルの内容も含める）
UNTRACKED=$(git ls-files --others --exclude-standard -- frontend | grep -E '\.tsx?$' || true)
HASH=$(
  {
    git diff HEAD -- frontend 2>/dev/null || true
    if [[ -n "$UNTRACKED" ]]; then
      # shellcheck disable=SC2086
      cat $UNTRACKED 2>/dev/null || true
    fi
  } | md5sum | cut -d' ' -f1
)

PASS_FILE="$STATE_DIR/last-pass-hash"
GAVE_UP_FILE="$STATE_DIR/gave-up-hash"
BLOCK_FILE="$STATE_DIR/block-count"

# この diff で既に成功済み / 既に上限到達して諦め済みなら何もしない
[[ -f "$PASS_FILE" && "$(cat "$PASS_FILE")" == "$HASH" ]] && exit 0
[[ -f "$GAVE_UP_FILE" && "$(cat "$GAVE_UP_FILE")" == "$HASH" ]] && exit 0

BLOCKS=0
[[ -f "$BLOCK_FILE" ]] && BLOCKS=$(cat "$BLOCK_FILE")
[[ "$BLOCKS" =~ ^[0-9]+$ ]] || BLOCKS=0

cd "$FRONTEND_DIR"
LOG=$(mktemp)
if pnpm typecheck > "$LOG" 2>&1; then
  echo "$HASH" > "$PASS_FILE"
  rm -f "$BLOCK_FILE" "$GAVE_UP_FILE" "$LOG"
  exit 0
fi

ERRORS=$(grep -E 'error TS' "$LOG" | head -20 || true)
[[ -z "$ERRORS" ]] && ERRORS=$(tail -30 "$LOG")
rm -f "$LOG"

if [[ "$BLOCKS" -ge "$MAX_BLOCKS" ]]; then
  echo "$HASH" > "$GAVE_UP_FILE"
  rm -f "$BLOCK_FILE"
  jq -n --arg e "$ERRORS" \
    '{"systemMessage": ("[typecheck-stop-check] pnpm typecheck は依然失敗していますが、block 上限に達したため停止を許可しました。手動で確認してください。\n\n" + $e)}'
  exit 0
fi

echo $((BLOCKS + 1)) > "$BLOCK_FILE"
jq -n --arg e "$ERRORS" \
  '{"decision": "block", "reason": ("frontend の pnpm typecheck が失敗しています。終了する前に修正してください。今回の変更と無関係な既存の問題だと考える場合は、base ブランチで同コマンドが失敗する証拠を確認し、ユーザーに報告して判断を仰いでください（黙って放置しない）。\n\n" + $e)}'
exit 0

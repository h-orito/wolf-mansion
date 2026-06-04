#!/usr/bin/env bash
set -euo pipefail

# Claude Code PostToolUse hook (Edit|Write).
# 編集された .ts/.tsx/.js/.jsx/.css が frontend/ 配下なら、そのファイルに対して
# oxlint と oxfmt --check を実行し、違反があれば additionalContext で Claude に返す。
# monorepo 構成: frontend は frontend/ 配下、Claude の cwd はリポジトリ root。
# 検査のみ (自動書換なし)。state ディレクトリ (.context/oxlint-hook、gitignore 済) で
# 連続失敗を数え、無限ループを防ぐ。

MAX_RETRIES=3

INPUT=$(cat)

FILE_PATH=$(echo "$INPUT" | jq -r '.tool_input.file_path // empty')
[[ -z "$FILE_PATH" ]] && exit 0

# 対象拡張子以外はスキップ
case "$FILE_PATH" in
  *.ts | *.tsx | *.js | *.jsx | *.mjs | *.cjs | *.css) ;;
  *) exit 0 ;;
esac

PROJECT_ROOT=$(echo "$INPUT" | jq -r '.cwd // empty')
[[ -z "$PROJECT_ROOT" ]] && exit 0

FRONTEND_DIR="$PROJECT_ROOT/frontend"

# frontend/ 配下でなければ対象外
case "$FILE_PATH" in
  "$FRONTEND_DIR"/*) ;;
  *) exit 0 ;;
esac

REL_PATH="${FILE_PATH#"$FRONTEND_DIR"/}"

# 生成物 / 依存はスキップ
case "$REL_PATH" in
  node_modules/* | build/* | .react-router/*) exit 0 ;;
esac

STATE_DIR="$PROJECT_ROOT/.context/oxlint-hook"
mkdir -p "$STATE_DIR"

if command -v md5sum >/dev/null 2>&1; then
  FILE_HASH=$(echo -n "$REL_PATH" | md5sum | cut -d' ' -f1)
else
  FILE_HASH=$(echo -n "$REL_PATH" | md5 -q)
fi
STATE_FILE="$STATE_DIR/$FILE_HASH"

FAIL_COUNT=0
[[ -f "$STATE_FILE" ]] && FAIL_COUNT=$(cat "$STATE_FILE")
# state ファイルが数値でない場合に算術評価で異常終了しないようガード
[[ "$FAIL_COUNT" =~ ^[0-9]+$ ]] || FAIL_COUNT=0

cd "$FRONTEND_DIR"

OXLINT="./node_modules/.bin/oxlint"
OXFMT="./node_modules/.bin/oxfmt"
# バイナリが無い (未 install 等) 場合は何もしない
[[ -x "$OXLINT" && -x "$OXFMT" ]] || exit 0

strip() { sed 's/\x1b\[[0-9;]*m//g'; }

# oxlint は JS/TS 系のみ対象。CSS は oxfmt のみで検査する
case "$REL_PATH" in
  *.css) LINT_APPLICABLE=0 ;;
  *) LINT_APPLICABLE=1 ;;
esac

run_checks() {
  if [[ "$LINT_APPLICABLE" -eq 1 ]]; then
    LINT_OUT=$("$OXLINT" "$REL_PATH" 2>&1) && LINT_RC=0 || LINT_RC=$?
  else
    LINT_OUT=""
    LINT_RC=0
  fi
  FMT_OUT=$("$OXFMT" --check "$REL_PATH" 2>&1) && FMT_RC=0 || FMT_RC=$?
}

# リトライ上限: 連続 MAX_RETRIES 回失敗したら手動対応を促してスキップ
if [[ "$FAIL_COUNT" -ge "$MAX_RETRIES" ]]; then
  run_checks
  if [[ "$LINT_RC" -eq 0 && "$FMT_RC" -eq 0 ]]; then
    rm -f "$STATE_FILE"
  else
    jq -n '{"hookSpecificOutput": {"hookEventName": "PostToolUse", "additionalContext": "oxlint/oxfmt check skipped (max retries reached). Run pnpm lint / pnpm format in frontend/ manually."}}'
  fi
  exit 0
fi

run_checks

if [[ "$LINT_RC" -eq 0 && "$FMT_RC" -eq 0 ]]; then
  rm -f "$STATE_FILE"
  exit 0
fi

# ツールエラー (exit >= 2) は lint 違反と区別し、リトライにカウントしない
if [[ "$LINT_RC" -ge 2 || "$FMT_RC" -ge 2 ]]; then
  ERR=""
  [[ "$LINT_RC" -ge 2 ]] && ERR+="oxlint (exit $LINT_RC):"$'\n'"$(echo "$LINT_OUT" | strip | head -15)"$'\n'
  [[ "$FMT_RC" -ge 2 ]] && ERR+="oxfmt (exit $FMT_RC):"$'\n'"$(echo "$FMT_OUT" | strip | head -15)"$'\n'
  jq -n --arg err "$ERR" '{"hookSpecificOutput": {"hookEventName": "PostToolUse", "additionalContext": ("frontend lint hook: ツール実行エラー (lint 違反ではありません)。環境/設定を確認してください。\n\n" + $err)}}'
  exit 0
fi

MSG=""
[[ "$LINT_RC" -ne 0 ]] && MSG+="[oxlint]"$'\n'"$(echo "$LINT_OUT" | strip | head -30)"$'\n'
[[ "$FMT_RC" -ne 0 ]] && MSG+="[oxfmt --check] このファイルは未フォーマットです。pnpm format で整形してください。"$'\n'
[[ -z "$MSG" ]] && MSG="oxlint/oxfmt failed for $REL_PATH. Run pnpm lint / pnpm format:check in frontend/."

echo $((FAIL_COUNT + 1)) > "$STATE_FILE"
REMAINING=$((MAX_RETRIES - FAIL_COUNT - 1))

jq -n \
  --arg msg "$MSG" \
  --argjson remaining "$REMAINING" \
  '{
    hookSpecificOutput: {
      hookEventName: "PostToolUse",
      additionalContext: ("frontend lint/format issues found. Please fix (pnpm lint / pnpm format in frontend/).\nRemaining auto-check retries: " + ($remaining | tostring) + "\n\n" + $msg)
    }
  }'

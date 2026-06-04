#!/usr/bin/env bash
set -euo pipefail

# Claude Code PostToolUse hook (Edit|Write).
# 編集された .kt / .kts が backend/ 配下なら、該当ソースセットの ktlintCheck を実行し、
# 違反があれば additionalContext として Claude にフィードバックする。
# monorepo 構成: Gradle プロジェクトは backend/ 配下、Claude の cwd はリポジトリ root。
# state ディレクトリ (.context/ktlint-hook、gitignore 済) で連続失敗を数え、無限ループを防ぐ。

MAX_RETRIES=3

INPUT=$(cat)

FILE_PATH=$(echo "$INPUT" | jq -r '.tool_input.file_path // empty')
[[ -z "$FILE_PATH" ]] && exit 0

# .kt / .kts 以外はスキップ
[[ "$FILE_PATH" != *.kt && "$FILE_PATH" != *.kts ]] && exit 0

PROJECT_ROOT=$(echo "$INPUT" | jq -r '.cwd // empty')
[[ -z "$PROJECT_ROOT" ]] && exit 0

BACKEND_DIR="$PROJECT_ROOT/backend"

# backend/ 配下のファイルでなければ対象外 (frontend 等の .kt は無い想定だが defensive)
case "$FILE_PATH" in
  "$BACKEND_DIR"/*) ;;
  *) exit 0 ;;
esac

# backend/ からの相対パス
REL_PATH="${FILE_PATH#"$BACKEND_DIR"/}"

# state ディレクトリ (.context/ はリポジトリ全体で gitignore 済み)
STATE_DIR="$PROJECT_ROOT/.context/ktlint-hook"
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

# 対象ソースセットの check タスクを特定
if [[ "$REL_PATH" == src/main/* ]]; then
  GRADLE_TASK="ktlintMainSourceSetCheck"
elif [[ "$REL_PATH" == src/test/* ]]; then
  GRADLE_TASK="ktlintTestSourceSetCheck"
elif [[ "$REL_PATH" == *.kts ]]; then
  GRADLE_TASK="ktlintKotlinScriptCheck"
else
  rm -f "$STATE_FILE"
  exit 0
fi

# リトライ上限: 連続 MAX_RETRIES 回失敗したら手動対応を促してスキップ
if [[ "$FAIL_COUNT" -ge "$MAX_RETRIES" ]]; then
  cd "$BACKEND_DIR"
  if ./gradlew "$GRADLE_TASK" > /dev/null 2>&1; then
    rm -f "$STATE_FILE"
  else
    jq -n '{"hookSpecificOutput": {"hookEventName": "PostToolUse", "additionalContext": "ktlint check skipped (max retries reached). Run ./gradlew ktlintCheck in backend/ manually."}}'
  fi
  exit 0
fi

cd "$BACKEND_DIR"
./gradlew "$GRADLE_TASK" > /dev/null 2>&1 && CHECK_EXIT=0 || CHECK_EXIT=$?

if [[ "$CHECK_EXIT" -eq 0 ]]; then
  rm -f "$STATE_FILE"
  exit 0
fi

# 違反あり → レポートから内容を取得
REPORT_DIR="build/reports/ktlint/${GRADLE_TASK}"
REPORT_FILE=$(find "$REPORT_DIR" -maxdepth 1 -name '*.txt' 2>/dev/null | head -1)

VIOLATIONS=""
if [[ -n "$REPORT_FILE" && -f "$REPORT_FILE" ]]; then
  VIOLATIONS=$(sed 's/\x1b\[[0-9;]*m//g' "$REPORT_FILE" | grep -v '^$' | grep -v '^Summary' | head -30)
fi
[[ -z "$VIOLATIONS" ]] && VIOLATIONS="ktlintCheck failed for $GRADLE_TASK but no detailed report found. Run ./gradlew $GRADLE_TASK in backend/ to see details."

echo $((FAIL_COUNT + 1)) > "$STATE_FILE"
REMAINING=$((MAX_RETRIES - FAIL_COUNT - 1))

jq -n \
  --arg violations "$VIOLATIONS" \
  --argjson remaining "$REMAINING" \
  '{
    hookSpecificOutput: {
      hookEventName: "PostToolUse",
      additionalContext: ("ktlint violations found. Please fix them (run ./gradlew ktlintFormat in backend/ for auto-fixable ones).\nRemaining auto-check retries: " + ($remaining | tostring) + "\n\n" + $violations)
    }
  }'

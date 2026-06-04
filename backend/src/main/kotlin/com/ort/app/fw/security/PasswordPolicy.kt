package com.ort.app.fw.security

/**
 * パスワードの形式ポリシー (signup / change-password で共有)。
 *
 * 移行時に緩和: 3〜60文字 / 印字可能 ASCII (`0x21`–`0x7E`、英数 + 記号)。スペース・制御文字・マルチバイトは不可。
 * - 上限 60 の根拠: BCrypt は入力先頭 72 バイトのみ使用。ASCII 60 文字 = 60 バイトで安全に収まる
 * - login (`/api/v1/auth/login`) はこのポリシーを **適用しない** (緩和後パスワードでログイン不能になるため)
 * - クライアント (zod) とは Step 3.3/3.4 で同じ定数を共有する
 */
object PasswordPolicy {
    const val MIN_LENGTH = 3
    const val MAX_LENGTH = 60

    /** 印字可能 ASCII のみ (`0x21`–`0x7E`)。Java 正規表現の hex 範囲指定。長さの上下限は [MIN_LENGTH]/[MAX_LENGTH] で担保。 */
    const val PATTERN = "[\\x21-\\x7E]+"
}

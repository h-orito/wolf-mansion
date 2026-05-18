package com.ort.app.fw.exception

/**
 * 「機能としては存在を予定しているが、まだ実装していない」ケースを表す例外。
 * REST API では `501 Not Implemented` に変換される。
 *
 * 例: オリジナルキャラチップ村への multipart 画像アップロード入村 (Step 7 時点で未対応)。
 */
class WolfMansionNotImplementedException(message: String) : Exception(message)

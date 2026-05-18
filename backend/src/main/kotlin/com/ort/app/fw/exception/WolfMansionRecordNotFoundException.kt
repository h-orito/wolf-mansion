package com.ort.app.fw.exception

/**
 * REST API でレコードが見つからない (404) ことを表す例外。
 * `RestApiExceptionHandler` で `404 Not Found` に変換される。
 */
class WolfMansionRecordNotFoundException(message: String) : Exception(message)

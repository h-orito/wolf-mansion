package com.ort.app.fw.exception

/**
 * REST API の入力検証エラー。フィールド単位のエラーを保持し、
 * [RestApiExceptionHandler] が ProblemDetail の `fieldErrors` として返す。
 */
class WolfMansionValidationException(
    val fieldErrors: List<FieldErrorItem>,
) : RuntimeException(fieldErrors.joinToString(", ") { "${it.field}: ${it.message}" }) {
    data class FieldErrorItem(
        val field: String,
        val message: String,
    )
}

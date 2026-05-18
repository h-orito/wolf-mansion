package com.ort.app.fw.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// REST API 用の例外ハンドラ。
// `com.ort.app.api` 直下にはまだ Thymeleaf 時代の `@Controller` が並んでおり、そこに
// `WolfMansionBusinessException → 400 JSON` を適用するとブラウザ向け遷移が JSON エラーに
// 化けてしまう。Step 9 で Thymeleaf を削除するまでは REST 専用パッケージに限定する。
@RestControllerAdvice(basePackages = ["com.ort.app.api.v1", "com.ort.app.api.auth"])
class RestApiExceptionHandler {

    private val logger = LoggerFactory.getLogger(RestApiExceptionHandler::class.java)

    @io.swagger.v3.oas.annotations.media.Schema(description = "APIエラーレスポンス")
    data class ErrorResponse(
        @field:io.swagger.v3.oas.annotations.media.Schema(description = "エラーメッセージ")
        val message: String,
        @field:io.swagger.v3.oas.annotations.media.Schema(description = "バリデーションエラー詳細")
        val errors: List<FieldError> = emptyList(),
    ) {
        @io.swagger.v3.oas.annotations.media.Schema(description = "フィールド単位のバリデーションエラー")
        data class FieldError(
            @field:io.swagger.v3.oas.annotations.media.Schema(description = "フィールド名")
            val field: String,
            @field:io.swagger.v3.oas.annotations.media.Schema(description = "エラーメッセージ")
            val message: String,
        )
    }

    @ExceptionHandler(BadCredentialsException::class, AuthenticationException::class)
    fun handleAuth(e: Exception): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(e.message ?: "Unauthorized"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = e.bindingResult.fieldErrors.map {
            ErrorResponse.FieldError(it.field, it.defaultMessage ?: "invalid")
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse("validation failed", errors))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.warn("Bad request: {}", e.message)
        return ResponseEntity.badRequest().body(ErrorResponse(e.message ?: "Bad request"))
    }

    @ExceptionHandler(WolfMansionRecordNotFoundException::class)
    fun handleNotFound(e: WolfMansionRecordNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(e.message ?: "Not Found"))

    @ExceptionHandler(WolfMansionBusinessException::class)
    fun handleBusiness(e: WolfMansionBusinessException): ResponseEntity<ErrorResponse> {
        logger.info("Business rule violation: {}", e.message)
        return ResponseEntity.badRequest().body(ErrorResponse(e.message ?: "Bad request"))
    }

    @ExceptionHandler(WolfMansionNotImplementedException::class)
    fun handleNotImplemented(e: WolfMansionNotImplementedException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ErrorResponse(e.message ?: "Not Implemented"))
}

package com.ort.app.fw.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// REST API (com.ort.app.api 配下) 用の例外ハンドラ。
// 既存の Thymeleaf 用 `ExceptionControllerAdvice` とは別。
@RestControllerAdvice(basePackages = ["com.ort.app.api"])
class RestApiExceptionHandler {

    private val logger = LoggerFactory.getLogger(RestApiExceptionHandler::class.java)

    data class ErrorResponse(val message: String, val errors: List<FieldError> = emptyList()) {
        data class FieldError(val field: String, val message: String)
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
}

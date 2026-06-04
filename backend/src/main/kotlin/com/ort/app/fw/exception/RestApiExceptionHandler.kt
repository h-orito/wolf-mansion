package com.ort.app.fw.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * REST API (`@RestController`) 専用の例外ハンドラ。エラーを ProblemDetail (RFC 7807) に統一する。
 * 既存の SSR (`@Controller`) には適用されない (annotations 限定)。
 */
@RestControllerAdvice(annotations = [RestController::class])
class RestApiExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(WolfMansionAuthException::class)
    fun handleAuth(e: WolfMansionAuthException): ProblemDetail =
        problem(HttpStatus.UNAUTHORIZED, e.message ?: "認証に失敗しました", "authentication_failed")

    @ExceptionHandler(WolfMansionTooManyRequestsException::class)
    fun handleTooManyRequests(e: WolfMansionTooManyRequestsException): ProblemDetail =
        problem(HttpStatus.TOO_MANY_REQUESTS, e.message ?: "リクエストが多すぎます", "too_many_requests")

    @ExceptionHandler(WolfMansionBusinessException::class)
    fun handleBusiness(e: WolfMansionBusinessException): ProblemDetail =
        problem(HttpStatus.BAD_REQUEST, e.message ?: "リクエストを処理できません", "business_error")

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ProblemDetail {
        val detail =
            e.bindingResult.fieldErrors
                .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
                .ifBlank { "入力内容が不正です" }
        return problem(HttpStatus.BAD_REQUEST, detail, "validation_error")
    }

    @ExceptionHandler(Exception::class)
    fun handleOther(e: Exception): ProblemDetail {
        logger.error(e.message, e)
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "サーバーエラーが発生しました", "internal_error")
    }

    private fun problem(
        status: HttpStatus,
        detail: String,
        error: String,
    ): ProblemDetail {
        val problem = ProblemDetail.forStatusAndDetail(status, detail)
        problem.setProperty("error", error)
        return problem
    }
}

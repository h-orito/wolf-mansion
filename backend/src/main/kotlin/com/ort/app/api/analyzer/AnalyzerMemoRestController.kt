package com.ort.app.api.analyzer

import com.ort.app.api.analyzer.request.AnalyzerMemoUpdateRequest
import com.ort.app.application.service.AnalyzerMemoService
import com.ort.app.domain.model.analyzer.AnalyzerMemo
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 推理補助 (analyzer) メモの REST。ログインプレイヤー本人のメモのみ読み書きできる
 * (security 設定で認証必須チェーンに乗せている)。
 */
@RestController
@RequestMapping("/api/v1/villages/{villageId}/analyzer-memo")
class AnalyzerMemoRestController(
    private val analyzerMemoService: AnalyzerMemoService,
) {
    @Operation(operationId = "getMyAnalyzerMemo")
    @GetMapping
    fun get(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable villageId: Int,
    ): AnalyzerMemo = analyzerMemoService.findAnalyzerMemo(resolvePlayerId(principal), villageId)

    @Operation(operationId = "updateMyAnalyzerMemo")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable villageId: Int,
        @Validated @RequestBody request: AnalyzerMemoUpdateRequest,
    ) {
        analyzerMemoService.saveAnalyzerMemo(resolvePlayerId(principal), request.toModel(villageId))
    }

    private fun resolvePlayerId(principal: JwtPrincipal?): Int {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        return principal.playerId
    }
}

package com.ort.app.api.randomkeyword

import com.ort.app.api.randomkeyword.request.RandomKeywordRegisterRequest
import com.ort.app.api.randomkeyword.request.RandomKeywordUpdateRequest
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * ランダムキーワードの REST。閲覧 (GET) は公開、書き込みはログイン必須
 * (security 設定で GET のみ permitAll)。
 */
@RestController
@RequestMapping("/api/v1/random-keywords")
class RandomKeywordRestController(
    private val randomKeywordService: RandomKeywordService,
) {
    @GetMapping
    fun list(): RandomKeywords = randomKeywordService.findRandomKeywords()

    @GetMapping("/{id}")
    fun detail(
        @PathVariable id: Int,
    ): RandomKeyword = findOrThrow(id)

    @PostMapping
    fun register(
        @Validated @RequestBody request: RandomKeywordRegisterRequest,
    ): RandomKeyword = randomKeywordService.registerRandomKeyword(request.toModel())

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @Validated @RequestBody request: RandomKeywordUpdateRequest,
    ): RandomKeyword {
        val existing = findOrThrow(id)
        randomKeywordService.updateRandomKeyword(existing.copy(contents = request.toContents()))
        return findOrThrow(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable id: Int,
    ) {
        val existing = findOrThrow(id)
        randomKeywordService.deleteRandomKeyword(existing.keyword)
    }

    private fun findOrThrow(id: Int): RandomKeyword =
        randomKeywordService.findRandomKeyword(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "random keyword not found")
}

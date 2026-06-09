package com.ort.app.api.charachip

import com.ort.app.api.charachip.response.CharachipDetailResponse
import com.ort.app.api.charachip.response.CharachipListResponse
import com.ort.app.application.service.CharaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/charachips")
class CharachipRestController(
    private val charaService: CharaService,
) {
    @GetMapping
    fun list(): CharachipListResponse = CharachipListResponse(charaService.findCharachips())

    @GetMapping("/{id}")
    fun detail(
        @PathVariable id: Int,
    ): CharachipDetailResponse {
        val charachip =
            charaService.findCharachip(id, false)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "charachip not found")
        return CharachipDetailResponse(charachip)
    }
}

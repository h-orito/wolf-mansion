package com.ort.app.api.rule

import com.ort.app.api.rule.response.JudgeListResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rule")
class RuleRestController {
    @GetMapping("/judges")
    fun judges(): JudgeListResponse = JudgeListResponse()
}

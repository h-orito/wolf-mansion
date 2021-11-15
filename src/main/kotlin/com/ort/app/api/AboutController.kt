package com.ort.app.api

import com.ort.app.api.view.RuleContent
import com.ort.app.domain.model.skill.Skills
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AboutController {

    @GetMapping("/about")
    fun about(): String = "about"

    @GetMapping("/announce")
    fun announce(): String = "announce"

    @GetMapping("/rule")
    fun rule(model: Model): String {
        model.addAttribute("content", RuleContent())
        return "rule"
    }

    @GetMapping("/intro")
    fun intro(): String = "intro"

    @GetMapping("/practice")
    fun practice(): String = "practice"

    @GetMapping("/faq")
    fun faq(): String = "faq"
}
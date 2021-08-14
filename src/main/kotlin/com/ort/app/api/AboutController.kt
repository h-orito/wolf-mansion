package com.ort.app.api

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AboutController {

    @GetMapping("/about")
    fun about(model: Model): String = "about"

    @GetMapping("/announce")
    fun announce(model: Model): String = "announce"

    @GetMapping("/rule")
    fun rule(model: Model): String = "rule"

    @GetMapping("/intro")
    fun intro(model: Model): String = "intro"

    @GetMapping("/practice")
    fun practice(model: Model): String = "practice"

    @GetMapping("/faq")
    fun faq(model: Model): String = "faq"
}
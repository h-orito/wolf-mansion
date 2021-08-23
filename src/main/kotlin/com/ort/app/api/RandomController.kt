package com.ort.app.api

import com.ort.app.api.request.RandomKeywordForm
import com.ort.app.api.request.validator.RandomKeywordFormValidator
import com.ort.app.api.view.RandomMessageListContent
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.domain.model.randomkeyword.RandomContent
import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.web.exception.WolfMansionBusinessException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RandomController(
    private val randomKeywordService: RandomKeywordService,
    private val randomKeywordFormValidator: RandomKeywordFormValidator
) {
    @InitBinder("randomKeywordForm")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(randomKeywordFormValidator)
    }

    @GetMapping("/random-message")
    private fun index(model: Model): String {
        val keywords = randomKeywordService.findRandomKeywords()
        val content = RandomMessageListContent(keywords)
        model.addAttribute("content", content)
        return "random-message"
    }

    @GetMapping("/new-random-keyword")
    private fun newRandomKeyword(model: Model): String {
        model.addAttribute("randomKeywordForm", RandomKeywordForm())
        return "new-random-keyword"
    }

    @PostMapping("/new-random-keyword")
    private fun registerRandomKeyword(
        @Validated @ModelAttribute("randomKeywordForm") form: RandomKeywordForm,  //
        result: BindingResult,  //
        model: Model
    ): String {
        if (result.hasErrors()) {
            model.addAttribute("randomKeywordForm", form)
            return "new-random-keyword"
        }
        val keyword = RandomKeyword(
            id = 0, // dummy
            keyword = form.keyword!!,
            contents = form.message!!.trim().split("\r\n").map { RandomContent(it) }
        )
        try {
            randomKeywordService.registerRandomKeyword(keyword)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("errorMessage", e.message)
            model.addAttribute("randomKeywordForm", form)
            return "new-random-keyword"
        }
        return "redirect:/random-message"
    }

    @GetMapping("/random-keyword/{keywordId}")
    private fun randomKeywordIndex(@PathVariable keywordId: Int, model: Model): String {
        val keyword = randomKeywordService.findRandomKeyword(keywordId) ?: return "random-message"
        val form = RandomKeywordForm(
            keyword = keyword.keyword,
            message = keyword.contents.joinToString("\n") { it.message }
        )
        model.addAttribute("randomKeywordForm", form)
        return "random-keyword"
    }

    @PostMapping("/delete-random-keyword")
    private fun deleteRandomKeyword(
        form: RandomKeywordForm,
        model: Model
    ): String {
        randomKeywordService.deleteRandomKeyword(form.keyword!!)
        return "redirect:/random-message"
    }

    @PostMapping("/update-random-keyword")
    private fun updateRandomKeyword(
        @Validated @ModelAttribute("randomKeywordForm") form: RandomKeywordForm,
        result: BindingResult,
        model: Model
    ): String {
        if (result.hasErrors()) {
            model.addAttribute("randomKeywordForm", form)
            return "random-keyword"
        }
        val keyword = RandomKeyword(
            id = 0, // dummy
            keyword = form.keyword!!,
            contents = form.message!!.trim().split("\r\n").map { RandomContent(it) }
        )
        randomKeywordService.updateRandomKeyword(keyword)
        return "redirect:/random-message"
    }
}
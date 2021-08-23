package com.ort.app.api.request.validator

import com.ort.app.api.request.RandomKeywordForm
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class RandomKeywordFormValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return RandomKeywordForm::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        if (errors.hasErrors()) return

        val form = target as RandomKeywordForm

        val keyword: String = form.keyword!!
        if (keyword.contains("or") || keyword.contains("who")) {
            errors.rejectValue("keyword", "RandomKeywordForm.validator.keyword.ngword")
        }

        val messages = form.message!!.trim().split("\r\n")
        if (messages.any { it.isEmpty() || it.length > 20 }) {
            errors.rejectValue("message", "RandomKeywordForm.validator.message.length")
        }
        if (messages.distinct().size != messages.size) {
            errors.rejectValue("message", "RandomKeywordForm.validator.message.duplicate")
        }
    }
}
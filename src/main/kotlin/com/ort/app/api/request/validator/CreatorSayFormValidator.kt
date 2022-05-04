package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageSayForm
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class CreatorSayFormValidator : Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return VillageSayForm::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        if (errors.hasErrors()) return
        val form = target as VillageSayForm

        form.message = form.message!!.trim()
        if (form.message!!.isEmpty()) return

        val message = form.message!!

        val length = message.length
        val lineSeparetorNum = message.split("\r\n").size - 1
        val messageLength = length - lineSeparetorNum
        if (messageLength !in 1..1000) {
            errors.rejectValue("message", "VillageSayForm.validator.creator.message.length")
        }
        // 行数が41以上
        if (lineSeparetorNum > 39) {
            errors.rejectValue("message", "VillageSayForm.validator.creator.message.line")
        }
    }
}
package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageSayForm
import com.ort.app.fw.util.removeSurrogate
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

        // 絵文字は事前に削除
        form.message = form.message!!.removeSurrogate().trim()
        if (form.message!!.isEmpty()) return

        val message = form.message!!

        val length = message.length
        val lineSeparetorNum = message.split("\r\n").size - 1
        val messageLength = length - lineSeparetorNum
        if (messageLength !in 1..400) {
            errors.rejectValue("message", "VillageSayForm.validator.creator.message.length")
        }
        // 行数が41以上
        if (lineSeparetorNum > 39) {
            errors.rejectValue("message", "VillageSayForm.validator.creator.message.line")
        }
    }
}
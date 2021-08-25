package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageParticipateForm
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class VillageParticipateFormValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean = VillageParticipateForm::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        if (errors.hasErrors()) return

        val form = target as VillageParticipateForm
        val message = form.joinMessage!!.trim()

        val length = message.length
        val lineSeparetorNum = message.split("\r\n").size - 1
        val messageLength = length - lineSeparetorNum
        if (messageLength !in 1..400) {
            errors.rejectValue("joinMessage", "VillageSayForm.validator.message.length")
        }
        // 行数が21以上
        if (lineSeparetorNum > 19) {
            errors.rejectValue("joinMessage", "VillageSayForm.validator.message.line")
        }
    }
}
package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageActionForm
import com.ort.app.fw.util.removeSurrogate
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class ActionFormValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean = VillageActionForm::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        if (errors.hasErrors()) return

        val form = target as VillageActionForm
        form.message = form.message!!.removeSurrogate().trim()
        if (form.message!!.isEmpty()) return

        val targetMessage = if (form.target.isNullOrBlank()) "" else form.target
        val messageText = form.myself!! + targetMessage + form.message
        if (messageText.length !in 1..400) {
            errors.rejectValue("message", "VillageSayForm.validator.message.length")
        }
    }
}
package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageParticipateForm
import com.ort.app.application.service.VillageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class VillageParticipateFormValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean = VillageParticipateForm::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        if (errors.hasErrors()) return

        val form = target as VillageParticipateForm

        validateMessage(errors, form)
        validateChara(errors, form)
    }

    private fun validateMessage(errors: Errors, form: VillageParticipateForm) {
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

    private fun validateChara(errors: Errors, form: VillageParticipateForm) {
        if (!form.charaName.isNullOrBlank()) {
            if (form.charaName.length !in 1..40) {
                errors.rejectValue("charaName", "VillageParticipateForm.validator.charaName")
            }
        }
        if (!form.charaShortName.isNullOrBlank()) {
            if (form.charaShortName.length != 1) {
                errors.rejectValue("charaShortName", "VillageParticipateForm.validator.charaShortName")
            }
        }
        form.charaImageFile?.let {
            if (it.size == 0L || 100000L < it.size) {
                errors.rejectValue("charaImageFile", "NewVillageForm.validator.dummyCharaImageFile.size")
            }
        }
    }
}
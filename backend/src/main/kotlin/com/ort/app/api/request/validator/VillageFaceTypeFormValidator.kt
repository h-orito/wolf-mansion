package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageFaceTypeForm
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class VillageFaceTypeFormValidator : Validator {
    override fun supports(clazz: Class<*>): Boolean = VillageFaceTypeForm::class.java.isAssignableFrom(clazz)

    override fun validate(
        target: Any,
        errors: Errors,
    ) {
        if (errors.hasErrors()) return

        val form = target as VillageFaceTypeForm
        validateChara(errors, form)
    }

    private fun validateChara(
        errors: Errors,
        form: VillageFaceTypeForm,
    ) {
        form.image.let {
            if (it == null || it.size == 0L || 100000L < it.size) {
                errors.rejectValue("image", "NewVillageForm.validator.dummyCharaImageFile.size")
            }
        }
    }
}

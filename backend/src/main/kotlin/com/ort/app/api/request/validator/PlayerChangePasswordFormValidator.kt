package com.ort.app.api.request.validator

import com.ort.app.api.request.PlayerChangePasswordForm
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class PlayerChangePasswordFormValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return PlayerChangePasswordForm::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        if (errors.hasErrors()) {
            // 既にエラーがある場合はチェックしない
            return
        }

        val form = target as PlayerChangePasswordForm

        // 新しいパスワードと確認用パスワードが一致しない
        if (form.password != form.confirmPassword) {
            errors.rejectValue("password", "PlayerChangePasswordForm.validator.password")
        }
    }
}
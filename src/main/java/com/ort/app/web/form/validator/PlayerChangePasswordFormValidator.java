package com.ort.app.web.form.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ort.app.web.form.PlayerChangePasswordForm;

@Component
public class PlayerChangePasswordFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return PlayerChangePasswordForm.class.isAssignableFrom(paramClass);
    }

    @Override
    public void validate(Object paramObject, Errors errors) {
        if (errors.hasErrors()) {
            // 既にエラーがある場合はチェックしない
            return;
        }

        PlayerChangePasswordForm form = (PlayerChangePasswordForm) paramObject;

        // 新しいパスワードと確認用パスワードが一致しない
        if (!StringUtils.equals(form.getPassword(), form.getConfirmPassword())) {
            errors.rejectValue("password", "PlayerChangePasswordForm.validator.password");
        }
    }
}

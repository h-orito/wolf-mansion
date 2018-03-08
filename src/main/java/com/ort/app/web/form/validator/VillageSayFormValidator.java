package com.ort.app.web.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ort.app.web.form.VillageSayForm;

@Component
public class VillageSayFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return VillageSayForm.class.isAssignableFrom(paramClass);
    }

    @Override
    public void validate(Object paramObject, Errors errors) {
        if (errors.hasErrors()) {
            // 既にエラーがある場合はチェックしない
            return;
        }

        VillageSayForm form = (VillageSayForm) paramObject;

        String message = form.getMessage();
        if (message == null) {
            return;
        }
        // 行数が11以上
        if (message.split("\n").length > 10) {
            errors.rejectValue("message", "VillageSayForm.validator.message.line");
        }
    }

}

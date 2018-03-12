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
        // 末尾に改行文字列が含まれているとsplit時に削られるので削除してチェック
        String trimedMessage = message.trim();
        // 改行数＋それ以外の文字が200文字以上
        int length = trimedMessage.length();
        int lineSeparatorNum = trimedMessage.split("\r\n").length - 1;
        int messageLength = length - lineSeparatorNum;
        if (messageLength <= 0 || 200 < messageLength) {
            errors.rejectValue("message", "VillageSayForm.validator.message.length");
        }
        // 行数が11以上
        if (trimedMessage.split("\r\n").length > 10) {
            errors.rejectValue("message", "VillageSayForm.validator.message.line");
        }
    }

}

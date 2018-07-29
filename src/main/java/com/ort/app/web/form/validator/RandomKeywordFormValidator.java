package com.ort.app.web.form.validator;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ort.app.web.form.RandomKeywordForm;

@Component
public class RandomKeywordFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return RandomKeywordForm.class.isAssignableFrom(paramClass);
    }

    @Override
    public void validate(Object paramObject, Errors errors) {
        if (errors.hasErrors()) {
            // 既にエラーがある場合はチェックしない
            return;
        }

        RandomKeywordForm form = (RandomKeywordForm) paramObject;

        String keyword = form.getKeyword();
        if (keyword != null && keyword.contains("or") || keyword.contains("who")) {
            errors.rejectValue("keyword", "RandomKeywordForm.validator.keyword.ngword");
        }

        String message = form.getMessage();
        if (message == null) {
            return;
        }
        String trimedMessage = message.trim();
        String[] messages = trimedMessage.split("\r\n");
        Stream.of(messages).forEach(mes -> {
            if (mes.length() < 1 || mes.length() > 20) {
                errors.rejectValue("message", "RandomKeywordForm.validator.message.length");
                return;
            }
        });
        for (int i = 0; i < messages.length - 1; i++) {
            for (int j = i + 1; j < messages.length; j++) {
                if (messages[i].equals(messages[j])) {
                    errors.rejectValue("message", "RandomKeywordForm.validator.message.duplicate");
                    return;
                }
            }
        }

    }
}

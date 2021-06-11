package com.ort.app.web.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ort.app.web.form.VillageActionForm;

@Component
public class VillageActionFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return VillageActionForm.class.isAssignableFrom(paramClass);
    }

    @Override
    public void validate(Object paramObject, Errors errors) {
        if (errors.hasErrors()) {
            // 既にエラーがある場合はチェックしない
            return;
        }

        VillageActionForm form = (VillageActionForm) paramObject;

        // 絵文字はこの時点で削除する
        form.setMessage(removeSurrogate(form.getMessage()));
        String message = form.getMessage();
        if (message == null) {
            return;
        }
        // 400文字以上
        String trimedMessage = message.trim();
        String targetMessage = form.getTarget() == null ? "" : form.getTarget();
        String messageContent = form.getMyself() + targetMessage + trimedMessage;
        int messageLength = messageContent.length();
        if (messageLength <= 0 || 400 < messageLength) {
            errors.rejectValue("message", "VillageSayForm.validator.message.length");
        }
    }

    // 絵文字を削除
    private String removeSurrogate(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!String.valueOf(c).matches("[\\uD800-\\uDFFF]")) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

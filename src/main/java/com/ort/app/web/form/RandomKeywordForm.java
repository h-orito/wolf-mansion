package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class RandomKeywordForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Length(min = 3, max = 10)
    @Pattern(regexp = "[a-zA-Z]*")
    private String keyword;

    @NotNull
    private String message;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

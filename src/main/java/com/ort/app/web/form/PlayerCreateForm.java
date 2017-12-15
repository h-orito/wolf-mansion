package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class PlayerCreateForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ユーザID */
    @NotNull
    @Length(min = 3, max = 12)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String userId;

    /** パスワード */
    @NotNull
    @Length(min = 3, max = 12)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

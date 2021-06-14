package com.ort.app.web.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

public class VillageMemoForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Length(max = 20)
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

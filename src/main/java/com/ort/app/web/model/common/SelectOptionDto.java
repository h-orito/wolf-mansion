package com.ort.app.web.model.common;

public class SelectOptionDto<T> {

    /** 表示名 */
    private String name;

    /** 値 */
    private T value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

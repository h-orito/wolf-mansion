package com.ort.app.web.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class VillageChangeNameForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Length(min = 1, max = 40)
    private String name;

    @NotNull
    @Length(min = 1, max = 1)
    private String shortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}

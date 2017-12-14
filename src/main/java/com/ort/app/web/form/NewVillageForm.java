package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class NewVillageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村表示名 */
    @NotNull
    @Length(min = 5, max = 20)
    private String villageName;

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
}

package com.ort.app.web.model.inner;

public class PlayerCampStatsDto extends PlayerStatsDto {

    /** 陣営名 */
    private String campName;

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

}

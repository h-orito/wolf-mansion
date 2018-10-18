package com.ort.app.web.form;

import java.io.Serializable;
import java.util.List;

public class NewVillageSayRestrictDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // 表示用
    private String skillName;

    // 入力用
    private String skillCode;

    private List<NewVillageSayRestrictDetailDto> detailList;

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public List<NewVillageSayRestrictDetailDto> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<NewVillageSayRestrictDetailDto> detailList) {
        this.detailList = detailList;
    }

}

package com.ort.app.web.form;

import java.io.Serializable;

public class NewVillageSayRestrictDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // 表示用
    private String skillName;

    // 入力用
    private String skillCode;

    /** 制限するか */
    private Boolean isRestrict;

    /** 発言回数 */
    private Integer count;

    /** 文字数 */
    private Integer length;

    public Boolean getIsRestrict() {
        return isRestrict;
    }

    public void setIsRestrict(Boolean isRestrict) {
        this.isRestrict = isRestrict;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

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
}

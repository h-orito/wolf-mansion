package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ort.dbflute.allcommon.CDef;

public class NewVillageRandomOrgSkillDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 役職 */
    private String skillCode;

    /** 役職名 */
    private String skillName;

    /** 最低人数 */
    @NotNull
    @Min(0)
    @Max(100)
    private Integer minNum;

    /** 最大人数 */
    @Min(0)
    @Max(100)
    private Integer maxNum;

    /** 配分 */
    @NotNull
    @Min(0)
    @Max(100)
    private Integer allocation;

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getAllocation() {
        return allocation;
    }

    public void setAllocation(Integer allocation) {
        this.allocation = allocation;
    }

    public void initialize(CDef.Skill skill) {
        this.skillCode = skill.code();
        this.skillName = skill.alias();
        if (skill == CDef.Skill.村人) {
            this.minNum = 1;
        } else {
            this.minNum = 0;
        }
        this.allocation = 50;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}

package com.ort.app.web.form;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ort.dbflute.allcommon.CDef;

public class NewVillageRandomOrgCampDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 陣営 */
    private String campCode;

    /** 陣営名 */
    private String campName;

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

    /** 役職ごとの配分 */
    private List<NewVillageRandomOrgSkillDto> skillAllocation;

    public String getCampCode() {
        return campCode;
    }

    public void setCampCode(String campCode) {
        this.campCode = campCode;
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

    public List<NewVillageRandomOrgSkillDto> getSkillAllocation() {
        return skillAllocation;
    }

    public void setSkillAllocation(List<NewVillageRandomOrgSkillDto> skillAllocation) {
        this.skillAllocation = skillAllocation;
    }

    public void initialize(CDef.Camp camp) {
        this.campCode = camp.code();
        this.campName = camp.alias();
        this.minNum = 0;
        this.allocation = 50;
        this.skillAllocation = CDef.Skill.listAll()
                .stream()
                .filter(s -> s.campCode().equals(this.campCode))
                .filter(s -> !s.isSomeoneSkill())
                .sorted((s1, s2) -> Integer.parseInt(s1.order()) - Integer.parseInt(s2.order()))
                .map(skill -> {
                    NewVillageRandomOrgSkillDto skillDto = new NewVillageRandomOrgSkillDto();
                    skillDto.initialize(skill);
                    return skillDto;
                })
                .collect(Collectors.toList());
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }
}

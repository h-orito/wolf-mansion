package com.ort.app.web.model.inner.village;

import java.util.List;

import com.ort.app.web.model.OptionDto;
import com.ort.app.web.model.inner.SayRestrictDto;

public class VillageActionFormDto {

    /** アクションフォームを表示するか */
    private Boolean isDispActionForm;

    /** target */
    private List<OptionDto> targetList;

    /** 発言制限 */
    private SayRestrictDto restrict;

    public Boolean getIsDispActionForm() {
        return isDispActionForm;
    }

    public void setIsDispActionForm(Boolean isDispActionForm) {
        this.isDispActionForm = isDispActionForm;
    }

    public SayRestrictDto getRestrict() {
        return restrict;
    }

    public void setRestrict(SayRestrictDto restrict) {
        this.restrict = restrict;
    }

    public List<OptionDto> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<OptionDto> targetList) {
        this.targetList = targetList;
    }
}
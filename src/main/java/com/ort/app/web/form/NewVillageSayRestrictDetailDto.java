package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class NewVillageSayRestrictDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // 表示用
    private String messageTypeName;

    // 入力用
    /** 発言種別コード */
    @NotNull
    private String messageTypeCode;

    /** 制限するか */
    private Boolean isRestrict;

    /** 発言回数 */
    private Integer count;

    /** 文字数 */
    private Integer length;

    public String getMessageTypeName() {
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }

    public String getMessageTypeCode() {
        return messageTypeCode;
    }

    public void setMessageTypeCode(String messageTypeCode) {
        this.messageTypeCode = messageTypeCode;
    }

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
}

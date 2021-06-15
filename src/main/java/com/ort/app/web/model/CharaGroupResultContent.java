package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.CharaGroupCharaDto;

public class CharaGroupResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** キャラグループID */
    private Integer charaGroupId;

    /** キャラグループ名 */
    private String charaGroupName;

    /** 作者名 */
    private String designerName;

    /** キャラチップURL */
    private String descriptionUrl;

    /** キャラリスト */
    private List<CharaGroupCharaDto> charaList;

    /** 名称変更可能か */
    private Boolean isAvailableChangeName;

    public Integer getCharaGroupId() {
        return charaGroupId;
    }

    public void setCharaGroupId(Integer charaGroupId) {
        this.charaGroupId = charaGroupId;
    }

    public String getCharaGroupName() {
        return charaGroupName;
    }

    public void setCharaGroupName(String charaGroupName) {
        this.charaGroupName = charaGroupName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public List<CharaGroupCharaDto> getCharaList() {
        return charaList;
    }

    public void setCharaList(List<CharaGroupCharaDto> charaList) {
        this.charaList = charaList;
    }

    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
    }

    public Boolean getIsAvailableChangeName() {
        return isAvailableChangeName;
    }

    public void setIsAvailableChangeName(Boolean isAvailableChangeName) {
        this.isAvailableChangeName = isAvailableChangeName;
    }
}

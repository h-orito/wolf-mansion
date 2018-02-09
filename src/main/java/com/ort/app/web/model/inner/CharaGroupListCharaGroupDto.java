package com.ort.app.web.model.inner;

public class CharaGroupListCharaGroupDto {

    /** キャラグループID */
    private Integer charaGroupId;

    /** キャラグループ名 */
    private String charaGroupName;

    /** 作者名 */
    private String designerName;

    /** キャラチップ数 */
    private Integer charaNum;

    /** ダミーキャラ画像URL */
    private String dummyImgUrl;

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

    public Integer getCharaNum() {
        return charaNum;
    }

    public void setCharaNum(Integer charaNum) {
        this.charaNum = charaNum;
    }

    public String getDummyImgUrl() {
        return dummyImgUrl;
    }

    public void setDummyImgUrl(String dummyImgUrl) {
        this.dummyImgUrl = dummyImgUrl;
    }
}

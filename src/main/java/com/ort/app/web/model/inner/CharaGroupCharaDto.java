package com.ort.app.web.model.inner;

public class CharaGroupCharaDto {

    /** キャラID */
    private Integer charaId;

    /** キャラ名 */
    private String charaName;

    /** キャラ名略称 */
    private String charaShortName;

    /** キャラ画像URL */
    private String charaImgUrl;

    public Integer getCharaId() {
        return charaId;
    }

    public void setCharaId(Integer charaId) {
        this.charaId = charaId;
    }

    public String getCharaName() {
        return charaName;
    }

    public void setCharaName(String charaName) {
        this.charaName = charaName;
    }

    public String getCharaShortName() {
        return charaShortName;
    }

    public void setCharaShortName(String charaShortName) {
        this.charaShortName = charaShortName;
    }

    public String getCharaImgUrl() {
        return charaImgUrl;
    }

    public void setCharaImgUrl(String charaImgUrl) {
        this.charaImgUrl = charaImgUrl;
    }
}

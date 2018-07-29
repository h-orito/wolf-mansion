package com.ort.app.web.model.inner;

public class RandomMessageDetailDto {

    private Integer keywordId;

    private String keyword;

    private String contentExample;

    public Integer getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Integer keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContentExample() {
        return contentExample;
    }

    public void setContentExample(String contentExample) {
        this.contentExample = contentExample;
    }
}

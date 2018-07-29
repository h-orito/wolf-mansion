package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.RandomMessageDetailDto;

public class RandomMessageResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<RandomMessageDetailDto> randomKeywordList;

    public List<RandomMessageDetailDto> getRandomKeywordList() {
        return randomKeywordList;
    }

    public void setRandomKeywordList(List<RandomMessageDetailDto> randomKeywordList) {
        this.randomKeywordList = randomKeywordList;
    }

}

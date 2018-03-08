package com.ort.app.web.model;

import java.io.Serializable;

import com.ort.app.web.model.inner.VillageMessageDto;

public class VillageAnchorMessageResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private VillageMessageDto message;

    public VillageMessageDto getMessage() {
        return message;
    }

    public void setMessage(VillageMessageDto message) {
        this.message = message;
    }

}

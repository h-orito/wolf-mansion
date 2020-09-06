package com.ort.dbflute.exentity;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.bsentity.BsChara;

/**
 * The entity of chara.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 */
public class Chara extends BsChara {

    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    public String getNormalCharaImgUrl() {
        return getCharaImageList().stream().filter(im -> im.isFaceTypeCode通常()).findFirst().get().getCharaImgUrl();
    }

    public String getCharaImgUrlByFaceType(CDef.FaceType faceType) {
        return getCharaImageList().stream().filter(im -> im.getFaceTypeCodeAsFaceType() == faceType).findFirst().get().getCharaImgUrl();
    }
}

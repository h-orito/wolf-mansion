package com.ort.dbflute.exentity;

import java.util.HashMap;
import java.util.Map;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
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

    private static Map<CDef.MessageType, CDef.FaceType> EXPECTED_FACE_TYPE_MAP = null;
    static {
        Map<CDef.MessageType, CDef.FaceType> map = new HashMap<>();
        map.put(CDef.MessageType.通常発言, CDef.FaceType.通常);
        map.put(CDef.MessageType.人狼の囁き, CDef.FaceType.囁き);
        map.put(CDef.MessageType.共鳴発言, CDef.FaceType.共鳴);
        map.put(CDef.MessageType.独り言, CDef.FaceType.独り言);
        map.put(CDef.MessageType.死者の呻き, CDef.FaceType.墓下);
        map.put(CDef.MessageType.見学発言, CDef.FaceType.通常);
        map.put(CDef.MessageType.恋人発言, CDef.FaceType.秘話);
        EXPECTED_FACE_TYPE_MAP = map;
    }

    public String getNormalCharaImgUrl() {
        return getCharaImageList().stream().filter(im -> im.isFaceTypeCode通常()).findFirst().get().getCharaImgUrl();
    }

    public String getCharaImgUrlByFaceType(CDef.FaceType faceType) {
        return getCharaImageList().stream().filter(im -> im.getFaceTypeCodeAsFaceType() == faceType).findFirst().get().getCharaImgUrl();
    }

    public CDef.FaceType detectDefaultFaceType(MessageType messageType) {
        CDef.FaceType expectFaceType = EXPECTED_FACE_TYPE_MAP.get(messageType);
        return getCharaImageList().stream()
                .map(CharaImage::getFaceTypeCodeAsFaceType)
                .filter(im -> im == expectFaceType)
                .findFirst()
                .orElse(CDef.FaceType.通常);
    }
}

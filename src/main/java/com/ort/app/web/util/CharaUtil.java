package com.ort.app.web.util;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.VillagePlayer;

public class CharaUtil {

    private CharaUtil() {
    }

    public static String makeCharaName(Integer roomNumber, String shortName, String name) {
        if (roomNumber == null) {
            return String.format("[%s] %s", shortName, name);
        }
        return String.format("[%02d%s] %s", roomNumber, shortName, name);
    }

    public static String makeCharaShortName(Integer roomNumber, String shortName) {
        if (roomNumber == null) {
            return shortName;
        }
        return String.format("%02d%s", roomNumber, shortName);
    }

    public static String makeCharaName(VillagePlayer vp) {
        Integer roomNumber = vp.getRoomNumber();
        String shortName = vp.getChara().get().getCharaShortName();
        String name = vp.getChara().get().getCharaName();
        return makeCharaName(roomNumber, shortName, name);
    }

    public static String makeCharaShortName(VillagePlayer vp) {
        Integer roomNumber = vp.getRoomNumber();
        String shortName = vp.getChara().get().getCharaShortName();
        return makeCharaShortName(roomNumber, shortName);
    }

    public static String getNormalCharaImgUrl(Chara chara) {
        return chara.getCharaImageList().stream().filter(im -> im.isFaceTypeCode通常()).findFirst().get().getCharaImgUrl();
    }

    public static String getCharaImgUrlByFaceType(Chara chara, CDef.FaceType faceType) {
        return chara.getCharaImageList()
                .stream()
                .filter(im -> im.getFaceTypeCodeAsFaceType() == faceType)
                .findFirst()
                .get()
                .getCharaImgUrl();
    }
}

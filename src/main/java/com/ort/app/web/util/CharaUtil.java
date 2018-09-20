package com.ort.app.web.util;

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

    public static String makeCharaName(VillagePlayer vp) {
        Integer roomNumber = vp.getRoomNumber();
        String shortName = vp.getChara().get().getCharaShortName();
        String name = vp.getChara().get().getCharaName();
        return makeCharaName(roomNumber, shortName, name);
    }
}

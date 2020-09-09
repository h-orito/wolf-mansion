package com.ort.dbflute.exentity;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.bsentity.BsVillagePlayer;

/**
 * The entity of village_player.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayer extends BsVillagePlayer {

    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    public VillagePlayer getTargetLover() {
        return getVillagePlayerStatusByVillagePlayerIdList().stream()
                .filter(vpSt -> vpSt.getVillagePlayerStatusCodeAsVillagePlayerStatusType() == CDef.VillagePlayerStatusType.後追い)
                .findFirst()
                .map(vpSt -> vpSt.getVillagePlayerByToVillagePlayerId().get())
                .orElse(null);
    }

    public String name() {
        Integer roomNumber = getRoomNumber();
        String shortName = getChara().get().getCharaShortName();
        String name = getChara().get().getCharaName();
        return makeCharaName(roomNumber, shortName, name);
    }

    public String shortName() {
        Integer roomNumber = getRoomNumber();
        String shortName = getChara().get().getCharaShortName();
        return makeCharaShortName(roomNumber, shortName);
    }

    public boolean isDeadWhen(int day) {
        return isIsDeadTrue() && getDeadDay() <= day;
    }

    public boolean isAliveWhen(int day) {
        return !isDeadWhen(day);
    }

    public CDef.FaceType detectDefaultFaceType(CDef.MessageType messageType) {
        return getChara().get().detectDefaultFaceType(messageType);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String makeCharaName(Integer roomNumber, String shortName, String name) {
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
}

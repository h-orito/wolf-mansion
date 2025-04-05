package com.ort.dbflute.exentity;

import com.ort.dbflute.bsentity.BsMessage;

/**
 * The entity of MESSAGE.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 *
 * @author DBFlute(AutoGenerator)
 */
public class Message extends BsMessage {

    /**
     * The serial version UID for object serialization. (Default)
     */
    private static final long serialVersionUID = 1L;

    public String name(Integer roomNumber) {
        String shortName = getCharaShortName();
        String name = getCharaName();
        return makeCharaName(roomNumber, shortName, name);
    }

    public String targetName(Integer roomNumber) {
        String shortName = getToCharaShortName();
        String name = getToCharaName();
        return makeCharaName(roomNumber, shortName, name);
    }

    private String makeCharaName(Integer roomNumber, String shortName, String name) {
        if (roomNumber == null) {
            return String.format("[%s] %s", shortName, name);
        }
        return String.format("[%02d%s] %s", roomNumber, shortName, name);
    }
}

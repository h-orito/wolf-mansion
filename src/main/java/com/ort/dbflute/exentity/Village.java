package com.ort.dbflute.exentity;

import com.ort.dbflute.bsentity.BsVillage;

/**
 * The entity of village.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 */
public class Village extends BsVillage {

    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    public VillagePlayers getVillagePlayers() {
        return new VillagePlayers(getVillagePlayerList());
    }

    public VillageDays getVillageDays() {
        return new VillageDays(getVillageDayList());
    }

    public String getVillageNumber() {
        return String.format("%04d", getVillageId());
    }
}

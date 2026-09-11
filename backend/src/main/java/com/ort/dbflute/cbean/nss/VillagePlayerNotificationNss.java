package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.VillagePlayerNotificationCQ;

/**
 * The nest select set-upper of village_player_notification.
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerNotificationNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final VillagePlayerNotificationCQ _query;
    public VillagePlayerNotificationNss(VillagePlayerNotificationCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayer'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public VillagePlayerNss withVillagePlayer() {
        _query.xdoNss(() -> _query.queryVillagePlayer());
        return new VillagePlayerNss(_query.queryVillagePlayer());
    }
}

package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.VillagePlayerStatusCQ;

/**
 * The nest select set-upper of village_player_status.
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerStatusNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final VillagePlayerStatusCQ _query;
    public VillagePlayerStatusNss(VillagePlayerStatusCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public VillagePlayerNss withVillagePlayerByToVillagePlayerId() {
        _query.xdoNss(() -> _query.queryVillagePlayerByToVillagePlayerId());
        return new VillagePlayerNss(_query.queryVillagePlayerByToVillagePlayerId());
    }
    /**
     * With nested relation columns to select clause. <br>
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public VillagePlayerNss withVillagePlayerByVillagePlayerId() {
        _query.xdoNss(() -> _query.queryVillagePlayerByVillagePlayerId());
        return new VillagePlayerNss(_query.queryVillagePlayerByVillagePlayerId());
    }
    /**
     * With nested relation columns to select clause. <br>
     * VILLAGE_PLAYER_STATUS_TYPE by my VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusType'.
     */
    public void withVillagePlayerStatusType() {
        _query.xdoNss(() -> _query.queryVillagePlayerStatusType());
    }
}

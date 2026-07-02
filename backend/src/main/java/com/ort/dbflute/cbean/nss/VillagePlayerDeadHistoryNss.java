package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.VillagePlayerDeadHistoryCQ;

/**
 * The nest select set-upper of VILLAGE_PLAYER_DEAD_HISTORY.
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerDeadHistoryNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final VillagePlayerDeadHistoryCQ _query;
    public VillagePlayerDeadHistoryNss(VillagePlayerDeadHistoryCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * DEAD_REASON by my DEAD_REASON_CODE, named 'deadReason'.
     */
    public void withDeadReason() {
        _query.xdoNss(() -> _query.queryDeadReason());
    }
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

package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.VoteCQ;

/**
 * The nest select set-upper of VOTE.
 * @author DBFlute(AutoGenerator)
 */
public class VoteNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final VoteCQ _query;
    public VoteNss(VoteCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * VILLAGE_DAY by my VILLAGE_ID, DAY, named 'villageDay'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public VillageDayNss withVillageDay() {
        _query.xdoNss(() -> _query.queryVillageDay());
        return new VillageDayNss(_query.queryVillageDay());
    }
}

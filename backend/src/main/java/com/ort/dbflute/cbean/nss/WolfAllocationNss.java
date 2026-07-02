package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.WolfAllocationCQ;

/**
 * The nest select set-upper of wolf_allocation.
 * @author DBFlute(AutoGenerator)
 */
public class WolfAllocationNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final WolfAllocationCQ _query;
    public WolfAllocationNss(WolfAllocationCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public VillageNss withVillage() {
        _query.xdoNss(() -> _query.queryVillage());
        return new VillageNss(_query.queryVillage());
    }
}

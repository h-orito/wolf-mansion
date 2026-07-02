package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.VillageCharaGroupCQ;

/**
 * The nest select set-upper of VILLAGE_CHARA_GROUP.
 * @author DBFlute(AutoGenerator)
 */
public class VillageCharaGroupNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final VillageCharaGroupCQ _query;
    public VillageCharaGroupNss(VillageCharaGroupCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public CharaGroupNss withCharaGroup() {
        _query.xdoNss(() -> _query.queryCharaGroup());
        return new CharaGroupNss(_query.queryCharaGroup());
    }
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

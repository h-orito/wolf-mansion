package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.VillageSettingsCQ;

/**
 * The nest select set-upper of VILLAGE_SETTINGS.
 * @author DBFlute(AutoGenerator)
 */
public class VillageSettingsNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final VillageSettingsCQ _query;
    public VillageSettingsNss(VillageSettingsCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * ALLOWED_SECRET_SAY by my ALLOWED_SECRET_SAY_CODE, named 'allowedSecretSay'.
     */
    public void withAllowedSecretSay() {
        _query.xdoNss(() -> _query.queryAllowedSecretSay());
    }
    /**
     * With nested relation columns to select clause. <br>
     * ORIGINAL_CHARA_GROUP by my ORIGINAL_CHARA_GROUP_ID, named 'originalCharaGroup'.
     */
    public void withOriginalCharaGroup() {
        _query.xdoNss(() -> _query.queryOriginalCharaGroup());
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

package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.VillagePlayerCQ;

/**
 * The nest select set-upper of village_player.
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final VillagePlayerCQ _query;
    public VillagePlayerNss(VillagePlayerCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * chara by my CHARA_ID, named 'chara'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public CharaNss withChara() {
        _query.xdoNss(() -> _query.queryChara());
        return new CharaNss(_query.queryChara());
    }
    /**
     * With nested relation columns to select clause. <br>
     * player by my PLAYER_ID, named 'player'.
     */
    public void withPlayer() {
        _query.xdoNss(() -> _query.queryPlayer());
    }
    /**
     * With nested relation columns to select clause. <br>
     * village by my VILLAGE_ID, named 'village'.
     */
    public void withVillage() {
        _query.xdoNss(() -> _query.queryVillage());
    }
}

package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.AnalyzerMemoCQ;

/**
 * The nest select set-upper of ANALYZER_MEMO.
 * @author DBFlute(AutoGenerator)
 */
public class AnalyzerMemoNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final AnalyzerMemoCQ _query;
    public AnalyzerMemoNss(AnalyzerMemoCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * PLAYER by my PLAYER_ID, named 'player'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public PlayerNss withPlayer() {
        _query.xdoNss(() -> _query.queryPlayer());
        return new PlayerNss(_query.queryPlayer());
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

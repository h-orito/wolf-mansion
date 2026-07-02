package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.RefreshTokenCQ;

/**
 * The nest select set-upper of REFRESH_TOKEN.
 * @author DBFlute(AutoGenerator)
 */
public class RefreshTokenNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final RefreshTokenCQ _query;
    public RefreshTokenNss(RefreshTokenCQ query) { _query = query; }
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
}

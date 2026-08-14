package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.PlayerFavoriteCharaCQ;

/**
 * The nest select set-upper of player_favorite_chara.
 * @author DBFlute(AutoGenerator)
 */
public class PlayerFavoriteCharaNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final PlayerFavoriteCharaCQ _query;
    public PlayerFavoriteCharaNss(PlayerFavoriteCharaCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * CHARA by my CHARA_ID, named 'chara'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public CharaNss withChara() {
        _query.xdoNss(() -> _query.queryChara());
        return new CharaNss(_query.queryChara());
    }
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

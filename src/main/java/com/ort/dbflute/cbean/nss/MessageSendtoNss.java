package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.MessageSendtoCQ;

/**
 * The nest select set-upper of MESSAGE_SENDTO.
 * @author DBFlute(AutoGenerator)
 */
public class MessageSendtoNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final MessageSendtoCQ _query;
    public MessageSendtoNss(MessageSendtoCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * MESSAGE by my MESSAGE_ID, named 'message'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MessageNss withMessage() {
        _query.xdoNss(() -> _query.queryMessage());
        return new MessageNss(_query.queryMessage());
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

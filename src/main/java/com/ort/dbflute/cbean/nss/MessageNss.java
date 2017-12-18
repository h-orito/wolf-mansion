package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.MessageCQ;

/**
 * The nest select set-upper of message.
 * @author DBFlute(AutoGenerator)
 */
public class MessageNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final MessageCQ _query;
    public MessageNss(MessageCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * MESSAGE_TYPE by my MESSAGE_TYPE_CODE, named 'messageType'.
     */
    public void withMessageType() {
        _query.xdoNss(() -> _query.queryMessageType());
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
    /**
     * With nested relation columns to select clause. <br>
     * VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public VillagePlayerNss withVillagePlayerByToVillagePlayerId() {
        _query.xdoNss(() -> _query.queryVillagePlayerByToVillagePlayerId());
        return new VillagePlayerNss(_query.queryVillagePlayerByToVillagePlayerId());
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
    /**
     * With nested relation columns to select clause. <br>
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public VillagePlayerNss withVillagePlayerByVillagePlayerId() {
        _query.xdoNss(() -> _query.queryVillagePlayerByVillagePlayerId());
        return new VillagePlayerNss(_query.queryVillagePlayerByVillagePlayerId());
    }
}

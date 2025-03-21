package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.OriginalCharaCQ;

/**
 * The nest select set-upper of original_chara.
 * @author DBFlute(AutoGenerator)
 */
public class OriginalCharaNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final OriginalCharaCQ _query;
    public OriginalCharaNss(OriginalCharaCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * ORIGINAL_CHARA_GROUP by my ORIGINAL_CHARA_GROUP_ID, named 'originalCharaGroup'.
     */
    public void withOriginalCharaGroup() {
        _query.xdoNss(() -> _query.queryOriginalCharaGroup());
    }
}

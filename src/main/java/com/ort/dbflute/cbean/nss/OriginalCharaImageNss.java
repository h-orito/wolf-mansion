package com.ort.dbflute.cbean.nss;

import com.ort.dbflute.cbean.cq.OriginalCharaImageCQ;

/**
 * The nest select set-upper of ORIGINAL_CHARA_IMAGE.
 * @author DBFlute(AutoGenerator)
 */
public class OriginalCharaImageNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final OriginalCharaImageCQ _query;
    public OriginalCharaImageNss(OriginalCharaImageCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * ORIGINAL_CHARA by my ORIGINAL_CHARA_ID, named 'originalChara'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public OriginalCharaNss withOriginalChara() {
        _query.xdoNss(() -> _query.queryOriginalChara());
        return new OriginalCharaNss(_query.queryOriginalChara());
    }
}

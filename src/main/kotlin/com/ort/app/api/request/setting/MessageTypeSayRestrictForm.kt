package com.ort.app.api.request.setting

import com.ort.dbflute.allcommon.CDef

data class MessageTypeSayRestrictForm(
    // 表示用
    var messageTypeName: String? = null,

    // 入力用
    /** 発言種別 */
    var messageTypeCode: String? = null,

    /** 制限するか */
    var restrict: Boolean? = null,

    /** 発言回数 */
    var count: Int? = null,

    /** 文字数 */
    var length: Int? = null
) {
    constructor(cdef: CDef.MessageType) : this(
        messageTypeName = cdef.alias(),
        messageTypeCode = cdef.code(),
        restrict = false,
        count = 20,
        length = 400
    )
}
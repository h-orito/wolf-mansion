package com.ort.app.domain.model.camp

import com.ort.dbflute.allcommon.CDef

data class Camps(val list: List<Camp>) {
    companion object {
        fun all(): Camps = Camps(
            listOf(
                Camp(CDef.Camp.村人陣営),
                Camp(CDef.Camp.人狼陣営),
                Camp(CDef.Camp.恋人陣営),
                Camp(CDef.Camp.狐陣営),
                Camp(CDef.Camp.愉快犯陣営)
            )
        )
    }
}
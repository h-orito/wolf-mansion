package com.ort.app.api.request

/** 更新間隔の上限 (時間)。村作成・村設定変更で共通。 */
const val DAY_CHANGE_INTERVAL_MAX_HOURS = 120

/** 更新間隔の下限/上限 (秒)。 */
const val DAY_CHANGE_INTERVAL_MIN_SECONDS = 60
const val DAY_CHANGE_INTERVAL_MAX_SECONDS = DAY_CHANGE_INTERVAL_MAX_HOURS * 60 * 60

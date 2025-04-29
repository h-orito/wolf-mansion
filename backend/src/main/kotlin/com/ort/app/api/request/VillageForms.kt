package com.ort.app.api.request

data class VillageForms(
    val sayForm: VillageSayForm? = null,
    val actionForm: VillageActionForm? = null,
    val participateForm: VillageParticipateForm? = null,
    val switchParticipateForm: VillageSwitchParticipateForm? = null,
    val changeRequestSkillForm: VillageChangeRequestSkillForm? = null,
    val changeNameForm: VillageChangeNameForm? = null,
    val memoForm: VillageMemoForm? = null,
    val faceTypeForm: VillageFaceTypeForm? = null,
    val faceTypeModifyForm: VillageFaceTypeModifyForm? = null,
    val notificationForm: VillageNotificationForm? = null
)
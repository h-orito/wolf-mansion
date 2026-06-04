package com.ort.app.api.view

import com.fasterxml.jackson.annotation.JsonProperty

data class SkillListContent(
    val list: List<CampSkillName>
) {
    data class CampSkillName(
        @JsonProperty("camp_name")
        val campName: String,
        @JsonProperty("skill_name_list")
        val skillList: List<String>
    )
}
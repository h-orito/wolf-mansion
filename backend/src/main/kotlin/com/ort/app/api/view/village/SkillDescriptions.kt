package com.ort.app.api.view.village

import java.util.Locale
import java.util.ResourceBundle

object SkillDescriptions {
    private val bundle: ResourceBundle = ResourceBundle.getBundle("messages", Locale.JAPAN)

    fun get(skillCode: String): String =
        try {
            bundle.getString("skill.${skillCode.lowercase()}.description")
        } catch (e: Exception) {
            ""
        }
}

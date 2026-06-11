package com.ort.app.fw.config

import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WolfMansionOpenApiConfig {
    /**
     * `Skill.histories` (とその先の SkillHistories/SkillHistory) を spec から除外する。
     * Skill ⇄ SkillHistories ⇄ SkillHistory の自己参照サイクルを SpringDoc が `$ref` で
     * 表現できず、内容が解決順に依存する退化ノードを出力してしまい、spec の再生成差分
     * (api-drift) を不安定にするため。histories はゲーム内部の役職変化履歴で
     * frontend からは参照しないため、spec から落としても支障がない。
     * シリアライズ自体には手を入れない (実レスポンスには引き続き含まれる)。
     */
    @Bean
    fun skillHistoriesSchemaCustomizer(): OpenApiCustomizer =
        OpenApiCustomizer { openApi ->
            val schemas = openApi.components?.schemas ?: return@OpenApiCustomizer
            schemas["Skill"]?.let { skill ->
                skill.properties?.remove("histories")
                skill.required = skill.required?.filterNot { it == "histories" }
            }
            schemas.remove("SkillHistories")
            schemas.remove("SkillHistory")
        }
}

package com.ort.app.domain.model.translate

interface TranslateRepository {

    // 再翻訳
    fun reTranslate(text: String): Triple<String, String, String>
}
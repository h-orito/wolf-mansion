package com.ort.app.infrastructure.microsoft

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.ort.app.domain.model.translate.TranslateRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


/**
 * 使用できる言語一覧
 * https://api.cognitive.microsofttranslator.com/languages?api-version=3.0
 */
@Repository
class TranslateRepositoryImpl : TranslateRepository {

    @Value("\${microsoft.translator.key:}")
    private lateinit var key: String

    // ランダム言語で再翻訳する
    // 言語, 翻訳, 再翻訳を返す
    override fun reTranslate(text: String): Triple<String, String, String> {
        if (key.isEmpty()) return Triple("再翻訳", text, text)
        val language = languages.random()
        val jaToLanguage = fromJa(text, language.first)
        return Triple(language.second, jaToLanguage, toJa(jaToLanguage, language.first))
    }

    private fun fromJa(str: String, language: String): String {
        val builder = UriComponentsBuilder.fromUriString(baseUrl)
        val uri = builder.queryParam("api-version", "3.0")
            .queryParam("from", "ja")
            .queryParam("to", language)
            .toUriString()
        return call(uri, str)
    }

    private fun toJa(str: String, language: String): String {
        val builder = UriComponentsBuilder.fromUriString(baseUrl)
        val uri = builder.queryParam("api-version", "3.0")
            .queryParam("from", language)
            .queryParam("to", "ja-Jpan")
            .toUriString()
        return call(uri, str)
    }

    private fun call(uri: String, str: String): String {
        val requestEntity = RequestEntity
            .post(URI(uri))
            .header("Ocp-Apim-Subscription-Key", key)
            .header("Ocp-Apim-Subscription-Region", "japaneast")
            .header("Content-Type", "application/json; charset=UTF-8")
            .body(listOf(TranslatorBody(str)))
        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(requestEntity, Array<TranslatorResponse>::class.java)
        return response.body?.firstOrNull()?.translations?.firstOrNull()?.text ?: str
    }

    data class TranslatorBody(
        val Text: String
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TranslatorResponse(
        val translations: List<TranslatorResponseContent>
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class TranslatorResponseContent(
            val text: String
        )
    }

    companion object {
        const val baseUrl = "https://api.cognitive.microsofttranslator.com/translate"

        val languages = listOf(
            "af" to "アフリカーンス語",
            "am" to "アムハラ語",
            "ar" to "アラビア語",
            "as" to "アッサム語",
            "az" to "アゼルバイジャン語",
            "ba" to "Bashkir",
            "bg" to "ブルガリア語",
            "bn" to "ベンガル語",
            "bo" to "チベット語",
            "bs" to "ボスニア語",
            "ca" to "カタロニア語",
            "cs" to "チェコ語",
            "cy" to "ウェールズ語",
            "da" to "デンマーク語",
            "de" to "ドイツ語",
            "dv" to "ディベヒ語",
            "el" to "ギリシャ語",
            "en" to "英語",
            "es" to "スペイン語",
            "et" to "エストニア語",
            "fa" to "ペルシア語",
            "fi" to "フィンランド語",
            "fil" to "フィリピノ語",
            "fj" to "フィジー語",
            "fr" to "フランス語",
            "fr-CA" to "フランス語 (カナダ)",
            "ga" to "アイルランド語",
            "gu" to "グジャラート語",
            "he" to "ヘブライ語",
            "hi" to "ヒンディー語",
            "hr" to "クロアチア語",
            "ht" to "ハイチ・クレオール語",
            "hu" to "ハンガリー語",
            "hy" to "アルメニア語",
            "id" to "インドネシア語",
            "is" to "アイスランド語",
            "it" to "イタリア語",
            "iu" to "イヌクティトット語",
            "ja" to "日本語",
            "ka" to "ジョージア語",
            "kk" to "カザフ語",
            "km" to "クメール語",
            "kmr" to "クルド語 (北)",
            "kn" to "カンナダ語",
            "ko" to "韓国語",
            "ku" to "クルド語 (中央)",
            "ky" to "Kyrgyz",
            "lo" to "ラオ語",
            "lt" to "リトアニア語",
            "lv" to "ラトビア語",
            "lzh" to "Chinese (Literary)",
            "mg" to "マダガスカル語",
            "mi" to "マオリ語",
            "mk" to "マケドニア語",
            "ml" to "マラヤーラム語",
            "mn-Cyrl" to "Mongolian (Cyrillic)",
            "mn-Mong" to "Mongolian (Traditional)",
            "mr" to "マラーティー語",
            "ms" to "マレー語",
            "mt" to "マルタ語",
            "mww" to "フモン語",
            "my" to "ミャンマー語",
            "nb" to "ノルウェー語(ブークモール)",
            "ne" to "ネパール語",
            "nl" to "オランダ語",
            "or" to "オディア語",
            "otq" to "ケレタロ オトミ語",
            "pa" to "パンジャブ語",
            "pl" to "ポーランド語",
            "prs" to "ダリー語",
            "ps" to "パシュトゥー語",
            "pt" to "ポルトガル語 (ブラジル)",
            "pt-PT" to "ポルトガル語 (ポルトガル)",
            "ro" to "ルーマニア語",
            "ru" to "ロシア語",
            "sk" to "スロバキア語",
            "sl" to "スロベニア語",
            "sm" to "サモア語",
            "sq" to "アルバニア語",
            "sr-Cyrl" to "セルビア語 (キリル文字)",
            "sr-Latn" to "セルビア語 (ラテン文字)",
            "sv" to "スウェーデン語",
            "sw" to "スワヒリ語",
            "ta" to "タミル語",
            "te" to "テルグ語",
            "th" to "タイ語",
            "ti" to "ティグリニア語",
            "tk" to "トルクメン語",
            "tlh-Latn" to "クリンゴン語 (ラテン文字)",
            "tlh-Piqd" to "クリンゴン語 (pIqaD)",
            "to" to "トンガ語",
            "tr" to "トルコ語",
            "tt" to "タタール語",
            "ty" to "タヒチ語",
            "ug" to "ウイグル語",
            "uk" to "ウクライナ語",
            "ur" to "ウルドゥー語",
            "uz" to "ウズベク語",
            "vi" to "ベトナム語",
            "yua" to "ユカテコ語",
            "yue" to "広東語 (繁体字)",
            "zh-Hans" to "簡体中国語 (簡体字)",
            "zh-Hant" to "繁体中国語 (繁体字)",
        )
    }
}
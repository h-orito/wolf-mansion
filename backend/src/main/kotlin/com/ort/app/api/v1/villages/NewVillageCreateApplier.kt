package com.ort.app.api.v1.villages

import com.ort.app.api.request.NewVillageForm
import com.ort.app.api.request.validator.NewVillageFormValidator
import com.ort.app.api.request.village.NewVillageCreateBody
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import java.util.Locale

/**
 * `NewVillageCreateBody` を Village に変換するヘルパー。
 *
 * cross-field バリデーションは既存の `NewVillageFormValidator` を共有
 * (body → form 変換 + `MessageSource` でメッセージ解決 → `WolfMansionBusinessException`)。
 * Step 8f の `VillageSettingsUpdateApplier` と同じパターン。
 *
 * Bean Validation 段階のフィールド単体チェックは `@Valid @RequestBody` で自動実行される
 * ため、本クラスではクロスフィールドのみを扱う。
 */
@Component
class NewVillageCreateApplier(
    private val newVillageFormValidator: NewVillageFormValidator,
    private val messageSource: MessageSource,
) {

    /**
     * バリデーション + Village 変換を一度に行う。違反があれば最初のエラーを
     * `WolfMansionBusinessException` (= 400) で送出する。
     */
    fun toVillage(body: NewVillageCreateBody, creator: Player): Village {
        val form = body.toForm()
        form.initialize() // null 値があると validator が NPE するので NewVillageForm の挙動に合わせて補完
        validate(form)
        return form.toVillage(creator)
    }

    private fun validate(form: NewVillageForm) {
        val errors = BeanPropertyBindingResult(form, "villageForm")
        newVillageFormValidator.validate(form, errors)
        if (!errors.hasErrors()) return
        val first = errors.allErrors.first()
        val message = messageSource.getMessage(first, Locale.JAPANESE)
        throw WolfMansionBusinessException(message)
    }
}

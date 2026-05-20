package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageChangeNameBody
import com.ort.app.api.request.village.VillageFaceTypeModifyBody
import com.ort.app.api.request.village.VillageMemoBody
import com.ort.app.api.response.myself.MyselfFaceTypeView
import com.ort.app.api.response.myself.MyselfFaceTypesView
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.fw.exception.WolfMansionBusinessException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * RP (キャラ名 / メモ / 表情差分) 系の REST API。
 *
 * 既存 `VillageRpController` (Thymeleaf) の置き換え。
 *
 * - PUT /api/v1/villages/{id}/rp/name: キャラ名 + 略称変更
 * - PUT /api/v1/villages/{id}/rp/memo: 簡易メモ変更
 * - PUT /api/v1/villages/{id}/rp/face-types: 表情差分の表示名 / 表示有無を編集 (オリジナルキャラチップ村)
 * - POST /api/v1/villages/{id}/rp/face-types: 表情差分の追加 (multipart、オリジナルキャラチップ村)
 *
 * 旧 Thymeleaf 実装は POST だが、REST 的に状態更新は PUT が自然なので統一した。
 *
 * face-types の認可: `myself.charaId` を `CharaService` に渡し、サービス層で `original_chara_image_id`
 * の所有者が自キャラと一致するかを検証する。旧 Thymeleaf 実装も同じ穴を持っていたため `VillageRpController`
 * 側も同時に修正済み。
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageRpRestController(
    private val villageContextLoader: VillageContextLoader,
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val villageCoordinator: VillageCoordinator,
) {

    @PutMapping("/{villageId}/rp/name")
    @Operation(summary = "キャラ名変更")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeName(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageChangeNameBody,
    ) {
        val (village, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        villageCoordinator.changeName(village, myself, body.name, body.shortName)
    }

    @PutMapping("/{villageId}/rp/memo")
    @Operation(summary = "簡易メモ変更")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun memo(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageMemoBody,
    ) {
        val (_, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        villageService.changeMemo(myself, body.memo)
    }

    @GetMapping("/{villageId}/rp/face-types")
    @Operation(
        summary = "表情差分一覧取得 (オリジナルキャラチップ)",
        description = "自分のキャラに紐づく表情差分一覧 (code / name / 画像 URL / display) を返す。" +
                "オリジナルキャラチップ村以外は空配列。" +
                "アクセス要件は『この村に参加している (=参加者または見学者)』。未参加ユーザは 400。",
    )
    fun listFaceTypes(
        @PathVariable villageId: Int,
    ): MyselfFaceTypesView {
        val (village, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        if (!village.setting.chara.isOriginalCharachip) {
            return MyselfFaceTypesView(list = emptyList())
        }
        val chara = charaService.findChara(myself.charaId, isOriginal = true)
            ?: throw WolfMansionBusinessException("自分のキャラが見つかりません")
        val list = chara.images.list.map {
            MyselfFaceTypeView(
                code = it.faceType.code,
                name = it.faceType.name,
                url = it.url,
                isDisplay = it.isDisplay,
            )
        }
        return MyselfFaceTypesView(list = list)
    }

    @PutMapping("/{villageId}/rp/face-types")
    @Operation(
        summary = "表情差分編集 (オリジナルキャラチップ)",
        description = "既存の表情差分の name / display を一括更新する。画像追加は POST /rp/face-types。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun modifyFaceTypes(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageFaceTypeModifyBody,
    ) {
        val (_, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        body.faceTypeList.forEach {
            charaService.updateOriginalCharaImage(myself.charaId, it.code, it.name, it.display)
        }
    }

    @PostMapping("/{villageId}/rp/face-types", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(
        summary = "表情差分追加 (オリジナルキャラチップ)",
        description = "自キャラ (オリジナル) に表情差分を 1 件追加する。201 を返し body は空。" +
                "オリジナルキャラチップ村以外は 400。" +
                "認可は `myself.rp.canAddFaceType` (= `village.canAddImage`) と同条件で backend でも確認する。" +
                "画像は 60x60px で表示されるため 60 の倍数解像度を推奨、サイズは 1〜100KB。表情差分名は 1〜5 文字。",
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun addFaceType(
        @PathVariable villageId: Int,
        @RequestPart("faceTypeName") faceTypeName: String,
        @RequestPart("image") image: MultipartFile,
    ) {
        val (village, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        // オリジナルキャラチップ村のみが対象であることをユーザにわかりやすく示すため、
        // canAddImage の判定よりも先にこちらを分離して個別エラーメッセージを返す。
        if (!village.setting.chara.isOriginalCharachip) {
            throw WolfMansionBusinessException("オリジナルキャラチップ村ではありません")
        }
        // `village.canAddImage(day)` は内部で `isOriginalCharachip && status.isNotFinished() && isLatestDay(day)` を
        // 確認している (isOriginalCharachip 部分は上で先に弾いた)。
        // 旧 `RpDomainService.canAddImage` には `myself.canAddImage()` も AND されているが、
        // 現状 `VillageParticipant.canAddImage()` は常に true を返す stub なので backend ガードとしては
        // 無効。将来このフラグが意味を持つようになった場合は再導入する。
        if (!village.canAddImage(village.latestDay())) {
            throw WolfMansionBusinessException("現在は表情差分を追加できません")
        }
        val trimmedName = faceTypeName.trim()
        if (trimmedName.isEmpty() || trimmedName.length > 5) {
            throw WolfMansionBusinessException("表情差分名は1〜5文字で入力してください")
        }
        // 旧 `VillageFaceTypeFormValidator.validateChara` と同条件 (1〜100,000 byte)
        if (image.size <= 0L || image.size > 100_000L) {
            throw WolfMansionBusinessException("画像サイズは1〜100KBで指定してください")
        }
        // `CharaDataSource.uploadCharaImage` は `originalFilename.lastIndexOf('.')` で
        // 拡張子を取り出すため、originalFilename が null か `.` を含まないと NPE /
        // StringIndexOutOfBoundsException で 500 になる (旧 Thymeleaf endpoint も同じ穴を持つ
        // が、本 endpoint 追加で曝露が広がるため境界側で先回り検証する)。
        // `lastIndexOf('.') >= 1` で `.hidden` のようなドット始まりファイル
        // (ext がファイル名全体になってしまう) を弾く。
        val filename = image.originalFilename
        if (filename == null || filename.lastIndexOf('.') < 1) {
            throw WolfMansionBusinessException("画像ファイル名に拡張子が含まれていません")
        }
        charaService.registerOriginalCharaImage(
            village.setting.chara.charachipIds.first(),
            myself.charaId,
            trimmedName,
            image,
        )
    }
}

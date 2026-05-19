package com.ort.app.application.service

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaRepository
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.chara.Charas
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CharaService(
    private val charaRepository: CharaRepository
) {

    fun findCharachips(): Charachips = charaRepository.findCharachips()

    fun findCharachips(ids: List<Int>, isOriginal: Boolean): Charachips = charaRepository.findCharachips(ids, isOriginal)

    fun findCharachip(charachipId: Int, isOriginal: Boolean): Charachip? = charaRepository.findCharachip(charachipId, isOriginal)

    fun findCharas(charachipId: Int, isOriginal: Boolean): Charas = charaRepository.findCharas(charachipId, isOriginal)

    fun findCharasByCharachipId(idList: List<Int>, isOriginal: Boolean): Charas = charaRepository.findCharasByCharaIdList(idList, isOriginal)

    fun findChara(charaId: Int, isOriginal: Boolean): Chara? = charaRepository.findChara(charaId, isOriginal)

    fun registerOriginalCharachip(
        name: String,
    ): Charachip = charaRepository.registerOriginalCharachip(name)

    fun registerOriginalChara(
        charachipId: Int,
        charaName: String,
        charaShortName: String,
        charaImageFile: MultipartFile
    ): Chara {
        return charaRepository.registerOriginalChara(charachipId, charaName, charaShortName, "通常", charaImageFile)
    }

    fun registerOriginalCharaImage(
        charachipId: Int,
        charaId: Int,
        faceTypeName: String,
        charaImageFile: MultipartFile
    ) {
        charaRepository.registerOriginalCharaImage(charachipId, charaId, faceTypeName, charaImageFile)
    }

    /**
     * 自分の original_chara に属する表情差分のみを更新する。
     * `ownerCharaId` の所有レコードでなければ [WolfMansionBusinessException] を投げて拒否する。
     */
    fun updateOriginalCharaImage(
        ownerCharaId: Int,
        faceTypeCode: String,
        faceTypeName: String,
        isDisplay: Boolean
    ) {
        val charaImageId = faceTypeCode.toInt()
        val ownerId = charaRepository.findOriginalCharaIdByCharaImageId(charaImageId)
            ?: throw WolfMansionBusinessException("表情差分が見つかりません")
        if (ownerId != ownerCharaId) {
            throw WolfMansionBusinessException("他のキャラの表情差分は編集できません")
        }
        charaRepository.updateOriginalCharaImage(charaImageId, faceTypeName, isDisplay)
    }
}
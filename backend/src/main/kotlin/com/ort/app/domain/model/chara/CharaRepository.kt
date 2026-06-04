package com.ort.app.domain.model.chara

import org.springframework.web.multipart.MultipartFile

interface CharaRepository {

    fun findCharachips(): Charachips

    fun findCharachips(ids: List<Int>, isOriginal: Boolean): Charachips

    fun findCharachip(id: Int, isOriginal: Boolean): Charachip?

    fun findCharas(charachipId: Int, isOriginal: Boolean): Charas

    fun findCharasByCharaIdList(idList: List<Int>, isOriginal: Boolean): Charas

    fun findChara(id: Int, isOriginal: Boolean): Chara?

    fun registerOriginalCharachip(name: String): Charachip

    fun registerOriginalChara(
        charachipId: Int,
        name: String,
        shortName: String,
        faceTypeName: String,
        charaImage: MultipartFile
    ): Chara

    fun registerOriginalCharaImage(
        charachipId: Int,
        charaId: Int,
        faceTypeName: String,
        charaImage: MultipartFile
    )

    fun updateOriginalCharaImage(
        charaImageId: Int,
        faceTypeName: String,
        isDisplay: Boolean
    )
}
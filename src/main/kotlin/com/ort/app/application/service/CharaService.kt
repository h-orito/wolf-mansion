package com.ort.app.application.service

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaRepository
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.chara.Charas
import org.springframework.stereotype.Service

@Service
class CharaService(
    private val charaRepository: CharaRepository
) {

    fun findCharachips(): Charachips = charaRepository.findCharachips()

    fun findCharachips(ids: List<Int>): Charachips = charaRepository.findCharachips(ids)

    fun findCharachip(charachipId: Int): Charachip? = charaRepository.findCharachip(charachipId)

    fun findCharas(charachipId: Int): Charas = charaRepository.findCharas(charachipId)

    fun findCharasByCharachipId(idList: List<Int>): Charas = charaRepository.findCharasByCharaIdList(idList)

    fun findChara(charaId: Int): Chara? = charaRepository.findChara(charaId)
}
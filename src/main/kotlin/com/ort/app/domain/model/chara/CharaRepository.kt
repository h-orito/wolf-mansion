package com.ort.app.domain.model.chara

interface CharaRepository {

    fun findCharachips(): Charachips

    fun findCharachip(id: Int): Charachip?

    fun findCharas(id: Int): Charas

    fun findChara(id: Int): Chara?
}
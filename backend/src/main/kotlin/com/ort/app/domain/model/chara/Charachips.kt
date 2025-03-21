package com.ort.app.domain.model.chara

data class Charachips(val list: List<Charachip>) {

    fun chara(id: Int): Chara {
        return list.flatMap { it.charas.list }.first { it.id == id }
    }

    fun charas(): Charas {
        return Charas(list = list.flatMap { it.charas.list })
    }
}
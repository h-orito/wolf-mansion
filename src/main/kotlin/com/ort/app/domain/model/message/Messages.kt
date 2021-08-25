package com.ort.app.domain.model.message

data class Messages(
    val list: List<Message>,
    val allPageCount: Int = 0,
    val isExistPrePage: Boolean = false,
    val isExistNextPage: Boolean = false,
    val currentPageNum: Int = 0
) {

    fun filterByType(type: MessageType): Messages = copy(list = list.filter { it.content.type.code == type.code })

    fun isSame(other: Messages): Boolean {
        return list.size == other.list.size
    }

    fun add(message: Message): Messages = copy(list = list + message)
}
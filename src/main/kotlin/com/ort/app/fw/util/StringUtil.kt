package com.ort.app.fw.util

fun String.removeSurrogate(): String {
    val sb = StringBuilder()
    for (i in this.indices) {
        val c: Char = this[i]
        if (!c.isSurrogate()) sb.append(c)
    }
    return sb.toString()
}
package com.ort.app.domain.model.randomkeyword

data class RandomKeyword(
    val id: Int,
    val keyword: String,
    val contents: List<RandomContent>
)
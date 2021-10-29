package com.ort.app.domain.model.village.room

data class RoomSize(
    val width: Int,
    val height: Int
) {
    companion object {
        fun invoke(personNum: Int): RoomSize {
            return when (personNum) {
                in 8..10 -> RoomSize(4, 3)
                11 -> RoomSize(5, 3)
                in 12..13 -> RoomSize(4, 4)
                in 14..17 -> RoomSize(5, 4)
                in 18..20 -> RoomSize(6, 4)
                else -> {
                    for (width in 6..100) {
                        for (height in width - 1..width) {
                            if (width * height - 13 <= personNum && personNum <= width * height - 4) {
                                return RoomSize(width, height)
                            }
                        }
                    }
                    return RoomSize(1, 1)
                }
            }
        }

        fun getRoomNumbers(personNum: Int): List<Int> {
            return when (personNum) {
                8 -> listOf(2, 3, 5, 6, 7, 8, 10, 11)
                9 -> listOf(2, 3, 5, 6, 7, 8, 10, 11, 12)
                10 -> listOf(1, 2, 3, 5, 6, 7, 8, 10, 11, 12)
                11 -> listOf(2, 3, 4, 6, 7, 8, 9, 10, 12, 13, 14)
                12 -> listOf(2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15)
                13 -> listOf(2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16)
                14 -> listOf(3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18)
                15 -> listOf(3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19)
                16 -> listOf(2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19)
                17 -> listOf(2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 20)
                18 -> listOf(3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22)
                19 -> listOf(2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22)
                20 -> listOf(2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22, 23)
                else -> {
                    val roomSize = this.invoke(personNum)
                    val width = roomSize.width
                    val height = roomSize.height

                    var list: List<Int> = (1..width * height).toList()
                    list = list.filterNot { it == 1 }
                    list = list.filterNot { it == width }
                    list = list.filterNot { it == width * (height - 1) + 1 }
                    list = list.filterNot { it == width * height }
                    if (list.size <= personNum) return list

                    list = list.filterNot { it == width * height - 1 }
                    if (list.size <= personNum) return list

                    list = list.filterNot { it == 2 }
                    if (list.size <= personNum) return list

                    list = list.filterNot { it == width * (height - 1) + 2 }
                    if (list.size <= personNum) return list

                    list = list.filterNot { it == width - 1 }
                    if (list.size <= personNum) return list

                    list = list.filterNot { it == width * (height - 1) }
                    if (list.size <= personNum) return list

                    list = list.filterNot { it == width + 1 }
                    if (list.size <= personNum) return list

                    list = list.filterNot { it == width * (height - 2) + 1 }
                    if (list.size <= personNum) return list

                    list = list.filterNot { it == width * 2 }
                    if (list.size <= personNum) return list

                    return list.filterNot { it == 3 }
                }
            }
        }
    }
}
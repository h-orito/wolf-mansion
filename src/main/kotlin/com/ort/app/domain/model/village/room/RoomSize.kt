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
                        for (height in (width - 1)..width) {
                            if (personNum <= width * height - 4) {
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

                    val list: List<Int> = (1..width * height).toList()

                    val excludes = mutableListOf<Int>()
                    var leftTopExcludeCandidates = createLeftTopExcludes(width)
                    var rightTopExcludeCandidates = createRightTopExcludes(width)
                    var leftBottomExcludeCandidates = createLeftBottomExcludes(width, height)
                    var rightBottomExcludeCandidates = createRightBottomExcludes(width, height)
                    while (true) {
                        when (excludes.size % 4) {
                            // 左上
                            0 -> {
                                excludes.add(leftTopExcludeCandidates[0])
                                leftTopExcludeCandidates = leftTopExcludeCandidates.drop(1)
                            }
                            // 右上
                            1 -> {
                                excludes.add(rightTopExcludeCandidates[0])
                                rightTopExcludeCandidates = rightTopExcludeCandidates.drop(1)
                            }
                            // 左下
                            2 -> {
                                excludes.add(leftBottomExcludeCandidates[0])
                                leftBottomExcludeCandidates = leftBottomExcludeCandidates.drop(1)
                            }
                            // 右下
                            3 -> {
                                excludes.add(rightBottomExcludeCandidates[0])
                                rightBottomExcludeCandidates = rightBottomExcludeCandidates.drop(1)
                            }
                        }
                        if (list.size - excludes.size <= personNum) break
                    }
                    return list.filterNot { excludes.contains(it) }
                }
            }
        }

        // 999人まではこれで対応できるはず
        private fun createLeftTopExcludes(width: Int): List<Int> = listOf(
            1, 2, width + 1, 3, width + 2, width * 2 + 1, 4, width + 3, width * 2 + 2, width * 3 + 1
        )

        private fun createRightTopExcludes(width: Int): List<Int> = listOf(
            width,
            width - 1,
            width * 2,
            width - 2,
            width * 2 - 1,
            width * 3,
            width - 3,
            width * 2 - 2,
            width * 3 - 1,
            width * 4
        )

        private fun createLeftBottomExcludes(width: Int, height: Int): List<Int> = listOf(
            width * (height - 1) + 1,
            width * (height - 1) + 2,
            width * (height - 2) + 1,
            width * (height - 1) + 3,
            width * (height - 2) + 2,
            width * (height - 3) + 1,
            width * (height - 1) + 4,
            width * (height - 2) + 3,
            width * (height - 3) + 2,
            width * (height - 4) + 1
        )

        private fun createRightBottomExcludes(width: Int, height: Int): List<Int> = listOf(
            width * height,
            width * height - 1,
            width * (height - 1),
            width * height - 2,
            width * (height - 1) - 1,
            width * (height - 2),
            width * height - 3,
            width * (height - 1) - 2,
            width * (height - 2) - 1,
            width * (height - 3)
        )
    }
}
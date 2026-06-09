package com.ort.app.api.room

import com.ort.app.domain.model.village.room.RoomSize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rooms")
class RoomRestController {
    @GetMapping
    fun roomAssignment(
        @RequestParam personNum: Int,
    ): RoomAssignmentResponse {
        val roomSize = RoomSize.invoke(personNum)
        val roomNumbers = RoomSize.getRoomNumbers(personNum)
        return RoomAssignmentResponse(
            width = roomSize.width,
            height = roomSize.height,
            roomNumbers = roomNumbers,
        )
    }
}

data class RoomAssignmentResponse(
    val width: Int,
    val height: Int,
    val roomNumbers: List<Int>,
)

package com.ssafy.talkeasy.feature.chat

import com.ssafy.talkeasy.core.domain.entity.response.Chat

class SampleData {

    companion object {

        private val msgShort = Chat(
            roomId = "",
            message = "오늘 점심 먹었니?",
            time = "2023-05-04T16:16:38.417705",
            readCount = 0,
            fromUserId = "",
            toUserId = "",
            type = 0
        )

        private val msgLong = Chat(
            roomId = "",
            message = "오늘 점심 먹었니? 어디 아픈 곳은 없고? 너무 잘하고 있다~~",
            time = "2023-05-04T16:16:38.417705",
            readCount = 1,
            fromUserId = "",
            toUserId = "",
            type = 0
        )

        private val locationRequest = Chat(
            roomId = "",
            message = "위치 열람 요청",
            time = "2023-05-04T16:16:38.417705",
            readCount = 1,
            fromUserId = "",
            toUserId = "",
            type = 1,
            status = 0
        )

        private val locationResult = Chat(
            roomId = "",
            message = "30:00",
            time = "2023-05-04T16:17:38.417705",
            readCount = 0,
            fromUserId = "",
            toUserId = "",
            type = 1,
            status = 1
        )

        private val locationReject = Chat(
            roomId = "",
            message = "요청 응답 없음",
            time = "2023-05-04T16:18:38.417705",
            readCount = 1,
            fromUserId = "",
            toUserId = "",
            type = 1,
            status = 2
        )

        private val sos = Chat(
            roomId = "",
            message = "긴급 도움 요청",
            time = "2023-05-04T16:18:38.417705",
            readCount = 1,
            fromUserId = "",
            toUserId = "",
            type = 2
        )

        val msgs = listOf(msgShort, msgLong)

        val locations = listOf(locationRequest, locationResult, locationReject)

        val soss = listOf(sos)
    }
}
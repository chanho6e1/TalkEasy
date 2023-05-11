package com.ssafy.talkeasy.feature.chat

import com.ssafy.talkeasy.core.domain.entity.Chat

class SampleData {

    companion object {

        private val msgShort = Chat(
            message = "오늘 점심 먹었니?",
            time = "2023-05-04T16:16:38.417705",
            type = 0
        )

        private val msgLong = Chat(
            message = "오늘 점심 먹었니? 어디 아픈 곳은 없고? 너무 잘하고 있다~~",
            time = "2023-05-04T16:16:38.417705",
            type = 0
        )

        private val locationRequest = Chat(
            message = "위치 열람 요청",
            time = "2023-05-04T16:16:38.417705",
            type = 1,
            status = 0
        )

        private val locationResult = Chat(
            message = "30:00",
            time = "2023-05-04T16:17:38.417705",
            type = 1,
            status = 1
        )

        private val locationReject = Chat(
            message = "요청 응답 없음",
            time = "2023-05-04T16:18:38.417705",
            type = 1,
            status = 2
        )

        val msgs = listOf(msgShort, msgLong)

        val locations = listOf(locationRequest, locationResult, locationReject)
    }
}
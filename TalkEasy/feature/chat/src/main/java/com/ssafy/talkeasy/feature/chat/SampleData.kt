package com.ssafy.talkeasy.feature.chat

import com.ssafy.talkeasy.core.domain.entity.Chat

class SampleData {

    companion object {

        private val chat = Chat(
            message = "오늘 점심 먹었니? 어디 아픈 곳은 없고? 너무 잘하고 있다~~",
            time = "2023-05-04T16:16:38.417705",
            type = 2
        )

        val messages = List(2) { chat }
    }
}
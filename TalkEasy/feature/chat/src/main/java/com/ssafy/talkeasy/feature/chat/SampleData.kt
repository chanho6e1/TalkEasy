package com.ssafy.talkeasy.feature.chat

import com.ssafy.talkeasy.core.domain.entity.Chat

class SampleData {

    companion object {

        val chat = Chat(message = "오늘 점심 먹었니? 어디 아픈 곳은 없고? 너무 잘하고 있다~~", time = "오전 11:25")

        val messages = List(2) { chat }
    }
}
package com.ssafy.talkeasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ssafy.talkeasy.feature.chat.SampleData.Companion.messages
import com.ssafy.talkeasy.feature.chat.ui.tablet.MyChat
import com.ssafy.talkeasy.feature.common.ui.theme.TalkEasyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalkEasyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // PartnerChat(memberName = memberName, nickname = nickname, messages = messages)

                    MyChat(messages = messages)
                }
            }
        }
    }
}
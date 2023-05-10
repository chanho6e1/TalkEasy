package com.ssafy.talkeasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ssafy.talkeasy.feature.aac.SampleData.Companion.memberName
import com.ssafy.talkeasy.feature.aac.ui.AACFrame
import com.ssafy.talkeasy.feature.aac.ui.RequestBrowse
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
                    AACFrame()
                    RequestBrowse(memberName = memberName)
                }
            }
        }
    }
}
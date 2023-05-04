package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AACFrame() {
    val onRight by remember {
        mutableStateOf(true)
    }
    val category = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AACTopBar(onRight = onRight)

        AACChatBox(words = SampleData.string7)

        AACFixedCards()

        if (category.value == "") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AACCategory(category)
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp, start = 36.dp, end = 36.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                AACSmallCards(words = SampleData.string20)
            }
        }
    }
}
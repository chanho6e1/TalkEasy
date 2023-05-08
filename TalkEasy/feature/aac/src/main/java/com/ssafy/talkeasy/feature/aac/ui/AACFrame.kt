package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.aac.SampleData

@Composable
fun AACFrame(aacViewModel: AACViewModel = hiltViewModel()) {
    val words by aacViewModel.selectedCard.collectAsState()

    val onRight by remember {
        mutableStateOf(true)
    }
    val category = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AACTopBar(onRight = onRight)

        AACChatBox(words = words)

        AACFixedCards()

        if (category.value == "") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AACCategory(category)
            }
        } else {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 20.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    AACSmallCards(words = SampleData.string25)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AACPaging()
                }
            }
        }
    }
}
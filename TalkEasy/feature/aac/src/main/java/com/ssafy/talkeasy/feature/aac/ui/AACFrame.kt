package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.aac.SampleData
import com.ssafy.talkeasy.feature.chat.ui.tablet.ChatFrame

@Composable
@Preview(showBackground = true, widthDp = 1429, heightDp = 857)
fun AACFrame(aacViewModel: AACViewModel = hiltViewModel()) {
    val words by aacViewModel.selectedCard.collectAsState()
    val category by aacViewModel.category.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        AACTopBar(onRight = aacViewModel.getOnRight())

        Row(modifier = Modifier.weight(1f)) {
            // ChatFrame(chats = listOf())

            Column() {
                AACChatBox(words = words)

                AACFixedCards()

                if (category == "") {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        AACCategory()
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
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
                                .fillMaxSize()
                                .padding(bottom = 20.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            AACPaging()
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 36.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            BackToCategory(category)
                        }
                    }
                }
            }

        }
    }
}
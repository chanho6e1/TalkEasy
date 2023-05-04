package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.seed

@Composable
@Preview(showBackground = true)
fun AACFixedCards() {
    val fixedWords = stringArrayResource(id = R.array.aac_fixed_words)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(fixedWords) { word ->
            AACCardWrap(word = word, color = seed)
        }
    }
}

@Composable
fun AACSmallCards(words: List<String>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        items(count = ((words.size - 1) / 5) + 1) { index ->
            LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                val rowItems = if (words.size >= (index * 5) + 5) {
                    words.subList(index * 5, (index * 5) + 5)
                } else {
                    words.subList(index * 5, words.size)
                }

                items(count = 5) { index ->
                    AACCardSmall(word = rowItems[index])
                }
            }
        }
    }
}

@Composable
fun AACPaging() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = drawable.ic_arrow_backward_hard),
                    contentDescription = stringResource(R.string.image_backward_hard)
                )
            }
        }
        item {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = drawable.ic_arrow_backward_soft),
                    contentDescription = stringResource(R.string.image_backward_soft)
                )
            }
        }
        item {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = drawable.ic_arrow_forward_soft),
                    contentDescription = stringResource(R.string.image_forward_soft)
                )
            }
        }
        item {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = drawable.ic_arrow_forward_hard),
                    contentDescription = stringResource(R.string.image_forward_hard)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewAACFixedCard() {
    AACCardWrap(word = "안녕하세요", color = seed)
}

@Composable
@Preview(showBackground = true, widthDp = 1429, heightDp = 857)
fun PreviewAACSmallCards() {
    AACSmallCards(words = SampleData.string20)
}
package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
@Preview
fun PreviewAACFixedCard() {
    AACCardWrap(word = "안녕하세요", color = seed)
}
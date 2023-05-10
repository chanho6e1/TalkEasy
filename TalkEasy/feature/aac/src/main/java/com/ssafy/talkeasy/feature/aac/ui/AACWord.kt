package com.ssafy.talkeasy.feature.aac.ui

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.aac.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.seed

@Composable
fun AACFixedCards(aacViewModel: AACViewModel = viewModel()) {
    val fixedWords = stringArrayResource(id = R.array.aac_fixed_words)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(fixedWords) { word ->
            AACCardWrap(word = word, color = seed) { it ->
                aacViewModel.addCard(it)
            }
        }
    }
}

@Composable
fun AACSmallCards(words: List<String>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp)) {
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
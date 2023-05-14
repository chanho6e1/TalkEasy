package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
        modifier = Modifier.fillMaxWidth(),
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
fun AACSmallCards(words: List<String>, column: Int = 5) {
    val list = words.subList(0, column * 5)
    LazyVerticalGrid(
        columns = GridCells.Fixed(column),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        reverseLayout = false,
        userScrollEnabled = false
    ) {
        items(list) {
            AACCardSmall(word = it)
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
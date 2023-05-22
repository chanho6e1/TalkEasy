package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.core.domain.entity.response.AACWord
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.aac.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_primary
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondary
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
fun AACFixedCards(cardClickEnable: Boolean, aacViewModel: AACViewModel = viewModel()) {
    val fixedWords by aacViewModel.aacFixedList.collectAsState()
    val aacWordList by aacViewModel.aacWordList.collectAsState()

    val totalFixedList =
        (if (aacWordList != null) aacWordList!!.fixedList else listOf()) + fixedWords

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(items = totalFixedList) { word ->
            AACCardWrap(
                word = word.title,
                color = seed,
                cardClickEnable = cardClickEnable,
                onCardSelectedListener = { aacViewModel.addCard(word.title) }
            )
        }
    }
}

@Composable
fun AACRelatedCards(cardClickEnable: Boolean, aacViewModel: AACViewModel = viewModel()) {
    val relatedWordList by aacViewModel.relativeVerbList.collectAsState()

    Box(modifier = Modifier.padding(start = 30.dp)) {
        Surface(
            modifier = Modifier.padding(top = 6.dp),
            border = BorderStroke(width = 1.dp, color = seed),
            shape = shapes.extraSmall
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 10.dp, start = 10.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (relatedWordList.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                            text = "연관 단어가 없습니다",
                            color = md_theme_light_secondary,
                            style = typography.titleMedium
                        )
                    }
                } else {
                    items(items = relatedWordList) { word ->
                        AACCardWrap(
                            word = word.title,
                            color = md_theme_light_secondaryContainer,
                            cardClickEnable = cardClickEnable,
                            onCardSelectedListener = { aacViewModel.addCard(word.title) }
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 7.dp)
                .background(color = md_theme_light_background)
                .padding(horizontal = 3.dp),
            text = "연관 단어 목록",
            color = md_theme_light_primary,
            style = typography.bodyMedium
        )
    }
}

@Composable
fun AACSmallCards(
    page: Int = 0,
    wordCountPerPage: Int,
    wordList: List<AACWord>,
    cardClickEnable: Boolean,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
        reverseLayout = false,
        userScrollEnabled = false
    ) {
        items(
            items = wordList.subList(
                page * wordCountPerPage,
                Integer.min((page + 1) * wordCountPerPage, wordList.size)
            )
        ) { word ->
            AACCardSmall(word = word, cardClickEnable = cardClickEnable)
        }
    }
}

@Composable
fun AACPaging(page: Int, totalPage: Int, setPage: (Int) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            IconButton(enabled = page != 0, onClick = { setPage(0) }) {
                Icon(
                    painter = painterResource(id = drawable.ic_arrow_backward_hard),
                    contentDescription = stringResource(R.string.image_backward_hard)
                )
            }
        }
        item {
            IconButton(enabled = page != 0, onClick = { setPage(page - 1) }) {
                Icon(
                    painter = painterResource(id = drawable.ic_arrow_backward_soft),
                    contentDescription = stringResource(R.string.image_backward_soft)
                )
            }
        }
        item {
            IconButton(enabled = page < totalPage - 1, onClick = { setPage(page + 1) }) {
                Icon(
                    painter = painterResource(id = drawable.ic_arrow_forward_soft),
                    contentDescription = stringResource(R.string.image_forward_soft)
                )
            }
        }
        item {
            IconButton(enabled = page < totalPage - 1, onClick = { setPage(totalPage - 1) }) {
                Icon(
                    painter = painterResource(id = drawable.ic_arrow_forward_hard),
                    contentDescription = stringResource(R.string.image_forward_hard)
                )
            }
        }
    }
}
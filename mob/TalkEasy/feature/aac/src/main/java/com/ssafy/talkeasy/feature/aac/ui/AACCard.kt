package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.core.domain.entity.response.AACWord
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_surfaceVariant
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal30
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AACCardWrap(
    word: String,
    color: Color,
    cardClickEnable: Boolean,
    onCardSelectedListener: () -> Unit,
) {
    Surface(
        shape = shapes.extraSmall,
        color = color,
        enabled = cardClickEnable,
        onClick = onCardSelectedListener
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            text = word,
            color = md_theme_light_onBackground,
            style = typography.titleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AACCardSmall(
    word: AACWord,
    cardClickEnable: Boolean,
    aacViewModel: AACViewModel = viewModel(),
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = shapes.extraSmall,
        color = md_theme_light_surfaceVariant,
        enabled = cardClickEnable,
        onClick = {
            aacViewModel.getRelativeVerbList(word.id)
            aacViewModel.addCard(word.title)
        }
    ) {
        Text(
            modifier = Modifier.padding(vertical = 18.dp),
            text = word.title,
            textAlign = TextAlign.Center,
            color = md_theme_light_onBackground,
            style = typography.titleMedium
        )
    }
}

@Composable
fun AACCardLarge(word: String) {
    Surface(
        modifier = Modifier.width(352.dp),
        shape = shapes.extraSmall,
        color = md_theme_light_surfaceVariant
    ) {
        Text(
            modifier = Modifier.padding(vertical = 18.dp),
            text = word,
            textAlign = TextAlign.Center,
            color = md_theme_light_onBackground,
            style = textStyleNormal30
        )
    }
}
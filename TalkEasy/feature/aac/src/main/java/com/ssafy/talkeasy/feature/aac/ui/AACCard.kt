package com.ssafy.talkeasy.feature.aac.ui

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
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.common.ui.theme.Typography
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_surfaceVariant
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AACCardWrap(word: String, color: Color, onCardSelectedListener: (String) -> Unit) {
    Surface(shape = shapes.extraSmall, color = color, onClick = { onCardSelectedListener(word) }) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            text = word,
            color = md_theme_light_onBackground,
            style = Typography.titleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AACCardSmall(word: String, aacViewModel: AACViewModel = viewModel()) {
    Surface(
        modifier = Modifier.width(225.dp),
        shape = shapes.extraSmall,
        color = md_theme_light_surfaceVariant,
        onClick = { aacViewModel.addCard(word) }
    ) {
        Text(
            modifier = Modifier.padding(vertical = 18.dp),
            text = word,
            textAlign = TextAlign.Center,
            color = md_theme_light_onBackground,
            style = Typography.titleMedium
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
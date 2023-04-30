package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.aac.R.array
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
@Preview(showBackground = true)
fun AACFixedCards() {
    val fixedWords = stringArrayResource(id = array.aac_fixed_words)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(fixedWords) { word ->
            AACFixedCard(word = word)
        }
    }
}

@Composable
fun AACFixedCard(word: String) {
    Surface(shape = shapes.extraSmall, color = seed) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            text = word,
            color = md_theme_light_onBackground,
            style = typography.titleMedium
        )
    }
}

@Composable
@Preview
fun AACCategory() {
}

@Composable
fun AACCategoryCard(iconId: Int, contentDescription: String, category: String) {
    Surface(shape = shapes.extraSmall, color = seed) {
        Column(
            modifier = Modifier.padding(horizontal = 83.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = iconId), contentDescription = contentDescription)

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
                text = category,
                color = md_theme_light_onBackground,
                style = typography.titleMedium
            )
        }
    }
}

@Composable
@Preview
fun PreviewAACFixedCard() {
    AACFixedCard(word = "안녕하세요")
}

@Composable
@Preview
fun PreviewAACCategoryCard() {
    AACCategoryCard(
        iconId = R.drawable.ic_category_food,
        contentDescription = stringArrayResource(id = array.aac_category_content_description)[0],
        category = stringArrayResource(id = array.aac_category_word)[0]
    )
}
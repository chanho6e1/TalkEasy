package com.ssafy.talkeasy.feature.aac

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_surfaceVariant
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold24

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

@SuppressLint("DiscouragedApi")
@Composable
@Preview(showBackground = true, widthDp = 1429, heightDp = 857)
fun AACCategory() {
    val categoryIndexArray =
        stringArrayResource(id = R.array.aac_category_index)
    val categoryValueArray = stringArrayResource(id = R.array.aac_category_word)
    val categoryImageContentDescriptionArray =
        stringArrayResource(id = R.array.aac_category_content_description)
    val categoryImageIdArray = List(8) { index ->
        val context = LocalContext.current

        context.resources.getIdentifier(
            "ic_category_${categoryIndexArray[index]}",
            "drawable",
            context.packageName
        )
    }

    val category = List(8) { index ->
        Category(
            index = categoryIndexArray[index],
            value = categoryValueArray[index],
            imageId = categoryImageIdArray[index],
            contentDescription = categoryImageContentDescriptionArray[index]
        )
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(40.dp)) {
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                items(items = category.subList(0, 4)) {
                    AACCategoryCard(
                        iconId = it.imageId,
                        contentDescription = it.contentDescription,
                        category = it.value
                    ) {}
                }
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                items(items = category.subList(4, 8)) {
                    AACCategoryCard(
                        iconId = it.imageId,
                        contentDescription = it.contentDescription,
                        category = it.value
                    ) {}
                }
            }
        }
    }
}

@Composable
fun AACCategoryCard(
    iconId: Int,
    contentDescription: String,
    category: String,
    onClickMethod: () -> Unit,
) {
    Button(
        modifier = Modifier.width(250.dp),
        shape = shapes.medium,
        colors = ButtonDefaults.buttonColors(md_theme_light_surfaceVariant),
        contentPadding = PaddingValues(vertical = 18.dp),
        onClick = { onClickMethod() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = iconId), contentDescription = contentDescription)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = category,
                color = md_theme_light_onBackground,
                style = textStyleBold24
            )
        }
    }
}

@Composable
@Preview
fun PreviewAACFixedCard() {
    AACCardWrap(word = "안녕하세요", color = seed)
}

@Composable
@Preview
fun PreviewAACCategoryCard() {
    AACCategoryCard(
        iconId = R.drawable.ic_category_food,
        contentDescription = stringArrayResource(id = R.array.aac_category_content_description)[0],
        category = stringArrayResource(id = R.array.aac_category_word)[0]
    ) {}
}

data class Category(
    val index: String,
    val value: String,
    val imageId: Int,
    val contentDescription: String,
)
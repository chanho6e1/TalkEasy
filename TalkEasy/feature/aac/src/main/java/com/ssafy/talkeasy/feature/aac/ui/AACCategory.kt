package com.ssafy.talkeasy.feature.aac.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.core.domain.entity.Category
import com.ssafy.talkeasy.feature.aac.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_outline
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_surfaceVariant
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold24
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@SuppressLint("DiscouragedApi")
@Composable
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
                    )
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
                    )
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
    aacViewModel: AACViewModel = viewModel(),
) {
    Button(
        modifier = Modifier.width(250.dp),
        shape = shapes.medium,
        colors = ButtonDefaults.buttonColors(md_theme_light_surfaceVariant),
        contentPadding = PaddingValues(vertical = 18.dp),
        onClick = { aacViewModel.setCategory(category) }
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
@Preview(showBackground = true)
fun BackToCategory(category: String = "음식", aacViewModel: AACViewModel = viewModel()) {
    Button(
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = { aacViewModel.setCategory() }
    ) {
        Column(
            modifier = Modifier.padding(end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = stringResource(R.string.title_back_to_category_select),
                color = md_theme_light_onBackground,
                style = typography.bodyLarge
            )
            Text(
                text = String.format(
                    stringResource(R.string.content_back_to_category_select),
                    category
                ),
                color = md_theme_light_outline,
                style = typography.bodyLarge
            )
        }

        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = drawable.ic_back_to_home),
            contentDescription = stringResource(R.string.image_back_to_category_select)
        )
    }
}
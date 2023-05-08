package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onSecondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.typography

/**
 * @param position -1: Start, 0: Middle, 1: End
 * */
@Composable
fun SegmentedButton(
    selectedValue: MutableState<String>,
    value: String,
    position: Int,
    onChangeGender: (String) -> Unit = {},
) {
    val isSelected = selectedValue.value == value

    OutlinedButton(
        modifier = Modifier.size(width = 122.dp, height = 47.dp),
        shape = when (position) {
            -1 -> RoundedCornerShape(
                topStart = 150.dp,
                bottomStart = 150.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            )

            1 -> RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 150.dp,
                bottomEnd = 150.dp
            )

            else -> RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            )
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = md_theme_light_onSecondaryContainer,
            containerColor = if (isSelected) {
                md_theme_light_secondaryContainer
            } else {
                md_theme_light_background
            }
        ),
        onClick = {
            selectedValue.value = value
            onChangeGender(value)
        }
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.image_icon_check)
            )
        }

        Spacer(modifier = Modifier.width(9.dp))

        Text(text = value, style = typography.titleMedium)
    }
}
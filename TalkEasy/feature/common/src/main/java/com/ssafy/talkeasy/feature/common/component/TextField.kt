package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.green_white
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_primary
import com.ssafy.talkeasy.feature.common.ui.theme.seed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    label: String,
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        focusedTextColor = md_theme_light_primary,
        containerColor = green_white,
        focusedIndicatorColor = seed,
        focusedLabelColor = seed,
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedLabelColor = delta,
        cursorColor = seed,
        errorCursorColor = md_theme_light_error,
        errorLabelColor = md_theme_light_error,
        errorIndicatorColor = md_theme_light_error
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = textStyle,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = textFieldColors,
        singleLine = true
    )
}

@Composable
fun NoLabelTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    setText: (String) -> Unit = {},
    textStyle: TextStyle,
    label: String,
    innerPaddingHorizontal: Int,
    innerPaddingVertical: Int,
) {
    BasicTextField(
        value = text,
        onValueChange = setText,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(),
        textStyle = textStyle,
        cursorBrush = SolidColor(md_theme_light_primary),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = green_white,
                        shape = RoundedCornerShape(size = 5.dp)
                    )
                    .padding(
                        vertical = innerPaddingVertical.dp,
                        horizontal = innerPaddingHorizontal.dp
                    )
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = label,
                        style = textStyle,
                        color = delta
                    )
                }
                innerTextField()
            }
        }
    )
}
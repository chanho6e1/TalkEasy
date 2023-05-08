package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.seed

@Composable
fun WideSeedButton(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
    text: String,
    textStyle: TextStyle,
) {
    val mainButtonColor = ButtonDefaults.buttonColors(
        contentColor = md_theme_light_onBackground,
        containerColor = seed
    )
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClicked,
        colors = mainButtonColor,
        shape = RoundedCornerShape(5.dp),
        contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Text(text = text, style = textStyle)
    }
}
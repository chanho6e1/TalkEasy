package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.harp
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
fun CustomDialog(
    message: String,
    okMessage: String,
    noMessage: String,
    visible: Boolean,
    buttonVerticalPadding: Int,
    outSideVerticalPadding: Int,
    outSideHorizontalPadding: Int,
    onDismissRequest: () -> Unit,
    onClickedOkButton: () -> Unit,
    properties: DialogProperties = DialogProperties(),
) {
    val okButtonColor = ButtonDefaults.filledTonalButtonColors(
        containerColor = seed,
        contentColor = md_theme_light_onBackground,
        disabledContainerColor = harp,
        disabledContentColor = delta
    )
    val noButtonColor = ButtonDefaults.filledTonalButtonColors(
        containerColor = md_theme_light_secondaryContainer,
        contentColor = md_theme_light_onBackground,
        disabledContainerColor = harp,
        disabledContentColor = delta
    )
    if (visible) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = properties
        ) {
            Surface(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth(),
                color = md_theme_light_background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = outSideHorizontalPadding.dp,
                            vertical = outSideVerticalPadding.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Text(
                        text = message,
                        style = typography.titleMedium
                    )
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        if (noMessage.isNotEmpty()) {
                            FilledTonalButton(
                                contentPadding = PaddingValues(vertical = buttonVerticalPadding.dp),
                                modifier = Modifier
                                    .weight(1f),
                                colors = noButtonColor,
                                shape = RoundedCornerShape(5.dp),
                                onClick = { onDismissRequest() }
                            ) {
                                Text(
                                    text = noMessage,
                                    style = typography.bodyLarge
                                )
                            }
                        }
                        if (okMessage.isNotEmpty()) {
                            FilledTonalButton(
                                modifier = Modifier
                                    .weight(1f),
                                colors = okButtonColor,
                                shape = RoundedCornerShape(5.dp),
                                onClick = { onClickedOkButton() }
                            ) {
                                Text(
                                    text = okMessage,
                                    style = typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
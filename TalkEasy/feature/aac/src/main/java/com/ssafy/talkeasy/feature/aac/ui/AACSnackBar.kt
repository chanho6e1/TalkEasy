package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ssafy.talkeasy.feature.aac.R
import com.ssafy.talkeasy.feature.common.ui.theme.Typography
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onSecondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun BrowseLocation(name: String = "") {
    Card(
        Modifier.padding(horizontal = 24.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(delta)
    ) {
        ConstraintLayout {
            val (text, button) = createRefs()

            Box(
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(button.start, margin = 12.dp)
                    top.linkTo(parent.top, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    width = Dimension.preferredWrapContent
                }
            ) {
                Text(
                    modifier = Modifier.basicMarquee(),
                    color = md_theme_light_background,
                    textAlign = TextAlign.Start,
                    style = textStyleNormal22,
                    text = String.format(stringResource(R.string.content_browse_location), name)
                )
            }

            TextButton(
                modifier = Modifier.constrainAs(button) {
                    end.linkTo(parent.end, margin = 18.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(md_theme_light_secondaryContainer),
                onClick = { }
            ) {
                Text(
                    color = md_theme_light_onSecondaryContainer,
                    style = Typography.bodyLarge,
                    text = stringResource(R.string.content_stop_browse)
                )
            }
        }
    }
}
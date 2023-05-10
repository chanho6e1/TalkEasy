package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ssafy.talkeasy.feature.aac.R
import com.ssafy.talkeasy.feature.aac.SampleData.Companion.time
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onSecondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_outline
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold24
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22
import com.ssafy.talkeasy.feature.common.ui.theme.typography

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
                    style = typography.bodyLarge,
                    text = stringResource(R.string.content_stop_browse)
                )
            }
        }
    }
}

@Composable
fun RequestBrowse(profileUrl: String = "", memberName: String) {
    Surface(modifier = Modifier.fillMaxWidth(), color = md_theme_light_outline) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.padding(
                    top = 12.dp,
                    bottom = 12.dp,
                    start = 24.dp,
                    end = 14.dp
                )
            ) {
                Profile(profileUrl, 58)
            }

            Text(color = seed, style = textStyleBold24, text = memberName)

            Text(
                modifier = Modifier.weight(1f),
                color = md_theme_light_background,
                style = textStyleBold24,
                text = stringResource(R.string.content_request_browse)
            )

            OutlinedButton(
                modifier = Modifier.padding(horizontal = 12.dp),
                border = BorderStroke(width = 1.5.dp, color = md_theme_light_secondaryContainer),
                onClick = { }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = md_theme_light_background,
                    style = typography.bodyLarge,
                    text = stringResource(R.string.content_reject)
                )
            }

            FilledTonalButton(
                modifier = Modifier.padding(end = 24.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = md_theme_light_secondaryContainer,
                    contentColor = md_theme_light_onSecondaryContainer
                ),
                onClick = { }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = typography.bodyLarge,
                    text = String.format(stringResource(R.string.content_approve_with_time), time)
                )
            }
        }
    }
}
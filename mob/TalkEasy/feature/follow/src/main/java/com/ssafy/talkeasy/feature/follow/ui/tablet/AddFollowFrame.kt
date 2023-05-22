package com.ssafy.talkeasy.feature.follow.ui.tablet

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo
import com.ssafy.talkeasy.feature.common.component.NoContentLogoMessage
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_primary
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold22
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.follow.FollowViewModel
import com.ssafy.talkeasy.feature.follow.R

@Preview
@Composable
fun AddFollowFrame(
    onDismissListener: () -> Unit = {},
    followViewModel: FollowViewModel = viewModel(),
) {
    val memberInfo by followViewModel.memberInfo.collectAsState()

    Dialog(onDismissRequest = { onDismissListener() }) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .wrapContentHeight(),
            shape = shapes.medium,
            colors = CardDefaults.cardColors(containerColor = md_theme_light_background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(top = 24.dp, bottom = 32.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TopBar() { onDismissListener() }

                if (memberInfo != null) {
                    QRCode(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth(),
                        memberInfo = memberInfo!!
                    )
                } else {
                    Box(modifier = Modifier.height(200.dp)) {
                        NoContentLogoMessage(
                            message = stringResource(R.string.content_fail_to_load_member_info),
                            textStyle = typography.titleMedium,
                            width = 156,
                            height = 72,
                            betweenValue = 20
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(onDismissListener: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "친구 추가 QR 코드",
            color = md_theme_light_onBackground,
            style = textStyleBold22
        )

        TextButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            colors = ButtonDefaults.textButtonColors(
                contentColor = md_theme_light_primary,
                containerColor = Color.Transparent
            ),
            onClick = { onDismissListener() }
        ) {
            Text(
                text = "닫기",
                style = typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun QRCode(modifier: Modifier, memberInfo: MemberInfo) {
    val memberInfoString = Gson().toJson(
        mapOf(
            "userId" to memberInfo.userId,
            "userName" to memberInfo.userName,
            "imageUrl" to memberInfo.imageUrl,
            "gender" to memberInfo.gender,
            "birthDate" to memberInfo.birthDate
        )
    )
    val barcodeEncoder = BarcodeEncoder()
    val bitmap: Bitmap =
        barcodeEncoder.encodeBitmap(
            memberInfoString,
            BarcodeFormat.QR_CODE,
            500,
            500,
            mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
        )

    Image(
        modifier = modifier,
        bitmap = bitmap.asImageBitmap(),
        contentDescription = stringResource(R.string.image_add_follow_qr)
    )
}
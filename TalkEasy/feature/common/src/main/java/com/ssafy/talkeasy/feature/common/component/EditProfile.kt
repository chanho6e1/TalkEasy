package com.ssafy.talkeasy.feature.common.component

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.dimens
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.toast
import java.io.InputStream

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun <T> EditProfile(
    modifier: Modifier = Modifier,
    profile: T? = null,
    size: Int = 110,
    textStyle: TextStyle = typography.titleLarge,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.noRippleClickable { onClick() }, contentAlignment = Alignment.Center) {
        GlideImage(
            model = profile ?: R.drawable.ic_default_profile,
            contentDescription = stringResource(id = R.string.ic_default_profile_text),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(dimens)
        )
        Text(
            text = stringResource(id = R.string.content_select),
            color = md_theme_light_background,
            style = textStyle,
            fontWeight = FontWeight.Bold
        )
    }
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

@Composable
fun ShowProfileDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onDismissRequest: () -> Unit = {},
    onImagePicked: (Bitmap) -> Unit,
) {
    val context = LocalContext.current
    val takePhotoFromCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            onImagePicked(bitmap)
        } else {
            context.toast(context.getString(R.string.content_camera_failed_message))
        }
    }
    val takePhotoFromGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream.use {
                val bitmap = BitmapFactory.decodeStream(it)
                onImagePicked(bitmap)
            }
        } else {
            context.toast(context.getString(R.string.content_camera_failed_message))
        }
    }
    if (visible) {
        CustomAlertDialog(
            onDismissRequest = { onDismissRequest() },
            content = {
                Surface(
                    modifier = modifier
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .fillMaxWidth(),
                    color = md_theme_light_background
                ) {
                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = PaddingValues(18.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        item {
                            Text(
                                text = stringResource(id = R.string.title_photo_picker),
                                style = typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        item {
                            Text(
                                text = stringResource(id = R.string.content_camera_select),
                                style = typography.titleSmall,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .clickable {
                                        takePhotoFromCameraLauncher.launch()
                                    }
                            )
                        }
                        item {
                            Text(
                                text = stringResource(id = R.string.content_gallery_select),
                                style = typography.titleSmall,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .clickable {
                                        takePhotoFromGalleryLauncher.launch("image/*")
                                    }
                            )
                        }
                    }
                }
            }
        )
    }
}
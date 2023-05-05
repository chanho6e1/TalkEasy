package com.ssafy.talkeasy.feature.auth

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.talkeasy.feature.common.component.EditProfile
import com.ssafy.talkeasy.feature.common.component.ShowProfileDialog
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.green_white
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_primary
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
internal fun JoinRoute(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    JoinScreen(
        modifier = modifier,
        onJoinButtonClick = {
            viewModel.requestJoin(it)
        },
        onImagePicked = { bitmap ->
            viewModel.setProfileImg(bitmap, context.cacheDir.toString())
        }
    )
}

@Preview(showBackground = true)
@Composable
internal fun JoinScreen(
    modifier: Modifier = Modifier,
    onJoinButtonClick: (String) -> Unit = {},
    onImagePicked: (Bitmap) -> Unit = {},
) {
    val profileImage = remember { mutableStateOf<Bitmap?>(null) }
    val (dialogState: Boolean, setDialogState: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }

    Box() {
        ShowProfileDialog(
            modifier = modifier,
            visible = dialogState,
            onDismissRequest = { setDialogState(!dialogState) },
            onImagePicked = { bitmap ->
                profileImage.value = bitmap
                onImagePicked(bitmap)
                setDialogState(false)
            }
        )
        JoinContent(
            modifier = modifier,
            onJoinButtonClick = onJoinButtonClick,
            onProfileClick = {
                setDialogState(!dialogState)
            },
            profile = profileImage.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun <T> JoinContent(
    modifier: Modifier = Modifier,
    onJoinButtonClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    profile: T,
) {
    val mainButtonColor = ButtonDefaults.buttonColors(
        contentColor = md_theme_light_onBackground,
        containerColor = seed
    )
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
    val (nickName: String, setNickName: (String) -> Unit) = remember {
        mutableStateOf("")
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(34.dp, 18.dp)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = stringResource(R.string.title_member_info_input),
                style = typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            EditProfile(
                size = 110,
                profile = profile,
                textStyle = typography.titleLarge,
                onClick = { onProfileClick() }
            )
            OutlinedTextField(
                value = nickName,
                onValueChange = setNickName,
                modifier = modifier.fillMaxWidth(),
                textStyle = typography.bodyLarge,
                label = { Text(text = "이름") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = textFieldColors,
                singleLine = true
            )
            Button(
                modifier = modifier.fillMaxWidth(),
                onClick = { onJoinButtonClick(nickName) },
                colors = mainButtonColor,
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(0.dp, 14.dp)
            ) {
                Text(text = "가입하기", style = typography.bodyLarge)
            }
        }
    }
}
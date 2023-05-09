package com.ssafy.talkeasy.feature.auth.ui.mobile

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ssafy.talkeasy.feature.auth.AuthViewModel
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.component.CustomTextField
import com.ssafy.talkeasy.feature.common.component.EditProfile
import com.ssafy.talkeasy.feature.common.component.ShowProfileDialog
import com.ssafy.talkeasy.feature.common.component.WideSeedButton
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun JoinRouteProtector(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    viewModel: AuthViewModel = hiltViewModel(navBackStackEntry),
    onJoinMember: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val memberState by rememberUpdatedState(newValue = viewModel.memberState.collectAsState().value)

    LaunchedEffect(memberState) {
        if (memberState == "MEMBER") {
            keyboardController?.hide()
            onJoinMember()
        }
    }

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

@Composable
internal fun JoinContent(
    modifier: Modifier = Modifier,
    onJoinButtonClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    profile: Bitmap?,
) {
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
                textStyle = typography.titleLarge
            ) { onProfileClick() }

            CustomTextField(
                nickName = nickName,
                onValueChange = setNickName,
                label = stringResource(id = R.string.content_name),
                textStyle = typography.bodyLarge
            )

            WideSeedButton(
                onClicked = { onJoinButtonClick(nickName) },
                text = stringResource(id = R.string.content_join),
                textStyle = typography.bodyLarge
            )
        }
    }
}
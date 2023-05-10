package com.ssafy.talkeasy.feature.auth.ui.tablet

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.talkeasy.feature.auth.AuthViewModel
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background

@Composable
internal fun LoginRouteWard(
    modifier: Modifier = Modifier,
    onIsNotMember: () -> Unit,
    onIsLoginMember: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
    role: Int,
) {
    val memberState by viewModel.memberState.collectAsState()

    SideEffect {
        if (memberState == "MEMBER") {
            viewModel.resetMemberState()
        }
    }

    when (memberState) {
        "NOT_MEMBER" -> {
            onIsNotMember()
        }
        "MEMBER" -> {
            onIsLoginMember()
        }
    }

    LoginFrame(
        modifier = modifier,
        onSuccessEvent = viewModel::onKakaoLoginSuccess,
        role = role
    )
}

@Composable
@Preview(widthDp = 1429, heightDp = 857)
fun LoginFrame(
    modifier: Modifier = Modifier,
    onSuccessEvent: (String, Int) -> Unit = { _, _ -> },
    role: Int = 1,
) {
    val context = LocalContext.current
    val TAG = "KaKao-Login-Ward"

    fun onLoginButtonClicked() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                onSuccessEvent(token.accessToken, role)
            }
        }
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(color = md_theme_light_background) {
            Image(
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter,
                imageVector = ImageVector.vectorResource(id = drawable.bg_log_in_wave_for_tablet),
                contentDescription = stringResource(R.string.bg_main_wave_text),
                contentScale = ContentScale.FillWidth
            )

            Image(
                painter = painterResource(id = drawable.bg_log_in_right_leaf_for_tablet),
                contentDescription = stringResource(id = R.string.bg_log_in_right_leaf_text),
                modifier = Modifier
                    .size(450.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 9.dp, start = 155.dp)
            )

            Image(
                painter = painterResource(id = drawable.bg_log_in_left_leaf_for_tablet),
                contentDescription = stringResource(id = R.string.bg_log_in_left_leaf_text),
                modifier = Modifier
                    .size(450.dp)
                    .align(Alignment.BottomStart)
                    .padding(end = 128.dp, top = 95.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        painter = painterResource(id = drawable.bg_talkeasy_logo_verticcal_trans),
                        contentDescription = stringResource(id = R.string.image_logo),
                        modifier = Modifier
                            .padding(bottom = 101.dp)
                            .fillMaxSize()
                            .size(200.dp)
                    )
                }

                item {
                    IconButton(
                        onClick = { onLoginButtonClicked() },
                        modifier = Modifier
                            .fillMaxSize()
                            .size(60.dp)
                    ) {
                        Image(
                            painter = painterResource(id = drawable.ic_kakao_login),
                            contentDescription = stringResource(id = R.string.kakao_login_text),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
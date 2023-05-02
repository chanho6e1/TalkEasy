package com.ssafy.talkeasy.feature.auth.ui.mobile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.talkeasy.feature.auth.LoginViewModel
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_surface

@Composable
internal fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    LoginScreen(
        onSuccessEvent = viewModel::onKakaoLoginSuccess,
        modifier = modifier
    )
}

@Composable
internal fun LoginScreen(
    onSuccessEvent: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box() {
        Background(Modifier.fillMaxSize())
        LoginContent(
            modifier = modifier,
            onSuccessEvent = onSuccessEvent
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onSuccessEvent: (String) -> Unit,
) {
    val context = LocalContext.current
    val TAG = "KaKao-Login"

    fun onLoginButtonClicked() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                onSuccessEvent(token.accessToken)
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

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(id = drawable.bg_talkeasy_logo_verticcal_trans),
                contentDescription = stringResource(id = R.string.app_logo_text),
                modifier = modifier
                    .padding(bottom = 60.dp)
                    .fillMaxSize()
            )
        }
        item {
            IconButton(
                onClick = { onLoginButtonClicked() },
                modifier = modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = drawable.ic_kakao_login),
                    contentDescription = stringResource(id = R.string.kakao_login_text)
                )
            }
        }
    }
}

@Composable
fun Background(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Surface(color = md_theme_light_surface) {
            Image(
                painter = painterResource(id = drawable.bg_main_wave),
                contentDescription = stringResource(id = R.string.bg_main_wave_text),
                modifier = modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter
            )
            Image(
                painter = painterResource(id = drawable.bg_main_leaf_big),
                contentDescription = stringResource(id = R.string.bg_main_leaf_big_text),
                modifier = modifier.align(Alignment.TopEnd)
            )
            Image(
                painter = painterResource(id = drawable.bg_main_leaf_small),
                contentDescription = stringResource(id = R.string.bg_main_leaf_small_text),
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 51.dp)
            )
        }
    }
}
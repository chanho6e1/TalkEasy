package com.ssafy.talkeasy.feature.auth

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Auth
import com.ssafy.talkeasy.core.domain.usecase.auth.JoinUseCase
import com.ssafy.talkeasy.core.domain.usecase.auth.LoginUseCase
import com.ssafy.talkeasy.feature.common.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val logInUseCase: LoginUseCase,
    private val joinUseCase: JoinUseCase,
) : ViewModel() {

    private val _accessToken: MutableState<String?> = mutableStateOf("")
    val accessToken: State<String?> = _accessToken

    fun setAccessToken(accessToken: String) {
        _accessToken.value = accessToken
    }

    fun requestLogin(accessToken: String) = viewModelScope.launch {
        when (val value = logInUseCase(accessToken)) {
            is Resource.Success<Auth> -> {
                if (value.code == 200) {
                    // login success
                    sharedPreferences.accessToken = value.data.data
                } else if (value.code == 201) {
                    // no member
                }
            }
            is Resource.Error -> Log.e("requestLogin", "requestLogin: ${value.errorMessage}")
        }
    }

    fun requestJoin(member: MemberRequestBody) = viewModelScope.launch {
        when (val value = joinUseCase(member)) {
            is Resource.Success<Auth> -> {
                sharedPreferences.accessToken = value.data.data
            }
            is Resource.Error ->
                Log.e("requestJoin", "requestJoin: ${value.errorMessage}")
        }
    }

    fun onKakaoLoginSuccess(accessToken: String) {
        setAccessToken(accessToken)
        requestLogin(accessToken)
    }
}
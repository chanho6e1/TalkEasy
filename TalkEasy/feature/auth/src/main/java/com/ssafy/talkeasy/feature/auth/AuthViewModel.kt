package com.ssafy.talkeasy.feature.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.usecase.auth.JoinUseCase
import com.ssafy.talkeasy.core.domain.usecase.auth.LoginUseCase
import com.ssafy.talkeasy.feature.common.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val logInUseCase: LoginUseCase,
    private val joinUseCase: JoinUseCase,
) : ViewModel() {

    private val accessToken = MutableStateFlow("")

    private val _isNewMember = MutableStateFlow(false)
    val isNewMember: StateFlow<Boolean> = _isNewMember

    private val profileImg = MutableStateFlow("")

    private fun requestLogin(accessToken: String) = viewModelScope.launch {
        when (val value = logInUseCase(accessToken)) {
            is Resource.Success<Default<String>> -> {
                if (value.data.status == 200) {
                    // login success
                    _isNewMember.value = false
                    sharedPreferences.accessToken = value.data.data
                } else if (value.data.status == 201) {
                    // no member
                    _isNewMember.value = true
                }
            }
            is Resource.Error -> Log.e("requestLogin", "requestLogin: ${value.errorMessage}")
        }
    }

    fun requestJoin(nickname: String) = viewModelScope.launch {
        val member = sharedPreferences.accessToken?.let {
            MemberRequestBody(
                accessToken = it,
                name = nickname,
                imageUrl = profileImg.value
            )
        }
        member?.let {
            when (val value = joinUseCase(member)) {
                is Resource.Success<Default<String>> -> {
                    if (value.data.status == 201) {
                        // login success
                        _isNewMember.value = false
                        sharedPreferences.accessToken = value.data.data
                    }
                }
                is Resource.Error ->
                    Log.e("requestJoin", "requestJoin: ${value.errorMessage}")
            }
        }
    }

    fun onKakaoLoginSuccess(accessToken: String) {
        this.accessToken.value = accessToken
        requestLogin(accessToken)
    }
}
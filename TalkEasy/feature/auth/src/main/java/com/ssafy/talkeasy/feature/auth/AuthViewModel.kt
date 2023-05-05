package com.ssafy.talkeasy.feature.auth

import android.graphics.Bitmap
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
import java.io.File
import java.io.FileOutputStream
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

    // Kakao Access Token
    private val accessToken = MutableStateFlow("")

    private val _isNewMember = MutableStateFlow(false)
    val isNewMember: StateFlow<Boolean> = _isNewMember

    private val profileImg = MutableStateFlow<File?>(null)

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
        val member = MemberRequestBody(
            accessToken = accessToken.value,
            role = 0,
            name = nickname,
            image = profileImg.value
        )
        member.let {
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

    fun setProfileImg(profile: Bitmap, strFilePath: String) {
        bitmapConvertFile(profile, strFilePath)
    }

    private fun bitmapConvertFile(bitmap: Bitmap, strFilePath: String) {
        val file = File("$strFilePath/profile.png")
        try {
            file.createNewFile()
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)
                profileImg.value = file
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
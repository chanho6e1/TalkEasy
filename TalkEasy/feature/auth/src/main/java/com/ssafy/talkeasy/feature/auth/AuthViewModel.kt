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

    private val kakaoAccessToken = MutableStateFlow("")

    // MEMBER, NOT_MEMBER
    private val _memberState = MutableStateFlow("")
    val memberState: StateFlow<String> = _memberState

    private val profileImg = MutableStateFlow<File?>(null)

    private val role = MutableStateFlow(0)

    private val _gender = MutableStateFlow(0)

    private val _birthDate = MutableStateFlow("")

    fun resetMemberState() {
        _memberState.value = ""
    }

    private fun requestLogin(accessToken: String, role: Int) = viewModelScope.launch {
        when (val value = logInUseCase(accessToken, role)) {
            is Resource.Success<Default<String>> -> {
                if (value.data.status == 200) {
                    // login success
                    _memberState.value = "MEMBER"
                    sharedPreferences.accessToken = value.data.data
                } else if (value.data.status == 201) {
                    // no member
                    _memberState.value = "NOT_MEMBER"
                }
            }
            is Resource.Error -> Log.e("requestLogin", "requestLogin: ${value.errorMessage}")
        }
        Log.d("requestLogin", "requestLogin-JWT : ${sharedPreferences.accessToken}")
    }

    fun requestJoin(nickname: String) = viewModelScope.launch {
        val member = MemberRequestBody(
            accessToken = kakaoAccessToken.value,
            role = role.value,
            name = nickname,
            birthDate = _birthDate.value,
            gender = _gender.value
        )
        member.let {
            when (val value = joinUseCase(member, profileImg.value)) {
                is Resource.Success<Default<String>> -> {
                    if (value.data.status == 201) {
                        // login success
                        _memberState.value = "MEMBER"
                        sharedPreferences.accessToken = value.data.data
                    }
                }
                is Resource.Error ->
                    Log.e("requestJoin", "requestJoin: ${value.errorMessage}")
            }
        }
    }

    fun onKakaoLoginSuccess(accessToken: String, role: Int) {
        kakaoAccessToken.value = accessToken
        requestLogin(accessToken, role)
        this.role.value = role
    }

    fun setProfileImg(profile: Bitmap, strFilePath: String) {
        bitmapConvertFile(profile, strFilePath)
    }

    fun setGender(mGender: String) {
        when (mGender) {
            "여성" -> _gender.value = 0
            "남성" -> _gender.value = 1
        }
    }

    fun setBirthDate(mBirthDate: String) {
        _birthDate.value = mBirthDate
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
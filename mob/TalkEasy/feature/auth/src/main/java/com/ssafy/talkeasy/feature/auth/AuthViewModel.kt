package com.ssafy.talkeasy.feature.auth

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Auth
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.usecase.auth.JoinUseCase
import com.ssafy.talkeasy.core.domain.usecase.auth.LoginUseCase
import com.ssafy.talkeasy.core.domain.usecase.fcm.RegisterFCMTokenUseCase
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
    private val fcmTokenUseCase: RegisterFCMTokenUseCase,
) : ViewModel() {

    private val kakaoAccessToken = MutableStateFlow("")

    // MEMBER, NOT_MEMBER
    private val _memberState = MutableStateFlow("")
    val memberState: StateFlow<String> = _memberState

    private val profileImg = MutableStateFlow<File?>(null)

    private val role = MutableStateFlow(0)

    private val gender = MutableStateFlow(1)

    private val birthDate = MutableStateFlow("")

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun resetMemberState() {
        _memberState.value = ""
    }

    private fun requestLogin(accessToken: String, role: Int) = viewModelScope.launch {
        when (val value = logInUseCase(accessToken, role)) {
            is Resource.Success<Default<Auth>> -> {
                if (value.data.status == 200) {
                    // login success
                    _memberState.value = "MEMBER"
                    sharedPreferences.accessToken = value.data.data.token
                    _name.value = value.data.data.name!!
                    Log.i("requestLogin", "requestLogin-JWT : ${sharedPreferences.accessToken}")
                } else if (value.data.status == 201) {
                    // no member
                    _memberState.value = "NOT_MEMBER"
                }
            }

            is Resource.Error -> Log.e("requestLogin", "requestLogin: ${value.errorMessage}")
        }
    }

    fun requestJoin(nickname: String) = viewModelScope.launch {
        val member = MemberRequestBody(
            accessToken = kakaoAccessToken.value,
            role = role.value,
            name = nickname,
            birthDate = birthDate.value,
            gender = gender.value
        )
        member.let {
            when (val value = joinUseCase(member, profileImg.value)) {
                is Resource.Success<Default<String>> -> {
                    if (value.data.status == 201) {
                        // login success
                        _memberState.value = "MEMBER"
                        _name.value = nickname
                        sharedPreferences.accessToken = value.data.data
                        _name.value = nickname
                    }
                }

                is Resource.Error ->
                    Log.e("requestJoin", "requestJoin: ${value.errorMessage}")
            }
        }
    }

    fun registerFCMToken() = viewModelScope.launch {
        if (sharedPreferences.appToken != null) {
            when (val value = fcmTokenUseCase(sharedPreferences.appToken!!)) {
                is Resource.Success<Default<String>> -> {}
                is Resource.Error ->
                    Log.e("registerFCMToken", "registerFCMToken: ${value.errorMessage}")
            }
        } else {
            Log.e("registerFCMToken", "registerFCMToken: appToken이 없습니다.")
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
            "남성" -> gender.value = 0
            "여성" -> gender.value = 1
        }
    }

    fun setBirthDate(mBirthDate: String) {
        birthDate.value = mBirthDate
    }

    private fun bitmapConvertFile(bitmap: Bitmap, strFilePath: String) {
        val file = File("$strFilePath/profile.png")
        try {
            file.createNewFile()
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
                profileImg.value = file
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
package com.ssafy.talkeasy.feature.follow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo
import com.ssafy.talkeasy.core.domain.usecase.follow.FollowListUseCase
import com.ssafy.talkeasy.core.domain.usecase.member.MemberInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val memberInfoUseCase: MemberInfoUseCase,
    private val followListUseCase: FollowListUseCase,
) : ViewModel() {

    private val _memberInfo = MutableStateFlow<MemberInfo?>(null)
    val memberInfo: StateFlow<MemberInfo?> = _memberInfo

    fun requestMemberInfo() = viewModelScope.launch {
        when (val value = memberInfoUseCase()) {
            is Resource.Success<Default<MemberInfo>> -> {
                if (value.data.status == 200) {
                    _memberInfo.value = value.data.data
                }
            }
            is Resource.Error -> Log.e(
                "requestMemberInfo",
                "requestMemberInfo: ${value.errorMessage}"
            )
        }
    }
}
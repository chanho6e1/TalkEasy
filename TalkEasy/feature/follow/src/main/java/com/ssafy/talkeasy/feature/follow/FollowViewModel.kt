package com.ssafy.talkeasy.feature.follow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.AddFollowDetailInfo
import com.ssafy.talkeasy.core.domain.entity.request.SosAlarmRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo
import com.ssafy.talkeasy.core.domain.entity.response.MyNotificationItem
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault
import com.ssafy.talkeasy.core.domain.usecase.follow.FollowListUseCase
import com.ssafy.talkeasy.core.domain.usecase.follow.NotificationListUseCase
import com.ssafy.talkeasy.core.domain.usecase.follow.RequestFollowUseCase
import com.ssafy.talkeasy.core.domain.usecase.follow.RequestSaveWardSOSUseCase
import com.ssafy.talkeasy.core.domain.usecase.member.MemberInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val memberInfoUseCase: MemberInfoUseCase,
    private val followListUseCase: FollowListUseCase,
    private val notificationListUseCase: NotificationListUseCase,
    private val requestFollowUseCase: RequestFollowUseCase,
    private val requestSaveWardSOSUseCase: RequestSaveWardSOSUseCase,
) : ViewModel() {

    private val _memberInfo = MutableStateFlow<MemberInfo?>(null)
    val memberInfo: StateFlow<MemberInfo?> = _memberInfo

    private val _followList = MutableStateFlow<List<Follow>?>(null)
    val followList: StateFlow<List<Follow>?> = _followList

    private val _selectFollow = MutableStateFlow<Follow?>(null)
    val selectFollow: StateFlow<Follow?> = _selectFollow

    private val _addFollowInfo: MutableStateFlow<AddFollowDetailInfo?> = MutableStateFlow(null)
    val addFollowInfo: StateFlow<AddFollowDetailInfo?> = _addFollowInfo

    private val _notificationList = MutableStateFlow<List<MyNotificationItem>?>(null)
    val notificationList: StateFlow<List<MyNotificationItem>?> = _notificationList

    private val _selectNotification = MutableStateFlow<MyNotificationItem?>(null)
    val selectNotification: StateFlow<MyNotificationItem?> = _selectNotification

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

    fun requestFollowList() = viewModelScope.launch {
        when (val value = followListUseCase()) {
            is Resource.Success<PagingDefault<List<Follow>>> -> {
                if (value.data.status == 200) {
                    _followList.value = value.data.data
                }
            }

            is Resource.Error -> Log.e(
                "requestFollowList",
                "requestFollowList: ${value.errorMessage}"
            )
        }
    }

    fun getAddFollowDetailInfo(addFollowDetailInfo: AddFollowDetailInfo) = viewModelScope.launch {
        _addFollowInfo.value = addFollowDetailInfo
    }

    fun requestFollow(toUserId: String, memo: String) = viewModelScope.launch {
        when (val value = requestFollowUseCase(toUserId, memo)) {
            is Resource.Success<String> -> {}

            is Resource.Error -> {
                Log.e("requestFollow", "requestFollow: ${value.errorMessage}")
            }
        }
    }

    fun setSelectFollow(follow: Follow) {
        _selectFollow.value = follow
    }

    fun setSelectNotification(notificationItem: MyNotificationItem) {
        _selectNotification.value = notificationItem
    }

    fun requestNotificationList() = viewModelScope.launch {
        when (val value = notificationListUseCase()) {
            is Resource.Success<Default<List<MyNotificationItem>>> -> {
                if (value.data.status == 200) {
                    _notificationList.value = value.data.data
                }
            }

            is Resource.Error -> Log.e(
                "requestNotificationList",
                "requestFollowList: ${value.errorMessage}"
            )
        }
    }

    fun requestSaveWardSOS() = viewModelScope.launch {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Date(System.currentTimeMillis())
        val time = dateFormat.format(currentTime)

        when (val value = requestSaveWardSOSUseCase(SosAlarmRequestBody(time))) {
            is Resource.Success<Default<String>> -> {}

            is Resource.Error -> Log.e(
                "requestSaveWardSOS",
                "requestSaveWardSOS: ${value.errorMessage}"
            )
        }
    }
}
package com.ssafy.talkeasy

import androidx.lifecycle.ViewModel
import com.ssafy.talkeasy.core.fcm.FirebaseTokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseTokenProvider: FirebaseTokenProvider,
) : ViewModel() {

    fun getFirebaseToken() {
        firebaseTokenProvider.getFirebaseToken()
    }
}
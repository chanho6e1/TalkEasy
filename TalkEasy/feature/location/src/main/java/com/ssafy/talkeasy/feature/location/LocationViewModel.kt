package com.ssafy.talkeasy.feature.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.talkeasy.core.domain.entity.response.LocationsInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel @Inject constructor() : ViewModel() {

    private val _locationsInfo = MutableStateFlow<LocationsInfo?>(null)
    val locationsInfo: StateFlow<LocationsInfo?> = _locationsInfo

    private val _isLocationOpenAccepted = MutableStateFlow(false)
    val isLocationOpenAccepted: StateFlow<Boolean> = _isLocationOpenAccepted

    fun requestLocationInfo() = viewModelScope.launch {
    }

    fun requestAcceptLocationInfo() = viewModelScope.launch {
    }
}
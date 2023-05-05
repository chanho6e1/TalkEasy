package com.ssafy.talkeasy.core.data.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

/**
 * 네트워크 상태 파악 class
 * **/

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : NetworkMonitor {

    override val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()

        val callback = object : NetworkCallback() {

            override fun onAvailable(network: Network) {
                // 네트워크가 연결될 때 호출됩니다.
                channel.trySend(connectivityManager.isCurrentlyConnected())
            }

            override fun onLost(network: Network) {
                // 네트워크가 끊길 때 호출됩니다.
                channel.trySend(connectivityManager.isCurrentlyConnected())
            }
        }
        connectivityManager?.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            callback
        )

        channel.trySend(connectivityManager.isCurrentlyConnected())

        awaitClose { connectivityManager?.unregisterNetworkCallback(callback) }
    }.conflate()

    private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
        null -> false
        else -> when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ->
                activeNetwork
                    ?.let(::getNetworkCapabilities)
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
            else -> activeNetworkInfo?.isConnected ?: false
        }
    }
}
package com.ssafy.talkeasy.feature.common

import android.content.Context

class SharedPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) = prefs.edit().putString("accessToken", value).apply()

    var isFirstRun: Boolean
        get() = prefs.getBoolean("isFirstRun", true)
        set(value) = prefs.edit().putBoolean("isFirstRun", value).apply()

    var onRight: Boolean
        get() = prefs.getBoolean("onRight", true)
        set(value) = prefs.edit().putBoolean("onRight", value).apply()

    var appToken: String?
        get() = prefs.getString("appToken", null)
        set(value) = prefs.edit().putString("appToken", value).apply()

    fun clearPreferences() {
        prefs.edit().clear().apply()
    }
}
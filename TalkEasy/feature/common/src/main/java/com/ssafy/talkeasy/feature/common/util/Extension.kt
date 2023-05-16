package com.ssafy.talkeasy.feature.common.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String.toTimeString(): String {
    val dateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return dateTime.format(DateTimeFormatter.ofPattern("a HH:mm", Locale.KOREA))
}

fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val currentTime = Date(System.currentTimeMillis())
    return dateFormat.format(currentTime)
}
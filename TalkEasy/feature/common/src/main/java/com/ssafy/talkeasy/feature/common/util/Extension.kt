package com.ssafy.talkeasy.feature.common.util

import android.content.Context
import android.widget.Toast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String.toTimeString(): String {
    val dateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return dateTime.format(DateTimeFormatter.ofPattern("a HH:mm", Locale.KOREA))
}
package com.ssafy.talkeasy.feature.aac.ui

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

fun ttsPlay(context: Context, ttsMp3Url: String) {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
    )

    mediaPlayer.setDataSource(context, Uri.parse(ttsMp3Url))
    mediaPlayer.prepare()
    mediaPlayer.start()
}
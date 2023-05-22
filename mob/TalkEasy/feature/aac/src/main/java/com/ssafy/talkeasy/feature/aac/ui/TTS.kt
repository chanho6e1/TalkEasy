package com.ssafy.talkeasy.feature.aac.ui

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

fun ttsPlay(context: Context, ttsMp3Url: String) {
    val mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )

        setDataSource(context, Uri.parse(ttsMp3Url))
        setOnPreparedListener { start() } // 준비가 완료되면 자동으로 시작
        prepareAsync()
    }

    // 재생이 완료되면 MediaPlayer를 리셋하고 해제
    mediaPlayer.setOnCompletionListener {
        it.reset()
        it.release()
    }
}
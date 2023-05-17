package com.ssafy.talkeasy.feature.location.stomp

import android.util.Log
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject
import okhttp3.OkHttpClient

class StompConnection @Inject constructor(private val stompClient: StompClient) {

    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable

    fun connect() {
        // connect
        stompConnection = stompClient.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {

                }

                Event.Type.CLOSED -> {

                }

                Event.Type.ERROR -> {

                }

                else -> {}
            }
        }
    }

    fun disconnect() {
        // disconnect
        stompConnection.dispose()
    }

    fun subscribe() {
        // subscribe
        topic = stompClient.join("/sub/1").subscribe {
            Log.i("스톰프 구독", it)
        }
    }

    fun unsubscribe() {
        // unsubscribe
        topic.dispose()
    }

    fun send() {
        // send
        stompClient.send("/sub/1", "바보야")
        // .subscribe {
        //     if (it) {
        //     }
        // }
    }
}

fun connectionTest() {

    val logger = Logger.getLogger("Main")

    var stompConnection: Disposable
    var topic: Disposable

    val localhost = "ws://localhost:8081/ws-stomp"
    val intervalMillis = 1000L
    val client = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                it.request().newBuilder().header(
                    "Authorization",
                    ""
                ).build()
            )
        }
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    val stomp = StompClient(client, intervalMillis).apply { this@apply.url = localhost }

    // connect
    stompConnection = stomp.connect().subscribe {
        when (it.type) {
            Event.Type.OPENED -> {

                // subscribe
                topic = stomp.join("/chains/costa/faces")
                    .subscribe { logger.log(Level.INFO, it) }

                //                // unsubscribe
                //                topic.dispose()

                // send

                //                stomp.send("/app/hello", Base64.getEncoder().encodeToString(File("/home/bishoybasily/Desktop/input.jpg").readBytes())).subscribe {
                //                    if (it) {
                //                    }
                //                }

            }

            Event.Type.CLOSED -> {

            }

            Event.Type.ERROR -> {

            }

            else -> {}
        }
    }

    // val scanner = Scanner(System.`in`)
    // scanner.nextLine()

    //    // disconnect
    //    stompConnection.dispose()

}
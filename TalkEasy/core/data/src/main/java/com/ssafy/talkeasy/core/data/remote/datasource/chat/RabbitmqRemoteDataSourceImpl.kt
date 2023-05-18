package com.ssafy.talkeasy.core.data.remote.datasource.chat

import android.util.Log
import com.google.gson.Gson
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.ssafy.talkeasy.core.data.common.util.Constants.RABBITMQ_URL
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class RabbitmqRemoteDataSourceImpl @Inject constructor(
    private val gson: Gson,
) : RabbitmqRemoteDataSource {

    private lateinit var connection: Connection
    private lateinit var channel: Channel
    private val factory: ConnectionFactory = ConnectionFactory().apply {
        setUri(RABBITMQ_URL)
    }
    private var chatConsumerTag: String? = null
    private var readConsumerTag: String? = null

    private fun connect() {
        try {
            if (!::connection.isInitialized || !connection.isOpen) {
                connection = factory.newConnection()
                channel = connection.createChannel()
                channel.confirmSelect()
                channel.basicQos(1)
            }
        } catch (e: Exception) {
            Log.e("Rabbitmq", "Error connecting to RabbitMQ", e)
        }
    }

    override suspend fun sendChatMessage(message: MessageRequest): Boolean {
        connect()

        val queueName = "chat.queue"
        val messageJson = gson.toJson(message).toByteArray()

        return try {
            channel.basicPublish("", queueName, null, messageJson)
            channel.waitForConfirms()
        } catch (e: Exception) {
            Log.e("Rabbitmq", "Error sending chat message", e)
            false
        }
    }

    override suspend fun receiveChatMessage(
        roomId: String,
        fromUserId: String,
        callback: (Any) -> Unit,
    ) {
        connect()
        val chatQueueName = "chat.queue.$roomId.$fromUserId"
        val readQueueName = "read.queue.$roomId.$fromUserId"

        val deliverCallback = DeliverCallback { _, delivery ->
            try {
                val messageJson = String(delivery.body, StandardCharsets.UTF_8)

                val key = messageJson.split("\"")
                when (key[1]) {
                    "msgId" -> {
                        val message = gson.fromJson(messageJson, ReadResponse::class.java)
                        callback(message.toDomainModel())
                    }
                    "id" -> {
                        val message = gson.fromJson(messageJson, ChatResponse::class.java)
                        callback(message.toDomainModel())
                    }
                    else -> callback("형식이 아닙니다.")
                }
            } catch (e: Exception) {
                Log.e("Rabbitmq", "Error in deliver callback", e)
            }
        }

        try {
            chatConsumerTag =
                channel.basicConsume(chatQueueName, true, deliverCallback, CancelCallback { })
            readConsumerTag =
                channel.basicConsume(readQueueName, true, deliverCallback, CancelCallback { })
        } catch (e: Exception) {
            Log.e("Rabbitmq", "Error setting up message consumption", e)
            chatConsumerTag = reconnectAndConsume(chatQueueName, deliverCallback)
            readConsumerTag = reconnectAndConsume(readQueueName, deliverCallback)
        }
    }

    override suspend fun readChatMessage(readMessageRequest: ReadMessageRequest): Boolean {
        connect()
        val queueName = "read.queue"
        val messageJson = gson.toJson(readMessageRequest).toByteArray()
        return try {
            channel.basicPublish("", queueName, null, messageJson)
            channel.waitForConfirms()
        } catch (e: Exception) {
            Log.e("Rabbitmq", "Error reading chat message", e)
            false
        }
    }

    override suspend fun stopReceiveMessage() {
        chatConsumerTag?.let {
            if (::channel.isInitialized && channel.isOpen) {
                try {
                    channel.basicCancel(it)
                } catch (e: Exception) {
                    Log.e("Rabbitmq", "Error stopping message receipt", e)
                }
            }
        }
        readConsumerTag?.let {
            if (::channel.isInitialized && channel.isOpen) {
                try {
                    channel.basicCancel(it)
                } catch (e: Exception) {
                    Log.e("Rabbitmq", "Error stopping message receipt", e)
                }
            }
        }
    }

    override suspend fun disconnect() {
        if (::connection.isInitialized && connection.isOpen) {
            try {
                channel.close()
                connection.close()
            } catch (e: Exception) {
                Log.e("Rabbitmq", "Error disconnecting from RabbitMQ", e)
            }
        }
    }

    private fun reconnectAndConsume(queueName: String, deliverCallback: DeliverCallback): String {
        channel.close()
        connect()
        return channel.basicConsume(queueName, true, deliverCallback, CancelCallback { })
    }
}
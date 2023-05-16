package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.google.gson.Gson
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.ssafy.talkeasy.core.data.common.util.Constants.RABBITMQ_URL
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest
import com.ssafy.talkeasy.core.domain.entity.response.Chat
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
    private var consumerTag: String? = null

    private fun connect() {
        if (!::connection.isInitialized || !connection.isOpen) {
            connection = factory.newConnection()
            channel = connection.createChannel()
            channel.confirmSelect()
        }
    }

    override suspend fun sendChatMessage(message: MessageRequest): Boolean {
        connect()

        val queueName = "chat.queue"
        val messageJson = gson.toJson(message).toByteArray()

        channel.basicPublish("", queueName, null, messageJson)
        return channel.waitForConfirms()
    }

    override suspend fun receiveChatMessage(
        roomId: String,
        fromUserId: String,
        callback: (Chat) -> Unit,
    ) {
        connect()
        val queueName = "chat.queue.$roomId.$fromUserId"

        val deliverCallback = DeliverCallback { _, delivery ->
            val messageJson = String(delivery.body, StandardCharsets.UTF_8)
            val message = gson.fromJson(messageJson, ChatResponse::class.java)
            callback(message.toDomainModel())
        }
        consumerTag = channel.basicConsume(queueName, true, deliverCallback, CancelCallback { })
    }

    override suspend fun readChatMessage(readMessageRequest: ReadMessageRequest): Boolean {
        connect()
        val queueName = "read.queue"
        val messageJson = gson.toJson(readMessageRequest).toByteArray()
        channel.basicPublish("", queueName, null, messageJson)
        return channel.waitForConfirms()
    }

    override suspend fun stopReceiveMessage() {
        consumerTag?.let {
            if (::channel.isInitialized && channel.isOpen) {
                try {
                    channel.basicCancel(it)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override suspend fun disconnect() {
        if (::connection.isInitialized && connection.isOpen) {
            channel.close()
            connection.close()
        }
    }
}
package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import com.ssafy.talkeasy.core.data.common.util.Constants.RABBITMQ_URL
import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.ChatApiService
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CompletableDeferred

class ChatRemoteDataSourceImpl @Inject constructor(
    private val chatApiService: ChatApiService,
) : ChatRemoteDataSource {

    private val factory = ConnectionFactory()

    init {
        factory.setUri(RABBITMQ_URL)
    }

    override suspend fun getChatHistory(
        roomId: String,
        offset: Int,
        size: Int,
    ): PagingDefaultResponse<List<ChatResponse>> =
        chatApiService.getChatHistory(roomId, offset, size)

    override suspend fun sendMessage(message: MessageRequest): RabbitMqResponse {
        val gson = Gson()

        val connection = factory.newConnection()
        val channel = connection.createChannel()

        val replyQueueName = channel.queueDeclare().queue
        val correlationId = UUID.randomUUID().toString()

        val props = AMQP.BasicProperties
            .Builder()
            .correlationId(correlationId)
            .replyTo(replyQueueName)
            .build()

        val messageJson = gson.toJson(message).toByteArray()
        channel.basicPublish("", "chat.queue", props, messageJson)

        val response = CompletableDeferred<RabbitMqResponse>()

        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String,
                envelope: Envelope,
                properties: AMQP.BasicProperties,
                body: ByteArray,
            ) {
                if (properties.correlationId == correlationId) {
                    response.complete(gson.fromJson(body.toString(), RabbitMqResponse::class.java))
                }
            }
        }

        channel.basicConsume(replyQueueName, false, consumer)

        return response.await()
    }
}
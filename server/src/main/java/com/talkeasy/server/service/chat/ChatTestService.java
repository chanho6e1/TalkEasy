package com.talkeasy.server.service.chat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.talkeasy.server.dto.chat.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class ChatTestService {

    public Object receiveMessage(String roomId, String receiveUserId) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("k8d207.p.ssafy.io"); // RabbitMQ 서버 호스트 이름 또는 IP 주소
        factory.setUsername("guest"); // RabbitMQ 사용자 이름
        factory.setPassword("guest"); // RabbitMQ 사용자 비밀번호
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 큐 이름
        String queueName = "chat.queue." + roomId + "." + receiveUserId;
        List<String> listTemp = new ArrayList<>();

        AtomicReference<String> list = new AtomicReference<>("");
        // 큐에서 메시지 받기
        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            String messageContent = new String(message.getBody());
            // 받은 메시지 처리
            System.out.println("Received message: " + messageContent);
            listTemp.add(messageContent);

        }, consumerTag -> {
        });

        return listTemp;
    }


    public static String sendMessage(String messageContent) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("k8d207.p.ssafy.io"); // RabbitMQ 서버 호스트 이름 또는 IP 주소
        factory.setUsername("guest"); // RabbitMQ 사용자 이름
        factory.setPassword("guest"); // RabbitMQ 사용자 비밀번호
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 큐 이름
            String queueName = "chat.queue";

            // JSON 문자열을 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            MessageDto chatMessage = objectMapper.readValue(messageContent, MessageDto.class);

            // 메시지 내용을 바이트 배열로 직렬화
            byte[] messageBytes = objectMapper.writeValueAsString(chatMessage).getBytes(StandardCharsets.UTF_8);

            // 큐에 메시지 전송
            channel.basicPublish("", queueName, null, messageBytes);
        }

        return messageContent;
    }

}

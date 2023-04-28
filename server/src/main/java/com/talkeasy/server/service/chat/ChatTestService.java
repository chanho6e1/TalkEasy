package com.talkeasy.server.service.chat;

import com.rabbitmq.client.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatTestService {
    public Object receiveMessage(String roomId, String receiveUserId, String queueName1) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("k8d207.p.ssafy.io"); // RabbitMQ 서버 호스트 이름 또는 IP 주소
        factory.setUsername("guest"); // RabbitMQ 사용자 이름
        factory.setPassword("guest"); // RabbitMQ 사용자 비밀번호
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 큐 이름
        String queueName = queueName1 +".queue." + roomId + "." + receiveUserId;
        log.info("queueName {}", queueName);
        List<String> list = new ArrayList<>();

        // 큐에서 메시지 받기
        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            String messageContent = new String(message.getBody());
            // 받은 메시지 처리
            System.out.println("Received message: " + messageContent);
            list.add(messageContent);
        }, consumerTag -> {});
        return list;

    }


}

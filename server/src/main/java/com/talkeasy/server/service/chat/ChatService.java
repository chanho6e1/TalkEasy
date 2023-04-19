package com.talkeasy.server.service.chat;

import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
    private final KafkaAdmin kafkaAdmin;
    private final KafkaListenerEndpointRegistry registry;

    public String createChatRoom(Long sendId, Long receiveId, String title) {
        ChatRoom chatRoom = new ChatRoom(new Long[]{sendId, receiveId}, title, LocalDateTime.now().toString());
        ChatRoom newRoom = mongoTemplate.insert(chatRoom);
        String topicName  = newRoom.getId();

        // 초대된 유저들이 토픽을 구독하도록 코드 추가




        NewTopic newTopic = new NewTopic(topicName, 1, (short) 1); // 파티션 추후 증가
        kafkaAdmin.createOrModifyTopics(newTopic);
        
        //상대방도 토픽 구독 해야함

        return newRoom.getId();

 }

    public Object sendMessage(MessageDto messageDto) {

        log.info("send Message : " + messageDto.getMsg());
        kafkaTemplate.send(messageDto.getRoomId(), messageDto);

        ChatRoomDetail chatRoom = new ChatRoomDetail(messageDto.getSender(), messageDto.getRoomId(), messageDto.getMsg(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        ChatRoomDetail newChat = mongoTemplate.insert(chatRoom);

        return newChat.getId();
    }



    private ConsumerFactory<String, MessageDto> kafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }


}
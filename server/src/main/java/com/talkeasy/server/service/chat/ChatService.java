package com.talkeasy.server.service.chat;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.domain.Test;
import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.ChatRoomDetailResponseDto;
import com.talkeasy.server.dto.ChatRoomResponseDto;
import com.talkeasy.server.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
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
import org.springframework.data.mongodb.core.query.Query;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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


        NewTopic newTopic = new NewTopic(topicName, 1, (short) 1); // 파티션 추후 증가
        kafkaAdmin.createOrModifyTopics(newTopic);
        
        return newRoom.getId();

 }

    public Object sendMessage(MessageDto messageDto) {

        log.info("send Message : " + messageDto.getMsg());
        kafkaTemplate.send(messageDto.getRoomId(), messageDto);

        ChatRoomDetail chatRoom = new ChatRoomDetail(messageDto);
        ChatRoomDetail newChat = mongoTemplate.insert(chatRoom);
        messageDto.setMsgId(newChat.getId());

        return newChat.getId();
    }

    public List<ChatRoomResponseDto> getChatRoom(Long userId) {

        Query query = Query.query(Criteria.where("users").elemMatch(Criteria.where("$eq").is(userId)));
        List<ChatRoom> chatRooms = mongoTemplate.find(query, ChatRoom.class);

        //ChatRoomResponseDto notReadCnt 추가
        return  chatRooms.stream()
                .map(room -> {
                    long notReadCnt = mongoTemplate.count(
                            Query.query(
                                    Criteria.where("roomId").is(room.getId())
                                            .and("sender").ne(userId)
                                            .and("isRead").is(false)
                            ),
                            ChatRoomDetail.class
                    );
                    return ChatRoomResponseDto.of(room, notReadCnt);
                })
                .collect(Collectors.toList());
    }

    public PagedResponse<ChatRoomDetail> getChatHistory(String chatRoomId, int offset, int size) {

        Pageable pageable = PageRequest.of(offset-1, size, Sort.by(Sort.Direction.ASC, "created_dt"));
        Query query = new Query(Criteria.where("roomId").is(chatRoomId)).with(pageable);

        List<ChatRoomDetail> filteredMetaData = mongoTemplate.find(query, ChatRoomDetail.class);

        Page<ChatRoomDetail> metaDataPage = PageableExecutionUtils.getPage(
                filteredMetaData,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1),ChatRoomDetail.class)
                // query.skip(-1).limit(-1)의 이유는 현재 쿼리가 페이징 하려고 하는 offset 까지만 보기에 이를 맨 처음부터 끝까지로 set 해줘 정확한 도큐먼트 개수를 구한다.
        );

        return new PagedResponse<>(metaDataPage.getContent(), metaDataPage.getNumber()+1, metaDataPage.getSize(), metaDataPage.getTotalElements(),
                metaDataPage.getTotalPages(), metaDataPage.isLast());

    }
}
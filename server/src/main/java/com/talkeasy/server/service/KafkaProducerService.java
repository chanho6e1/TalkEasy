package com.talkeasy.server.service;

import com.talkeasy.server.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, LocationDto> kafkaTemplate;

    public void sendMessage(LocationDto message) {

        String topicName = "topic-test-01";
        // String topic, Integer partition, Long timestamp, K key,
        //			@Nullable V data
        ListenableFuture<SendResult<String, LocationDto>> kafka = kafkaTemplate.send(topicName, 0, System.currentTimeMillis(),null, message);
        kafka.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, LocationDto> result) {
                log.info("========== [send success] message : {} record : {}", message, result.getProducerRecord());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("========== [send fail] message : {} ", message);
            }
        });
    }
}


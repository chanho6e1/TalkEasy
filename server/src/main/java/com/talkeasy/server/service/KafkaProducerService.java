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
        log.info("========== Produce message : {}", message.toString());

        String topicName = "exam-topic3";
        ListenableFuture<SendResult<String, LocationDto>> kafka = kafkaTemplate.send(topicName, message);
        kafka.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, LocationDto> result) {
                log.info("========== [message = {}] Location event Sent successfully, record : {}", message, result.getProducerRecord());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("========== [message = {}] Location event Send Fail", message);
            }
        });
    }
}


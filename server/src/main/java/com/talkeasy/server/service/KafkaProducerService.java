package com.talkeasy.server.service;

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

    private final KafkaTemplate<String, String> kafkaTemplate;

    private String topicName = "testTopic333";

    public void sendMessage(String message) {
        System.out.println(String.format("Produce message : %s", message));
        log.info("==========Produce message : {}", message);

        ListenableFuture<SendResult<String, String>> kafka = kafkaTemplate.send(topicName, message);
        kafka.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("[message = {}] Location event Sent, record : {}", message, result.getProducerRecord());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("[message = {}] Location event Send Fail", message);
            }
        });
    }
}


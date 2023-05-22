package com.talkeasy.server.service.location;

import com.talkeasy.server.dto.location.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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

        String topicName = "location-event";

        ListenableFuture<SendResult<String, LocationDto>> kafka = kafkaTemplate.send(topicName, message);

        kafka.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, LocationDto> result) {
                log.info("========== [Produced message] record : {}", result.getProducerRecord());
            }

            @Override
            public void onFailure(@NotNull Throwable ex) {
                log.warn("========== [Produced fail] message : {} ", message);
            }
        });
    }
}


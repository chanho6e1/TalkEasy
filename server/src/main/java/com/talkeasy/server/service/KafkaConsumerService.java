package com.talkeasy.server.service;

import com.talkeasy.server.dto.MessageDto;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaConsumerService {

    private final ConsumerFactory<String, MessageDto> consumerFactory;
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, ConcurrentMessageListenerContainer<String, MessageDto>> listenerContainers = new ConcurrentHashMap<>();

    public KafkaConsumerService(ConsumerFactory<String, MessageDto> consumerFactory, SimpMessagingTemplate messagingTemplate) {
        this.consumerFactory = consumerFactory;
        this.messagingTemplate = messagingTemplate;
    }

    public void subscribe(String topic) {
        if (!listenerContainers.containsKey(topic)) {
            ConcurrentMessageListenerContainer<String, MessageDto> listenerContainer = createListenerContainer(topic);
            listenerContainers.put(topic, listenerContainer);
            listenerContainer.start();
        }
    }

    public void unsubscribe(String topic) {
        if (listenerContainers.containsKey(topic)) {
            ConcurrentMessageListenerContainer<String, MessageDto> listenerContainer = listenerContainers.get(topic);
            listenerContainer.stop();
            listenerContainers.remove(topic);
        }
    }

    private ConcurrentMessageListenerContainer<String, MessageDto> createListenerContainer(String topic) {
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener((MessageListener<String, MessageDto>) record -> {
            System.out.println("Received message from Kafka: " + record.value());
            MessageDto messageDto = record.value();
            messagingTemplate.convertAndSend("/topic/" + messageDto.getRoomId(), messageDto);
        });

        ConcurrentMessageListenerContainer<String, MessageDto> listenerContainer = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
        return listenerContainer;
    }
}

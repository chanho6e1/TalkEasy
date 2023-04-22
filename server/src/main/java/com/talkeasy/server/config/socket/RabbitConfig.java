package com.talkeasy.server.config.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    private static final String CHAT_QUEUE_NAME = "chat.queue";
    private static final String CHAT_EXCHANGE_NAME = "chat.exchange";
    private static final String ROUTING_KEY = "room.*";

    // Queue 등록
    @Bean
    public Queue queue() {
        return new Queue(CHAT_QUEUE_NAME, true);
    }

    //
//    // Exchange 등록
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CHAT_EXCHANGE_NAME);
    }

    //
//    // Exchange 와 Queue 바인딩
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }
//
//
//    @Bean
//    public  com.fasterxml.jackson.databind.Module dateTimeModule() {
//        return new JavaTimeModule();
//    }
//
//
//    // Spring 에서 자동생성해주는 ConnectionFactory 는 SimpleConnectionFactory
//    // 여기서 사용하는 건 CachingConnectionFactory 라 새로 등록해줌
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost("localhost");
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        return factory;
//    }
//
//    /**
//     * messageConverter를 커스터마이징 하기 위해 Bean 새로 등록
//     */
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        rabbitTemplate.setRoutingKey(CHAT_QUEUE_NAME);
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        container.setQueueNames(CHAT_QUEUE_NAME);
////        container.setConcurrentConsumers(10);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter jsonMessageConverter() {
//        //LocalDateTime serializable 을 위해
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//        objectMapper.registerModule(dateTimeModule());
//        return new Jackson2JsonMessageConverter(objectMapper);
//    }

    // 메시지 리스너를 정의한다. Subscriber에 대한 설정 등.
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver,
//                                           Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receive");
//        messageListenerAdapter.setDefaultListenerMethod("receive"); // 실행할 메소드 지정.
//        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter); // Json형태로 받기 위해 MessageConverter 설정.
//        return messageListenerAdapter;
//    }


    /////////////


    @Bean
    public RabbitTemplate template() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
//        container.setQueueNames(CHAT_QUEUE_NAME);
        return container;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("k8d207.p.ssafy.io");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


//    @Bean
//    MessageListenerAdapter listenerAdapter(RabbitReceiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
}
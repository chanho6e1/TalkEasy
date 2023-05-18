//package com.talkeasy.server.config.socket;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableRabbit
//public class RabbitConfig {
//
//    private static final String CHAT_QUEUE_NAME = "chat.queue";
//    private static final String READ_QUEUE_NAME = "read.queue";
//    private static final String ROOM_QUEUE_NAME = "room.queue";
//    private static final String CHAT_EXCHANGE_NAME = "chat.exchange";
//    private static final String READ_EXCHANGE_NAME = "read.exchange";
//    private static final String ROUTING_KEY = "room.*";
////    private static final String ROUTING_KEY = "chat.queue.*";
//
//
//    // Queue 등록
//    @Bean
//    public Queue queue1() {
//        return new Queue(CHAT_QUEUE_NAME, true);
//    }
//    @Bean
//    public Queue queue() {
//        return new Queue(READ_QUEUE_NAME, true);
//    }
//
//    //
//    // Exchange 등록
//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(CHAT_EXCHANGE_NAME);
//    }
//    @Bean
//    public TopicExchange exchange2() {
//        return new TopicExchange(READ_EXCHANGE_NAME);
//    }
//    //
////    // Exchange 와 Queue 바인딩
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue1()).to(exchange()).with(ROUTING_KEY);
//    }
//
//
//    @Bean
//    public RabbitTemplate template() {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer container() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
////        container.setQueueNames(CHAT_QUEUE_NAME);
//        return container;
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost("k8d207.p.ssafy.io");
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        return factory;
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//
////    @Bean
////    MessageListenerAdapter listenerAdapter(RabbitReceiver receiver) {
////        return new MessageListenerAdapter(receiver, "receiveMessage");
////    }
//}
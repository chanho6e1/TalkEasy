package com.talkeasy.server.config.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Configuration
//@EnableWebSocketMessageBroker
//public class StompConfig implements WebSocketMessageBrokerConfigurer {
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/stomp/chat")
//                .setAllowedOriginPatterns("*") //안해도 무관
//                .withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setPathMatcher(new AntPathMatcher("."))  // url을 chat/room/3 -> chat.room.3으로 참조하기 위한 설정
//        .setApplicationDestinationPrefixes("/pub")
//
////        .enableStompBrokerRelay("/topic")
//        .enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
//                .setClientLogin("guest")
//                .setClientPasscode("guest")
//                .setSystemLogin("guest")
//                .setSystemPasscode("guest");
////                .setRelayHost()
//    }
//}

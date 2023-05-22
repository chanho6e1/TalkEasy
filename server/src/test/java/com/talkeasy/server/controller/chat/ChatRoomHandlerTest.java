//package com.talkeasy.server.controller.chat;
//
//import com.google.gson.Gson;
//import com.talkeasy.server.domain.chat.ChatRoomDetail;
//import com.talkeasy.server.domain.member.Follow;
//import com.talkeasy.server.domain.member.Member;
//import com.talkeasy.server.service.chat.ChatReadService;
//import com.talkeasy.server.service.chat.ChatService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageBuilder;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Query;
//
//import java.io.IOException;
//
//import static com.google.common.base.Verify.verify;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.times;
//
//@ExtendWith(MockitoExtension.class)
//class ChatRoomHandlerTest {
//
//    @InjectMocks
//    private ChatRoomHandler chatRoomHandler;
//
//    @Mock
//    private ChatService chatService;
//
//    @Mock
//    private ChatReadService chatReadService;
//
//    @Mock
//    MongoTemplate mongoTemplate;
//
//    Gson gson;
//
//    @BeforeEach
//    void setUp() {
//        gson = new Gson();
//        chatRoomHandler = new ChatRoomHandler(chatService, chatReadService, gson);
//    }
//
//    @Test
//    void chatControl() throws IOException {
////
////        ChatRoomDetail chat = ChatRoomDetail.builder().roomId("3").fromUserId("1").toUserId("2").created_dt("2023.05.01").build();
////        Message msg = MessageBuilder.withBody(gson.toJson(chat).getBytes()).build();
////
//////        Mockito.when(gson.fromJson(any(String.class), eq(ChatRoomDetail.class))).thenReturn(chat);
////        Mockito.when(mongoTemplate.insert(any(ChatRoomDetail.class))).thenReturn(chat);
////
////        chatRoomHandler.chatControl(msg);
////        Mockito.verify(mongoTemplate, times(1)).insert(eq(ChatRoomDetail.class));
//    }
//}
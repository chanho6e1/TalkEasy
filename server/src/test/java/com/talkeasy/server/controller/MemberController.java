//package com.talkeasy.server.controller;
//
//import com.talkeasy.server.config.s3.S3Uploader;
//import com.talkeasy.server.service.member.MemberService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.io.IOException;
//
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//
//@WebMvcTest(MemberController.class)
//@DisplayName("MemberController 테스트")
//public class MemberController {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Mock
//    private MemberService memberService;
//    @Mock
//    private S3Uploader s3Uploader;
//
//    @Test
//
//    @DisplayName("유저 탈퇴 테스트")
//    public void deleteUserInfo() throws IOException {
//
//        String userId = "1";
//        doReturn(userId).when(memberService.deleteUserInfo("email"));
//        mockMvc.perform(delete("/api/members")
//                .with(csrf()))
//                .andExpect(HttpStatus.OK)
//                .andExpect();
//    }
//}

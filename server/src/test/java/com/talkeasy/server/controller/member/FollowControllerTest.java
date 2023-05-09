package com.talkeasy.server.controller.member;

import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
//@WebMvcTest(FollowController.class)
class FollowControllerTest {

    //    @Mock
    @InjectMocks
    private FollowController followController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(followController).build();
    }

    //    @BeforeEach
    public OAuth2AuthenticationToken authenticationsetUp() {
        OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(
                new DefaultOAuth2User(
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                        Collections.singletonMap("id", "1"),
                        "id"
                ),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                "provider"
        );

        return authenticationToken;
    }


    @Test
    @DisplayName("팔로우 하기")
    void follow() throws Exception {
//        OAuth2UserImpl oAuth2User = new OAuth2UserImpl(Member.builder().id("1").build());
//        String toUserId = "2";
//
//        Authentication authenticationToken = authenticationsetUp();
//
//        mockMvc.perform(post("/api/follows/{toUserId}", toUserId)
//                        .with(csrf())
////                        .with(SecurityMockMvcRequestPostProcessors.authentication(authenticationToken))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andDo(print());
//
//        ResponseEntity<CommonResponse> response = followController.follow(oAuth2User , toUserId);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(response.getBody().getData()).isEqualTo("팔로우 성공");
    }

    @Test
    void getfollow() {
    }

    @Test
    void putProtector() {
    }

    @Test
    void putLocationStatus() {
    }

    @Test
    void testFollow() {
    }

    @Test
    void unfollow() {
    }

    @Test
    void testGetfollow() {
    }

    @Test
    void testPutProtector() {
    }

    @Test
    void testPutLocationStatus() {
    }
}
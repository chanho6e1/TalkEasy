package com.talkeasy.server.controller.member;

import com.google.gson.Gson;
import com.talkeasy.server.authentication.OAuthUserInfo;
import com.talkeasy.server.common.CommonResponse;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.user.FollowRequestDto;
import com.talkeasy.server.service.chat.ChatService;
import com.talkeasy.server.service.member.FollowService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import com.talkeasy.server.testutil.WithCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
//@WebMvcTest(FollowController.class)
class FollowControllerTest {

    @InjectMocks
    private FollowController followController;

    @Mock
    private FollowService followService;

    private MockMvc mockMvc;

    @Mock
    private OAuth2UserImpl oAuth2UserImpl;
    @Mock
    private ChatService chatService;
    @Mock
    private OAuthUserInfo oAuthUserInfo;

    @Mock
    private Member member;
    @Mock
    private Authentication authentication;

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


    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(followController).build();
//        oAuth2UserImpl = new OAuth2UserImpl(Member.builder().id("1").build());
        member = Member.builder().id("1").build();

        oAuth2UserImpl = new OAuth2UserImpl(Member.builder()
                .id("1")
                .name("name")
                .email("test@test.com")
                .age(20)
                .role(0)
                .build());
        authentication = new UsernamePasswordAuthenticationToken(oAuth2UserImpl, "1234", oAuth2UserImpl.getAuthorities());
    }

    @Test
    @WithCustomUser
    @DisplayName("팔로우 하기")
    void follow() throws Exception {
        String toUserId = "2";
        Authentication authenticationToken = authenticationsetUp();

        FollowRequestDto followRequestDto = new FollowRequestDto();
        followRequestDto.setMemo("string");
        String requestJson = new Gson().toJson(followRequestDto);


        mockMvc.perform(post("/api/follows/{toUserId}", toUserId)
                        .with(csrf())
//                        .with(SecurityMockMvcRequestPostProcessors.authentication(authenticationToken))
                        .with(authentication(authentication))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());


//        Mockito.when(followService.follow(oAuth2UserImpl, "2", followRequestDto)).thenReturn("팔로우 성공");
        Mockito.when(followService.follow("name", "2", followRequestDto)).thenReturn("팔로우 성공");

//        ResponseEntity<CommonResponse> response = followController.follow(oAuth2UserImpl, toUserId, followRequestDto);

//        System.out.println(response);
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
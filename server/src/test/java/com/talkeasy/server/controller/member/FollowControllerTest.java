package com.talkeasy.server.controller.member;

import com.talkeasy.server.common.exception.ResourceAlreadyExistsException;
import com.talkeasy.server.domain.member.Follow;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.service.member.FollowService;
import com.talkeasy.server.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@RequiredArgsConstructor
class FollowControllerTest {

    @Mock
    FollowService followService;

    @Mock
    MemberService memberService;
//    private final MongoTemplate mongoTemplate;
    @Test
    @DisplayName("팔로우 하기")
    void follow() {
        // given
        MongoTemplate mongoTemplate = mock(MongoTemplate.class);
        Member fromUser = Member.builder().id("64520e5971aa51bc5c6dde5a").build();
        given(mongoTemplate.findOne(any(Query.class), any(Class.class))).willReturn(fromUser);
        Member toUser = Member.builder().id("64475bb2970b4a6441e96c50").build();
        given(mongoTemplate.findOne(any(Query.class), any(Class.class))).willReturn(toUser);
//        given(mongoTemplate.findOne(any(Query.class), any(Class.class))).willReturn(new Follow());

        // when, then
        FollowService followService = new FollowService(mongoTemplate);
        assertThatThrownBy(() -> followService.follow("64520e5971aa51bc5c6dde5a", "64475bb2970b4a6441e96c50"))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("이미 팔로우되어 있습니다");
    }


    @Test
    void unfollow() {
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
}
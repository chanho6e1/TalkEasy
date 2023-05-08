package com.talkeasy.server.service.member;

import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ResourceAlreadyExistsException;
import com.talkeasy.server.common.exception.ResourceNotFoundException;
import com.talkeasy.server.domain.member.Follow;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.user.FollowResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @InjectMocks
    private FollowService followService;
    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("팔로우 - 정상 팔로우")
    void followDetailCaseSuccess() {

        Member fromUser = Member.builder().id("1").name("unfollowTest").age(10).deleteStatus(false).build();
        Mockito.when(mongoTemplate.findOne(any(Query.class), eq(Member.class))).thenReturn(fromUser);

        Mockito.when(mongoTemplate.findOne(Query.query(Criteria.where("fromUserId").is("1").and("toUserId").is("2")), Follow.class)).thenReturn(null);

        followService.followDetail("1", "2");

        verify(mongoTemplate, times(1)).findOne(Query.query(Criteria.where("id").is("1")), Member.class);
        verify(mongoTemplate, times(1)).findOne(Query.query(Criteria.where("fromUserId").is("1").and("toUserId").is("2")), Follow.class);
        verify(mongoTemplate, times(1)).insert(any(Follow.class));
    }

    @Test
    @DisplayName("팔로우 - 이미 팔로우 상태인 경우")
    void followCaseAlreadyFollow() {

        Member fromUser = Member.builder().id("1").name("unfollowTest").age(10).deleteStatus(false).build();
        Mockito.when(mongoTemplate.findOne(any(Query.class), eq(Member.class))).thenReturn(fromUser);

        Follow existFollow = Follow.builder().id("3").fromUserId("1").toUserId("2").memo(null).MainStatus(false).locationStatus(false).build();

        Mockito.when(mongoTemplate.findOne(Query.query(Criteria.where("fromUserId").is("1").and("toUserId").is("2")), Follow.class)).thenReturn(existFollow);

        assertThatThrownBy(() -> followService.followDetail("1", "2"))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("이미 팔로우되어 있습니다");

        verify(mongoTemplate, times(1)).findOne(Query.query(Criteria.where("id").is("1")), Member.class);
        verify(mongoTemplate, times(1)).findOne(Query.query(Criteria.where("fromUserId").is("1").and("toUserId").is("2")), Follow.class);

        verify(mongoTemplate, never()).insert(any(Follow.class));

    }

    @Test
    @DisplayName("언팔로우 하기-정상동작")
    void deleteByFollowDetailCaseSuccess() {

        Follow follow = Follow.builder().id("3").fromUserId("1").toUserId("2").build();
        Mockito.when(mongoTemplate.findOne(any(Query.class), eq(Follow.class))).thenReturn(follow);

        followService.deleteByFollowDetail("1", "2");

        // then
        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Follow.class));
        verify(mongoTemplate, times(1)).remove(any(Follow.class));

    }

    @Test
    @DisplayName("언팔로우 하기-이미 언팔로우한 경우")
    void deleteByFollowCaseAlreadyUnfollow() {

        Mockito.when(mongoTemplate.findOne(any(Query.class), eq(Follow.class))).thenReturn(null);

        assertThatThrownBy(() -> followService.deleteByFollowDetail("1", "2"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("이미 언팔로우 상태입니다");

        // then
        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Follow.class));
        verify(mongoTemplate, never()).remove(any(Query.class), eq(Follow.class));
    }

    @Test
    @DisplayName("언팔로우 하기 - 언팔로우할 유저가 없는 경우(fromUser가 없는 경우)")
    void deleteByFollowCaseNoFromUser() {

        Mockito.when(mongoTemplate.findOne(any(Query.class), eq(Member.class))).thenReturn(null);

        assertThatThrownBy(() -> followService.deleteByFollow("1", "2"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("없는 유저입니다");

        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Member.class));
        verify(mongoTemplate, never()).remove(any(Query.class), eq(Follow.class));
    }

    @Test
    @DisplayName("언팔로우 하기 - 언팔로우할 유저가 없는 경우(toUser가 없는 경우)")
    void deleteByFollowCaseNoUserToUser() {

        Member fromUser = Member.builder().id("1").name("unfollowTest").build();
        Mockito.when(mongoTemplate.findOne(Query.query(Criteria.where("id").is("1")), Member.class)).thenReturn(fromUser);

        Mockito.when(mongoTemplate.findOne(Query.query(Criteria.where("id").is("2")), Member.class)).thenReturn(null);

        assertThatThrownBy(() -> followService.deleteByFollow("1", "2"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("없는 유저입니다");

        verify(mongoTemplate, times(1)).findOne(eq(Query.query(Criteria.where("id").is("1"))), eq(Member.class));
        verify(mongoTemplate, times(1)).findOne(eq(Query.query(Criteria.where("id").is("2"))), eq(Member.class));
        verify(mongoTemplate, never()).remove(any(Query.class), eq(Follow.class));
    }

    @Test
    @DisplayName("팔로워 목록을 불러온다")
    void getfollowCaseSuccess() {
        // Given
        Member fromUser = Member.builder().id("1").build();
        Mockito.when(mongoTemplate.findOne(Query.query(Criteria.where("id").is("1")), Member.class)).thenReturn(fromUser);

        Follow follow = Follow.builder().id("3").toUserId("2").fromUserId("1").build();
        List<Follow> follows = Collections.singletonList(follow);
        Mockito.when(mongoTemplate.find(any(Query.class), eq(Follow.class))).thenReturn(follows);

        Member toUser = Member.builder().id("2").name("testUser").imageUrl("testImage").build();
        Mockito.when(mongoTemplate.findOne(Query.query(Criteria.where("id").is("2")), Member.class)).thenReturn(toUser);

        // When
        PagedResponse<FollowResponse> result = followService.getfollow("1");

        // Then
        verify(mongoTemplate, times(1)).findOne(eq(Query.query(Criteria.where("id").is("1"))), eq(Member.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Follow.class));
        verify(mongoTemplate, times(1)).findOne(eq(Query.query(Criteria.where("id").is("2"))), eq(Member.class));

        assertThat(result).isNotNull();
        assertThat(result.getData().size()).isEqualTo(1);

        FollowResponse expectedFollowResponse = FollowResponse.builder()
                .userId(follow.getToUserId())
                .userName(toUser.getName())
                .imageUrl(toUser.getImageUrl())
                .build();

        assertThat(result.getData().get(0)).isEqualTo(expectedFollowResponse);
    }

    @Test
    @DisplayName("팔로워 목록이 없을 경우")
    void getfollowCaseNoFollow() {
        // Given
        Member fromUser = Member.builder().id("1").build();
        Mockito.when(mongoTemplate.findOne(Query.query(Criteria.where("id").is("1")), Member.class)).thenReturn(fromUser);
        Mockito.when(mongoTemplate.find(any(Query.class), eq(Follow.class))).thenReturn(Collections.emptyList());

        // When
        PagedResponse<FollowResponse> result = followService.getfollow("1");

        // Then
        verify(mongoTemplate, times(1)).findOne(eq(Query.query(Criteria.where("id").is("1"))), eq(Member.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Follow.class));
        verify(mongoTemplate, never()).findOne(eq(Query.query(Criteria.where("id").is(String.class))), eq(Member.class));

        assertThat(result).isNotNull();
        assertThat(result.getData().size()).isEqualTo(0);
    }

    @Test
    void putProtector() {
    }

    @Test
    void putLocationStatus() {
    }
}
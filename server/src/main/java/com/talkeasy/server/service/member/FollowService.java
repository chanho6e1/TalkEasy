package com.talkeasy.server.service.member;

import com.google.gson.Gson;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.common.exception.ArgumentMismatchException;
import com.talkeasy.server.common.exception.ResourceAlreadyExistsException;
import com.talkeasy.server.common.exception.ResourceNotFoundException;
import com.talkeasy.server.domain.chat.ChatRoom;
import com.talkeasy.server.domain.chat.LastChat;
import com.talkeasy.server.domain.member.Follow;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.user.FollowRequestDto;
import com.talkeasy.server.dto.user.FollowResponse;
import com.talkeasy.server.dto.user.LastChatResponse;
import com.talkeasy.server.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final MongoTemplate mongoTemplate;
    private final ChatService chatService;
    private final RabbitAdmin rabbitAdmin;

    //    public String follow(OAuth2UserImpl myId, String toUserId, FollowRequestDto followRequestDto) throws IOException {
    public String follow(String myId, String toUserId, FollowRequestDto followRequestDto) throws IOException {
//
        Follow follow1 = followDetail(myId, toUserId); // 보호자가 피보호자를 친구추가
        Follow follow2 = followDetail(toUserId, myId);
        chatService.createRoom(myId, toUserId);

        /* memo 저장*/
        saveMemo(follow1, followRequestDto);

        return "팔로우 성공";
    }

    public void saveMemo(Follow follow1, FollowRequestDto followRequestDto) {
        if (followRequestDto.getMemo() != null) {
            follow1.setMemo(followRequestDto.getMemo());
            mongoTemplate.save(follow1);
        }
    }

    public Follow followDetail(String myId, String toUserId) {

        Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(myId)), Member.class)).orElseThrow(() -> new ResourceNotFoundException("member", "userId", myId));

        Follow follow = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("fromUserId").is(myId).and("toUserId").is(toUserId)), Follow.class)).orElse(null);

        if (follow != null) {
            throw new ResourceAlreadyExistsException("이미 팔로우되어 있습니다");
        }

        Member member = chatService.getMemberById(myId);
        Follow toFollow;

        if (member.getRole() == 1) { // 내가 피보호자
            //보호자
            toFollow = Follow.builder().fromUserId(myId).toUserId(toUserId).memo("").mainStatus(false).locationStatus(false).nickName("").build();
        } else {
            //비보호자
            toFollow = Follow.builder().fromUserId(myId).toUserId(toUserId).memo("").mainStatus(false).locationStatus(true).nickName("").build();
        }

        return mongoTemplate.insert(toFollow);

    }


    public String deleteByFollow(String myId, String toUserId) throws IOException {

        Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(myId)), Member.class)).orElseThrow(() -> new ResourceNotFoundException("없는 유저입니다"));
        Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(toUserId)), Member.class)).orElseThrow(() -> new ResourceNotFoundException("없는 유저입니다"));

        deleteByFollowDetail(myId, toUserId);
        deleteByFollowDetail(toUserId, myId);

        ChatRoom chatRoom = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("users").all(toUserId, myId)), ChatRoom.class)).orElse(null);

        if (chatRoom != null) {
            chatService.deleteRoom(chatRoom.getId(), myId);
        }
        return "언팔로우 성공";
    }

    public void deleteByFollowDetail(String myId, String toUserId) {

        Follow follow = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("fromUserId").is(myId).and("toUserId").is(toUserId)), Follow.class)).orElseThrow(() -> new ResourceNotFoundException("이미 언팔로우 상태입니다"));
        mongoTemplate.remove(follow);

    }

    public PagedResponse<FollowResponse> getfollow(String userId) {

        Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(userId)), Member.class)).orElseThrow(() -> new ResourceNotFoundException("member", "userId", userId));

        List<Follow> filteredMetaData = mongoTemplate.find(new Query(Criteria.where("fromUserId").is(userId))
                .with(Sort.by(Sort.Direction.DESC, "mainStatus")), Follow.class);

        List<FollowResponse> result = filteredMetaData.stream()
                .map((follow) -> {
                    FollowResponse followResponse = new FollowResponse(mongoTemplate.findOne(Query.query(Criteria.where("id").is(follow.getToUserId()))
                            .with(Sort.by(Sort.Direction.ASC, "name")), Member.class),
                            follow,
                            findChatRoom(follow.getFromUserId(), follow.getToUserId()));

                    LastChat lastChat = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("roomId").is(followResponse.getRoomId())
                            .and("userId").is(userId)), LastChat.class)).orElse(null);

                    LastChatResponse lastChatResponse = null;
                    if (lastChat != null) {
                        lastChatResponse = LastChatResponse.builder()
                                .msg(lastChat.getMsg())
                                .created_dt(lastChat.getCreated_dt())
                                .readCnt(getNotReadCount(lastChat, userId))
                                .build();
                    }
                    followResponse.setLastChat(lastChatResponse);

                    return followResponse;
                })
                .collect(Collectors.toList());


//        Collections.sort(result, Comparator.comparing(FollowResponse::getUserName));

        return new PagedResponse(HttpStatus.OK, result, 1);
    }

    // 피보호자 특이사항 수정
    public String putMemo(String myId, String followId, FollowRequestDto followRequestDto) {

        Follow follow = getTargetUser(followId);

        isMine(myId, follow.getFromUserId());

        follow.setMemo(followRequestDto.getMemo());

        mongoTemplate.save(follow);

        return "수정 성공";
    }

    public String putNickName(Member myInfo, String followId, String nickName) {
        Follow follow = getTargetUser(followId);

        isMine(myInfo.getId(), follow.getFromUserId());

        if (myInfo.getRole() == 0) {
            // 보호자라면 피보호자의 별명을 설정할 수 없다
            throw new ArgumentMismatchException("보호자는 피보호자의 별명을 설정할 수 없습니다.");
        }

        follow.setNickName(nickName);

        mongoTemplate.save(follow);

        return "수정 성공";
    }

    // 친구 정보 상세조회
    public FollowResponse getUserInfoByFollow(String myId, String followId) {

        Follow follow = getTargetUser(followId);

        isMine(myId, follow.getFromUserId());

        Member member = mongoTemplate.findOne(Query.query(Criteria.where("id").is(follow.getToUserId())), Member.class);

        /* 두명이 속한 채팅방 번호도 response에 추가*/
        ChatRoom chatRoom = findChatRoom(follow.getFromUserId(), follow.getToUserId());
        
        FollowResponse result = new FollowResponse(member, follow, chatRoom);

        LastChat lastChat = mongoTemplate.findOne(Query.query(Criteria.where("roomId").is(result.getRoomId())
        .and("userId").is(myId)), LastChat.class);

        LastChatResponse lastChatResponse = null;
        if (lastChat != null) {
            lastChatResponse = LastChatResponse.builder()
                    .msg(lastChat.getMsg())
                    .created_dt(lastChat.getCreated_dt())
                    .readCnt(getNotReadCount(lastChat, myId))
                    .build();
        }

        result.setLastChat(lastChatResponse);

        return result;
    }

    int getNotReadCount(LastChat lastChat, String myId){
        QueueInformation queueInformation = chatService.getQueueInfo(lastChat.getRoomId(), myId);
        if(queueInformation!=null) {
            return queueInformation.getMessageCount();
        }
        return 0;
    }

    private ChatRoom findChatRoom(String fromUserId, String toUserId) {

        return Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("users").all(fromUserId, toUserId)), ChatRoom.class)).orElse(null);

    }


    private void isMine(String myId, String fromUserId) {

        if (!myId.equals(fromUserId)) {
            throw new ArgumentMismatchException("나의 팔로우가 아닙니다");
        }
    }

    /* 주보호자 등록, targetId: 보호자*/
    public boolean putProtector(String userId, String followId) {

        Follow follow = getTargetUser(followId);

        isMine(userId, follow.getFromUserId());

        Follow mainProtector = Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("mainStatus").is(true)
                .and("fromUserId").is(userId)), Follow.class)).orElse(null);

        if (mainProtector != null) { // 기존에 등록된 주 보호자가 있을 경우, 해제
            mainProtector.setMainStatus(false);
            mongoTemplate.save(mainProtector);

            if (mainProtector.getToUserId().equals(follow.getToUserId())) // 기존에 등록된 보호자와 동일할 경우
                return mainProtector.getMainStatus();
        }

        // 새로운 주보호자 등록
        follow.setMainStatus(true);
        mongoTemplate.save(follow);

        return follow.getMainStatus();
    }

    /* 위치정보 접근권한 설정 */
    public boolean putLocationStatus(String userId, String followId) {
        Follow follow = getTargetUser(followId);


        isMine(userId, follow.getFromUserId());

        follow.setLocationStatus(!follow.getLocationStatus());
        mongoTemplate.save(follow);

        return follow.getLocationStatus();
    }

    /* 피보호자와 친구설정되어있는 보호자 정보 조회 */
    public Follow getTargetUser(String followId) {
        return Optional.ofNullable(mongoTemplate.findOne(Query.query(Criteria.where("id").is(followId)), Follow.class)).orElseThrow(() -> new ResourceNotFoundException("친구목록에 존재하지 않는 사용자 입니다."));
    }

}

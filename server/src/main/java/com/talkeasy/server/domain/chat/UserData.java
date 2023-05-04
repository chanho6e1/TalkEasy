package com.talkeasy.server.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserData {
    private Boolean nowIn; // 현재 채팅방에 들어와있는지 여부
    private String leaveTime; // 채팅방 떠난 시간

    public UserData(Boolean nowIn, String leaveTime){
        this.nowIn = nowIn;
        this.leaveTime = leaveTime;
    }
}

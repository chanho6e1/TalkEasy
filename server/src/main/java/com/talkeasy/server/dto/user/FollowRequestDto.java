package com.talkeasy.server.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowRequestDto {
    private String memo;
}

package com.talkeasy.server.domain.app;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user_app_token")
@Data
public class UserAppToken {
    @Id
    private String id;
    private String userId;
    private String appToken;
    private Boolean state; // 앱 내부에 있는지 유무

    public UserAppToken(String userId, String appToken){
        this.userId = userId;
        this.appToken = appToken;
    }
}

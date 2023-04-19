package com.talkeasy.server.domain;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Document(collection = "USER")
@Builder
public class Member {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "USER_NAME", length = 100)
    @NotNull
    @Size(max = 100)
    private String userName;

    @Column(name = "EMAIL", length = 512, unique = false)
    @Nullable
    @Size(max = 512)
    private String email;


    @Column(name = "IMAGE_URL", length = 512)
//    @NotNull
    @Size(max = 512)
    private String imageUrl;

//    @Column(name = "PROVIDER_TYPE", length = 20)
//    @Enumerated(EnumType.STRING)
//    @NotNull
//    private AuthProvider providerType;

    //    String providerId;
    @Column(name = "ROLE")
    @Nullable
    private Integer role; //0:보호자/1:피보호자
    @Column(name = "GENDER")
    @Nullable
    private Integer gender; //0:남/1:여

    @Column(name = "AGE")
    @Nullable
    private Integer age;

    @Column(name = "ACCESS_TOKEN")
    @Nullable
    private String accessToken;

}

package com.talkeasy.server.domain;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Document(collection = "MEMBER")
@Builder
public class Member {
    @Id
    private String id;

    @Column(name = "name", length = 100)
    @Size(max = 100)
    private String userName;

    @Column(name = "email", length = 512, unique = false)
    @Size(max = 512)
    private String email;

//
    @Column(name = "imageUrl", length = 512)
//    @NotNull
    @Size(max = 512)
    private String imageUrl;

    @Column(name = "role")
    private Integer role; //0:보호자/1:피보호자
    @Column(name = "gender")
    private Integer gender; //0:남/1:여
//
    @Column(name = "age")
    private Integer age;

    @Column(name = "accessToken")
    private String accessToken;

    @Column(name = "birthDate")
    private String birthDate;
//
////    @Column(name = "PROVIDER_TYPE", length = 20)
////    @Enumerated(EnumType.STRING)
////    @NotNull
////    private AuthProvider providerType;
//
//        String providerId;
}

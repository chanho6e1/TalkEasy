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
    private String _id;

//    @Column(name = "name", length = 100)
//    @NotNull
//    @Size(max = 100)
    private String name;

//    @Column(name = "email", length = 512, unique = false)
//    @Nullable
//    @Size(max = 512)
    private String email;

//
//    @Column(name = "profile", length = 512)
////    @NotNull
//    @Size(max = 512)
//    private String imageUrl;
//
////    @Column(name = "PROVIDER_TYPE", length = 20)
////    @Enumerated(EnumType.STRING)
////    @NotNull
////    private AuthProvider providerType;
//
//    //    String providerId;
//    @Column(name = "role")
//    @Nullable
//    private Integer role; //0:보호자/1:피보호자
//    @Column(name = "gender")
//    @Nullable
//    private Integer gender; //0:남/1:여
//
//    @Column(name = "age")
//    @Nullable
//    private Integer age;
//
//    @Column(name = "access_token")
//    @Nullable
//    private String accessToken;

}

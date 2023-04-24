package com.talkeasy.server.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoUpdateRequest {
    String name;
    Integer gender;
    String birthDate;
}

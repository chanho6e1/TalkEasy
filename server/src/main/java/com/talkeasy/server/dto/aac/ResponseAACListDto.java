package com.talkeasy.server.dto.aac;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ResponseAACListDto {
    List<ResponseAACDto> fixedList; // 고정 aac 리스트
    List<ResponseAACDto> aacList; // 일반 aac 리스트
}

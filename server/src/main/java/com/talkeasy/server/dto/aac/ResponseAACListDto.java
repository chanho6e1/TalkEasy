package com.talkeasy.server.dto.aac;

import com.talkeasy.server.domain.aac.AAC;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ResponseAACListDto {
    List<ResponseAACDto> fixedList;
    List<ResponseAACDto> aacList;
}

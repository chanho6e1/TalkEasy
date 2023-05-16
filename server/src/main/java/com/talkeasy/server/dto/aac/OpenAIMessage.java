package com.talkeasy.server.dto.aac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIMessage {
    private String role;
    private String content;
}

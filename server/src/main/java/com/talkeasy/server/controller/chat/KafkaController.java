package com.talkeasy.server.controller.chat;

import com.talkeasy.server.dto.MessageDto;
import com.talkeasy.server.service.chat.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final KafkaProducer producer;

    @PostMapping
    public String sendMessage(@RequestBody MessageDto message) {
        this.producer.sendMessage(message);
        return "success";
    }
}

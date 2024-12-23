package com.aldeamo.microservice1.service;

import com.aldeamo.microservice1.model.MessageDTO;

public interface MessageService {
    void sendMessage(MessageDTO message);
}

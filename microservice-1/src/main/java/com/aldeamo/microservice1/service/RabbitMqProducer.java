package com.aldeamo.microservice1.service;

import com.aldeamo.microservice1.model.MessageDTO;

public interface RabbitMqProducer {
    void sendToRabbitMQ(MessageDTO messageDTO);
}

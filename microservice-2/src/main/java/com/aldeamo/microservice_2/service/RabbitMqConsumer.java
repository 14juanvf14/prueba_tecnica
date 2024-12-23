package com.aldeamo.microservice_2.service;

import com.aldeamo.microservice_2.model.MessageDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMqConsumer {

    private final MessageTransactionService transactionService;

    @Autowired
    public RabbitMqConsumer(MessageTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(MessageDTO messageDTO, @Headers Map<String, Object> headers) {
        String timestampHeader = (String) headers.get("Timestamp");
        transactionService.saveMessageTransaction(messageDTO, timestampHeader);
    }
}


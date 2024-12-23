package com.aldeamo.microservice_2.controller;

import com.aldeamo.microservice_2.model.MessageTransactionDTO;
import com.aldeamo.microservice_2.service.MessageTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageTransactionController {
    private final MessageTransactionService messageTransactionService;

    @Autowired
    public MessageTransactionController(MessageTransactionService messageTransactionService) {
        this.messageTransactionService = messageTransactionService;
    }

    @GetMapping("/messages/{destination}")
    public ResponseEntity<List<MessageTransactionDTO>> getMessagesByDestination(@PathVariable String destination) {
        List<MessageTransactionDTO> messageTransactions = messageTransactionService.getAllMessageTransactionsByDestination(destination);

        if (messageTransactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messageTransactions);
    }
}

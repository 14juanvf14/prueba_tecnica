package com.aldeamo.microservice_2.service;

import com.aldeamo.microservice_2.model.MessageDTO;
import com.aldeamo.microservice_2.model.MessageTransactionDTO;

import java.util.List;

public interface MessageTransactionService {
    void saveMessageTransaction(MessageDTO messageDTO, String createdDate);

    List<MessageTransactionDTO> getAllMessageTransactionsByDestination(String destination);
}

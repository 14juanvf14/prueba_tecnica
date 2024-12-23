package com.aldeamo.microservice_2.service;

import com.aldeamo.microservice_2.exception.InvalidMessageTypeException;
import com.aldeamo.microservice_2.exception.InvalidPhoneNumberException;
import com.aldeamo.microservice_2.model.MessageDTO;
import com.aldeamo.microservice_2.model.MessageTransaction;
import com.aldeamo.microservice_2.model.MessageTransactionDTO;
import com.aldeamo.microservice_2.repository.MessageTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class MessageTransactionServiceImpl implements MessageTransactionService{
    private final MessageTransactionRepository messageTransactionRepository;

    @Autowired
    public MessageTransactionServiceImpl(MessageTransactionRepository messageTransactionRepository) {
        this.messageTransactionRepository = messageTransactionRepository;
    }


    @Override
    public void saveMessageTransaction(MessageDTO messageDTO, String createdDate) {
        validateMessage(messageDTO, createdDate);

        MessageTransaction messageTransaction = MessageTransaction.builder()
                .messageType(messageDTO.getMessageType())
                .content(messageDTO.getContent())
                .destination(messageDTO.getDestination())
                .origin(messageDTO.getOrigin())
                .createdDate(Instant.parse(createdDate))
                .build();


        Instant processingDate = Instant.now();

        if (!isValidToSend(messageDTO.getDestination(),processingDate)) {
            messageTransaction.setError("El destinatario ha alcanzado el maximo de comunicaciones en las ultimas 24 horas");
        }

        messageTransaction.setProcessingTime((int) Duration.between(Instant.parse(createdDate), Instant.now()).toMillis());
        messageTransactionRepository.save(messageTransaction);
    }


    @Override
    public List<MessageTransactionDTO> getAllMessageTransactionsByDestination(String destination) {
        return messageTransactionRepository.findByDestination(destination).stream()
                .map(this::convertToDTO)
                .toList();
    }


    private void validateMessage (MessageDTO messageDTO, String createdDate){
        if (messageDTO == null) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo.");
        }
        if (!messageDTO.getMessageType().equals("Texto") &&
                !messageDTO.getMessageType().equals("Imagen") &&
                !messageDTO.getMessageType().equals("Video") &&
                !messageDTO.getMessageType().equals("Documento")) {
            throw new InvalidMessageTypeException("El tipo de mensaje no esta soportado.");
        }
        if (!messageDTO.getMessageType().equals("Texto") && !isValidURL(messageDTO.getContent())) {
            throw new IllegalArgumentException("El contenido del mensaje debe ser una URL.");
        }
        if (isValidNumber(messageDTO.getOrigin())) {
            throw new InvalidPhoneNumberException("El número de origen no es válido.");
        }

        if (isValidNumber(messageDTO.getDestination())) {
            throw new InvalidPhoneNumberException("El número de destino no es válido.");
        }

        if (!isValidInstant(createdDate)) {
            throw new IllegalArgumentException("La fecha de solicitud no tiene el formato correcto");
        }
    }

    private boolean isValidURL(String content) {
        try {
            URI uri = new URI(content);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private boolean isValidNumber(String number) {
        if (number == null || number.isEmpty()) {
            return true;
        }
        return !number.matches("\\d+");
    }

    private boolean isValidInstant(String createdDate) {
        try {
            Instant.parse(createdDate);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    private boolean isValidToSend(String destination, Instant processingDate){
        Instant limit = processingDate.minus(24, ChronoUnit.HOURS);
        return messageTransactionRepository.findByDestinationAndCreatedDateAfter(destination, limit).size() < 3;
    }

    private MessageTransactionDTO convertToDTO(MessageTransaction messageTransaction) {
        return MessageTransactionDTO.builder()
                .id(messageTransaction.getId())
                .origin(messageTransaction.getOrigin())
                .destination(messageTransaction.getDestination())
                .messageType(messageTransaction.getMessageType())
                .content(messageTransaction.getContent())
                .processingTime(messageTransaction.getProcessingTime())
                .createdDate(messageTransaction.getCreatedDate())
                .error(messageTransaction.getError())
                .build();
    }
}

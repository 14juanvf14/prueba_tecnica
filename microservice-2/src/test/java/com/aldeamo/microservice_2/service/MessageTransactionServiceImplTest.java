package com.aldeamo.microservice_2.service;

import com.aldeamo.microservice_2.model.MessageDTO;
import com.aldeamo.microservice_2.model.MessageTransaction;
import com.aldeamo.microservice_2.repository.MessageTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageTransactionServiceImplTest {

    @Mock
    private MessageTransactionRepository messageTransactionRepository;

    @InjectMocks
    private MessageTransactionServiceImpl messageTransactionService;

    @Test
    void saveMessageTransaction_ShouldSetError_WhenMoreThanThreeMessagesSent() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setOrigin("987654321");
        messageDTO.setDestination("123456789");
        messageDTO.setMessageType("Texto");
        messageDTO.setContent("Hello");

        String createdDate = Instant.now().minus(1, ChronoUnit.HOURS).toString();

        // Usar el patrón Builder para crear la instancia de MessageTransaction
        MessageTransaction messageTransaction = MessageTransaction.builder()
                .origin("origin")
                .destination("123456789")
                .messageType("Texto")
                .content("content")
                .processingTime(100)
                .createdDate(Instant.now())
                .error(null)
                .build();

        when(messageTransactionRepository.findByDestinationAndCreatedDateAfter(eq("123456789"), any(Instant.class)))
                .thenReturn(List.of(messageTransaction, messageTransaction, messageTransaction));

        messageTransactionService.saveMessageTransaction(messageDTO, createdDate);

        ArgumentCaptor<MessageTransaction> captor = ArgumentCaptor.forClass(MessageTransaction.class);
        verify(messageTransactionRepository).save(captor.capture());

        MessageTransaction savedTransaction = captor.getValue();
        assertEquals("El destinatario ha alcanzado el maximo de comunicaciones en las ultimas 24 horas", savedTransaction.getError());
    }
}

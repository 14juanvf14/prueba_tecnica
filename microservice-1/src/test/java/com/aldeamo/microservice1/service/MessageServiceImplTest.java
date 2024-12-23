package com.aldeamo.microservice1.service;


import com.aldeamo.microservice1.exception.InvalidMessageTypeException;
import com.aldeamo.microservice1.exception.InvalidPhoneNumberException;
import com.aldeamo.microservice1.exception.UnauthorizedOriginException;
import com.aldeamo.microservice1.model.MessageDTO;
import com.aldeamo.microservice1.model.Origins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private OriginsService originsService;

    @Mock
    private RabbitMqProducer rabbitMqProducer;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void sendMessage_validMessage_shouldSendToRabbitMQ() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setOrigin("987654321");
        messageDTO.setDestination("123456789");
        messageDTO.setMessageType("Texto");
        messageDTO.setContent("Hello");

        Origins origin = new Origins();
        Mockito.when(originsService.getOriginByNumber("987654321")).thenReturn(Optional.of(origin));

        messageService.sendMessage(messageDTO);

        Mockito.verify(rabbitMqProducer, Mockito.times(1)).sendToRabbitMQ(messageDTO);
    }

    @Test
    void sendMessage_nullMessage() {
        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(null));
    }

    @Test
    void sendMessage_unauthorizedOrigin() {
        // Arrange
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setOrigin("987654321");
        messageDTO.setDestination("123456789");
        messageDTO.setMessageType("Texto");
        messageDTO.setContent("Hello");

        Mockito.when(originsService.getOriginByNumber("987654321")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedOriginException.class, () -> messageService.sendMessage(messageDTO));
    }

    @Test
    void sendMessage_invalidMessageType() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setOrigin("987654321");
        messageDTO.setDestination("123456789");
        messageDTO.setMessageType("Audio"); // Tipo de mensaje no soportado
        messageDTO.setContent("Hello");

        Origins origin = new Origins();
        Mockito.when(originsService.getOriginByNumber("987654321")).thenReturn(Optional.of(origin));

        assertThrows(InvalidMessageTypeException.class, () -> messageService.sendMessage(messageDTO));
    }

    @Test
    void sendMessage_invalidURLContent() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setOrigin("987654321");
        messageDTO.setDestination("123456789");
        messageDTO.setMessageType("Imagen");
        messageDTO.setContent("not-a-url"); //URL No valida para el tipo de mensaje (Debe ser una url)

        Origins origin = new Origins();
        Mockito.when(originsService.getOriginByNumber("987654321")).thenReturn(Optional.of(origin));

        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(messageDTO));
    }

    @Test
    void sendMessage_invalidOriginNumber() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setOrigin("/245#invalid-number");
        messageDTO.setDestination("123456789");
        messageDTO.setMessageType("Texto");
        messageDTO.setContent("Hello");
        assertThrows(InvalidPhoneNumberException.class, () -> messageService.sendMessage(messageDTO));
    }

    @Test
    void sendMessage_invalidDestinationNumber() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setOrigin("987654321");
        messageDTO.setDestination("#invalid-number//");
        messageDTO.setMessageType("Texto");
        messageDTO.setContent("Hello");

        Origins origin = new Origins();
        Mockito.when(originsService.getOriginByNumber("987654321")).thenReturn(Optional.of(origin));

        assertThrows(InvalidPhoneNumberException.class, () -> messageService.sendMessage(messageDTO));
    }

}
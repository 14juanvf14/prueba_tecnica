package com.aldeamo.microservice1.service;

import com.aldeamo.microservice1.model.MessageDTO;
import com.aldeamo.microservice1.exception.InvalidMessageTypeException;
import com.aldeamo.microservice1.exception.InvalidPhoneNumberException;
import com.aldeamo.microservice1.exception.UnauthorizedOriginException;
import com.aldeamo.microservice1.model.Origins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.URI;
import java.util.Optional;

/**
 * La clase implementa el servicio de mensajeria para envíos de mensajes
 * Se encarga de validar el contenido y lanzar excepciones en caso de que no
 * se supere la misma, antes de enviar al Exchange de RabbitMQ
 * @author juanv
 */
@Service
public class MessageServiceImpl implements MessageService {
    private final OriginsService originsService;
    private final RabbitMqProducer rabbitMqProducer;

    @Autowired
    public MessageServiceImpl(OriginsService originsService, RabbitMqProducer rabbitMqProducer) {
        this.originsService = originsService;
        this.rabbitMqProducer = rabbitMqProducer;
    }

    /**
     * Este metodo envia al servicio producer de RabbitMQ el mensaje despues de validarlo
     * @param message El mensaje que se debe validar
     */
    @Override
    public void sendMessage(MessageDTO message) {
        validateMessage(message);
        rabbitMqProducer.sendToRabbitMQ(message);

    }

    /**
     * El metodo verifica que el mensaje no sea nulo, que la linea de origen esté autorizada, que el tipo de mensaje sea válido,
     * que el contenido cumpla con las reglas correspondientes y que los números de origen y destino sean válidos.
     *
     * @param message El mensaje a validar.
     * @throws IllegalArgumentException Si alguna de las validaciones falla.
     * @throws UnauthorizedOriginException Si la linea de origen del mensaje no está autorizado.
     * @throws InvalidMessageTypeException Si el tipo de mensaje no es soportado.
     * @throws InvalidPhoneNumberException Si los números de origen o destino no son válidos como números enteros.
     */
    public void validateMessage(MessageDTO message) {
        if (message == null) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo.");
        }

        if (isNotValidNumber(message.getOrigin())) {
            throw new InvalidPhoneNumberException("El número de origen no es válido.");
        }

        Optional<Origins> origin = originsService.getOriginByNumber(message.getOrigin());
        if (origin.isEmpty()) {
            throw new UnauthorizedOriginException("La línea de origen no está autorizada.");
        }

        if (!message.getMessageType().equals("Texto") &&
                !message.getMessageType().equals("Imagen") &&
                !message.getMessageType().equals("Video") &&
                !message.getMessageType().equals("Documento")) {
            throw new InvalidMessageTypeException("El tipo de mensaje no esta soportado.");
        }
        if (!message.getMessageType().equals("Texto") && !isValidURL(message.getContent())) {
            throw new IllegalArgumentException("El contenido del mensaje debe ser una URL.");
        }

        if (isNotValidNumber(message.getDestination())) {
            throw new InvalidPhoneNumberException("El número de destino no es válido.");
        }

    }

    /**
     * Valida si el contenido es una URL válida.
     *
     * @param content El contenido a validar.
     * @return true si el contenido es una URL válida
     */
    public boolean isValidURL(String content) {
        try {
            URI uri = new URI(content);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    /**
     * Se encarga de validar el número
     * @param number Numero que se debe validar
     * @return true en caso de que no sea nulo ni vacio
     */
    public boolean isNotValidNumber(String number) {
        if (number == null || number.isEmpty()) {
            return true;
        }
        return !number.matches("\\d+");
    }
}

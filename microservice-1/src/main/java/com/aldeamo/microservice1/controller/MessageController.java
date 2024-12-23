package com.aldeamo.microservice1.controller;

import com.aldeamo.microservice1.model.MessageDTO;
import com.aldeamo.microservice1.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que establece un controlador REST
 * para el envío de mensajes
 *
 * @author juanv
 */
@RestController
@RequestMapping("/api")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Endpoint para peticiones de envíos de mensajes
     *
     * Recibe peticiones de HTTP de tipo POST en "/api/send"
     * @param message objeto que representa un json con el cuerpo de mensaje a enviar.
     * @return Si es procesado correcamente, retorna un HTTP Status 2xx y mensaje de confirmación
     */
    @PostMapping("/send")
    public ResponseEntity <String> sendMessage(@RequestBody MessageDTO message) {
        messageService.sendMessage(message);
        return ResponseEntity.ok("Mensaje procesado");
    }
}

package com.aldeamo.microservice1.service;

import com.aldeamo.microservice1.model.MessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Esta clase implementa el servicio que funciona como productor (publisher)
 * para la cola de Rabbit MQ, en este caso, delega el envío a un Exchange
 *
 * @author juanv
 */
@Service
public class RabbitMQProducerImpl implements RabbitMqProducer{

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Este metodo se encargar de convertir el mensaje a JSON, partiendo del objeto entregado,
     * enviando el mensaje al exchange que enruta mediante el routingKey a la cola correspondiente.
     * Añade a la cabecera Timestamp, que corresponde al tiempo de creación de la solicitud.
     * @param messageDTO el mensaje que se desea enviar a RabbitMQ
     */
    @Override
    public void sendToRabbitMQ(MessageDTO messageDTO) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDTO, message -> {
            message.getMessageProperties().setHeader("Timestamp", Instant.now().toString());
            return message;
        });
    }

}

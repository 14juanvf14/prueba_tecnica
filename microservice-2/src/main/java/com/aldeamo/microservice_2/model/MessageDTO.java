package com.aldeamo.microservice_2.model;

import lombok.Data;

@Data
public class MessageDTO {
    private String origin;
    private String destination;
    private String messageType;
    private String content;
}

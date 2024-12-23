package com.aldeamo.microservice1.model;

import lombok.Data;

@Data
public class MessageDTO {
    private String origin;
    private String destination;
    private String messageType;
    private String content;
}

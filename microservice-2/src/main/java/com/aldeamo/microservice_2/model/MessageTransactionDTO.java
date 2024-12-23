package com.aldeamo.microservice_2.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MessageTransactionDTO {
    private String id;
    private String origin;
    private String destination;
    private String messageType;
    private String content;
    private Integer processingTime;
    private Instant createdDate;
    private String error;
}

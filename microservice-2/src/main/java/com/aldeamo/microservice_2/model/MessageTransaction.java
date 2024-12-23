package com.aldeamo.microservice_2.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "mesagge_transactions")
@Data
@Builder
public class MessageTransaction {
    @Id
    private String id;
    private String origin;
    private String destination;
    private String messageType;
    private String content;
    private Integer processingTime;
    private Instant createdDate;
    private String error;
}

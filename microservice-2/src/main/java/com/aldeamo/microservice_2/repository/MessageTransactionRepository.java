package com.aldeamo.microservice_2.repository;

import com.aldeamo.microservice_2.model.MessageTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface MessageTransactionRepository extends MongoRepository<MessageTransaction, String> {
    List<MessageTransaction> findByDestinationAndCreatedDateAfter(String destination, Instant createdDate);
    List<MessageTransaction> findByDestination(String destination);
}

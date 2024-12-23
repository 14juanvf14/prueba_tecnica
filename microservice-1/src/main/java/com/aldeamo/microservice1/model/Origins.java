package com.aldeamo.microservice1.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Origins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String origin;
}

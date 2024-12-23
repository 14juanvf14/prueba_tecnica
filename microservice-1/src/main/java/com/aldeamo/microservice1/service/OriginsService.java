package com.aldeamo.microservice1.service;

import com.aldeamo.microservice1.model.Origins;

import java.util.Optional;

public interface OriginsService {
    Optional<Origins> getOriginByNumber(String number);
}

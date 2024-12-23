package com.aldeamo.microservice1.repository;

import com.aldeamo.microservice1.model.Origins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriginsRepository extends JpaRepository<Origins, Long> {
    Optional<Origins> findByOrigin(String origin);
}

package com.aldeamo.microservice1.service;

import com.aldeamo.microservice1.model.Origins;
import com.aldeamo.microservice1.repository.OriginsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Esta clase implementa un servicio para gestionar las operaciones relacionadas
 * con las lineas de orígen autorizadas.
 * Se encarga de interactuar con el repositorio para recuperar datos de las lineas orígen.
 *
 * @author juanv
 */
@Service
public class OriginsServiceImpl implements OriginsService {

    private final OriginsRepository originsRepository;

    @Autowired
    public OriginsServiceImpl(OriginsRepository originsRepository) {
        this.originsRepository = originsRepository;
    }

    @Override
    public Optional<Origins> getOriginByNumber(String number) {
        return originsRepository.findByOrigin(number);
    }
}

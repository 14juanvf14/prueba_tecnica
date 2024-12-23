package com.aldeamo.microservice_2.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Esta clase funciona como extractor de headers para el filtro
 * de seguridad y valida el Api Key de acuerdo a la configuración del microservicio
 *
 * Esta clase forma parte de la implementación del filtro de API Key
 *
 * @author juanv
 */
@Component
public class ApiKeyAuthExtractor {

    @Value("${api.key}")
    private String apiKey;

    /**
     * El metodo obtiene y valida el Api Key de la solicitud HTTP.
     * Si es valido genera una instancia de la clase {@link ApiKeyAuth}
     *
     * @param request petición HTTP que ingresa al filtro
     * @return un objeto {@link Optional} con la instancia ApiKeyAuth si es valido el ApiKey, vacio si no es valido.
     */
    public Optional<Authentication> extract(HttpServletRequest request) {
        String providedKey = request.getHeader("ApiKey");
        if (providedKey == null || !providedKey.equals(apiKey))
            return Optional.empty();

        return Optional.of(new ApiKeyAuth(providedKey, AuthorityUtils.NO_AUTHORITIES));
    }

}

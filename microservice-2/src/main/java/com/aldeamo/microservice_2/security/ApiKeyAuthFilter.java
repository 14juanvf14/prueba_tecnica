package com.aldeamo.microservice_2.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Esta clase corresponde al filtro de la cofiguración de Spring Security
 *
 * Debido a la herencia de la clase {@link OncePerRequestFilter} este mismo solo se ejecuta
 * una vez por solicitud HTTP
 *
 * @author juanv
 */
@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final ApiKeyAuthExtractor extractor;

    /**
     * Este metodo se encarga de filtrar las solicitudes, extrayendo el Api Key
     * Si el mismo es valido se encarga de generar el contexto de seguridad.
     *
     * Finalmente, retorna a los filtros configurados en SecurityConfiguration
     *
     * @param request El objeto HTTP Servlet de la petición hacia el microservicio
     * @param response El objeto HTTP Servlet de la respuesta desde el microservicio
     * @param filterChain La configuración de filtros del microservicio
     * @throws ServletException En caso de un error en el servlet de request, response
     * @throws IOException En caso de un error de entrada/salida
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractor.extract(request)
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

        filterChain.doFilter(request, response);
    }
}

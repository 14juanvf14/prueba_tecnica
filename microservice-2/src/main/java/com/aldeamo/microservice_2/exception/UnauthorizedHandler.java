package com.aldeamo.microservice_2.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ExceptionHandlerController.ErrorResponse errorResponse = ExceptionHandlerController.ErrorResponse.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .message("No cuenta se encuentra autenticado")
                .build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}

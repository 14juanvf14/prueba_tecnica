package com.aldeamo.microservice_2.security;

import com.aldeamo.microservice_2.exception.UnauthorizedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuracion de seguridad para el microservicio,
 * Donde se determinan politicas de autenticación y filtros personalizados.
 *
 * El filtro configurado esta basado en autenticación mediante API Key,
 * en este caso la clase {@link ApiKeyAuthFilter}
 *
 * @author juanv
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ApiKeyAuthFilter authFilter;
    private final UnauthorizedHandler unauthorizedHandler;

    /**
     * Este metodo configura los filtros de seguridad de la aplicación
     * - Se inactivan los CORS y CSRF puesto que la prueba no tiene un ambiente de browser
     * - Se configura las sesiones "Sin estado"
     * - Configuración del manejo de excepciones para peticiones no autenticadas
     * - Se definen reglas de autorización a todas las rutas requiriendo autorización
     *
     * @param http Objeto Http entregado por Spring Security
     * @return configuración de filtros para seguridad
     * @throws Exception si ocurre un error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(unauthorizedHandler)) // Este paso es importante para el funcionamiento, requiere manejar excepciones
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
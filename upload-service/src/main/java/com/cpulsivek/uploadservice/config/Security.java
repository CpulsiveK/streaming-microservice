package com.cpulsivek.uploadservice.config;

import com.cpulsivek.uploadservice.service.jwt.JwtAuthFilter;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Configuration
public class Security {
  private final JwtAuthFilter jwtAuthFilter;

  @Autowired
  public Security(JwtAuthFilter jwtAuthFilter) {
    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
            .cors(
                    cors ->
                            cors.configurationSource(
                                    request -> {
                                      CorsConfiguration configuration = new CorsConfiguration();
                                      configuration.setAllowedOrigins(
                                              List.of("http://localhost:4200"));
                                      configuration.setAllowedMethods(
                                              List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
                                      configuration.setAllowedHeaders(
                                              List.of("Content-Type", "Content-Disposition", "Authorization"));
                                      return configuration;
                                    }))
        .csrf(AbstractHttpConfigurer::disable)
        .securityContext(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}

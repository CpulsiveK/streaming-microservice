package com.cpulsivek.gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info =
        @Info(
            contact =
                @Contact(name = "Streaming Microservice", email = "impulisvecliff7@gmail.com"),
            description = "Documentation for Streaming Microservice API",
            title = "Streaming Microservice API",
            version = "1.0.0",
            license = @License(name = "CpulsiveK")),
    servers = {
      @Server(description = "Development Server", url = "http://localhost:3040"),
      @Server(description = "Production Server", url = "To be replaced with server if ready")
    })
public class OpenAPIConfig {}

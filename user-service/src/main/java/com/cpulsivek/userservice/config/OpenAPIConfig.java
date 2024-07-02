package com.cpulsivek.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * OpenAPI Configuration class for the User Management Service of the Streaming microservice API.
 * This class provides metadata for the API documentation including contact information, license,
 * description, title, and server details.
 */
@OpenAPIDefinition(
    info =
        @Info(
            contact =
                @Contact(name = "Streaming Microservice", email = "impulsivecliff7@gmail.com"),
            description =
                "This documentation provides the detailed API specifications for the User Management Service "
                    + "of the Streaming microservice API. It includes all the available endpoints, their request "
                    + "and response formats, "
                    + "and other relevant details.",
            title = "Streaming Microservice API - User Management",
            version = "1.0.0",
            license = @License(name = "CpulsiveK")),
    servers = {
      @Server(description = "Development Server", url = "http://localhost:3004"),
      @Server(
          description = "Production Server",
          url = "N/A" // To be updated with actual production URL when available
          )
    })
public class OpenAPIConfig {
  // No additional configuration is required here as the annotations provide all necessary details.
}

package com.yohagos.multiple_databases.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Yohagos",
                        url = "yohagos.github.com"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification",
                version = "1.0.0",
                license = @License(
                        name = "License name",
                        url = "https://test.com"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "DEV environment",
                        url = "http://localhost:9090/api/v1"
                ),
                @Server(
                        description = "PROD environment -test",
                        url = "https://yohagos.github.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Keycloak",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "Keycloak",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

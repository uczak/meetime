package com.test.meetime.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${hubspot.server-url}")
    private String serverUrl;
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl(serverUrl); //  Cloud Run URL

        return new OpenAPI()
                .info(new Info()
                        .title("Hubspot API")
                        .description("API Document")
                        .version("1.0.0"))
                .servers(List.of(server));
    }
}

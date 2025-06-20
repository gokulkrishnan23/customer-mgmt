package com.tcs.customer_management.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI registrationOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Customer Management")
                        .description("Customer API Description")
                        .version("1.0"));
    }


}

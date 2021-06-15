package com.musala.examnoel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 15/5/21
    Time: 20:58
*/
@Component
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title("Mantenedor API").version(appVersion)
                        .license(new License().name("Apache 2.0").url("http://demo.org"))
                .contact(new Contact().name("Eduardo Noel").email("enoel@soaint.com")));
    }

}

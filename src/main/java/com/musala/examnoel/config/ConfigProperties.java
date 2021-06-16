package com.musala.examnoel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 13/5/21
    Time: 3:08
*/
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class ConfigProperties {

    private String guardado;
    private String borrado;
    private String noId;
    private String duplicado;
    private String actualizacion;
    private String msgError;
    private String notFound;

}
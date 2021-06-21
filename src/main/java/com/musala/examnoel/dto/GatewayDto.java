package com.musala.examnoel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GatewayDto implements Serializable {

    @JsonIgnore
    private String idGateway;

    private String uuid;

    @NotNull(message = "You must send this value")
    @NotEmpty(message = "Cannot be sent in blank")
    private String name;

    @NotNull
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
            message = "IP address is wrong!!!")
    private String ip;

    @Valid
    @Size(min = 1, max = 10,message = "Must send at least 1 peripheral")
    private List<PeripheralDto> peripherals;

}

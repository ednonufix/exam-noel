package com.musala.examnoel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PeripheralDto implements Serializable {

    private Long uid;

    @NotNull(message = "You must send this value")
    @NotEmpty(message = "Cannot be sent in blank")
    private String vector;

    @NotNull(message = "You must specify a date")
    private LocalDate dateCreated;

    @NotNull(message = "Must send true or false")
    private Boolean status;

}

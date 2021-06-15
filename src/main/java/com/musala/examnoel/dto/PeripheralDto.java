package com.musala.examnoel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PeripheralDto implements Serializable {

    private Long uid;

    @NotNull
    @NotEmpty
    private String vector;

    @NotNull
    private LocalDate dateCreated;

    @NotNull
    private Boolean status;

}

package com.musala.examnoel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Peripheral implements Serializable {

    private Long uid;
    private String vector;
    private LocalDate dateCreated;
    private Boolean status;

}

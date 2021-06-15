package com.musala.examnoel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Gateway implements Serializable {

    private String uuid;
    private String name;
    private String ip;
    private List<Peripheral> peripherals;
}

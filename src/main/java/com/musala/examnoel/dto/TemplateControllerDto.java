package com.musala.examnoel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 13/5/21
    Time: 18:33
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateControllerDto implements Serializable {

    private String msg;

}

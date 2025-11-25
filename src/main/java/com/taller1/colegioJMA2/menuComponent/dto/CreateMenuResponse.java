package com.taller1.colegioJMA2.menuComponent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuResponse {
    private Integer id;
    private String name;
    private String message;
}
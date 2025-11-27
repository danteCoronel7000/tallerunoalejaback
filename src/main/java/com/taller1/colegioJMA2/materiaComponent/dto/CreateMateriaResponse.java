package com.taller1.colegioJMA2.materiaComponent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMateriaResponse {
    private Integer id;
    private String name;
    private String message;
}
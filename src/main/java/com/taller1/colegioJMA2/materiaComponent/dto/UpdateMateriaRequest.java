package com.taller1.colegioJMA2.materiaComponent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateMateriaRequest {
    private String nombre;
    private String estado;
}
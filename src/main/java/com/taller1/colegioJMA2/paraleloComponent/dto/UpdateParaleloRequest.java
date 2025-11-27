package com.taller1.colegioJMA2.paraleloComponent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateParaleloRequest {
    private String nombre;
    private String estado;
}

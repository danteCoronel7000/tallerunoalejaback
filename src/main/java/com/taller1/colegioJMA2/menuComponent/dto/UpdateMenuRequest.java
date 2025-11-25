package com.taller1.colegioJMA2.menuComponent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateMenuRequest {
    private String nombre;
    private String estado;
}

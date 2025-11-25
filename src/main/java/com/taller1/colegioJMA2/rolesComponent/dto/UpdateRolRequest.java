package com.taller1.colegioJMA2.rolesComponent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateRolRequest {
    private String nombre;
    private String estado;
}
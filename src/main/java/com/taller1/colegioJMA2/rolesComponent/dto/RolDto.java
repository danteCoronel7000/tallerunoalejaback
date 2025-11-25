package com.taller1.colegioJMA2.rolesComponent.dto;

import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RolDto {
    private Integer codr;
    private String nombre;
    private String estado;

    public RolDto(RolEntity rol){
        this.codr = rol.getCodr();
        this.nombre = rol.getNombre();
        this.estado = rol.getEstado();
    }
}

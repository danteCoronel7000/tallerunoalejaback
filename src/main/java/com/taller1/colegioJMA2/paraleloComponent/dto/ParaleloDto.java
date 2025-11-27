package com.taller1.colegioJMA2.paraleloComponent.dto;

import com.taller1.colegioJMA2.paraleloComponent.entitys.ParaleloEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParaleloDto {
    private Integer codpar;
    private String nombre;
    private String estado;

    public ParaleloDto(ParaleloEntity paralelo) {
        this.codpar = paralelo.getCodpar();
        this.nombre = paralelo.getNombre();
        this.estado = paralelo.getEstado();
    }
}
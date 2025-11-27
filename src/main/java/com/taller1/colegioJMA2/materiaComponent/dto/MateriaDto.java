package com.taller1.colegioJMA2.materiaComponent.dto;

import com.taller1.colegioJMA2.materiaComponent.entitys.MateriaEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MateriaDto {
    private Integer codmat;
    private String nombre;
    private String estado;

    public MateriaDto(MateriaEntity materia) {
        this.codmat = materia.getCodmat();
        this.nombre = materia.getNombre();
        this.estado = materia.getEstado();
    }
}
package com.taller1.colegioJMA2.procesosComponent.dto;

import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProcesoDto {
    private Integer codp;
    private String nombre;
    private String enlace;
    private String ayuda;
    private String estado;

    public ProcesoDto(ProcesoEntity proceso) {
        this.codp = proceso.getCodp();
        this.nombre = proceso.getNombre();
        this.enlace = proceso.getEnlace();
        this.ayuda = proceso.getAyuda();
        this.estado = proceso.getEstado();
    }
}
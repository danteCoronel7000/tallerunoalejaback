package com.taller1.colegioJMA2.dto;

import lombok.Data;

@Data
public class ProcesoDTO {
    private Integer codp;
    private String nombre;
    private String enlace;
    private String ayuda;
    private Integer estado;
}
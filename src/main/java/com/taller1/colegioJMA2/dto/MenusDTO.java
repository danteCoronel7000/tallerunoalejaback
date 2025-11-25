package com.taller1.colegioJMA2.dto;



import lombok.Data;
import java.util.List;

@Data
public class MenusDTO {
    private Integer codm;
    private String nombre;
    private Integer estado;
    
    
    private List<ProcesoDTO> procesos;
}
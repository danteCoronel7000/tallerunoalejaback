package com.taller1.colegioJMA2.menuComponent.dto;

import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDto {
    private Integer codm;
    private String nombre;
    private String estado;

    public MenuDto(MenuEntity menu){
        this.codm = menu.getCodm();
        this.nombre = menu.getNombre();
        this.estado = menu.getEstado();
    }
}

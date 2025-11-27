package com.taller1.colegioJMA2.menuComponent.dto;

import lombok.Data;

import java.util.List;
@Data
public class AsignarMenusRolDTO {
    private Integer rolId;
    private List<Integer> menusIds;
    // getters y setters
}
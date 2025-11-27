package com.taller1.colegioJMA2.procesosComponent.dto;

import lombok.Data;

import java.util.List;
@Data
public class AsignarProcesosMenuDTO {
    private Integer menuId;
    private List<Integer> procesosIds;
}

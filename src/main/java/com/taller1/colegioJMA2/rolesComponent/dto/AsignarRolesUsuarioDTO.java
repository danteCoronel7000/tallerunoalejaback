package com.taller1.colegioJMA2.rolesComponent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AsignarRolesUsuarioDTO {

    private String usuarioId;      // login del usuario
    private List<Integer> rolesIds;

    // getters y setters
}

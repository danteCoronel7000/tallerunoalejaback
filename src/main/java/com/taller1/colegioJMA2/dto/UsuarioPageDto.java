package com.taller1.colegioJMA2.dto;

import com.taller1.colegioJMA2.model.PersonalModel;
import com.taller1.colegioJMA2.model.UsuariosModel;
import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioPageDto {
    private String login;
    private int estado;
    private String nombre;
    private String ap;
    private String am;
    public UsuarioPageDto(UsuariosModel usuario){
        this.login = usuario.getLogin();
        this.estado = usuario.getEstado();
        this.nombre = usuario.getPersonal().getNombre();
        this.ap = usuario.getPersonal().getAp();
        this.am = usuario.getPersonal().getAm();
    }
}
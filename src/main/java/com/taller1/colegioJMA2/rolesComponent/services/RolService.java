package com.taller1.colegioJMA2.rolesComponent.services;


import com.taller1.colegioJMA2.model.UsuariosModel;
import com.taller1.colegioJMA2.rolesComponent.dto.*;
import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RolService {
    List<RolEntity> listar();
    RolEntity buscarPorId(Integer id);
    RolEntity guardar(RolEntity rol);
    RolEntity actualizar(Integer id, RolEntity rol);
    void eliminar(Integer id);
    CreateRoleResponse createRole(CreateRoleRequest request);
    Page<RolDto> getRolesPaginados(Pageable pageable);
    // Si no tienes este método, agrégalo para buscar por ID
    RolEntity findById(Integer id);
    // Método para actualizar rol
    RolEntity updateRol(Integer id, UpdateRolRequest updateRolRequest);
    void habilitarRol(Integer codr);
    void deshabilitarRol(Integer codr);
    List<RolDto> buscarRolesPorNombre(String nombre);
    List<RolDto> findByEstado(String estado);
    List<RolDto> getRolesForUser(String login);
    List<RolDto> getUnassignedRoles();
    List<RolDto> getRolesAssignedToAnyUser();
    UsuariosModel asignarRoles(AsignarRolesUsuarioDTO dto);
}
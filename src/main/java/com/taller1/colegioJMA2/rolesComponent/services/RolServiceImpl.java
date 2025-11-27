package com.taller1.colegioJMA2.rolesComponent.services;


import com.taller1.colegioJMA2.model.UsuariosModel;
import com.taller1.colegioJMA2.repository.UsuariosRepo;
import com.taller1.colegioJMA2.rolesComponent.dto.*;
import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import com.taller1.colegioJMA2.rolesComponent.respositorys.RolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuariosRepo usuariosRepo;

    @Override
    public List<RolEntity> listar() {
        return rolRepository.findAll();
    }

    @Override
    public RolEntity buscarPorId(Integer id) {
        return rolRepository.findById(id)
                .orElse(null);
    }

    @Override
    public RolEntity guardar(RolEntity rol) {
        return rolRepository.save(rol);
    }

    @Override
    public RolEntity actualizar(Integer id, RolEntity rol) {
        RolEntity actual = buscarPorId(id);
        if (actual == null) return null;

        actual.setNombre(rol.getNombre());
        actual.setEstado(rol.getEstado());

        return rolRepository.save(actual);
    }

    @Override
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }

    @Override
    public CreateRoleResponse createRole(CreateRoleRequest request) {

        RolEntity role = new RolEntity();
        role.setNombre(request.getName());
        role.setEstado("1");

        RolEntity saved = rolRepository.save(role);

        return new CreateRoleResponse(
                saved.getCodr(),
                saved.getNombre(),
                "Rol creado exitosamente"
        );
    }

    @Override
    public Page<RolDto> getRolesPaginados(Pageable pageable) {
        Page<RolEntity> pageEntities = rolRepository.findAll(pageable);

        return pageEntities.map(entity -> {
            RolDto dto = new RolDto(entity);
            return dto;
        });
    }

    @Override
    public RolEntity updateRol(Integer id, UpdateRolRequest updateRolRequest) {
        // Buscar el rol existente
        RolEntity rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));

        // Validar que el nuevo nombre no exista en otros roles (excepto el actual)
        if (updateRolRequest.getNombre() != null &&
                !updateRolRequest.getNombre().equals(rolExistente.getNombre())) {

            Optional<RolEntity> rolConMismoNombre = rolRepository.findByNombre(updateRolRequest.getNombre());
            if (rolConMismoNombre.isPresent() && !rolConMismoNombre.get().getCodr().equals(id)) {
                throw new RuntimeException("Ya existe un rol con el nombre: " + updateRolRequest.getNombre());
            }
        }

        // Actualizar campos
        if (updateRolRequest.getNombre() != null) {
            rolExistente.setNombre(updateRolRequest.getNombre());
        }

        if (updateRolRequest.getEstado() != null) {
            rolExistente.setEstado(updateRolRequest.getEstado());
        }

        // Guardar y retornar
        return rolRepository.save(rolExistente);
    }

    @Override
    public RolEntity findById(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }


    public void habilitarRol(Integer codr) {
        RolEntity rol = rolRepository.findById(codr)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con código: " + codr));
        rol.setEstado("1");
        rolRepository.save(rol);
    }

    public void deshabilitarRol(Integer codr) {
        RolEntity rol = rolRepository.findById(codr)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con código: " + codr));
        rol.setEstado("0");
        rolRepository.save(rol);
    }

    // Nuevo metodo para buscar roles por nombre
    public List<RolDto> buscarRolesPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return rolRepository.findAllByOrderByNombreAsc();
        }
        return rolRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    @Override
    public List<RolDto> findByEstado(String estado) {
        return rolRepository.findByEstado(estado);
    }

    // Roles de un usuario específico
    public List<RolDto> getRolesForUser(String login) {
        return rolRepository.findRolesAssignedToUser(login)
                .stream()
                .map(RolDto::new)
                .collect(Collectors.toList());
    }

    // Roles que NO están asignados a ningún usuario
    public List<RolDto> getUnassignedRoles() {
        return rolRepository.findRolesNotAssignedToAnyUser()
                .stream()
                .map(RolDto::new)
                .collect(Collectors.toList());
    }

    // Roles que están asignados a cualquier usuario (al menos uno)
    public List<RolDto> getRolesAssignedToAnyUser() {
        return rolRepository.findAllRolesAssignedToAnyUser()
                .stream()
                .map(RolDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuariosModel asignarRoles(AsignarRolesUsuarioDTO dto) {

        // Buscar usuario
        UsuariosModel usuario = usuariosRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener roles actuales del usuario (Set para evitar duplicados)
        Set<RolEntity> rolesActuales = new HashSet<>(usuario.getRoles());

        // Buscar roles enviados en el DTO
        List<RolEntity> rolesNuevosSolicitados = rolRepository.findAllById(dto.getRolesIds());

        if (rolesNuevosSolicitados.isEmpty()) {
            throw new RuntimeException("Ningún rol válido encontrado");
        }

        // Agregar solo roles que NO tenga ya el usuario
        for (RolEntity rol : rolesNuevosSolicitados) {
            if (!rolesActuales.contains(rol)) {
                rolesActuales.add(rol);
            }
        }

        // Actualizar roles final
        usuario.setRoles(new ArrayList<>(rolesActuales));

        // Guardar
        return usuariosRepo.save(usuario);
    }


    @Override
    @Transactional
    public UsuariosModel desasignarRoles(AsignarRolesUsuarioDTO dto) {

        // Buscar usuario
        UsuariosModel usuario = usuariosRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener roles actuales del usuario
        Set<RolEntity> rolesActuales = new HashSet<>(usuario.getRoles());

        // Buscar roles a eliminar en el DTO
        List<RolEntity> rolesAEliminar = rolRepository.findAllById(dto.getRolesIds());

        if (rolesAEliminar.isEmpty()) {
            throw new RuntimeException("Ningún rol válido encontrado para desasignar");
        }

        // Eliminar los roles especificados
        for (RolEntity rol : rolesAEliminar) {
            rolesActuales.remove(rol);
        }

        // Actualizar roles final (no puede quedar sin al menos un rol)
        if (rolesActuales.isEmpty()) {
            throw new RuntimeException("El usuario debe tener al menos un rol asignado");
        }

        usuario.setRoles(new ArrayList<>(rolesActuales));

        // Guardar
        return usuariosRepo.save(usuario);
    }

}
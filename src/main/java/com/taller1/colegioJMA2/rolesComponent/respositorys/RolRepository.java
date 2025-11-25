package com.taller1.colegioJMA2.rolesComponent.respositorys;


import com.taller1.colegioJMA2.rolesComponent.dto.RolDto;
import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Integer> {
    Optional<RolEntity> findByNombre(String nombre);
    // Buscar roles por nombre (case insensitive y contiene)
    List<RolDto> findByNombreContainingIgnoreCase(String nombre);

    // Opcional: Buscar roles habilitados por nombre
    List<RolDto> findByNombreContainingIgnoreCaseAndEstado(String nombre, String estado);

    // Opcional: Buscar todos los roles ordenados por nombre
    List<RolDto> findAllByOrderByNombreAsc();

    List<RolDto> findByEstado(String estado);
}
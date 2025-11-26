package com.taller1.colegioJMA2.rolesComponent.respositorys;


import com.taller1.colegioJMA2.rolesComponent.dto.RolDto;
import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // 1. Obtener roles asignados a un usuario específico
    @Query(value = "SELECT r.* FROM roles r " +
            "INNER JOIN usurol ur ON r.codr = ur.codr " +
            "WHERE ur.login = :login",
            nativeQuery = true)
    List<RolEntity> findRolesAssignedToUser(@Param("login") String login);

    // 2. Obtener roles que NO están asignados a ningún usuario
    @Query(value = "SELECT r.* FROM roles r " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 FROM usurol ur " +
            "    WHERE ur.codr = r.codr" +
            ")",
            nativeQuery = true)
    List<RolEntity> findRolesNotAssignedToAnyUser();

    // Alternativa usando LEFT JOIN (también funciona para el caso 2)
    @Query(value = "SELECT r.* FROM roles r " +
            "LEFT JOIN usurol ur ON r.codr = ur.codr " +
            "WHERE ur.codr IS NULL",
            nativeQuery = true)
    List<RolEntity> findUnassignedRoles();

    // 3. Obtener TODOS los roles que están asignados a al menos un usuario
    @Query(value = "SELECT DISTINCT r.* FROM roles r " +
            "INNER JOIN usurol ur ON r.codr = ur.codr",
            nativeQuery = true)
    List<RolEntity> findAllRolesAssignedToAnyUser();

    // Alternativa usando EXISTS (más eficiente en algunos casos)
    @Query(value = "SELECT r.* FROM roles r " +
            "WHERE EXISTS (" +
            "    SELECT 1 FROM usurol ur " +
            "    WHERE ur.codr = r.codr" +
            ")",
            nativeQuery = true)
    List<RolEntity> findRolesWithAssignments();
}
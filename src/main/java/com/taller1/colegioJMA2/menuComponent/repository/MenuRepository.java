package com.taller1.colegioJMA2.menuComponent.repository;

import com.taller1.colegioJMA2.menuComponent.dto.MenuDto;
import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    Optional<MenuEntity> findByNombre(String nombre);

    // Buscar menus por nombre (case insensitive y contiene)
    List<MenuDto> findByNombreContainingIgnoreCase(String nombre);

    // Opcional: Buscar menus habilitados por nombre
    List<MenuDto> findByNombreContainingIgnoreCaseAndEstado(String nombre, String estado);

    // Opcional: Buscar todos los menus ordenados por nombre
    List<MenuDto> findAllByOrderByNombreAsc();

    List<MenuDto> findByEstado(String estado);
    // 1. Obtener menús asignados a un rol específico
    @Query(value = "SELECT m.* FROM menus m " +
            "INNER JOIN rolme rm ON m.codm = rm.codm " +
            "WHERE rm.codr = :codr",
            nativeQuery = true)
    List<MenuEntity> findMenusAssignedToRole(@Param("codr") Integer codr);

    // 2. Obtener menús que NO están asignados a ningún rol
    @Query(value = "SELECT m.* FROM menus m " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 FROM rolme rm " +
            "    WHERE rm.codm = m.codm" +
            ")",
            nativeQuery = true)
    List<MenuEntity> findMenusNotAssignedToAnyRole();

    // 3. Obtener TODOS los menús que están asignados a al menos un rol
    @Query(value = "SELECT DISTINCT m.* FROM menus m " +
            "INNER JOIN rolme rm ON m.codm = rm.codm",
            nativeQuery = true)
    List<MenuEntity> findAllMenusAssignedToAnyRole();
}
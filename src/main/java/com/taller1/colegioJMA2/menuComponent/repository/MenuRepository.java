package com.taller1.colegioJMA2.menuComponent.repository;

import com.taller1.colegioJMA2.menuComponent.dto.MenuDto;
import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
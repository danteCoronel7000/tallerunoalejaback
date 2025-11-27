package com.taller1.colegioJMA2.paraleloComponent.repositorys;

import com.taller1.colegioJMA2.paraleloComponent.dto.ParaleloDto;
import com.taller1.colegioJMA2.paraleloComponent.entitys.ParaleloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParaleloRepository extends JpaRepository<ParaleloEntity, Integer> {
    Optional<ParaleloEntity> findByNombre(String nombre);

    // Buscar paralelos por nombre (case insensitive y contiene)
    List<ParaleloDto> findByNombreContainingIgnoreCase(String nombre);

    // Opcional: Buscar paralelos habilitados por nombre
    List<ParaleloDto> findByNombreContainingIgnoreCaseAndEstado(String nombre, String estado);

    // Opcional: Buscar todos los paralelos ordenados por nombre
    List<ParaleloDto> findAllByOrderByNombreAsc();

    List<ParaleloDto> findByEstado(String estado);
}

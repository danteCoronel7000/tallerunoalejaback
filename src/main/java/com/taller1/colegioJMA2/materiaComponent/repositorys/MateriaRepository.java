package com.taller1.colegioJMA2.materiaComponent.repositorys;

import com.taller1.colegioJMA2.materiaComponent.dto.MateriaDto;
import com.taller1.colegioJMA2.materiaComponent.entitys.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MateriaRepository extends JpaRepository<MateriaEntity, Integer> {
    Optional<MateriaEntity> findByNombre(String nombre);

    // Buscar materias por nombre (case insensitive y contiene)
    List<MateriaDto> findByNombreContainingIgnoreCase(String nombre);

    // Opcional: Buscar materias habilitadas por nombre
    List<MateriaDto> findByNombreContainingIgnoreCaseAndEstado(String nombre, String estado);

    // Opcional: Buscar todas las materias ordenadas por nombre
    List<MateriaDto> findAllByOrderByNombreAsc();

    List<MateriaDto> findByEstado(String estado);
}

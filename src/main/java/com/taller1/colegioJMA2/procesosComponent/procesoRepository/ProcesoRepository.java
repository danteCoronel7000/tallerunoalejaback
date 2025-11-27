package com.taller1.colegioJMA2.procesosComponent.procesoRepository;


import com.taller1.colegioJMA2.procesosComponent.dto.ProcesoDto;
import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProcesoRepository extends JpaRepository<ProcesoEntity, Integer> {

    List<ProcesoDto> findByNombreContainingIgnoreCase(String nombre);

    List<ProcesoDto> findByNombreContainingIgnoreCaseAndEstado(String nombre, String estado);

    List<ProcesoDto> findAllByOrderByNombreAsc();

    List<ProcesoDto> findByEstado(String estado);
    // ================================
    // 1. Procesos asignados a un menú
    // ================================
    @Query(value = "SELECT p.* FROM procesos p " +
            "INNER JOIN mepro pm ON p.codp = pm.codp " +
            "WHERE pm.codm = :codm",
            nativeQuery = true)
    List<ProcesoEntity> findProcesosAssignedToMenu(@Param("codm") Integer codm);


    // =============================================
    // 2. Procesos que NO están asignados a ningún menú
    // =============================================
    @Query(value = "SELECT p.* FROM procesos p " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 FROM mepro pm " +
            "    WHERE pm.codp = p.codp" +
            ")",
            nativeQuery = true)
    List<ProcesoEntity> findProcesosNotAssignedToAnyMenu();


    // ===========================================
    // 3. TODOS los procesos asignados a algún menú
    // ===========================================
    @Query(value = "SELECT DISTINCT p.* FROM procesos p " +
            "INNER JOIN mepro pm ON p.codp = pm.codp",
            nativeQuery = true)
    List<ProcesoEntity> findAllProcesosAssignedToAnyMenu();
}
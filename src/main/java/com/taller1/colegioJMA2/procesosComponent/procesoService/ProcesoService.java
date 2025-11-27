package com.taller1.colegioJMA2.procesosComponent.procesoService;


import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import com.taller1.colegioJMA2.procesosComponent.dto.AsignarProcesosMenuDTO;
import com.taller1.colegioJMA2.procesosComponent.dto.ProcesoDto;
import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProcesoService {
    List<ProcesoEntity> listar();
    ProcesoEntity buscarPorId(Integer id);
    ProcesoEntity guardar(ProcesoEntity proceso);
    ProcesoEntity actualizar(Integer id, ProcesoEntity proceso);
    void eliminar(Integer id);
    List<ProcesoDto> buscarProcesosPorNombre(String nombre);
    List<ProcesoDto> getProcesosForMenu(Integer codm);
    List<ProcesoDto> getUnassignedProcesos();
    List<ProcesoDto> getProcesosAssignedToAnyMenu();
    MenuEntity asignarProcesos(AsignarProcesosMenuDTO dto);
    MenuEntity desasignarProcesos(AsignarProcesosMenuDTO dto);
    Page<ProcesoDto> getProcesosPaginados(Pageable pageable);
}
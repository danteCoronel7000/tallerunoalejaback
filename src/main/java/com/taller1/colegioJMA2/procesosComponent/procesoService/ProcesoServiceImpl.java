package com.taller1.colegioJMA2.procesosComponent.procesoService;


import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import com.taller1.colegioJMA2.menuComponent.repository.MenuRepository;
import com.taller1.colegioJMA2.procesosComponent.dto.AsignarProcesosMenuDTO;
import com.taller1.colegioJMA2.procesosComponent.dto.ProcesoDto;
import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
import com.taller1.colegioJMA2.procesosComponent.procesoRepository.ProcesoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcesoServiceImpl implements ProcesoService {

    private final ProcesoRepository procesoRepository;
    private final MenuRepository menuRepository;

    @Override
    public List<ProcesoEntity> listar() {
        return procesoRepository.findAll();
    }

    @Override
    public ProcesoEntity buscarPorId(Integer id) {
        return procesoRepository.findById(id)
                .orElse(null);
    }

    @Override
    public ProcesoEntity guardar(ProcesoEntity proceso) {
        return procesoRepository.save(proceso);
    }

    @Override
    public ProcesoEntity actualizar(Integer id, ProcesoEntity proceso) {
        ProcesoEntity actual = buscarPorId(id);
        if (actual == null) return null;

        actual.setNombre(proceso.getNombre());
        actual.setEnlace(proceso.getEnlace());
        actual.setAyuda(proceso.getAyuda());
        actual.setEstado(proceso.getEstado());
        actual.setMenus(proceso.getMenus());

        return procesoRepository.save(actual);
    }

    @Override
    public void eliminar(Integer id) {
        procesoRepository.deleteById(id);
    }

    @Override
    public Page<ProcesoDto> getProcesosPaginados(Pageable pageable) {
        Page<ProcesoEntity> pageEntities = procesoRepository.findAll(pageable);

        return pageEntities.map(entity -> {
            ProcesoDto dto = new ProcesoDto(entity);
            return dto;
        });
    }

    // Nuevo método para buscar procesos por nombre
    public List<ProcesoDto> buscarProcesosPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return procesoRepository.findAllByOrderByNombreAsc();
        }
        return procesoRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    // Procesos de un menú específico
    @Override
    public List<ProcesoDto> getProcesosForMenu(Integer codm) {
        return procesoRepository.findProcesosAssignedToMenu(codm)
                .stream()
                .map(ProcesoDto::new)
                .collect(Collectors.toList());
    }

    // Procesos que NO están asignados a ningún menú
    @Override
    public List<ProcesoDto> getUnassignedProcesos() {
        return procesoRepository.findProcesosNotAssignedToAnyMenu()
                .stream()
                .map(ProcesoDto::new)
                .collect(Collectors.toList());
    }

    // Procesos asignados a cualquier menú
    @Override
    public List<ProcesoDto> getProcesosAssignedToAnyMenu() {
        return procesoRepository.findAllProcesosAssignedToAnyMenu()
                .stream()
                .map(ProcesoDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MenuEntity asignarProcesos(AsignarProcesosMenuDTO dto) {
        System.out.println("dto: " + dto);

        // Buscar menú
        MenuEntity menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new RuntimeException("Menú no encontrado"));

        // Obtener procesos actuales del menú
        Set<ProcesoEntity> procesosActuales = new HashSet<>(menu.getProcesos());

        // Buscar procesos enviados en el DTO
        List<ProcesoEntity> procesosNuevos = procesoRepository.findAllById(dto.getProcesosIds());

        if (procesosNuevos.isEmpty()) {
            throw new RuntimeException("Ningún proceso válido encontrado");
        }

        // Agregar solo procesos que no estén ya asignados
        for (ProcesoEntity proceso : procesosNuevos) {
            if (!procesosActuales.contains(proceso)) {
                procesosActuales.add(proceso);
            }
        }

        // Actualizar lista final
        menu.setProcesos(new HashSet<>(procesosActuales));

        // Guardar
        return menuRepository.save(menu);
    }

    @Override
    @Transactional
    public MenuEntity desasignarProcesos(AsignarProcesosMenuDTO dto) {

        // Buscar menú
        MenuEntity menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new RuntimeException("Menú no encontrado"));

        // Procesos actuales del menú
        Set<ProcesoEntity> procesosActuales = new HashSet<>(menu.getProcesos());

        // Procesos a eliminar
        List<ProcesoEntity> procesosAEliminar = procesoRepository.findAllById(dto.getProcesosIds());

        if (procesosAEliminar.isEmpty()) {
            throw new RuntimeException("Ningún proceso válido para desasignar");
        }

        // Remover procesos
        for (ProcesoEntity proceso : procesosAEliminar) {
            procesosActuales.remove(proceso);
        }

        // Actualizar lista final
        menu.setProcesos(new HashSet<>(procesosActuales));

        // Guardar
        return menuRepository.save(menu);
    }

}
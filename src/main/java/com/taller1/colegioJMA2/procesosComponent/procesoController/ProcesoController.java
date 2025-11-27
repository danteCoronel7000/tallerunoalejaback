package com.taller1.colegioJMA2.procesosComponent.procesoController;


import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import com.taller1.colegioJMA2.procesosComponent.dto.AsignarProcesosMenuDTO;
import com.taller1.colegioJMA2.procesosComponent.dto.ProcesoDto;
import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
import com.taller1.colegioJMA2.procesosComponent.procesoService.ProcesoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procesos")
@RequiredArgsConstructor
public class ProcesoController {

    private final ProcesoService procesoService;

    @GetMapping
    public List<ProcesoEntity> listar() {
        return procesoService.listar();
    }

    @GetMapping("/{id}")
    public ProcesoEntity obtener(@PathVariable Integer id) {
        return procesoService.buscarPorId(id);
    }

    @PostMapping
    public ProcesoEntity crear(@RequestBody ProcesoEntity proceso) {
        return procesoService.guardar(proceso);
    }

    @PutMapping("/{id}")
    public ProcesoEntity actualizar(@PathVariable Integer id, @RequestBody ProcesoEntity proceso) {
        return procesoService.actualizar(id, proceso);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        procesoService.eliminar(id);
    }

    @GetMapping("/get/paginado/procesos")
    public ResponseEntity<Page<ProcesoDto>> getAllProcesosPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                sortDir.equalsIgnoreCase("desc") ?
                        Sort.by(sortBy).descending() :
                        Sort.by(sortBy).ascending()
        );

        Page<ProcesoDto> procesos = procesoService.getProcesosPaginados(pageable);
        return new ResponseEntity<>(procesos, HttpStatus.OK);
    }

    // Nuevo endpoint para buscar procesos
    @GetMapping("/buscar")
    public ResponseEntity<List<ProcesoDto>> buscarProcesos(
            @RequestParam(required = false) String nombre) {
        List<ProcesoDto> procesos = procesoService.buscarProcesosPorNombre(nombre);
        return ResponseEntity.ok(procesos);
    }

    // Obtener procesos de un menú específico
    @GetMapping("/menu/{codm}")
    public List<ProcesoDto> getMenuProcesos(@PathVariable Integer codm) {
        return procesoService.getProcesosForMenu(codm);
    }

    // Obtener procesos que NO están asignados a ningún menú
    @GetMapping("/sin/asignar")
    public List<ProcesoDto> getUnassignedProcesos() {
        return procesoService.getUnassignedProcesos();
    }

    // Obtener todos los procesos que están asignados a cualquier menú
    @GetMapping("/asignados")
    public List<ProcesoDto> getProcesosAssignedToAnyMenu() {
        return procesoService.getProcesosAssignedToAnyMenu();
    }

    @PostMapping("/asignar/procesos")
    public ResponseEntity<?> asignarProcesos(@RequestBody AsignarProcesosMenuDTO dto) {
        MenuEntity actualizado = procesoService.asignarProcesos(dto);
        return ResponseEntity.ok(actualizado);
    }

    @PostMapping("/desasignar/procesos")
    public ResponseEntity<?> desasignarProcesos(@RequestBody AsignarProcesosMenuDTO dto) {
        MenuEntity actualizado = procesoService.desasignarProcesos(dto);
        return ResponseEntity.ok(actualizado);
    }

}
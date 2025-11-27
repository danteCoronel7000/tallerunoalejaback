package com.taller1.colegioJMA2.materiaComponent.controllers;

import com.taller1.colegioJMA2.materiaComponent.dto.CreateMateriaRequest;
import com.taller1.colegioJMA2.materiaComponent.dto.CreateMateriaResponse;
import com.taller1.colegioJMA2.materiaComponent.dto.MateriaDto;
import com.taller1.colegioJMA2.materiaComponent.dto.UpdateMateriaRequest;
import com.taller1.colegioJMA2.materiaComponent.entitys.MateriaEntity;
import com.taller1.colegioJMA2.materiaComponent.services.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {
    @Autowired
    private MateriaService materiaService;

    @PostMapping("/create")
    public CreateMateriaResponse createMateria(@RequestBody CreateMateriaRequest request,
                                               @RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("Header recibido: " + authHeader);
        return materiaService.createMateria(request);
    }

    @GetMapping("/get/paginado/materias")
    public ResponseEntity<Page<MateriaDto>> getAllMateriasPaginado(
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

        Page<MateriaDto> materias = materiaService.getMateriasPaginados(pageable);
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMateria(
            @PathVariable Integer id,
            @RequestBody UpdateMateriaRequest updateMateriaRequest) {
        try {
            MateriaEntity materiaActualizado = materiaService.updateMateria(id, updateMateriaRequest);
            return ResponseEntity.ok(materiaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al actualizar la materia"));
        }
    }

    @PutMapping("/{codmat}/habilitar")
    public ResponseEntity<String> habilitarMateria(@PathVariable Integer codmat) {
        materiaService.habilitarMateria(codmat);
        return ResponseEntity.ok("Éxito: Materia habilitada correctamente");
    }

    @PutMapping("/{copmat}/deshabilitar")
    public ResponseEntity<String> deshabilitarMateria(@PathVariable Integer copmat) {
        materiaService.deshabilitarMateria(copmat);
        return ResponseEntity.ok("Éxito: Materia deshabilitada correctamente");
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MateriaDto>> getMateriasPorEstado(@PathVariable String estado) {
        List<MateriaDto> materias = materiaService.findByEstado(estado);
        return ResponseEntity.ok(materias);
    }

    // Nuevo endpoint para buscar materias
    @GetMapping("/buscar")
    public ResponseEntity<List<MateriaDto>> buscarMaterias(
            @RequestParam(required = false) String nombre) {
        List<MateriaDto> materias = materiaService.buscarMateriasPorNombre(nombre);
        return ResponseEntity.ok(materias);
    }
}
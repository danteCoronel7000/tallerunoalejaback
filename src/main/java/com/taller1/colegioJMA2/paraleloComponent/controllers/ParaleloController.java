package com.taller1.colegioJMA2.paraleloComponent.controllers;

import com.taller1.colegioJMA2.paraleloComponent.dto.CreateParaleloRequest;
import com.taller1.colegioJMA2.paraleloComponent.dto.CreateParaleloResponse;
import com.taller1.colegioJMA2.paraleloComponent.dto.ParaleloDto;
import com.taller1.colegioJMA2.paraleloComponent.dto.UpdateParaleloRequest;
import com.taller1.colegioJMA2.paraleloComponent.entitys.ParaleloEntity;
import com.taller1.colegioJMA2.paraleloComponent.services.ParaleloService;
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
@RequestMapping("/api/paralelos")
@RequiredArgsConstructor
public class ParaleloController {
    @Autowired
    private ParaleloService paraleloService;

    @PostMapping("/create")
    public CreateParaleloResponse createParalelo(@RequestBody CreateParaleloRequest request,
                                                 @RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("Header recibido: " + authHeader);
        return paraleloService.createParalelo(request);
    }

    @GetMapping("/get/paginado/paralelos")
    public ResponseEntity<Page<ParaleloDto>> getAllParalelosPaginado(
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

        Page<ParaleloDto> paralelos = paraleloService.getParalelosPaginados(pageable);
        return new ResponseEntity<>(paralelos, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateParalelo(
            @PathVariable Integer id,
            @RequestBody UpdateParaleloRequest updateParaleloRequest) {
        try {
            ParaleloEntity paraleloActualizado = paraleloService.updateParalelo(id, updateParaleloRequest);
            return ResponseEntity.ok(paraleloActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al actualizar el paralelo"));
        }
    }

    @PutMapping("/{codpar}/habilitar")
    public ResponseEntity<String> habilitarParalelo(@PathVariable Integer codpar) {
        paraleloService.habilitarParalelo(codpar);
        return ResponseEntity.ok("Éxito: Paralelo habilitado correctamente");
    }

    @PutMapping("/{coppar}/deshabilitar")
    public ResponseEntity<String> deshabilitarParalelo(@PathVariable Integer coppar) {
        paraleloService.deshabilitarParalelo(coppar);
        return ResponseEntity.ok("Éxito: Paralelo deshabilitado correctamente");
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ParaleloDto>> getParalelosPorEstado(@PathVariable String estado) {
        List<ParaleloDto> paralelos = paraleloService.findByEstado(estado);
        return ResponseEntity.ok(paralelos);
    }

    // Nuevo endpoint para buscar paralelos
    @GetMapping("/buscar")
    public ResponseEntity<List<ParaleloDto>> buscarParalelos(
            @RequestParam(required = false) String nombre) {
        List<ParaleloDto> paralelos = paraleloService.buscarParalelosPorNombre(nombre);
        return ResponseEntity.ok(paralelos);
    }
}

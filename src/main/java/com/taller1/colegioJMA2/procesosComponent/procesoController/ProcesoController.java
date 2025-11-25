package com.taller1.colegioJMA2.procesosComponent.procesoController;


import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
import com.taller1.colegioJMA2.procesosComponent.procesoService.ProcesoService;
import lombok.RequiredArgsConstructor;
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
}
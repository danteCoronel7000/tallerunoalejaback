package com.taller1.colegioJMA2.rolesComponent.controllers;


import com.taller1.colegioJMA2.rolesComponent.dto.CreateRoleRequest;
import com.taller1.colegioJMA2.rolesComponent.dto.CreateRoleResponse;
import com.taller1.colegioJMA2.rolesComponent.dto.RolDto;
import com.taller1.colegioJMA2.rolesComponent.dto.UpdateRolRequest;
import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import com.taller1.colegioJMA2.rolesComponent.services.RolService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public List<RolEntity> listar() {
        return rolService.listar();
    }

    @GetMapping("/{id}")
    public RolEntity obtener(@PathVariable Integer id) {
        return rolService.buscarPorId(id);
    }

    @PostMapping
    public RolEntity crear(@RequestBody RolEntity rol) {
        return rolService.guardar(rol);
    }

    @PutMapping("/{id}")
    public RolEntity actualizar(@PathVariable Integer id, @RequestBody RolEntity rol) {
        return rolService.actualizar(id, rol);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        rolService.eliminar(id);
    }


    @PostMapping("/create")
    public CreateRoleResponse createRole(@RequestBody CreateRoleRequest request,
                                         @RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("Header recibido: " + authHeader);
        return rolService.createRole(request);
    }

    @GetMapping("/get/paginado/roles")
    public ResponseEntity<Page<RolDto>> getAllRolesPaginado(
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

        Page<RolDto> roles = rolService.getRolesPaginados(pageable);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRol(
            @PathVariable Integer id,
            @RequestBody UpdateRolRequest updateRolRequest) {
        try {
            RolEntity rolActualizado = rolService.updateRol(id, updateRolRequest);
            return ResponseEntity.ok(rolActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al actualizar el rol"));
        }
    }

    @PutMapping("/{codr}/habilitar")
    public ResponseEntity<String> habilitarRol(@PathVariable Integer codr) {
        rolService.habilitarRol(codr);
        return ResponseEntity.ok("Éxito: Rol habilitado correctamente");
    }

    @PutMapping("/{codr}/deshabilitar")
    public ResponseEntity<String> deshabilitarRol(@PathVariable Integer codr) {
        rolService.deshabilitarRol(codr);
        return ResponseEntity.ok("Éxito: Rol deshabilitado correctamente");
    }

    // Nuevo endpoint para buscar roles
    @GetMapping("/buscar")
    public ResponseEntity<List<RolDto>> buscarRoles(
            @RequestParam(required = false) String nombre) {
        List<RolDto> roles = rolService.buscarRolesPorNombre(nombre);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RolDto>> getRolesPorEstado(@PathVariable String estado) {
        List<RolDto> roles = rolService.findByEstado(estado);
        return ResponseEntity.ok(roles);
    }

}




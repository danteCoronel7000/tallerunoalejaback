package com.taller1.colegioJMA2.menuComponent.controllers;


import com.taller1.colegioJMA2.menuComponent.dto.CreateMenuRequest;
import com.taller1.colegioJMA2.menuComponent.dto.CreateMenuResponse;
import com.taller1.colegioJMA2.menuComponent.dto.MenuDto;
import com.taller1.colegioJMA2.menuComponent.dto.UpdateMenuRequest;
import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import com.taller1.colegioJMA2.menuComponent.services.MenuService;
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
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public List<MenuEntity> listar() {
        return menuService.listar();
    }

    @GetMapping("/{id}")
    public MenuEntity obtener(@PathVariable Integer id) {
        return menuService.buscarPorId(id);
    }

    @PostMapping
    public MenuEntity crear(@RequestBody MenuEntity menu) {
        return menuService.guardar(menu);
    }

    @PutMapping("/{id}")
    public MenuEntity actualizar(@PathVariable Integer id, @RequestBody MenuEntity menu) {
        return menuService.actualizar(id, menu);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        menuService.eliminar(id);
    }

    @PostMapping("/create")
    public CreateMenuResponse createMenu(@RequestBody CreateMenuRequest request,
                                         @RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("Header recibido: " + authHeader);
        return menuService.createMenu(request);
    }

    @GetMapping("/get/paginado/menus")
    public ResponseEntity<Page<MenuDto>> getAllMenusPaginado(
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

        Page<MenuDto> menus = menuService.getMenusPaginados(pageable);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMenu(
            @PathVariable Integer id,
            @RequestBody UpdateMenuRequest updateMenuRequest) {
        try {
            MenuEntity menuActualizado = menuService.updateMenu(id, updateMenuRequest);
            return ResponseEntity.ok(menuActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al actualizar el menu"));
        }
    }

    @PutMapping("/{codm}/habilitar")
    public ResponseEntity<String> habilitarMenu(@PathVariable Integer codm) {
        menuService.habilitarMenu(codm);
        return ResponseEntity.ok("Éxito: Menu habilitado correctamente");
    }

    @PutMapping("/{codm}/deshabilitar")
    public ResponseEntity<String> deshabilitarMenu(@PathVariable Integer codm) {
        menuService.deshabilitarMenu(codm);
        return ResponseEntity.ok("Éxito: Menu deshabilitado correctamente");
    }

    // Nuevo endpoint para buscar menus
    @GetMapping("/buscar")
    public ResponseEntity<List<MenuDto>> buscarMenus(
            @RequestParam(required = false) String nombre) {
        List<MenuDto> menus = menuService.buscarMenusPorNombre(nombre);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MenuDto>> getMenusPorEstado(@PathVariable String estado) {
        List<MenuDto> menus = menuService.findByEstado(estado);
        return ResponseEntity.ok(menus);
    }
}
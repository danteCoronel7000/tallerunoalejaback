package com.taller1.colegioJMA2.menuComponent.services;


import com.taller1.colegioJMA2.menuComponent.dto.*;
import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import com.taller1.colegioJMA2.menuComponent.repository.MenuRepository;
import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import com.taller1.colegioJMA2.rolesComponent.respositorys.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;@Autowired
    private RolRepository rolRepository;


    @Override
    public List<MenuEntity> listar() {
        return menuRepository.findAll();
    }

    @Override
    public MenuEntity buscarPorId(Integer id) {
        return menuRepository.findById(id)
                .orElse(null);
    }

    @Override
    public MenuEntity guardar(MenuEntity menu) {
        return menuRepository.save(menu);
    }

    @Override
    public MenuEntity actualizar(Integer id, MenuEntity menu) {
        MenuEntity actual = buscarPorId(id);
        if (actual == null) return null;

        actual.setNombre(menu.getNombre());
        actual.setEstado(menu.getEstado());

        return menuRepository.save(actual);
    }

    @Override
    public void eliminar(Integer id) {
        menuRepository.deleteById(id);
    }

    @Override
    public CreateMenuResponse createMenu(CreateMenuRequest request) {

        MenuEntity menu = new MenuEntity();
        menu.setNombre(request.getName());
        menu.setEstado("1");

        MenuEntity saved = menuRepository.save(menu);

        return new CreateMenuResponse(
                saved.getCodm(),
                saved.getNombre(),
                "Menu creado exitosamente"
        );
    }

    @Override
    public Page<MenuDto> getMenusPaginados(Pageable pageable) {
        Page<MenuEntity> pageEntities = menuRepository.findAll(pageable);

        return pageEntities.map(entity -> {
            MenuDto dto = new MenuDto(entity);
            return dto;
        });
    }

    @Override
    public MenuEntity updateMenu(Integer id, UpdateMenuRequest updateMenuRequest) {
        // Buscar el menu existente
        MenuEntity menuExistente = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu no encontrado con ID: " + id));

        // Validar que el nuevo nombre no exista en otros menus (excepto el actual)
        if (updateMenuRequest.getNombre() != null &&
                !updateMenuRequest.getNombre().equals(menuExistente.getNombre())) {

            Optional<MenuEntity> menuConMismoNombre = menuRepository.findByNombre(updateMenuRequest.getNombre());
            if (menuConMismoNombre.isPresent() && !menuConMismoNombre.get().getCodm().equals(id)) {
                throw new RuntimeException("Ya existe un menu con el nombre: " + updateMenuRequest.getNombre());
            }
        }

        // Actualizar campos
        if (updateMenuRequest.getNombre() != null) {
            menuExistente.setNombre(updateMenuRequest.getNombre());
        }

        if (updateMenuRequest.getEstado() != null) {
            menuExistente.setEstado(updateMenuRequest.getEstado());
        }

        // Guardar y retornar
        return menuRepository.save(menuExistente);
    }

    @Override
    public MenuEntity findById(Integer id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu no encontrado con ID: " + id));
    }

    public void habilitarMenu(Integer codm) {
        MenuEntity menu = menuRepository.findById(codm)
                .orElseThrow(() -> new RuntimeException("Menu no encontrado con código: " + codm));
        menu.setEstado("1");
        menuRepository.save(menu);
    }

    public void deshabilitarMenu(Integer codm) {
        MenuEntity menu = menuRepository.findById(codm)
                .orElseThrow(() -> new RuntimeException("Menu no encontrado con código: " + codm));
        menu.setEstado("0");
        menuRepository.save(menu);
    }

    @Override
    public List<MenuDto> findByEstado(String estado) {
        return menuRepository.findByEstado(estado);
    }


    // Nuevo metodo para buscar menus por nombre
    public List<MenuDto> buscarMenusPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return menuRepository.findAllByOrderByNombreAsc();
        }
        return menuRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }


    // Menús de un rol específico
    @Override
    public List<MenuDto> getMenusForRole(Integer codr) {
        return menuRepository.findMenusAssignedToRole(codr)
                .stream()
                .map(MenuDto::new)
                .collect(Collectors.toList());
    }

    // Menús que NO están asignados a ningún rol
    @Override
    public List<MenuDto> getUnassignedMenus() {
        return menuRepository.findMenusNotAssignedToAnyRole()
                .stream()
                .map(MenuDto::new)
                .collect(Collectors.toList());
    }

    // Menús que están asignados a cualquier rol (al menos uno)
    @Override
    public List<MenuDto> getMenusAssignedToAnyRole() {
        return menuRepository.findAllMenusAssignedToAnyRole()
                .stream()
                .map(MenuDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RolEntity asignarMenus(AsignarMenusRolDTO dto) {
        System.out.println("dto: "+dto);
        // Buscar rol
        RolEntity rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Obtener menús actuales del rol (Set para evitar duplicados)
        Set<MenuEntity> menusActuales = new HashSet<>(rol.getMenus());

        // Buscar menús enviados en el DTO
        List<MenuEntity> menusNuevosSolicitados = menuRepository.findAllById(dto.getMenusIds());

        if (menusNuevosSolicitados.isEmpty()) {
            throw new RuntimeException("Ningún menú válido encontrado");
        }

        // Agregar solo menús que NO tenga ya el rol
        for (MenuEntity menu : menusNuevosSolicitados) {
            if (!menusActuales.contains(menu)) {
                menusActuales.add(menu);
            }
        }

        // Actualizar menús final
        rol.setMenus(new HashSet<>(menusActuales));


        // Guardar
        return rolRepository.save(rol);
    }

    @Override
    @Transactional
    public RolEntity desasignarMenus(AsignarMenusRolDTO dto) {

        // Buscar rol
        RolEntity rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Obtener menús actuales del rol
        Set<MenuEntity> menusActuales = new HashSet<>(rol.getMenus());

        // Buscar menús a eliminar en el DTO
        List<MenuEntity> menusAEliminar = menuRepository.findAllById(dto.getMenusIds());

        if (menusAEliminar.isEmpty()) {
            throw new RuntimeException("Ningún menú válido encontrado para desasignar");
        }

        // Eliminar los menús especificados
        for (MenuEntity menu : menusAEliminar) {
            menusActuales.remove(menu);
        }

        // Actualizar menús final (puede quedar sin menús asignados)
        rol.setMenus(new HashSet<>(menusActuales));

        // Guardar
        return rolRepository.save(rol);
    }
}
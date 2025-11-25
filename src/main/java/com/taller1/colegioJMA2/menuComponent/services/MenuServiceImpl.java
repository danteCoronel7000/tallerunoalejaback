package com.taller1.colegioJMA2.menuComponent.services;


import com.taller1.colegioJMA2.menuComponent.dto.CreateMenuRequest;
import com.taller1.colegioJMA2.menuComponent.dto.CreateMenuResponse;
import com.taller1.colegioJMA2.menuComponent.dto.MenuDto;
import com.taller1.colegioJMA2.menuComponent.dto.UpdateMenuRequest;
import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import com.taller1.colegioJMA2.menuComponent.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

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

    // Nuevo método para buscar menus por nombre
    public List<MenuDto> buscarMenusPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return menuRepository.findAllByOrderByNombreAsc();
        }
        return menuRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    @Override
    public List<MenuDto> findByEstado(String estado) {
        return menuRepository.findByEstado(estado);
    }


}
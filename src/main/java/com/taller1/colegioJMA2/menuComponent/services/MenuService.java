package com.taller1.colegioJMA2.menuComponent.services;

import com.taller1.colegioJMA2.menuComponent.dto.CreateMenuRequest;
import com.taller1.colegioJMA2.menuComponent.dto.CreateMenuResponse;
import com.taller1.colegioJMA2.menuComponent.dto.MenuDto;
import com.taller1.colegioJMA2.menuComponent.dto.UpdateMenuRequest;
import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {
    List<MenuEntity> listar();
    MenuEntity buscarPorId(Integer id);
    MenuEntity guardar(MenuEntity menu);
    MenuEntity actualizar(Integer id, MenuEntity menu);
    void eliminar(Integer id);
    CreateMenuResponse createMenu(CreateMenuRequest request);
    Page<MenuDto> getMenusPaginados(Pageable pageable);
    // Si no tienes este método, agrégalo para buscar por ID
    MenuEntity findById(Integer id);
    // Método para actualizar menu
    MenuEntity updateMenu(Integer id, UpdateMenuRequest updateMenuRequest);
    void habilitarMenu(Integer codm);
    void deshabilitarMenu(Integer codm);
    List<MenuDto> buscarMenusPorNombre(String nombre);
    List<MenuDto> findByEstado(String estado);
}

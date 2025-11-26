package com.taller1.colegioJMA2.service;

import com.taller1.colegioJMA2.dto.UsuarioPageDto;
import com.taller1.colegioJMA2.menuComponent.dto.MenuDto;
import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.taller1.colegioJMA2.model.UsuariosModel;
import com.taller1.colegioJMA2.repository.UsuariosRepo;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements usuarioService {

	@Autowired
	private UsuariosRepo usuariosrepo;
    // En tu servicio
@Override
    public Page<UsuariosModel> getUsuarioPaginados(Pageable pageable) {
        Page<UsuariosModel> pageEntities = usuariosrepo.findAll(pageable);
        return pageEntities;

    }

    @Override
    public Page<UsuarioPageDto> getUsuarioPaginadosDto(Pageable pageable) {
        Page<UsuariosModel> pageEntities = usuariosrepo.findAll(pageable);
        return pageEntities.map(entity -> {
            UsuarioPageDto dto = new UsuarioPageDto(entity);
            return dto;
        });
    }
    @Override
    public List<UsuarioPageDto> buscarUsuariosPorNombre(String nombre) {
    System.out.println(nombre);
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>(); // ❗ No devolver nada si no hay criterio
        }

        List<UsuariosModel> usuarios = usuariosrepo.buscarPorNombre(nombre.trim());

        return usuarios.stream()
                .map(UsuarioPageDto::new)
                .toList();
    }


}

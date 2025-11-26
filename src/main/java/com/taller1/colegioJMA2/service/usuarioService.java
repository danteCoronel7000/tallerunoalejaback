package com.taller1.colegioJMA2.service;

import com.taller1.colegioJMA2.dto.UsuarioPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taller1.colegioJMA2.model.UsuariosModel;

import java.util.List;

public interface usuarioService {
    Page<UsuariosModel> getUsuarioPaginados(Pageable pageable);
    Page<UsuarioPageDto> getUsuarioPaginadosDto(Pageable pageable);
    // En la interfaz del servicio
    List<UsuarioPageDto> buscarUsuariosPorNombre(String nombre);
}

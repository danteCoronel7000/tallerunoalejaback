package com.taller1.colegioJMA2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taller1.colegioJMA2.model.UsuariosModel;

public interface usuarioService {
    Page<UsuariosModel> getUsuarioPaginados(Pageable pageable);
}

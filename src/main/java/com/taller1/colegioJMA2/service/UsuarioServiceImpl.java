package com.taller1.colegioJMA2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.taller1.colegioJMA2.model.UsuariosModel;
import com.taller1.colegioJMA2.repository.UsuariosRepo;

import lombok.RequiredArgsConstructor;

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

}

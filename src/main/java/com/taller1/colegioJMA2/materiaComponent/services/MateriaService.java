package com.taller1.colegioJMA2.materiaComponent.services;

import com.taller1.colegioJMA2.materiaComponent.dto.CreateMateriaRequest;
import com.taller1.colegioJMA2.materiaComponent.dto.CreateMateriaResponse;
import com.taller1.colegioJMA2.materiaComponent.dto.MateriaDto;
import com.taller1.colegioJMA2.materiaComponent.dto.UpdateMateriaRequest;
import com.taller1.colegioJMA2.materiaComponent.entitys.MateriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MateriaService {
    CreateMateriaResponse createMateria(CreateMateriaRequest request);
    Page<MateriaDto> getMateriasPaginados(Pageable pageable);
    // Si no tienes este método, agrégalo para buscar por ID
    MateriaEntity findById(Integer id);
    // Método para actualizar materia
    MateriaEntity updateMateria(Integer id, UpdateMateriaRequest updateMateriaRequest);
    void habilitarMateria(Integer copmat);
    void deshabilitarMateria(Integer copmat);
    List<MateriaDto> buscarMateriasPorNombre(String nombre);
    List<MateriaDto> findByEstado(String estado);
    List<MateriaEntity> listar();}

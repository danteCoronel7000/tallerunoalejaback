package com.taller1.colegioJMA2.paraleloComponent.services;

import com.taller1.colegioJMA2.paraleloComponent.dto.CreateParaleloRequest;
import com.taller1.colegioJMA2.paraleloComponent.dto.CreateParaleloResponse;
import com.taller1.colegioJMA2.paraleloComponent.dto.ParaleloDto;
import com.taller1.colegioJMA2.paraleloComponent.dto.UpdateParaleloRequest;
import com.taller1.colegioJMA2.paraleloComponent.entitys.ParaleloEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ParaleloService {
    CreateParaleloResponse createParalelo(CreateParaleloRequest request);
    Page<ParaleloDto> getParalelosPaginados(Pageable pageable);
    // Si no tienes este mtodo, agrégalo para buscar por ID
    ParaleloEntity findById(Integer id);
    // Mtodo para actualizar paralelo
    ParaleloEntity updateParalelo(Integer id, UpdateParaleloRequest updateParaleloRequest);
    void habilitarParalelo(Integer coppar);
    void deshabilitarParalelo(Integer coppar);
    List<ParaleloDto> buscarParalelosPorNombre(String nombre);
    List<ParaleloDto> findByEstado(String estado);
}

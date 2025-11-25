package com.taller1.colegioJMA2.procesosComponent.procesoService;


import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;

import java.util.List;

public interface ProcesoService {
    List<ProcesoEntity> listar();
    ProcesoEntity buscarPorId(Integer id);
    ProcesoEntity guardar(ProcesoEntity proceso);
    ProcesoEntity actualizar(Integer id, ProcesoEntity proceso);
    void eliminar(Integer id);
}
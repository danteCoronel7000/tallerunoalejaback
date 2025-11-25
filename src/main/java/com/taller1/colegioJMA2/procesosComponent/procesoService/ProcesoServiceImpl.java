package com.taller1.colegioJMA2.procesosComponent.procesoService;


import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
import com.taller1.colegioJMA2.procesosComponent.procesoRepository.ProcesoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcesoServiceImpl implements ProcesoService {

    private final ProcesoRepository procesoRepository;

    @Override
    public List<ProcesoEntity> listar() {
        return procesoRepository.findAll();
    }

    @Override
    public ProcesoEntity buscarPorId(Integer id) {
        return procesoRepository.findById(id)
                .orElse(null);
    }

    @Override
    public ProcesoEntity guardar(ProcesoEntity proceso) {
        return procesoRepository.save(proceso);
    }

    @Override
    public ProcesoEntity actualizar(Integer id, ProcesoEntity proceso) {
        ProcesoEntity actual = buscarPorId(id);
        if (actual == null) return null;

        actual.setNombre(proceso.getNombre());
        actual.setEnlace(proceso.getEnlace());
        actual.setAyuda(proceso.getAyuda());
        actual.setEstado(proceso.getEstado());
        actual.setMenus(proceso.getMenus());

        return procesoRepository.save(actual);
    }

    @Override
    public void eliminar(Integer id) {
        procesoRepository.deleteById(id);
    }
}
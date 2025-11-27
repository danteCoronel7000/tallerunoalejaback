package com.taller1.colegioJMA2.materiaComponent.services;

import com.taller1.colegioJMA2.materiaComponent.dto.CreateMateriaRequest;
import com.taller1.colegioJMA2.materiaComponent.dto.CreateMateriaResponse;
import com.taller1.colegioJMA2.materiaComponent.dto.MateriaDto;
import com.taller1.colegioJMA2.materiaComponent.dto.UpdateMateriaRequest;
import com.taller1.colegioJMA2.materiaComponent.entitys.MateriaEntity;
import com.taller1.colegioJMA2.materiaComponent.repositorys.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public CreateMateriaResponse createMateria(CreateMateriaRequest request) {

        MateriaEntity materia = new MateriaEntity();
        materia.setNombre(request.getName());
        materia.setEstado("1");

        MateriaEntity saved = materiaRepository.save(materia);

        return new CreateMateriaResponse(
                saved.getCodmat(),
                saved.getNombre(),
                "Materia creada exitosamente"
        );
    }

    @Override
    public Page<MateriaDto> getMateriasPaginados(Pageable pageable) {
        Page<MateriaEntity> pageEntities = materiaRepository.findAll(pageable);

        return pageEntities.map(entity -> {
            MateriaDto dto = new MateriaDto(entity);
            return dto;
        });
    }

    // Nuevo metodo para buscar materias por nombre
    public List<MateriaDto> buscarMateriasPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return materiaRepository.findAllByOrderByNombreAsc();
        }
        return materiaRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    @Override
    public MateriaEntity updateMateria(Integer id, UpdateMateriaRequest updateMateriaRequest) {
        // Buscar la materia existente
        MateriaEntity materiaExistente = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));

        // Validar que el nuevo nombre no exista en otras materias (excepto la actual)
        if (updateMateriaRequest.getNombre() != null &&
                !updateMateriaRequest.getNombre().equals(materiaExistente.getNombre())) {

            Optional<MateriaEntity> materiaConMismoNombre = materiaRepository.findByNombre(updateMateriaRequest.getNombre());
            if (materiaConMismoNombre.isPresent() && !materiaConMismoNombre.get().getCodmat().equals(id)) {
                throw new RuntimeException("Ya existe una materia con el nombre: " + updateMateriaRequest.getNombre());
            }
        }

        // Actualizar campos
        if (updateMateriaRequest.getNombre() != null) {
            materiaExistente.setNombre(updateMateriaRequest.getNombre());
        }

        if (updateMateriaRequest.getEstado() != null) {
            materiaExistente.setEstado(updateMateriaRequest.getEstado());
        }

        // Guardar y retornar
        return materiaRepository.save(materiaExistente);
    }

    @Override
    public MateriaEntity findById(Integer id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));
    }

    public void habilitarMateria(Integer copmat) {
        MateriaEntity materia = materiaRepository.findById(copmat)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con código: " + copmat));
        materia.setEstado("1");
        materiaRepository.save(materia);
    }

    public void deshabilitarMateria(Integer copmat) {
        MateriaEntity materia = materiaRepository.findById(copmat)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con código: " + copmat));
        materia.setEstado("0");
        materiaRepository.save(materia);
    }

    @Override
    public List<MateriaDto> findByEstado(String estado) {
        return materiaRepository.findByEstado(estado);
    }
}

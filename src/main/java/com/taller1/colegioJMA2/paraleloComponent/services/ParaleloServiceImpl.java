package com.taller1.colegioJMA2.paraleloComponent.services;

import com.taller1.colegioJMA2.paraleloComponent.dto.CreateParaleloRequest;
import com.taller1.colegioJMA2.paraleloComponent.dto.CreateParaleloResponse;
import com.taller1.colegioJMA2.paraleloComponent.dto.ParaleloDto;
import com.taller1.colegioJMA2.paraleloComponent.dto.UpdateParaleloRequest;
import com.taller1.colegioJMA2.paraleloComponent.entitys.ParaleloEntity;
import com.taller1.colegioJMA2.paraleloComponent.repositorys.ParaleloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParaleloServiceImpl implements ParaleloService {

    @Autowired
    private ParaleloRepository paraleloRepository;

    @Override
    public CreateParaleloResponse createParalelo(CreateParaleloRequest request) {

        ParaleloEntity paralelo = new ParaleloEntity();
        paralelo.setNombre(request.getName());
        paralelo.setEstado("1");

        ParaleloEntity saved = paraleloRepository.save(paralelo);

        return new CreateParaleloResponse(
                saved.getCodpar(),
                saved.getNombre(),
                "Paralelo creado exitosamente"
        );
    }

    @Override
    public Page<ParaleloDto> getParalelosPaginados(Pageable pageable) {
        Page<ParaleloEntity> pageEntities = paraleloRepository.findAll(pageable);

        return pageEntities.map(entity -> {
            ParaleloDto dto = new ParaleloDto(entity);
            return dto;
        });
    }

    // Nuevo metodo para buscar paralelos por nombre
    public List<ParaleloDto> buscarParalelosPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return paraleloRepository.findAllByOrderByNombreAsc();
        }
        return paraleloRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    @Override
    public ParaleloEntity updateParalelo(Integer id, UpdateParaleloRequest updateParaleloRequest) {
        // Buscar el paralelo existente
        ParaleloEntity paraleloExistente = paraleloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paralelo no encontrado con ID: " + id));

        // Validar que el nuevo nombre no exista en otros paralelos (excepto el actual)
        if (updateParaleloRequest.getNombre() != null &&
                !updateParaleloRequest.getNombre().equals(paraleloExistente.getNombre())) {

            Optional<ParaleloEntity> paraleloConMismoNombre = paraleloRepository.findByNombre(updateParaleloRequest.getNombre());
            if (paraleloConMismoNombre.isPresent() && !paraleloConMismoNombre.get().getCodpar().equals(id)) {
                throw new RuntimeException("Ya existe un paralelo con el nombre: " + updateParaleloRequest.getNombre());
            }
        }

        // Actualizar campos
        if (updateParaleloRequest.getNombre() != null) {
            paraleloExistente.setNombre(updateParaleloRequest.getNombre());
        }

        if (updateParaleloRequest.getEstado() != null) {
            paraleloExistente.setEstado(updateParaleloRequest.getEstado());
        }

        // Guardar y retornar
        return paraleloRepository.save(paraleloExistente);
    }

    @Override
    public ParaleloEntity findById(Integer id) {
        return paraleloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paralelo no encontrado con ID: " + id));
    }

    public void habilitarParalelo(Integer coppar) {
        ParaleloEntity paralelo = paraleloRepository.findById(coppar)
                .orElseThrow(() -> new RuntimeException("Paralelo no encontrado con código: " + coppar));
        paralelo.setEstado("1");
        paraleloRepository.save(paralelo);
    }

    public void deshabilitarParalelo(Integer coppar) {
        ParaleloEntity paralelo = paraleloRepository.findById(coppar)
                .orElseThrow(() -> new RuntimeException("Paralelo no encontrado con código: " + coppar));
        paralelo.setEstado("0");
        paraleloRepository.save(paralelo);
    }

    @Override
    public List<ParaleloDto> findByEstado(String estado) {
        return paraleloRepository.findByEstado(estado);
    }
}

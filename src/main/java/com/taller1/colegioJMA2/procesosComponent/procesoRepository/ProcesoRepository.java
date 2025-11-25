package com.taller1.colegioJMA2.procesosComponent.procesoRepository;


import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcesoRepository extends JpaRepository<ProcesoEntity, Integer> {

}
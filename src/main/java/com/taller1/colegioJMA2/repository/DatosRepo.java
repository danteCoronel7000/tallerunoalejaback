package com.taller1.colegioJMA2.repository;

import com.taller1.colegioJMA2.model.DatosModel;
import com.taller1.colegioJMA2.model.DatosPKModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosRepo extends JpaRepository<DatosModel, DatosPKModel> {
}
package com.taller1.colegioJMA2.controller;

import com.taller1.colegioJMA2.model.DatosModel;
import com.taller1.colegioJMA2.model.DatosPKModel;
import com.taller1.colegioJMA2.repository.DatosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/datos")
public class DatosController {

    @Autowired
    private DatosRepo datosRepo;

    
    @GetMapping
    public List<DatosModel> listarDatos() {
        return datosRepo.findAll();
    }
    
    
    @PostMapping
    public DatosModel guardarDatos(@RequestBody DatosModel datos) {
        return datosRepo.save(datos);
    }
    
    
    @GetMapping("/{codp}/{cedula}")
    public ResponseEntity<DatosModel> obtenerPorCodpYCedula(
            @PathVariable Integer codp, @PathVariable String cedula) {
        
    	DatosPKModel pk = new DatosPKModel(codp, cedula);
        Optional<DatosModel> datos = datosRepo.findById(pk);
        
        return datos.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    
    @DeleteMapping("/{codp}/{cedula}")
    public void eliminarDatos(
            @PathVariable Integer codp, @PathVariable String cedula) {
        
        DatosPKModel pk = new DatosPKModel(codp, cedula);
        datosRepo.deleteById(pk);
    }
}
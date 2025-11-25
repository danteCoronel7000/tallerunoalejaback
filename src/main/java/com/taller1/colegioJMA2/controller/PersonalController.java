package com.taller1.colegioJMA2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.taller1.colegioJMA2.model.PersonalModel;
import com.taller1.colegioJMA2.repository.PersonalRepo;

import java.util.List;

@RestController
@RequestMapping("/api/personal")
public class PersonalController {

    @Autowired
    private PersonalRepo personalRepo;

    //GET 
    @GetMapping
    public List<PersonalModel> ListarPersonal() {
        return personalRepo.findAll();
    }
    /*
    //POST
    @PostMapping
    public void GuardarPersonal(@RequestBody PersonalModel personal) {
        personalRepo.save(personal);
    }
    
    //DELETE 
    @DeleteMapping("/{codp}")
    public void EliminarPersonal(@PathVariable Integer codp) {
        personalRepo.deleteById(codp);
    }
    
    //PUT 
    @PutMapping("/{codp}")
    public void ActuliaPersonal(@RequestBody PersonalModel personal, @PathVariable Integer codp) {
        personal.setCodp(codp);
        personalRepo.save(personal);
    }*/
}
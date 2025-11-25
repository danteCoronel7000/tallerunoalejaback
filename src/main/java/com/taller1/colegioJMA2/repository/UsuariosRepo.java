package com.taller1.colegioJMA2.repository;


import org.springframework.data.jpa.repository.JpaRepository; 

import com.taller1.colegioJMA2.model.UsuariosModel;

import java.util.Optional;

public interface UsuariosRepo extends JpaRepository<UsuariosModel, String> {
    
    // Este método le dice a Spring Data JPA que busque un usuario por su login
    Optional<UsuariosModel> findByLogin(String login);
    
}


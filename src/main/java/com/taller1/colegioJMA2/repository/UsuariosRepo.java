package com.taller1.colegioJMA2.repository;


import com.taller1.colegioJMA2.dto.UsuarioPageDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.taller1.colegioJMA2.model.UsuariosModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuariosRepo extends JpaRepository<UsuariosModel, String> {
    
    // Este método le dice a Spring Data JPA que busque un usuario por su login
    Optional<UsuariosModel> findByLogin(String login);

    @Query(
            value = "SELECT u.* FROM usuarios u " +
                    "JOIN Personal p ON u.codp = p.codp " +
                    "WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))",
            nativeQuery = true)
    List<UsuariosModel> buscarPorNombre(@Param("nombre") String nombre);

}


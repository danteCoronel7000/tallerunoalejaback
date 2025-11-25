package com.taller1.colegioJMA2.rolesComponent.entyties;

import com.taller1.colegioJMA2.model.UsuariosModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codr")
    private Integer codr;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String estado;

    @ManyToMany(mappedBy = "roles")
    private Set<UsuariosModel> usuarios;
}
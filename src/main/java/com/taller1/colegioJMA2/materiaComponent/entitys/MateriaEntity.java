package com.taller1.colegioJMA2.materiaComponent.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "materias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codmat")
    private Integer codmat;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String estado;
}

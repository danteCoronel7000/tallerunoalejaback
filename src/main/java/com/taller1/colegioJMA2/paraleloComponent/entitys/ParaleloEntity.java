package com.taller1.colegioJMA2.paraleloComponent.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paralelos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParaleloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codpar")
    private Integer codpar;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String estado;
}

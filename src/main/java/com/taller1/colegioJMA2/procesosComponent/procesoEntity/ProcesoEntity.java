package com.taller1.colegioJMA2.procesosComponent.procesoEntity;


import com.taller1.colegioJMA2.menuComponent.entitys.MenuEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "procesos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcesoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codp")
    private Integer codp;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String enlace;

    @Column(nullable = true)
    private String ayuda;

    @Column(nullable = false)
    private String estado;

    @ManyToMany
    @JoinTable(
            name = "proceso_menus",
            joinColumns = @JoinColumn(name = "proceso_id", referencedColumnName = "codp"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "codm")
    )
    private Set<MenuEntity> menus;
}

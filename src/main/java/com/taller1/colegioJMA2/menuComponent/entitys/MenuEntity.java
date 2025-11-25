package com.taller1.colegioJMA2.menuComponent.entitys;


import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codm")
    private Integer codm;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String estado;

    @ManyToMany
    @JoinTable(
            name = "menu_roles",
            joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "codm"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "codr")
    )
    private Set<RolEntity> roles;
}
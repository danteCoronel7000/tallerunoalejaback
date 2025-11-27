package com.taller1.colegioJMA2.menuComponent.entitys;


import com.taller1.colegioJMA2.procesosComponent.procesoEntity.ProcesoEntity;
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

    @ManyToMany(mappedBy = "menus")   // inverso
    private Set<RolEntity> roles;

    // Dueño de la relación
    @ManyToMany
    @JoinTable(
            name = "mepro",
            joinColumns = @JoinColumn(name = "codm", referencedColumnName = "codm"),
            inverseJoinColumns = @JoinColumn(name = "codp", referencedColumnName = "codp")
    )
    private Set<ProcesoEntity> procesos;

    // --- equals y hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuEntity)) return false;
        MenuEntity that = (MenuEntity) o;
        return codm != null && codm.equals(that.codm);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}


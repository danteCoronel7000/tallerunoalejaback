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

    // Lado inverso — NO tiene JoinTable
    @ManyToMany(mappedBy = "procesos")
    private Set<MenuEntity> menus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcesoEntity)) return false;
        ProcesoEntity that = (ProcesoEntity) o;
        return codp != null && codp.equals(that.codp);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

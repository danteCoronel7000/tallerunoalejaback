package com.taller1.colegioJMA2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DatosPKModel implements Serializable {

    
    @Column(name = "codp", nullable = false)
    private Integer codp;

   
    @Column(name = "cedula", length = 10, nullable = false, unique = true)
    private String cedula;
}
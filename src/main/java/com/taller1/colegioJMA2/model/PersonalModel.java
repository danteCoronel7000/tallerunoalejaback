package com.taller1.colegioJMA2.model;



import jakarta.persistence.*; 
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@Entity
@Table(name = "Personal")
public class PersonalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//para generar el id de manera consecutiva 1,2,3.....
    @Column(name = "codp")
    private Integer codp;

    @Column(name = "nombre", length = 40, nullable = false)
    private String nombre;

    @Column(name = "ap", length = 40)
    private String ap;

    @Column(name = "am", length = 40)
    private String am;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "fnac", nullable = false)
    private Date fnac;

    @Column(name = "ecivil", length = 1, nullable = false)
    private String ecivil;

    @Column(name = "genero", length = 1, nullable = false)
    private String genero;

    @Column(name = "direc", length = 50)
    private String direc;

    @Column(name = "telf", length = 20)
    private String telf;

    @Column(name = "tipo", length = 1, nullable = false)
    private String tipo;

    @Column(name = "foto", length = 30)
    private String foto;
    
    @OneToOne(mappedBy="personal")
	@JsonBackReference
	private UsuariosModel usuarios;
    
 
    @OneToOne(mappedBy = "personal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("personal") 
    private DatosModel datos;

}
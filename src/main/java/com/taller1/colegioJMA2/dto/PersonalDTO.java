package com.taller1.colegioJMA2.dto;

import java.util.Date;
import lombok.Data;

@Data
public class PersonalDTO {
	private String nombre;
	private String ap;
	private String am;

	private Date fnac;
	private char ecivil;
	private char genero;
	private String direc;
	private String telf;
	private char tipo;
	private String foto;

	private String cedula;
}

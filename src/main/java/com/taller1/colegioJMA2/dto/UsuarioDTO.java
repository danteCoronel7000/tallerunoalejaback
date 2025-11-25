package com.taller1.colegioJMA2.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
	private String login;
	private String password;

	private Integer codr;

	private PersonalDTO datosPersonales;
}

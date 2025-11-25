package com.taller1.colegioJMA2.dto;

import java.util.List;

import com.taller1.colegioJMA2.model.PersonalModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

	private String token;
	private String login;
	private String nombre;
	private List<String> cedula;
	private PersonalModel personal;
	private List<String> roles;
}
package com.taller1.colegioJMA2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.taller1.colegioJMA2.rolesComponent.entyties.RolEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class UsuariosModel {

	@Id
	@Column(name = "login", length = 10)
	String login;

	@Column(name = "estado", nullable = false)
	int estado;

	@Column(name = "password", length = 200, nullable = false)
	String password;

	@OneToOne(cascade = CascadeType.ALL) 
	@JoinColumn(name = "codp")
	@JsonManagedReference
	private PersonalModel personal;

	@ManyToMany (fetch = FetchType.EAGER) 
	@JoinTable(name = "usurol", joinColumns = @JoinColumn(name = "login"), inverseJoinColumns = @JoinColumn(name = "codr"))
	@JsonIgnoreProperties("usuarios")
	List<RolEntity> roles;

	
	public String getNombreCompleto() {
		if (this.personal != null) {
			return this.personal.getNombre() + " " + this.personal.getAp() + " " + this.personal.getAm();
		}
		return this.login; 
	}
	// En tu UsuarioEntity
	public List<String> getRoleNames() {
		if (this.roles != null) {
			return this.roles.stream().map(RolEntity::getNombre).toList();
		}
		return List.of();
	}
}

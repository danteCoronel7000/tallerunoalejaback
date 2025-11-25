package com.taller1.colegioJMA2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "datos")
public class DatosModel {

	@EmbeddedId
	private DatosPKModel id;

	@MapsId("codp") 
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codp", referencedColumnName = "codp", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "datos" })
    private PersonalModel personal;

	@Transient
	public String getCedula() {
		return this.id != null ? this.id.getCedula() : null;
	}
}
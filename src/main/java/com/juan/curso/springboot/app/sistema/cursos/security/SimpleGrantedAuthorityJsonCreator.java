package com.juan.curso.springboot.app.sistema.cursos.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {
	@JsonCreator
	public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role ) {	}
}

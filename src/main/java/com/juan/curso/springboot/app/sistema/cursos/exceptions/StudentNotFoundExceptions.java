package com.juan.curso.springboot.app.sistema.cursos.exceptions;

public class StudentNotFoundExceptions extends RuntimeException{
	public StudentNotFoundExceptions(String message) {
		super(message);
	}
}

package com.juan.curso.springboot.app.sistema.cursos.exceptions;

public class CourseNotFoundExceptions extends RuntimeException{
	public CourseNotFoundExceptions(String message) {
		super(message);
	}
}

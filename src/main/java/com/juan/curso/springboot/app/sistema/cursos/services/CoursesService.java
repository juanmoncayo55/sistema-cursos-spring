package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;

public interface CoursesService {
	List<Courses> findAll();
	Optional<Courses> findById(Long id);
	Courses save(Courses course);
	Optional<Courses> update(Long id, Courses course);
	Optional<Courses> delete(Long id);
	Optional<Courses> addClass(Long idCourse, Long idClass);
}

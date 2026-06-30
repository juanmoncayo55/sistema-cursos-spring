package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;

public interface CoursesService {
	Page<Courses> findAll(Pageable pageable);
	Optional<Courses> findById(Long id);
	Courses save(Courses course);
	List<Courses> saveAll(List<Courses> courses);
	Optional<Courses> update(Long id, Courses course);
	Optional<Courses> delete(Long id);
	Optional<Courses> addClass(Long idCourse, Long idClass);
}

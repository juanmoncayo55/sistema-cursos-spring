package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;

public interface TeacherService {
	Page<Teacher> findAll(Pageable pageable);
	Optional<Teacher> findById(Long id);
	Teacher save(Teacher teacher);
	Optional<Teacher> update(Long id, Teacher teacher);
	Optional<Teacher> delete(Long id);
	Optional<Teacher> assignCourse(Long idTeacher, Long idCourse);
}

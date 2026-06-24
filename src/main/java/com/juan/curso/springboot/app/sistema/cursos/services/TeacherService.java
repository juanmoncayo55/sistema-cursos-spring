package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;

public interface TeacherService {
	List<Teacher> findAll();
	Optional<Teacher> findById(Long id);
	Teacher save(Teacher teacher);
	Optional<Teacher> update(Long id, Teacher teacher);
	Optional<Teacher> delete(Long id);
	Optional<Teacher> assignCourse(Long idTeacher, Long idCourse);
}

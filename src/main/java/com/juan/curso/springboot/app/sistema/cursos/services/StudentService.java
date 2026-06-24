package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import com.juan.curso.springboot.app.sistema.cursos.entities.Student;

public interface StudentService {
	List<Student> findAll();
	Optional<Student> findById(Long id);
	Student save(Student student);
	Optional<Student> update(Long id, Student student);
	Optional<Student> delete(Long id);
	Optional<Student> assignCourse(Long idCourse, Long idStudent);
}

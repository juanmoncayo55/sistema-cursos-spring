package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juan.curso.springboot.app.sistema.cursos.entities.Student;

public interface StudentService {
	Page<Student> findAll(Pageable pageable);
	Optional<Student> findById(Long id);
	Student save(Student student);
	List<Student> saveAll(List<Student> students);
	Optional<Student> update(Long id, Student student);
	Optional<Student> delete(Long id);
	Optional<Student> assignCourse(Long idCourse, Long idStudent);
}

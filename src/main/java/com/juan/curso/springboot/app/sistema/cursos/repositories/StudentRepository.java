package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	Page<Student> findAll(Pageable pageable);
}

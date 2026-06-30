package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	Page<Teacher> findAll(Pageable pageable);
}

package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;

public interface CoursesRepository extends JpaRepository<Courses, Long>{
	Page<Courses> findAll(Pageable pageable);
}

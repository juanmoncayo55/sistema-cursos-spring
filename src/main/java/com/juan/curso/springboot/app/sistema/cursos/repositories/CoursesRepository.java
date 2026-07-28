package com.juan.curso.springboot.app.sistema.cursos.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;

public interface CoursesRepository extends JpaRepository<Courses, Long>{
	Page<Courses> findAll(Pageable pageable);
	
	@Query("select c from Courses c where c.name like %?1%")
	List<Courses> searchByFullname(String search);
	
	@Query("select c from Courses c")
	List<Courses> getAll();
}

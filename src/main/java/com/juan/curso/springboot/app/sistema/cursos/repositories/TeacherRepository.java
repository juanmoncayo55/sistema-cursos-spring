package com.juan.curso.springboot.app.sistema.cursos.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	Page<Teacher> findAll(Pageable pageable);
	@Query("select t from Teacher t where concat(t.name, ' ', t.lastname) like %?1%")
	List<Teacher> searchByFullname(String search);
	
	@Query("select t from Teacher t")
	List<Teacher> getAll();
}

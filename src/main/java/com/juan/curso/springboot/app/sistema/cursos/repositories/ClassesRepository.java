package com.juan.curso.springboot.app.sistema.cursos.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.juan.curso.springboot.app.sistema.cursos.entities.Classes;

public interface ClassesRepository extends JpaRepository<Classes, Long>{
	Page<Classes> findAll(Pageable pageable);
	
	@Query("select c from Classes c where c.name like %?1%")
	List<Classes> searchByName(String search);
}

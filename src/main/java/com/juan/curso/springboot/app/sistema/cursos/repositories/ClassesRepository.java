package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Classes;

public interface ClassesRepository extends CrudRepository<Classes, Long>{
	Page<Classes> findAll(Pageable pageable);
}

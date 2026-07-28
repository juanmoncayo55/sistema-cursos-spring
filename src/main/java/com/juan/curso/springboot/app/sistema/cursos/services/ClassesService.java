package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juan.curso.springboot.app.sistema.cursos.entities.Classes;

public interface ClassesService {
	Page<Classes> findAll(Pageable pageable);
	Optional<Classes> findById(Long id);
	Classes save(Classes classe);
	List<Classes> saveAll(List<Classes> classes);
	Optional<Classes> update(Long id, Classes classe);
	Optional<Classes> delete(Long id);
	List<Classes> searchClasses(String name);
}

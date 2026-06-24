package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import com.juan.curso.springboot.app.sistema.cursos.entities.Classes;

public interface ClassesService {
	List<Classes> findAll();
	Optional<Classes> findById(Long id);
	Classes save(Classes classe);
	Optional<Classes> update(Long id, Classes classe);
	Optional<Classes> delete(Long id);
}

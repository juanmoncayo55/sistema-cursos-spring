package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;

public interface CommentsService {
	List<Comments> findAll();
	Optional<Comments> findById(Long id);
	Comments save(String content, Long idClasse, Long idStudent);
	Optional<Comments> update(Long id, Comments comment);
	Optional<Comments> delete(Long id);
}

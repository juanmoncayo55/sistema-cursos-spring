package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import com.juan.curso.springboot.app.sistema.cursos.dto.CommentCreateDTO;
import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;

public interface CommentsService {
	List<Comments> findAll();
	Optional<Comments> findById(Long id);
	Comments save(CommentCreateDTO comment);
	Optional<Comments> update(Long id, CommentCreateDTO comment);
	Optional<Comments> delete(Long id);
}

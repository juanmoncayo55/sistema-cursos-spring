package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juan.curso.springboot.app.sistema.cursos.dto.CommentCreateDTO;
import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;

public interface CommentsService {
	Page<Comments> findAll(Pageable pageable);
	Optional<Comments> findById(Long id);
	Comments save(CommentCreateDTO comment);
	Optional<Comments> update(Long id, CommentCreateDTO comment);
	Optional<Comments> delete(Long id);
	
	List<Comments> commentsForIdClassIdStudent(Long idClass, Long idStudent);
	List<Comments> commentsForIdClass(Long idClass);
	List<Comments> commentsForIdStudent(Long idStudent);
}

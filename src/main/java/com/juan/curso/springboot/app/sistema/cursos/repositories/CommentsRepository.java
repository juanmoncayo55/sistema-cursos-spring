package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long>{
	Page<Comments> findAll(Pageable pageable);
}

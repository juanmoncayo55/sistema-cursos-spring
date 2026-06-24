package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;

public interface CommentsRepository extends CrudRepository<Comments, Long>{

}

package com.juan.curso.springboot.app.sistema.cursos.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long>{
	Page<Comments> findAll(Pageable pageable);
	
	@Query("select c from Comments c where c.classe.id=?1 and c.student.id=?2")
	List<Comments> commentsForIdClassIdStudent(Long idClass, Long idStudent);
	
	@Query("select c from Comments c where c.classe.id=?1")
	List<Comments> commentsForIdClass(Long idClass);
	
	@Query("select c from Comments c where c.student.id=?1")
	List<Comments> commentsForIdStudent(Long idStudent);
}

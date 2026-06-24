package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan.curso.springboot.app.sistema.cursos.dto.CommentCreateDTO;
import com.juan.curso.springboot.app.sistema.cursos.entities.Classes;
import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;
import com.juan.curso.springboot.app.sistema.cursos.entities.Student;
import com.juan.curso.springboot.app.sistema.cursos.repositories.ClassesRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.CommentsRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.StudentRepository;

@Service
public class CommentsServiceImpl implements CommentsService{
	
	@Autowired
	private CommentsRepository repository;
	
	@Autowired
	private ClassesRepository repositoryClasse;
	
	@Autowired
	private StudentRepository repositoryStudent;

	@Override
	@Transactional(readOnly = true)
	public List<Comments> findAll() {
		return (List<Comments>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Comments> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Comments save(CommentCreateDTO comment) {
		System.out.println(comment.toString());
		Optional<Classes> classeOptional = repositoryClasse.findById(comment.getIdClass());
		Optional<Student> studentOptional = repositoryStudent.findById(comment.getIdStudent());
		
		if(!classeOptional.isPresent()) {
			throw new RuntimeException("La clase con id: "+comment.getIdClass()+", no existe!");
		}
		if(!studentOptional.isPresent()) {
			throw new RuntimeException("El estudiante con id: "+comment.getIdStudent()+", no existe!");
		}
		
		System.out.println( classeOptional.get().toString() );
		System.out.println( studentOptional.get().toString() );
		
		Comments commentNew = new Comments();
		commentNew.setComment(comment.getComment());
		commentNew.setDate(new Date());
		commentNew.setClasse(classeOptional.get());
		commentNew.setStudent(studentOptional.get());
		
		return repository.save(commentNew);
	}

	@Override
	@Transactional
	public Optional<Comments> update(Long id, Comments comment) {
		Optional<Comments> commentOptional = repository.findById(id);
		if(commentOptional.isPresent()){
			Comments commentDB = commentOptional.get();
			commentDB.setComment(comment.getComment());
			commentDB.setDate(new Date());
			Optional.of(repository.save(commentDB));
		}
		return commentOptional;
	}

	@Override
	@Transactional
	public Optional<Comments> delete(Long id) {
		Optional<Comments> commentOptional = repository.findById(id);
		commentOptional.ifPresent(comment -> {
			repository.delete(comment);
		});
		return commentOptional;
	}

}

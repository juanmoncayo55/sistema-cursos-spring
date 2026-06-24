package com.juan.curso.springboot.app.sistema.cursos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.curso.springboot.app.sistema.cursos.dto.CommentCreateDTO;
import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;
import com.juan.curso.springboot.app.sistema.cursos.services.CommentsService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	@Autowired
	private CommentsService commentsService;
	
	@GetMapping
	public List<Comments> getAll(){
		return commentsService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Optional<Comments> commentOptional = commentsService.findById(id); 
		
		if(!commentOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(commentOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody CommentCreateDTO comment) {
		try {
			Comments commentNew = commentsService.save(comment);
			return ResponseEntity.status(HttpStatus.CREATED).body(commentNew);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Comments comment){
		Optional<Comments> optionalComment = commentsService.update(id, comment);
		if(!optionalComment.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(optionalComment.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Comments> commentOptional = commentsService.delete(id);
		if(!commentOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(commentOptional.get());
	}
	
	
}














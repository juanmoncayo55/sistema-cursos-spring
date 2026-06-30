package com.juan.curso.springboot.app.sistema.cursos.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.curso.springboot.app.sistema.cursos.dto.CommentCreateDTO;
import com.juan.curso.springboot.app.sistema.cursos.dto.PageStudentResponse;
import com.juan.curso.springboot.app.sistema.cursos.entities.Comments;
import com.juan.curso.springboot.app.sistema.cursos.services.CommentsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	@Autowired
	private CommentsService commentsService;
	
	@GetMapping
	public ResponseEntity<PageStudentResponse> getAll(
		@RequestParam(defaultValue = "0") int page, 
		@RequestParam(defaultValue = "10") int size
	){
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		Page<Comments> pageComments = commentsService.findAll(pageable);
		PageStudentResponse pageResponse = new PageStudentResponse(pageComments);
		
		return ResponseEntity.ok(pageResponse);
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
	public ResponseEntity<?> save(@Valid @RequestBody CommentCreateDTO comment, BindingResult result) {
		if(result.hasFieldErrors()) {
			return validate(result);
		}
		try {
			Comments commentNew = commentsService.save(comment);
			return ResponseEntity.status(HttpStatus.CREATED).body(commentNew);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody CommentCreateDTO comment, BindingResult result, @PathVariable Long id){
		if(result.hasFieldErrors()) {
			return validate(result);
		}
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
	
	private ResponseEntity<?> validate(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		
		result.getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errors);
	}
}














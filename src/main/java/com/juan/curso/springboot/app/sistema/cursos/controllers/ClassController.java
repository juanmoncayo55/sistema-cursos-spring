package com.juan.curso.springboot.app.sistema.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.juan.curso.springboot.app.sistema.cursos.entities.Classes;
import com.juan.curso.springboot.app.sistema.cursos.services.ClassesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/class")
public class ClassController {
	
	@Autowired
	private ClassesService classService;
	
	@GetMapping
	public List<Classes> findAll(){
		return classService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id){
		Optional<Classes> classOptional = classService.findById(id);
		if(!classOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(classOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Classes classe, BindingResult result){
		if(result.hasFieldErrors()) {
			return validate(result);
		}
		try {
			Classes newClass = classService.save(classe);
			return ResponseEntity.status(HttpStatus.CREATED).body(newClass);
		}catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Classes classe, BindingResult result, @PathVariable Long id){
		if(result.hasFieldErrors()) {
			return validate(result);
		}
		Optional<Classes> optionalClass = classService.update(id, classe);
		
		if(!optionalClass.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(optionalClass.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		//Optional<Classes> delete(Long id)
		Optional<Classes> classOptional = classService.delete(id);
		
		if(!classOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(classOptional.get());
	}
	
	private ResponseEntity<?> validate(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		
		result.getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errors);
	}
}
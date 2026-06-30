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

import com.juan.curso.springboot.app.sistema.cursos.dto.PageStudentResponse;
import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;
import com.juan.curso.springboot.app.sistema.cursos.services.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping
	public ResponseEntity<PageStudentResponse> getAll(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	){
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		Page<Teacher> pageTacher = teacherService.findAll(pageable);
		PageStudentResponse pageResponse = new PageStudentResponse(pageTacher);
		
		return ResponseEntity.ok(pageResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id){
		Optional<Teacher> teacherOptional = teacherService.findById(id);
		
		if(!teacherOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(teacherOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Teacher teacher, BindingResult result) {
		if(result.hasFieldErrors()) {
			return validate(result);
		}
		try {
			Teacher newTeacher = teacherService.save(teacher);
			return ResponseEntity.status(HttpStatus.CREATED).body(newTeacher);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Teacher teacher, @PathVariable Long id, BindingResult result){	
		if(result.hasFieldErrors()) {
			return validate(result);
		}
		Optional<Teacher> teacherOptional = teacherService.update(id, teacher);
		
		if(!teacherOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();			
		}
			
		return ResponseEntity.status(HttpStatus.OK).body(teacherOptional.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		//Optional<Teacher> delete(Long id);
		
		Optional<Teacher> teacherOptional = teacherService.delete(id);
		
		if(!teacherOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(teacherOptional.get());
	}
	
	//Optional<Teacher> assignCourse(Long idTeacher, Long idCourse);
	
	@PostMapping("/{idTeacher}/course/{idCourse}")
	public ResponseEntity<?> assignCourse(@PathVariable Long idTeacher, @PathVariable Long idCourse){
		Optional<Teacher> teacherOptional = teacherService.assignCourse(idTeacher, idCourse);
		
		if(!teacherOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(teacherOptional.get());
	}
	private ResponseEntity<?> validate(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		
		result.getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errors);
	}
}

















package com.juan.curso.springboot.app.sistema.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.curso.springboot.app.sistema.cursos.entities.Student;
import com.juan.curso.springboot.app.sistema.cursos.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;

	@GetMapping
	public List<Student> getAll(){
		return studentService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Optional<Student> student = studentService.findById(id);
		
		if(!student.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(student.get());
	}
	
	@PostMapping
	public ResponseEntity<?> saveStudent(@Valid @RequestBody Student student, BindingResult result){
		if(result.hasFieldErrors()) {
			return validation(result);
		}
		
		try {
			Student studentSave = studentService.save(student);
			return ResponseEntity.status(HttpStatus.CREATED).body(studentSave);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudent(@Valid @RequestBody Student student, BindingResult result, @PathVariable Long id){
		if(result.hasFieldErrors()) {
			return validation(result);
		}
		
		Optional<Student> studentOptional = studentService.update(id, student);
		
		if(!studentOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
			
		return ResponseEntity.status(HttpStatus.OK).body(studentOptional.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id){
		
		Optional<Student> studentOptional = studentService.delete(id);
		
		if(!studentOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/{studentId}/course/{courseId}")
	public ResponseEntity<?> assignCourse(@PathVariable Long studentId, @PathVariable Long courseId){
		
		Optional<Student> studentCourse = studentService.assignCourse(courseId, studentId);
		
		if(!studentCourse.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(studentCourse.get());
	}
	
	private ResponseEntity<?> validation(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		
		result.getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errors);
	}
}













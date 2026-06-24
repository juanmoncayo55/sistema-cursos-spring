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

import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;
import com.juan.curso.springboot.app.sistema.cursos.services.TeacherService;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping
	public List<Teacher> getAll(){
		return teacherService.findAll();
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
	public ResponseEntity<?> save(@RequestBody Teacher teacher) {
		try {
			Teacher newTeacher = teacherService.save(teacher);
			return ResponseEntity.status(HttpStatus.CREATED).body(newTeacher);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Teacher teacher){		
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
			ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
	
}

















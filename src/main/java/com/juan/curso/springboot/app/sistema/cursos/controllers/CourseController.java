package com.juan.curso.springboot.app.sistema.cursos.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;
import com.juan.curso.springboot.app.sistema.cursos.services.CoursesService;

@RestController
@RequestMapping("/api/course")
public class CourseController {
	
	@Autowired
	private CoursesService courseService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll());		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(courseService.findById(id));		
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Courses course){
		try {
			Courses courseDB = courseService.save(course);
			return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Courses course){
		
		Optional<Courses> courseUpdate = courseService.update(id, course);
		
		if(!courseUpdate.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(courseUpdate.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable Long id){
		Optional<Courses> courseOptional = courseService.delete(id);
		
		if(!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.noContent().build();		
	}
	
	
}





















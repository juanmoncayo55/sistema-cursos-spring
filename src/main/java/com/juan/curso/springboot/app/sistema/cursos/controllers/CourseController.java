package com.juan.curso.springboot.app.sistema.cursos.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;
import com.juan.curso.springboot.app.sistema.cursos.services.CoursesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/course")
public class CourseController {
	
	@Autowired
	private CoursesService courseService;
	
	
	
	@GetMapping
	public ResponseEntity<PageStudentResponse> getAll(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		
		Page<Courses> coursesPage = courseService.findAll(pageable);
		PageStudentResponse pageCourse = new PageStudentResponse(coursesPage);
		
		return ResponseEntity.status(HttpStatus.OK).body(pageCourse);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllCourses(){
		List<Courses> findAll = courseService.getAllCourses();
		
		return ResponseEntity.status(HttpStatus.OK).body(findAll);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Optional<Courses> courseOptional = courseService.findById(id);
		if(!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(courseOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Courses course, BindingResult result){
		if(result.hasFieldErrors()) {
			return validate(result);
		}
		try {
			course.setId(null);
			Courses courseDB = courseService.save(course);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@PostMapping("/all")
	public ResponseEntity<?> createAll(@RequestBody ArrayList<Courses> courses){
		List<Courses> coursesResponse = courseService.saveAll(courses);
		return ResponseEntity.status(HttpStatus.CREATED).body(coursesResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCourse(@Valid @RequestBody Courses course, BindingResult result, @PathVariable Long id){
		if(result.hasFieldErrors()) {
			return validate(result);
		}
		
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
	
	//Optional<Courses> addClass(Long idClass, Long idCourse);
	@PutMapping("/{idCourse}/class/{idClass}")
	public ResponseEntity<?> addClass(@PathVariable Long idCourse, @PathVariable Long idClass){
		
		Optional<Courses> courseOptional = courseService.addClass(idCourse, idClass);
		
		if(!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(courseOptional.get());
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<?> searchCourse(@PathVariable String name){
		List<Courses> courses = courseService.searchByFullname(name);
		
		return ResponseEntity.status(HttpStatus.OK).body(courses);
	}
	
	private ResponseEntity<?> validate(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		
		result.getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errors);
	}
}





















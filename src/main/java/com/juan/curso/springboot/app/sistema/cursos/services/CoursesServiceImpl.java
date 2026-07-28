package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan.curso.springboot.app.sistema.cursos.entities.Classes;
import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;
import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;
import com.juan.curso.springboot.app.sistema.cursos.exceptions.TeacherNotFoundExceptions;
import com.juan.curso.springboot.app.sistema.cursos.repositories.ClassesRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.CoursesRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.TeacherRepository;

@Service
public class CoursesServiceImpl implements CoursesService{
	
	@Autowired
	private CoursesRepository repository;
	
	@Autowired
	private ClassesRepository repositoryClass;
	
	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<Courses> findAll(Pageable pageable) {
		return (Page<Courses>) repository.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Courses> getAllCourses() {
		return repository.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Courses> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Courses save(Courses course) {
		
		return repository.save(course);
	}

	@Override
	@Transactional
	public Optional<Courses> update(Long id, Courses course) {
		Optional<Courses> courseOptional = repository.findById(id);
		

		if(courseOptional.isPresent()) {
			
			Teacher teacher = teacherRepository.findById(course.getTeacher().getId()).orElseThrow(() -> new TeacherNotFoundExceptions("El Profesor con No existe"));
			
			Courses courseDB = courseOptional.orElseThrow();
			courseDB.setName( course.getName() );
			courseDB.setCategory( course.getCategory() );
			courseDB.setTimeHour( course.getTimeHour() );
			courseDB.setTeacher(teacher);
			
			repository.save(courseDB);
			
			return Optional.of(courseDB);
		}
		return courseOptional;
	}

	@Override
	@Transactional
	public Optional<Courses> delete(Long id) {
		Optional<Courses> courseOptional = repository.findById(id);
		courseOptional.ifPresent(course -> {
			repository.delete(course);
		});
		return courseOptional;
	}

	@Override
	@Transactional
	public Optional<Courses> addClass(Long idCourse, Long idClass) {
		
		Optional<Classes> classExist = repositoryClass.findById(idClass);
		Optional<Courses> courseExist = repository.findById(idCourse);
		
		if(!classExist.isPresent()) {
			throw new RuntimeException("La clase con id: "+idClass+", No existe!");
		}
		if(!courseExist.isPresent()) {
			throw new RuntimeException("El curso con id: "+idCourse+", No existe!");
		}
		
		Courses courseDB = courseExist.get();
		Classes classeDB = classExist.get();
		
		courseDB.addClass(classeDB);
		Courses saveC = repository.save(courseDB);
		
		return Optional.of(saveC);
	}

	@Override
	@Transactional
	public List<Courses> saveAll(List<Courses> courses) {
		return (List<Courses>) repository.saveAll(courses);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Courses> searchByFullname(String fullname) {
		return repository.searchByFullname(fullname);
	}

	

}

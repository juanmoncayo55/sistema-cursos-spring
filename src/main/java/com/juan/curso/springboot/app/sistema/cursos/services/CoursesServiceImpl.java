package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan.curso.springboot.app.sistema.cursos.entities.Classes;
import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;
import com.juan.curso.springboot.app.sistema.cursos.repositories.ClassesRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.CoursesRepository;

@Service
public class CoursesServiceImpl implements CoursesService{
	
	@Autowired
	private CoursesRepository repository;
	
	@Autowired
	private ClassesRepository repositoryClass;

	@Override
	public List<Courses> findAll() {
		return (List<Courses>) repository.findAll();
	}

	@Override
	public Optional<Courses> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Courses save(Courses course) {
		return repository.save(course);
	}

	@Override
	public Optional<Courses> update(Long id, Courses course) {
		Optional<Courses> courseOptional = repository.findById(id);
		
		if(courseOptional.isPresent()) {
			Courses courseDB = courseOptional.orElseThrow();
			courseDB.setName( course.getName() );
			courseDB.setCategory( course.getCategory() );
			courseDB.setTimeHour( course.getTimeHour() );
			
			repository.save(courseDB);
			
			return Optional.of(courseDB);
		}
		return courseOptional;
	}

	@Override
	public Optional<Courses> delete(Long id) {
		Optional<Courses> courseOptional = repository.findById(id);
		courseOptional.ifPresent(course -> {
			repository.delete(course);
		});
		return courseOptional;
	}

	@Override
	@Transactional
	public Optional<Courses> addClass(Long idClass, Long idCourse) {
		
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

}

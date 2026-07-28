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
import com.juan.curso.springboot.app.sistema.cursos.repositories.ClassesRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.CoursesRepository;

@Service
public class ClassesServiceImpl implements ClassesService{
	
	@Autowired
	private ClassesRepository repository;
	
	@Autowired
	private CoursesRepository courseRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<Classes> findAll(Pageable pageable) {
		return (Page<Classes>) repository.findAll(pageable);
	}
	

	@Override
	@Transactional(readOnly = true)
	public Optional<Classes> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Classes save(Classes classe) {
		System.out.println("DEsde el service");
		System.out.println(classe.toString());
		
		Courses course = courseRepository.getById(classe.getCourse().getId());
		classe.setId(null);
		classe.setCourse(course);
		return repository.save(classe);
	}
	
	@Override
	@Transactional
	public List<Classes> saveAll(List<Classes> classes) {
		return (List<Classes>) repository.saveAll(classes);
	}

	@Override
	@Transactional
	public Optional<Classes> update(Long id, Classes classe) {
		Optional<Classes> classExist = repository.findById(id);
		
		if(classExist.isPresent()) {
			Classes classDB = classExist.orElseThrow();
			classDB.setName(classe.getName());
			classDB.setMode(classe.getMode());
			classDB.setMaxCapacity(classe.getMaxCapacity());
			classDB.setStatus(classe.getStatus());
			repository.save(classDB);
			
			return Optional.of( classDB );
		}
		
		return classExist;
	}

	@Override
	@Transactional
	public Optional<Classes> delete(Long id) {
		Optional<Classes> classOptional = repository.findById(id);
		classOptional.ifPresent(classe -> {
			repository.delete(classe);
		});
		return classOptional;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Classes> searchClasses(String name) {
		return repository.searchByName(name);
	}
}

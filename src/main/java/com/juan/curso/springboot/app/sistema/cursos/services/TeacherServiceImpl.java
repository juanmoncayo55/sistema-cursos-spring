package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;
import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;
import com.juan.curso.springboot.app.sistema.cursos.repositories.CoursesRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	private TeacherRepository repository;
	
	@Autowired
	private CoursesRepository repositoryCourse;

	@Override
	@Transactional(readOnly = true)
	public Page<Teacher> findAll(Pageable pageable) {
		return (Page<Teacher>) repository.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getAll() {
		return repository.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Teacher> findById(Long id) {
		return repository.findById(id);	
	}

	@Override
	@Transactional
	public Teacher save(Teacher teacher) {
		return repository.save(teacher);
	}

	@Override
	@Transactional
	public Optional<Teacher> update(Long id, Teacher teacher) {
		Optional<Teacher> teacherOptional = repository.findById(id);
		if(teacherOptional.isPresent()) {
			Teacher teacherDB = teacherOptional.get();
			teacherDB.setName(teacher.getName());
			teacherDB.setLastname(teacher.getLastname());
			teacherDB.setEmail(teacher.getEmail());
			return Optional.of(repository.save(teacherDB));
		}
		return teacherOptional;
	}

	@Override
	@Transactional
	public Optional<Teacher> delete(Long id) {
		Optional<Teacher> teacherOptional = repository.findById(id);
		teacherOptional.ifPresent(teacher -> {
			repository.delete(teacher);
		});
		return Optional.of(teacherOptional.get());
	}

	@Override
	@Transactional
	public Optional<Teacher> assignCourse(Long idTeacher, Long idCourse) {
		Optional<Teacher> teacherExist = repository.findById(idTeacher);
		Optional<Courses> courseExist = repositoryCourse.findById(idCourse);
		
		if(!teacherExist.isPresent()) {
			throw new RuntimeException("El profesor con id: "+idTeacher+", no existe!");
		}
		if(!courseExist.isPresent()) {
			throw new RuntimeException("El curso con id: "+idCourse+", no existe!");
		}
		
		Teacher teacherDB = teacherExist.get();
		
		teacherDB.addCourse(courseExist.get());
		repositoryCourse.save(courseExist.get());
		return Optional.of( teacherDB );
	}

	@Override
	@Transactional(readOnly = true)
	public List<Teacher> searchByFullname(String fullname) {
		// TODO Auto-generated method stub
		return repository.searchByFullname(fullname);
	}
}

















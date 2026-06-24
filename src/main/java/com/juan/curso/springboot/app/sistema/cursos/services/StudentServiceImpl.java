package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan.curso.springboot.app.sistema.cursos.entities.CardId;
import com.juan.curso.springboot.app.sistema.cursos.entities.Courses;
import com.juan.curso.springboot.app.sistema.cursos.entities.Student;
import com.juan.curso.springboot.app.sistema.cursos.repositories.CoursesRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository repository;
	
	@Autowired
	private CoursesRepository repositoryCourse;

	@Override
	@Transactional(readOnly = true)
	public List<Student> findAll() {
		return (List<Student>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Student> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Student save(Student student) {
		CardId cardId = new CardId();
		cardId.setDateIssue(new Date());
		student.setCardId(cardId);
		cardId.setStudent(student);
		
		return repository.save(student);
	}

	@Override
	@Transactional
	public Optional<Student> update(Long id, Student student) {
		Optional<Student> studentExists = repository.findById(id);
		if(studentExists.isPresent()) {
			Student studentDB = studentExists.orElseThrow();
			studentDB.setName(student.getName());
			studentDB.setLastname(student.getLastname());
			studentDB.setEmail(student.getEmail());
			studentDB.setDni(student.getDni());
			return Optional.of(repository.save(studentDB));
		}
		return studentExists;
	}

	@Override
	@Transactional
	public Optional<Student> delete(Long id) {
		Optional<Student> studentExists = repository.findById(id);
		studentExists.ifPresent(student -> {
			repository.delete(student);
		});
		return studentExists;
	}

	@Override
	@Transactional
	public Optional<Student> assignCourse(Long idCourse, Long idStudent) {
		Optional<Student> studentExist = repository.findById(idStudent);
		Optional<Courses> courseExist = repositoryCourse.findById(idCourse);
		
		if(!studentExist.isPresent()) {
			throw new RuntimeException("El estudiante con id: "+ idStudent +", no existe");
		}
		if(!courseExist.isPresent()) {
			throw new RuntimeException("El curso con id: "+ idCourse +", no existe");
		}
		
		Student studentNew = studentExist.get();
		//studentNew.getCourses().add(courseExist.get());
		studentNew.addCourse(courseExist.get());
		
		return Optional.of(repository.save(studentNew));
		
	}

}

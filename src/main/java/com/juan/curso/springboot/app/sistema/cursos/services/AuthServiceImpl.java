package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan.curso.springboot.app.sistema.cursos.dto.RegisterStudentDTO;
import com.juan.curso.springboot.app.sistema.cursos.entities.CardId;
import com.juan.curso.springboot.app.sistema.cursos.entities.Student;
import com.juan.curso.springboot.app.sistema.cursos.entities.User;
import com.juan.curso.springboot.app.sistema.cursos.repositories.StudentRepository;
import com.juan.curso.springboot.app.sistema.cursos.repositories.UserRepository;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public Optional<Student> registerStudent(RegisterStudentDTO studentRegister) {
		Student student = new Student();
		User newUser = new User();
		
		student.setName(studentRegister.getName());
		student.setLastname(studentRegister.getLastname());
		student.setEmail(studentRegister.getEmail());
		student.setDni(studentRegister.getDni());
		
		CardId cardId = new CardId();
		cardId.setDateIssue(new Date());
		student.setCardId(cardId);
		cardId.setStudent(student);
		
		Student studentDB = studentRepository.save(student);
		
		newUser.setEmail(studentRegister.getEmail());
		newUser.setPassword(studentRegister.getPassword());
		newUser.setRole("STUDENT");
		newUser.setStudent(studentDB);
		
		userRepository.save(newUser);
		
		return Optional.of(studentDB);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> login(String email, String password) {
		
		Optional<User> userExists = userRepository.findByEmail(email);
		
		if(userExists.isPresent()) {
			User user = userExists.get();
			if(user.getPassword().equals(password)) {
				return userExists;
			}
		}
		
		return Optional.empty();
	}

}







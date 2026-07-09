package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.Optional;

import com.juan.curso.springboot.app.sistema.cursos.dto.RegisterStudentDTO;
import com.juan.curso.springboot.app.sistema.cursos.entities.Student;
import com.juan.curso.springboot.app.sistema.cursos.entities.User;

public interface AuthService {
	Optional<Student> registerStudent(RegisterStudentDTO student);
	Optional<User> existsByEmail(String email);
}

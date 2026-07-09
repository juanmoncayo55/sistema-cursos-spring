package com.juan.curso.springboot.app.sistema.cursos.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.curso.springboot.app.sistema.cursos.dto.LoginDTO;
import com.juan.curso.springboot.app.sistema.cursos.dto.RegisterStudentDTO;
import com.juan.curso.springboot.app.sistema.cursos.entities.Student;
import com.juan.curso.springboot.app.sistema.cursos.entities.User;
import com.juan.curso.springboot.app.sistema.cursos.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/register/student")
	public ResponseEntity<?> registerStudent(@RequestBody RegisterStudentDTO student){
		try {
			Student newStudent = authService.registerStudent(student).orElseThrow(
				() -> new RuntimeException("Error al crear el Estudiante")
			);

			return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
		}	
	}
	
	/*@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO login){
		
		try {
			User user = authService.login(login.getEmail(), login.getPassword()).orElseThrow(
				() -> new RuntimeException("Error en las credenciales")
			);
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}catch(RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales Incorrectas");	
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
		}
	}*/
}





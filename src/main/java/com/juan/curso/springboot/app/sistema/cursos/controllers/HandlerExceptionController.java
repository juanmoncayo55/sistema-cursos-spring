package com.juan.curso.springboot.app.sistema.cursos.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.juan.curso.springboot.app.sistema.cursos.dto.ErrorDTO;
import com.juan.curso.springboot.app.sistema.cursos.exceptions.ClassesNotFoundExceptions;
import com.juan.curso.springboot.app.sistema.cursos.exceptions.CourseNotFoundExceptions;
import com.juan.curso.springboot.app.sistema.cursos.exceptions.StudentNotFoundExceptions;
import com.juan.curso.springboot.app.sistema.cursos.exceptions.TeacherNotFoundExceptions;

@RestControllerAdvice
public class HandlerExceptionController {
	//IllegalArgumentException
	//HttpMessageNotReadableException
	//IllegalStateException
	//HttpRequestMethodNotSupportedException
	@ExceptionHandler(NullPointerException.class)
	public Map<String, Object> nullPointerExceptionError(Exception ex){
		Map<String, Object> error = new HashMap<>();
		error.put("date", new Date());
        error.put("message", ex.getMessage());
        error.put("error", "Error interno del servidor.");
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		return error;
	}
	
	@ExceptionHandler(HttpMessageNotWritableException.class)
	public Map<String, Object> objectNotTypeExceptionError(Exception ex){
		Map<String, Object> error = new HashMap<>();
		error.put("date", new Date());
        error.put("message", ex.getMessage());
        error.put("error", "Objeto JSON mal estructurado.");
        error.put("status", HttpStatus.BAD_REQUEST.value());
		
		return error;
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDTO> notArgumentValid(Exception ex){
		ErrorDTO error = new ErrorDTO();
		error.setDate(new Date());
		error.setMessage(ex.getMessage());
		error.setError("Argumento no válido");
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDTO> invalidGetEndpoint(Exception ex){
		ErrorDTO error = new ErrorDTO();
		error.setDate(new Date());
		error.setMessage(ex.getMessage());
		error.setError("La URL no es válida");
		error.setStatus(HttpStatus.NOT_FOUND.value());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
	}
	
	@ExceptionHandler(StudentNotFoundExceptions.class)
	public Map<String, Object> studentNotFound(Exception ex){
		Map<String, Object> error = new HashMap<>();
		error.put("date", new Date());
		error.put("message", ex.getMessage());
		error.put("error", "El Estudiante no existe");
		error.put("status", HttpStatus.NOT_FOUND.value());
		return error;
	}
	
	@ExceptionHandler(CourseNotFoundExceptions.class)
	public Map<String, Object> courseNotFound(Exception ex){
		Map<String, Object> error = new HashMap<>();
		error.put("date", new Date());
		error.put("message", ex.getMessage());
		error.put("error", "El Curso no existe");
		error.put("status", HttpStatus.NOT_FOUND.value());
		return error;
	}
	
	@ExceptionHandler(TeacherNotFoundExceptions.class)
	public Map<String, Object> teacherNotFound(Exception ex){
		Map<String, Object> error = new HashMap<>();
		error.put("date", new Date());
		error.put("message", ex.getMessage());
		error.put("error", "El Profesor no existe");
		error.put("status", HttpStatus.NOT_FOUND.value());
		return error;
	}
	
	@ExceptionHandler(ClassesNotFoundExceptions.class)
	public Map<String, Object> classesNotFound(Exception ex){
		Map<String, Object> error = new HashMap<>();
		error.put("date", new Date());
		error.put("message", ex.getMessage());
		error.put("error", "La clase no existe");
		error.put("status", HttpStatus.NOT_FOUND.value());
		return error;
	}
}

package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

}

package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long>{

}

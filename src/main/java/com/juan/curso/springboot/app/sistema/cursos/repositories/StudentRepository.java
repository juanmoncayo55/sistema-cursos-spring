package com.juan.curso.springboot.app.sistema.cursos.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.juan.curso.springboot.app.sistema.cursos.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	Page<Student> findAll(Pageable pageable);
	@Query("select s from Student s where concat(s.name, ' ', s.lastname) like %?1%")
	List<Student> searchByFullname(String search);
}

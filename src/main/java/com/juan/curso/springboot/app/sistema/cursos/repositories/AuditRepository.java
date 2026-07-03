package com.juan.curso.springboot.app.sistema.cursos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.Audit;

public interface AuditRepository extends JpaRepository<Audit, Long>{

}

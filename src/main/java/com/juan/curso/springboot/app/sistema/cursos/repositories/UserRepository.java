package com.juan.curso.springboot.app.sistema.cursos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juan.curso.springboot.app.sistema.cursos.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}

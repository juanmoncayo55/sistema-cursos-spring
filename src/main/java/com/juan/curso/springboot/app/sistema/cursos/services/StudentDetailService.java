package com.juan.curso.springboot.app.sistema.cursos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan.curso.springboot.app.sistema.cursos.entities.User;
import com.juan.curso.springboot.app.sistema.cursos.repositories.UserRepository;

@Service
public class StudentDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> studentOptional = userRepository.findByEmail(email);
		if(studentOptional.isEmpty()) {
			throw new UsernameNotFoundException(
				String.format("El estudiante con el email: %s No existe", email)
			);
		}
		User student = studentOptional.orElseThrow();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + student.getRole()));		
		
		return new org.springframework.security.core.userdetails.User(
			student.getEmail(),
			student.getPassword(),
			true,
			true,
			true,
			true,
			authorities
		);
	}

}

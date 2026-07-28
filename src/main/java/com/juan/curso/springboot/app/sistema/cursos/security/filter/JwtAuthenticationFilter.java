package com.juan.curso.springboot.app.sistema.cursos.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.juan.curso.springboot.app.sistema.cursos.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;

import static com.juan.curso.springboot.app.sistema.cursos.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl("/api/auth/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		User user = null;
		String email = null;
		String password = null;
		
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);
			email = user.getEmail();
			password = user.getPassword();
			
			System.out.println("El email es: " + email);
			System.out.println("El password es: " + password);
			
		} catch(StreamReadException e) {
			e.printStackTrace();
		} catch(DatabindException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		
		return this.authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
		
		String username = user.getUsername();
		List<String> roles = authResult.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.filter(role -> !role.startsWith("FACTOR_"))
			.collect(Collectors.toList());
		
		Claims claims = Jwts.claims().add("authorities", roles).build();
		
		String token = Jwts.builder()
			.subject(username)
			.claims(claims)
			.expiration(new Date(System.currentTimeMillis() + 3600000))
			.issuedAt(new Date())
			.signWith(SECRET_KEY)
			.compact();
		
		response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
		
		Map<String, String> body = new HashMap<>();
		body.put("token", token);
		body.put("email", username);
		body.put("message", String.format("Hola %s has iniciado sesion correctamente, Bienvenido!", username));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setContentType(CONTENT_TYPE);
		response.setStatus(200);


	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		Map<String, String> body = new HashMap<>();
		body.put("message", "Error en la autenticacion username o password incorrectos!");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType(CONTENT_TYPE);
		
	}

}
